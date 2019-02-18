#include <stdio.h>
#include <cstdlib>
#include <iostream>  
#include <string>
#include <map>
#include <EnhancerClass.h>
#include <EnhancerResult.h>
#include <EnhancerResults.h>

using namespace std ;
using namespace boost ;

//TABLEHIT Hits[500000] ;

// purpose:
// takes the contents of a file in the form of a string
// and searches for all the C++ class definitions, storing
// their locations in a map of strings/int's

int DetermineOverlap (unsigned long &, int &, unsigned long , int ) ;
bool DetermineAnyOverLap (char *, boost::match_results<std::string::const_iterator> &, int, string &, long) ;

void SearchPatterns(map_type& m, char *sequence, int count, Results & Results, EnhancerParam &Param, char type, char filename[]) 
{

  vector <PatternClass *> DebugPattern = Param.GetPatternLists() ;
  char *pattern ;
  char *checkrevpat ;

  if (type == 'r')
    {
      checkrevpat = (char *) DebugPattern[count]->GetRegEx() ;
      pattern= (char *) DebugPattern[count]->GetRevRegEx() ;
    }
  else
    {
      pattern = (char *) DebugPattern[count]->GetRegEx() ;
      checkrevpat = NULL ;
    }
  char name = DebugPattern[count]->GetPatternName() ;
  bool overlap = DebugPattern[count]->GetOverlap() ;
  std::string seq(sequence) ;
  std::string::const_iterator start, end;
  std::string re(pattern) ;
  boost::regex expression (pattern) ;
  unsigned long  offset ;
  unsigned long Current ;
  int PrevPatLength ;
  int Overlapped ;
  FILE *wf ;

  Current = 0 ;
  PrevPatLength = 0 ;
 
  offset = 0 ;
  start = seq.begin();
  end = seq.end();   

  boost::match_results<std::string::const_iterator> what ;
  unsigned int flags = boost::match_default;
  fstream CacheFile ;

  CacheFile.open (filename, ios::out | ios::binary) ;
  //  wf = fopen (filename, "wb") ;

  Overlapped = 0 ;


  while(boost::regex_search (start, end, what, expression, (boost::regex_constants::_match_flags) flags))   
    {
      m[std::string(what[5].first, what[5].second) + std::string(what[6].first, what[6].second)] = 
	what[5].first - seq.begin();      
      // update search position:
      if (offset == 0)
	{
	  offset += (long) (what[0].first - start) ;
	}
      else
	{
	  offset += (long) (what[0].first - start) + 1;
	}
      int PatLength ;
      PatLength  = what[0].second - what[0].first  ;
#ifdef DEBUG
      //     char match[100] ;
      //     strncpy(match, &sequence[offset], PatLength) ;
      //     match[PatLength] = '\0' ;
      //     cout << match << "\t" << offset << "\t" << pattern << endl ;
#endif      
      Overlapped = 0 ;
      Overlapped = DetermineOverlap (Current, PrevPatLength, offset, PatLength) ;
      if (type == 'r')
	{
	  if (!Overlapped)
	    {
	      if (DetermineAnyOverLap(checkrevpat, what, PatLength, seq, offset))
		Overlapped = 1 ;
	    }
	}

      char *NoOverLapString = Param.GetNoOverLapString();

      if (Overlapped) goto skipoverlapstring ;

      if (strchr (NoOverLapString, name))
	{
	  for (int OverLapCount = 0 ; OverLapCount < strlen(NoOverLapString) ;
	       OverLapCount++)
	    {
	      if (NoOverLapString[OverLapCount] == name)
		break ;
	      //		  cout << "reverse " << DebugPattern[(int)NoOverLapString[OverLapCount] -0x41]->GetRevRegEx() << endl ;
	      if (DetermineAnyOverLap ((char *) DebugPattern[(int)NoOverLapString[OverLapCount] 
							     -0x41]->GetRevRegEx(),
				       what, PatLength, seq, offset))
		{
		  Overlapped = 1 ;
		  break ;
		}
		  //		  cout << "reverse " << DebugPattern[(int)NoOverLapString[OverLapCount] -0x41]->GetRegEx() << endl ;
	      if (DetermineAnyOverLap ((char *) DebugPattern[(int)NoOverLapString[OverLapCount] 
							     -0x41]->GetRegEx(),
				       what, PatLength, seq, offset))
		{
		  Overlapped = 1 ;
		  break ;
		}
	    }	    

	}
	      
      skipoverlapstring : ;

      // update flags:
      start = what[0].first + 1 ;      
      flags |= boost::match_prev_avail;
      flags |= boost::match_not_bob;

      if (!Overlapped)
	{
	  Results.PushElement (type, name, offset, PatLength, Overlapped) ;
	}
      else
	{
	  if (overlap)
	    Results.PushElement (type, name, offset, PatLength, Overlapped) ;
	}
      //      Hits[HitCount].Name = name ;
      //Hits[HitCount].Type = type ;
      //Hits[HitCount].PatLength = PatLength ;
      //Hits[HitCount].offset = offset ;
      //Hits[HitCount].Overlapped = Overlapped ;
      CacheFile.write ((char *) &name, sizeof (char))  ;
      CacheFile.write ((char *) &type, sizeof (char))  ;
      CacheFile.write ((char *) &PatLength, sizeof (int))  ;
      CacheFile.write ((char *) &offset, sizeof (unsigned long)) ;
      CacheFile.write ((char *) &Overlapped, sizeof (int)) ;
      //      HitCount++ ;
    }
  //  fwrite (Hits, sizeof (TABLEHIT), HitCount, wf) ;
  //  fclose (wf) ;
  CacheFile.close() ;
}

int DetermineOverlap (unsigned long &Current, int &PrevPatLength, unsigned long offset, int PatLength)
{
  
  if ((Current == 0 ) || Current == offset)
    {
      Current = offset ;
      PrevPatLength = PatLength ;
      return 0 ;
    }
  else
    {
      if ((Current + PrevPatLength) < offset)
	{
	  Current = offset ;
	  PrevPatLength = PatLength ;
	  return 0 ;
	}
    }
  return 1 ;
}

void ReadPatterns(Results & Results, char filename[], bool overlap) 
{

 ifstream CacheFile ;
 char type, name ;
 long offset ;
 int PatLength ;
 int Overlapped ;

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

