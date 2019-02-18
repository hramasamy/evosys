/*
  Copyright (C) 2003-2009 Kevin Thornton, krthornt[]@[]uci.edu

  Remove the brackets to email me.

  This file is part of libsequence.

  libsequence is free software: you can redistribute it and/or modify
  it under the terms of the GNU General Public License as published by
  the Free Software Foundation, either version 3 of the License, or
  (at your option) any later version.

  libsequence is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  GNU General Public License for more details.

  You should have received a copy of the GNU General Public License
  long with libsequence.  If not, see <http://www.gnu.org/licenses/>.
*/

#include <cmath>
#include <cfloat>
#include <algorithm>
#include <cctype>
#include <Sequence/PolyTable.hpp>
#include <Sequence/SimData.hpp>
#include <Sequence/Recombination.hpp>
#include <Sequence/stateCounter.hpp>
#include <Sequence/SeqProperties.hpp>

using std::pow;
using std::vector;

namespace Sequence
{
  namespace Recombination
  {

    /*!
      The degree of numeric precision desired in calculating \f$\rho_{87}\f$
      \c FLT_EPSILON from cfloat is used, which has a precision of
      about 10^-7 on my machine.  I tried \c DBL_EPSILON, but the numerical routines
      never seemed to finish...
    */
    const double PRECISION = FLT_EPSILON;
    /*!
      The max value that can be taken by \f$\rho_{87}\f$
    */
    const double CMAX = 10000;

    namespace
    {
      //These functions are implementation details of Hudson's C, and
      //are in an unnamed namespace to restrict their scope to this file,
      //which is the C++ equivalent of a function declared with storage
      //class static
      double CalcSamplingVariance (const Sequence::PolyTable * data,
                                   const bool haveOutgroup,
                                   const int outrgoup,
                                   int totsam, int ss);
      void GetPairDiffs (const Sequence::PolyTable * data,
                         const bool haveOutgroup,
                         const int outgroup,
                         const int totsam, vector<int> &list,
                         const int ss);
      void get_h_vals (const int ss, const int totsam,
                       const Sequence::PolyTable * data, const bool haveOutgroup,
                       const int outgroup, double *sumhi, double *sumhisq);
      double chatsub (const int totsam, const double sksq,
                      const double sumhi, const double sumhisq);
      double solveit (const int nsam, const double stat);
      double g (const int nsam, const double c);

      double
      CalcSamplingVariance (const Sequence::PolyTable *data,
                            const bool haveOutgroup,
                            const int outgroup, int totsam, int ss)
      /*!
        Calculate the sampling variance of the segregating sites. Called by Recombination::HudsonsC
        @author Dick Hudson
      */
      {
        int i;
        double Svar = 0.0, meandiffs = 0.0;
        unsigned __meandiffs=0;
        double nsam = double (totsam);
        vector < int >pairdiffs;
        GetPairDiffs (data, haveOutgroup, outgroup, totsam, pairdiffs,
                      ss);
        //increment __meandiffs to avoid loss of precision when incrementing doubles
        for (i = 0; unsigned(i) < pairdiffs.size (); ++i)
          __meandiffs += pairdiffs[i];
        meandiffs = double(__meandiffs)/pow (nsam, 2.0);
        for (i = 0; unsigned(i) < pairdiffs.size (); ++i)
          Svar += pow ((double(pairdiffs[i]) - meandiffs), 2);
        Svar /= pow (nsam, 2.);
        return (Svar);
      }

      void
      GetPairDiffs (const Sequence::PolyTable *data, const bool haveOutgroup,
                    const int outgroup, const int totsam,
                    vector < int > &list, const int ss)
      /*!
        Fill &list with the number of pairwise differences between sequences in the data.
        Called by Recombination::CalcSamplingVariance
        @author Dick Hudson
      */
      {
        unsigned i,j,k;
        for (i = 0; int(i) < totsam; ++i)
          {
            for (j = 0; int(j) < totsam ; ++j)
              {
                if ((!(haveOutgroup))
                    || (haveOutgroup && i != unsigned(outgroup)
                        && j != unsigned(outgroup)))
                  {
                    if(i!=j)
                      {
                        int ndiffs=0;
                        for (k = 0; k < unsigned(ss); ++k)
                          {
                            //comparison to 'N' has the effect of skipping missing data
			    char ch1 = char(std::toupper((*data)[i][k])), ch2 = char(std::toupper((*data)[j][k]));
                            if( ch2 !='N' && ch2 != 'N' )
                              if ( ch1 != ch2 )
                                ++ndiffs;
                          }
                        list.push_back (ndiffs);
                      }
                    else
                      list.push_back(0);
                  }
              }
          }
      }


      void
      get_h_vals (const int ss, const int totsam,
                  const Sequence::PolyTable *data,
                  const bool haveOutgroup, const int outgroup,
                  double *sumhi, double *sumhisq)
      /*!
        Calculate \f$\widehat\theta_\pi\f$ and \f$\widehat\theta_\pi^2\f$ from the data
        Called by Recombination::HudsonsC
        @author Dick Hudson
      */
      {
        *sumhi = 0.;
        *sumhisq = 0.;

        if (ss == 0)
          {
            //don't need to change anything
            // *sumhi = 0.;
            // *sumhisq = 0.;
          }
        else
          {
            for (int i = 0; i < ss; ++i)
              {
                stateCounter Counts;
                //samplesize is counted per site, and is adjusted if missing data is encountere
                unsigned samplesize = totsam;
                for (unsigned j = 0; j < data->size (); ++j)
                  {
                    if ((!(haveOutgroup))
                        || (haveOutgroup && j != unsigned(outgroup)))
                      {
                        samplesize -= (std::toupper((*data)[j][i]) == 'N') ? 1 : 0;
                        Counts ((*data)[j][i]);
                      }
                  }

                //a copy-paste from ThetaPi() from PolySNP.cc
                double SSH = 0.;
                double denom = (double(samplesize)* (double(samplesize) - 1.0));
                SSH += (Counts.a > 0) ? double(Counts.a) * double (Counts.a-1) /denom : 0. ;
                SSH += (Counts.g > 0) ? double(Counts.g) * double (Counts.g-1) /denom : 0. ;
                SSH += (Counts.c > 0) ? double(Counts.c) * double (Counts.c-1) /denom : 0. ;
                SSH += (Counts.t > 0) ? double(Counts.t) * double (Counts.t-1) /denom : 0. ;
                SSH += (Counts.zero > 0) ? double(Counts.zero) * double (Counts.zero-1) /denom : 0. ;
                SSH += (Counts.one > 0) ? double(Counts.one) * double (Counts.one-1) /denom : 0. ;
                *sumhi += (1.0 - SSH);
                *sumhisq += pow(1.0-SSH,2.0);
              }
          }
      }


      double
      chatsub (const int totsam, const double sksq,
               const double sumhi, const double sumhisq)
      /*!
        Estimate Hudson's C.
        @author Dick Hudson.
      */
      {
        double stat, thetahat, estimate;
        thetahat = sumhi;
        stat = (sksq - sumhi + sumhisq) / (thetahat * thetahat);
        estimate = solveit (totsam, stat);
        return (estimate);
      }


      double
      solveit (const int totsam, const double stat)
      /*!
        A routine to handle numerically solving g(C,n).
        Called by Recombination::chatsub
        @author Dick Hudson.
      */
      {
        double xbig, xsmall, xtemp;

        xbig = 10.;
        xsmall = PRECISION;

        if(fabs(g(totsam,xsmall))-stat<=DBL_EPSILON)
          return (xsmall);

        while (fabs(g (totsam, xbig))-stat >= DBL_EPSILON && xbig<CMAX)
          xbig += 10.;
        if ((xbig >= CMAX) && fabs(g (totsam, xbig)-stat) > DBL_EPSILON)
          return (CMAX);
        if (xbig > 10.)
          xsmall = xbig - 10.;

        while ((xbig - xsmall) > PRECISION)
          {
            xtemp = (xbig + xsmall) / 2.;
            if (fabs(g (totsam, xtemp))-stat > DBL_EPSILON)
              xsmall = xtemp;
            else
              xbig = xtemp;
          }
        return ((xbig + xsmall) / 2.);

      }


      double
      g (const int totsam, const double c)
      /*!
        Solve g(C,n) for a particular C value.
        Called by Recombination::solveit.
        @author Dick Hudson.
      */
      {
        double x, y, esk;
        double r97, i1, i2, sumpd, n, b1, b2, b3;

        n = double (totsam);

        r97 = sqrt (97.);
        x = 13. + r97;
        y = 13. - r97;
        i2 = (log (((2. * c + y) * x) / ((2. * c + x) * y))) / r97;
        i1 = (0.5) * log ((c * c + 13. * c + 18.) / 18.) - 13. * i2 / 2.;
        sumpd = (-c + (c - 1.) * i1 + 2. * (7. * c + 9.) * i2) / (c * c);
        b1 = 1. / 2. + 1. / c + ((5. - c) * i1 -
                                 18. * (c + 1.) * i2) / (c * c);
        b2 = -1. / 2. + 2. / c - 2. * ((c + 9.) * i1 +
                                       2. * (2. * c + 9.) * i2) / (c * c);
        b3 = -2. / c + 2. * ((c + 7.) * i1 + 6. * (c + 3.) * i2) / (c * c);
        esk = sumpd + b1 / n + b2 / (n * n) + b3 / (n * n * n);
        return (2. * esk);
      }
    }
    double
    HudsonsC (const Sequence::PolyTable *data, const bool & haveOutgroup,
              const unsigned &  outgroup)
    /*!
      Returns Hudson's (1987) Genetical Research 50:245-250
      moment estimator of the population recombination rate.  Please note that
      the properties of this estimator are not ideal, and one should prefer
      Hudson (2001) Genetics 159: 1805-1817 
      or McVean et al. (2002) Genetics 160: 1231-1241
    */
    {
      unsigned totsam = unsigned(data->size());
      if (haveOutgroup)
        --totsam;
      int ss = int((*data)[0].length ());
      double sumhi = 0.0, sumhisq = 0.0, sksq = 0.0;
      get_h_vals (ss, totsam, data, haveOutgroup, outgroup, &sumhi, &sumhisq);
      sksq = CalcSamplingVariance (data, haveOutgroup, outgroup, totsam, ss);
      return( chatsub (totsam, sksq, sumhi, sumhisq) );
    }

    std::vector< std::vector<double> > Disequilibrium (const Sequence::PolyTable *data,
						       const bool & haveOutgroup, const unsigned & outgroup,
						       const unsigned & mincount,
						       const double max_distance)
    {
      enum {SITEI,SITEJ,RSQ,D,DPRIME};

      const char _alphabet[10] = {'A','G','C','T','0','1','a','g','c','t'};
      static const unsigned alphsize = 6;
      PolyTable::const_site_iterator beg = data->sbegin();

      unsigned ss = data->numsites();
      const size_t datasize = data->size();
      const size_t nsam = datasize-haveOutgroup;

      //chars1.first is the character state of the low-frequency/derived allele at site i
      //chars1.second is the character state of the high-frequency/ancestral allele at site i
      //chars2 is for site j
      std::pair<char,char> chars1,chars2;
      //counts2.first is the # occurences of the low-frequency/derived allele at site j
      //counts2.second is the # occurences of the high-frequency/anecstral allele at site j
      //counts2 is for site j
      std::pair<ptrdiff_t,ptrdiff_t> counts1,counts2;
      
      std::vector< std::vector<double> > returnList;
      
      unsigned states1=0,states2=0;

      for(unsigned i = 0 ; i < ss-1 ; ++i)
        {
          chars1.first = 'Z';//assign to a dummy character not in the alphabet
          chars1.second = 'Z';
          counts1.first=0;
          counts1.second=0;
          states1=0;
	  std::string site1((beg+i)->second);
          for(unsigned letter = 0 ; letter < alphsize ; ++letter)
            {
	      std::transform(site1.begin(),site1.end(),site1.begin(),
			     ::toupper);
              if(std::find(site1.begin(),
			   site1.end(),
                           _alphabet[letter]) != site1.end())
                {
                  if (chars1.first == 'Z')
		    {
		      chars1.first = char(std::toupper(_alphabet[letter]));
		      ++states1;
		    }
                  else
		    {
		      chars1.second = char(std::toupper(_alphabet[letter]));
		      if(chars1.first!=chars1.second)
			{
			  ++states1;
			}
		    }
                }
            }
          //skip if site i has > 2 sites4
          if (states1 == 2)
            {
	      if(haveOutgroup)
		{
		  site1.replace(outgroup,1,"");
		}
              for(unsigned j = i+1 ; j < ss ; ++j)
                {
		  if ( std::abs( data->position(j) - data->position(i) ) <= max_distance )
		    {
		      std::string site2( (beg+j)->second );
		      std::transform(site2.begin(),site2.end(),site2.begin(),
				     ::toupper);
		      chars2.first = 'Z';//assign to a dummy character not in the alphabet
		      chars2.second = 'Z';
		      counts2.first=0;
		      counts2.second=0;
		      states2=0;
		      for(unsigned letter = 0 ; letter < alphsize ; ++letter)
			{
			  if(std::find(site2.begin(),
				       site2.end(),
				       _alphabet[letter]) != site2.end())
			    {
			      if (chars2.first == 'Z')
				{
				  chars2.first = char(std::toupper(_alphabet[letter]));
				  ++states2;
				}
			      else
				{
				  chars2.second = char(std::toupper(_alphabet[letter]));
				  if(chars2.first!=chars2.second)
				    {
				      ++states2;
				    }
				}
			    }
			}
		      
		      //skip if site j has > 2 states
		      if (states2 == 2)
			{
			  if(haveOutgroup)
			    {
			      site2.replace(outgroup,1,"");
			    }
			  
			  //skip pairs of sites with missing data
			  if( std::find(site1.begin(),site1.end(),'N')==site1.end()
			      && std::find(site2.begin(),site2.end(),'N')==site2.end() )
			    {
			      //Make sure that the .first and .second
			      //mean what we want them to for chars1 and chars2.
			      //If there is no outgroup,then .second will
			      //be the high frequency allele.  If there
			      //is an outgroup, .second will be the derived
			      //allele.  With this scheme, chars1.second
			      //and chars2.second together represent the 11
			      //gamete
			      if ( haveOutgroup == false)
				{
				  //count the # occurences of chars1.first
				  //at site i
				  size_t x = std::count(site1.begin(),
							site1.end(),
							chars1.first);
				  //count the # occurences of chars2.first
				  //at site j
				  size_t y = std::count(site2.begin(),
							site2.end(),
							chars2.first);
				  
				  if ( x > nsam-x )
				    {
				      //need to swap chars1.first and chars1.second
				      //such that chars1.second is the high-frequency
				      //allele
				      std::swap(chars1.first,chars1.second);
				      counts1.first = nsam-x;
				      counts1.second = x;
				    }
				  else
				    {
				      counts1.first = x;
				      counts1.second = nsam-x;
				    }
				  
				  if ( y > nsam-y )
				    {
				      //need to swap chars2.first and chars2.second
				      //such that chars2.second is the high-frequency
				      //allele
				      std::swap(chars2.first,chars2.second);
				      counts2.first = nsam-y;
				      counts2.second = y;
				    }
				  else
				    {
				      counts2.first = y;
				      counts2.second = nsam-y;
				    }
				}
			      else if (haveOutgroup == true)
				{
				  //if chars1.first is ancestral, swap chars1.first
				  //and chars1.second so chars1.first represents
				  //the derived state
				  if (chars1.first == char(std::toupper((beg+i)->second[outgroup])))
				    {
				      std::swap(chars1.first,chars1.second);
				    }
				  counts1.first = std::count(site1.begin(),
							     site1.end(),
							     chars1.first);
				  counts1.second = nsam-counts1.first;
				  
				  //if chars2.first is ancestral, swap chars2.first
				  //and chars2.second so chars2.first represents
				  //the derived state
				  if (chars2.first == char(std::toupper((beg+j)->second[outgroup])))
				    {
				      std::swap(chars2.first,chars2.second);
				    }
				  counts2.first = std::count(site2.begin(),
							     site2.end(),
							     chars2.first);
				  counts2.second = nsam-counts2.first;
				}
			      
			      //don't continue unless minor/derived
			      //allele counts are greater than mincount
			      //at both sites
			      if ( counts1.first >= ptrdiff_t(mincount) &&
				   counts2.first >= ptrdiff_t(mincount) )
				{
				  //now we can actually do the work...
				  std::vector<double> temp(5);
				  temp[SITEI] = (beg+i)->first;
				  temp[SITEJ] = (beg+j)->first;
				  
				  //count the haplotype frequencies for D and D'
				  unsigned counts00=0,counts01=0,counts10=0,counts11=0;
				  for(unsigned k = 0 ; k < nsam ; ++k)
				    {
				      if(site1[k]==chars1.first &&
					 site2[k]==chars2.first)
					++counts11; //the 11 type is based on minor/derived allele
				      
				      if(site1[k]==chars1.first &&
					 site2[k]==chars2.second)
					++counts10;
				      
				      if(site1[k]==chars1.second &&
					 site2[k]==chars2.first)
					++counts01;
				      
				      if(site1[k]==chars1.second &&
					 site2[k]==chars2.second)
					++counts00;
				    }

				  double p0 = double(counts1.second)/double(nsam);
				  double p1 = double(counts1.first)/double(nsam);
				  double q0 = double(counts2.second)/double(nsam);
				  double q1 = double(counts2.first)/double(nsam);
				  
				  temp[D] = double(counts11)/double(nsam) -p1*q1;
				  temp[RSQ] = (temp[D]*temp[D])/(p0*p1*q0*q1);
				  double dmin = std::max(-p0*q0,-p1*q1);
				  double dmax = std::min(p1*q0,p0*q1);
				  temp[DPRIME] = (temp[D] < 0 ? -(temp[D]/dmin) : temp[D]/dmax);
				  returnList.push_back(temp);
				}
			    }
			}
		    }
		}
	    }
	}
      return returnList;
    }

    bool Disequilibrium (const Sequence::PolyTable *data,
			 vector<double> & return_values,
			 unsigned * i , unsigned * j,
			 const bool & haveOutgroup,
			 const unsigned & outgroup,
			 const unsigned & mincount,
			 const double max_distance )
    /*!
      More efficient calculation of LD stats
      \param data The polymorphism data
      \param return_values a vector with enough space to store 6 elements
      \param i a pointer to site i.  Should be initialized to 0 on the first call
      \param j a pointer to site j.  Should be initialized to 1 on the first call
      \param haveOutgroup true if \a data contains an outgroup, false otherwise
      \param outgroup the index of the outgroup in \a data \a haveOutgroup is true
      \param mincount the minimum sample frequency to include
      \param max_distance max distance between markers (on scale in which positions in \a data are stored) for inclusion
      \return a list of 6 doubles: sitei sitej rsq D D', and the 6th element is 1 if the site pair was skipped (in which case the other 5 elements are meaningless), and 0 otherwise
    */
    {
      unsigned ss = data->numsites();
      if ( ! (*i < ss - 1 ) ) return false;
      enum {SITEI,SITEJ,RSQ,D,DPRIME,SKIP};
      return_values[SKIP]=1;
      const char _alphabet[10] = {'A','G','C','T','0','1','a','g','c','t'};
      static const unsigned alphsize = 6;
      PolyTable::const_site_iterator beg = data->sbegin();
      const double pos1=(beg + *i)->first,pos2=(beg + *j)->first;
      const double op1=pos1,op2=pos2;
      const size_t datasize = data->size();
      const size_t nsam = datasize-haveOutgroup;

      //chars1.first is the character state of the low-frequency/derived allele at site i
      //chars1.second is the character state of the high-frequency/ancestral allele at site i
      //chars2 is for site j
      std::pair<char,char> chars1,chars2;
      //counts2.first is the # occurences of the low-frequency/derived allele at site j
      //counts2.second is the # occurences of the high-frequency/anecstral allele at site j
      //counts2 is for site j
      std::pair<ptrdiff_t,ptrdiff_t> counts1,counts2;
      
      unsigned states1=0,states2=0;

      chars1.first = 'Z';//assign to a dummy character not in the alphabet
      chars1.second = 'Z';
      counts1.first=0;
      counts1.second=0;
      states1=0;
      std::string site1((beg+*i)->second);
      for(unsigned letter = 0 ; letter < alphsize ; ++letter)
	{
	  std::transform(site1.begin(),site1.end(),site1.begin(),
			 ::toupper);
	  if(std::find(site1.begin(),
		       site1.end(),
		       _alphabet[letter]) != site1.end())
	    {
	      if (chars1.first == 'Z')
		{
		  chars1.first = char(std::toupper(_alphabet[letter]));
		  ++states1;
		}
	      else
		{
		  chars1.second = char(std::toupper(_alphabet[letter]));
		  if(chars1.first!=chars1.second)
		    {
		      ++states1;
		    }
		}
	    }
	}
      //skip if site i has > 2 sites4
      if (states1 == 2)
	{
	  if(haveOutgroup)
	    {
	      site1.replace(outgroup,1,"");
	    }
	  if ( std::abs( data->position(*j) - data->position(*i) ) <= max_distance )
	    {
	      std::string site2( (beg+*j)->second );
	      std::transform(site2.begin(),site2.end(),site2.begin(),
			     ::toupper);
	      chars2.first = 'Z';//assign to a dummy character not in the alphabet
	      chars2.second = 'Z';
	      counts2.first=0;
	      counts2.second=0;
	      states2=0;
	      for(unsigned letter = 0 ; letter < alphsize ; ++letter)
		{
		  if(std::find(site2.begin(),
			       site2.end(),
			       _alphabet[letter]) != site2.end())
		    {
		      if (chars2.first == 'Z')
			{
			  chars2.first = char(std::toupper(_alphabet[letter]));
			  ++states2;
			}
		      else
			{
			  chars2.second = char(std::toupper(_alphabet[letter]));
			  if(chars2.first!=chars2.second)
			    {
			      ++states2;
			    }
			}
		    }
		}
	      
	      //skip if site j has > 2 states
	      if (states2 == 2)
		{
		  if(haveOutgroup)
		    {
		      site2.replace(outgroup,1,"");
		    }
		  
		  //skip pairs of sites with missing data
		  if( std::find(site1.begin(),site1.end(),'N')==site1.end()
		      && std::find(site2.begin(),site2.end(),'N')==site2.end() )
		    {
		      //Make sure that the .first and .second
		      //mean what we want them to for chars1 and chars2.
		      //If there is no outgroup,then .second will
		      //be the high frequency allele.  If there
		      //is an outgroup, .second will be the derived
		      //allele.  With this scheme, chars1.second
		      //and chars2.second together represent the 11
		      //gamete
		      if ( haveOutgroup == false)
			{
			  //count the # occurences of chars1.first
			  //at site i
			  size_t x = std::count(site1.begin(),
						site1.end(),
						chars1.first);
			  //count the # occurences of chars2.first
			  //at site j
			  size_t y = std::count(site2.begin(),
						site2.end(),
						chars2.first);
			  
			  if ( x > nsam-x )
			    {
			      //need to swap chars1.first and chars1.second
			      //such that chars1.second is the high-frequency
			      //allele
			      std::swap(chars1.first,chars1.second);
			      counts1.first = nsam-x;
			      counts1.second = x;
			    }
			  else
			    {
			      counts1.first = x;
			      counts1.second = nsam-x;
			    }
			  
			  if ( y > nsam-y )
			    {
			      //need to swap chars2.first and chars2.second
			      //such that chars2.second is the high-frequency
			      //allele
			      std::swap(chars2.first,chars2.second);
			      counts2.first = nsam-y;
			      counts2.second = y;
			    }
			  else
			    {
			      counts2.first = y;
			      counts2.second = nsam-y;
			    }
			}
		      else if (haveOutgroup == true)
			{
			  //if chars1.first is ancestral, swap chars1.first
			  //and chars1.second so chars1.first represents
			  //the derived state
			  if (chars1.first == char(std::toupper((beg+*i)->second[outgroup])))
			    {
			      std::swap(chars1.first,chars1.second);
			    }
			  counts1.first = std::count(site1.begin(),
						     site1.end(),
						     chars1.first);
			  counts1.second = nsam-counts1.first;
			  
			  //if chars2.first is ancestral, swap chars2.first
			  //and chars2.second so chars2.first represents
			  //the derived state
			  if (chars2.first == char(std::toupper((beg+*j)->second[outgroup])))
			    {
			      std::swap(chars2.first,chars2.second);
			    }
			  counts2.first = std::count(site2.begin(),
						     site2.end(),
						     chars2.first);
			  counts2.second = nsam-counts2.first;
			}
		      
		      //don't continue unless minor/derived
		      //allele counts are greater than mincount
		      //at both sites
		      if ( counts1.first >= ptrdiff_t(mincount) &&
			   counts2.first >= ptrdiff_t(mincount) )
			{
			  //now we can actually do the work...
			  assert(pos1==op1);
			  assert(pos2==op2);
			  return_values[SITEI] = pos1;
			  return_values[SITEJ] = pos2;
			  
			  unsigned counts00=0,counts01=0,counts10=0,counts11=0;
			  for(unsigned k = 0 ; k < nsam ; ++k)
			    {
			      if(site1[k]==chars1.first &&
				 site2[k]==chars2.first)
				++counts11; //the 11 type is based on minor/derived allele
			      
			      if(site1[k]==chars1.first &&
				 site2[k]==chars2.second)
				++counts10;
			      
			      if(site1[k]==chars1.second &&
				 site2[k]==chars2.first)
				++counts01;
			      
			      if(site1[k]==chars1.second &&
				 site2[k]==chars2.second)
				++counts00;
			    }
			  
			  double p0 = double(counts1.second)/double(nsam);
			  double p1 = double(counts1.first)/double(nsam);
			  double q0 = double(counts2.second)/double(nsam);
			  double q1 = double(counts2.first)/double(nsam);
			  
			  return_values[D] = double(counts11)/double(nsam) - p1*q1;
			  return_values[RSQ]=(return_values[D]*return_values[D])/(p0*p1*q0*q1);
			  double dmin = std::max(-p0*q0,-p1*q1);
			  double dmax = std::min(p1*q0,p0*q1);
			  return_values[DPRIME] = (return_values[D] < 0 ?
						   -(return_values[D]/dmin) : 
						   return_values[D]/dmax);
			  return_values[SKIP]=0;
			}
		      else
			{
			  return_values[SKIP]=1;
			}
		    }
		  else
		    {
		      return_values[SKIP]=1;
		    }
		}
	      else
		{
		  return_values[SKIP]=1;
		}
	    }
	  else
	    {
	      return_values[SKIP]=1;
	    }
	}
      (*j)++;
      if(*j > ss-1) 
	{
	  (*i)++;
	  *j = (*i+1);
	}
      return ( *i < ss-1 );
    }
      
  } //namespace Recombination
} //ns Sequence

