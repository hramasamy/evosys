#include <stdio.h>
#include <cstdlib>
#include <iostream>  
#include <string>
#include <map>
#include <EnhancerClass.h>
#include <EnhancerResult.h>
#include <EnhancerResults.h>
#include <ClusterEnhancerClass.h>

using namespace std ;
using namespace boost ;

// purpose:
// takes the contents of a file in the form of a string
// and searches for all the C++ class definitions, storing
// their locations in a map of strings/int's

int DetermineOverlap (unsigned long &, int &, unsigned long , int ) ;
bool DetermineAnyOverLap (char *, boost::match_results<std::string::const_iterator> &, int, string &, long) ;

void ReadPatterns(char filename[], bool overlap) 
{

 ifstream CacheFile ;
 int Number
 char type, name ;
 long offset ;
 int PatLength ;
 int Overlapped ;
 EnhancerClass *Enhancer ;

 cout << filename << endl ;

 CacheFile.open (filename, ios::in | ios::binary) ;
 while(!CacheFile.eof())
    {
      CacheFile.read ((char *) &name, sizeof (char))  ;
      if (CacheFile.eof())
	break ;
      CacheFile.read ((char *) &type, sizeof (char))  ;
      CacheFile.read ((char *) &PatLength, sizeof (int))  ;
      CacheFile.read ((char *) &offset, sizeof (unsigned long)) ;
      CacheFile.read ((char *) &Overlapped, sizeof (int)) ;
      if (overlap)
	{
	  Results.PushElement (type, name, offset, PatLength, Overlapped) ;
	}
      else
	{
	  if (!Overlapped)
	    {
	      Results.PushElement (type, name, offset, PatLength, Overlapped) ;
	    }
	}
    }
  CacheFile.close() ;
}

bool DetermineAnyOverLap (char *pat, boost::match_results<std::string::const_iterator>& what , int PatLength, string &seq, long offset )

{

  unsigned int flags = boost::match_default;
  std::string reoverlap (pat) ;
  boost::regex eoverlap (pat) ;
  boost::match_results<std::string::const_iterator> ewhat ;
  std::string::const_iterator revstart, revend;
  if ((offset - (4 * PatLength)) > 0)
      revstart = what[0].first - (4 * PatLength) ;
  else
    revstart = seq.begin() ;
  if ((offset + (4 * PatLength)) < seq.length())
    revend = what[0].first + (4 * PatLength) ; 
  else
    revend = seq.end() ;
  while (boost::regex_search (revstart, revend, ewhat, eoverlap, (boost::regex_constants::_match_flags) flags))
    {
      if ((ewhat[0].first >= what[0].first) && (ewhat[0].first <= what[0].second))
	{
	  return true ;
	}
      if ((ewhat[0].second >= what[0].first) && (ewhat[0].second <= what[0].second))
	{
	  return true ;
	}
      if ((ewhat[0].first <= what[0].first) && (ewhat[0].second >= what[0].second))
	{
	  return true ;
	}
      revstart = ewhat[0].first + 1 ;
      flags |= boost::match_prev_avail;
      flags |= boost::match_not_bob;
    }

  return false ;
}


bool DetermineAnyFileOverLap (char *pat, char *sequence, unsigned long LocateOffset, int PatLength )

{

  std::string seq(sequence) ;
  std::string::const_iterator start, end;
  std::string re(pat) ;
  boost::regex expression (pat) ;
  unsigned long  offset ;
  unsigned long Current ;

  start = seq.begin() + LocateOffset ;
  end = start+ PatLength ;
  unsigned int flags = boost::match_default;
  std::string reoverlap (pat) ;
  boost::regex eoverlap (pat) ;
  boost::match_results<std::string::const_iterator> ewhat ;
  std::string::const_iterator revstart, revend;
  if (offset > (4*PatLength))
    revstart = start  - (4 * PatLength) ;
  else
    revstart = seq.begin() ;
  if ((LocateOffset + PatLength) < (seq.length() - 4 * PatLength))
    revend = end  + (4 * PatLength) ; 
  else
    revend = seq.end() ;
  
  while (boost::regex_search (revstart, revend, ewhat, eoverlap, (boost::regex_constants::_match_flags) flags))
    {
      if ((ewhat[0].first >= start) && (ewhat[0].first <= end))
	{
	  return true ;
	}
      if ((ewhat[0].second >= start) && (ewhat[0].second <= end))
	{
	  return true ;
	}
      revstart = ewhat[0].first + 1 ;
      flags |= boost::match_prev_avail;
      flags |= boost::match_not_bob;
    }

  return false ;
}

