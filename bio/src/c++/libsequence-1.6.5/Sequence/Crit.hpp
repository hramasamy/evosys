#ifndef __CRIT_HPP__
#define __CRIT_HPP__

/*! \file Crit.hpp
  Functions for calculating critical values of empirical 
  distributions
*/

/*! \example critical_values.cc
  Examples of the following classes and functions : Sequence::descriptiveStats, Sequence::upperCrit, Sequence::lowerCrit, Sequence::mean, and Sequence::variance.
*/
#include <utility>
#include <limits>

namespace Sequence
{
  /*!
    \struct upperCrit Crit.hpp Sequence/Crit.hpp
  */
  struct upperCrit
  /*!
    \short Find the upper critical value of a sorted list.
    \ingroup stats
  */
  {
    template<typename BwdIter> 
    inline typename std::pair< BwdIter, double >
    operator()(BwdIter beg, BwdIter end, double alpha = 0.95)
      /*!
	\return A std::pair. The first member of the pair is an iterator
	pointing to the critical value of the list at probability \a alpha.
	The second member of the pair is a double representin the actual
	probability of the value stored at the first member.
      */
    {
      BwdIter i,j,crit;
      crit=end;
      double new_alpha=0.;
      double temp=0.;
    
      i=beg;
      j=end;
      unsigned n = 0;
      while(i != j)
	{
	  ++n;
	  ++i;
	}
      i=end-1;
      j=end-1;
      unsigned prob = 0;
      while (i != beg)
	{
	  BwdIter k = i;
	  --k;
	  while( *i==*k && i != beg && k !=beg)
	    {
	      --i;
	      --k;
	    }
	  if(i==beg)
	    break;

	  while( (*j>*i) && j != beg)
	    {
	      ++prob;
	      --j;
	    }

	  temp=(double)prob/(double)n;

	  if(1.-temp>=alpha+std::numeric_limits<double>::epsilon())
	    {
	      new_alpha=1.-temp;
	      crit=i--;
	    }
	  else
	    i = beg;
	}
      return ( std::pair< BwdIter, double >( crit, new_alpha ) );
    }
  };

  /*!
    \struct lowerCrit Crit.hpp Sequence/Crit.hpp
  */						      
  struct lowerCrit
  /*!
    \short Find the upper critical value of a sorted list.
    \ingroup stats
  */
  {
  public:
    template<typename FwdIter> 
    inline typename std::pair< FwdIter, double >
    operator() (FwdIter beg, FwdIter end, double alpha = 0.05)
      /*!
	\return A std::pair. The first member of the pair is an iterator
	pointing to the critical value of the list at probability \a alpha.
	The second member of the pair is a double representin the actual
	probability of the value stored at the first member.
      */
    {
      FwdIter i,j,crit;
      crit=end;

      double new_alpha=0.;
      double temp=0.;
    
      i=beg;
      j=end;
      unsigned n = 0;
      while(i != j )
	{
	  ++n;
	  ++i;
	}
      i=beg;
      j=beg;

      unsigned prob = 0;
      while (i != end)
	{
	  FwdIter k = i;
	  ++k;
	  while( *i==*k && i != end && k != end)
	    {
	      ++i;
	      ++k;
	    }
	  if(i==end)
	    break;

	  while( *j<*i && j != end)
	    {
	      ++prob;
	      ++j;
	    }

	  temp=(double)prob/(double)n;

	  if(temp<=alpha-std::numeric_limits<double>::epsilon())
	    {
	      new_alpha=temp;
	      crit=i++;
	    }
	  else 
	    i=end;
	}
      return std::pair<FwdIter,double>(crit,new_alpha);
    }
  };

}						 
#endif
