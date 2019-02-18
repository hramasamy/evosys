#ifndef _PATTERN_CLASS
#define _PATTERN_CLASS
#include <stdio.h>
#include <iostream>
#include <stdlib.h>
#include <fstream>
#include <cctype>
#include <string>
#include <vector>
#include <stdbool.h>
#include <algorithm>
#include <map>
#include <boost/regex.hpp>
#include <boost/filesystem/operations.hpp>
#include <boost/filesystem/path.hpp>
#include <boost/shared_ptr.hpp>
#include <boost/iterator.hpp>

#include <EnhancerDefines.h>

#define MAX_MOTIF_LENGTH 100 

using namespace std ;
using namespace boost ;


class PatternClass  {

  public :
    
    PatternClass() 
    {
      NumberofForward = 0 ;
      NumberofReverse = 0 ;
    } 
  
  void SetPattern (char * pat) 
    {
      int count ;
      OriginalPattern = pat ;
      reversePattern() ;
      TranslateToRegex(OriginalPattern, RegEx) ;
      TranslateToRegex(ReversePattern, ReverseRegEx) ;
      SetVarCodesCount() ;
    }

  void SetVarCodesCount()  ;

  void TranslateToRegex (string &source, string & destination)  ;

  const char *GetPattern ()
    {
      return OriginalPattern.c_str() ;
    }

  const char *GetRevPattern ()
    {
      return ReversePattern.c_str() ;
    }

  const char *GetRegEx ()
    {
      return RegEx.c_str() ;
    }

  const char *GetRevRegEx ()
    {
      return ReverseRegEx.c_str() ;
    }

  void SetPatternName (char *name) 
    {
      PatternName = name ;
    }

  const char * GetPatternName ()
    {
      return PatternName.c_str() ;
    }

  void SetNumberofForward (int value)
    {
      NumberofForward = value ;
    }

  int GetNumberofForward ()
    {
      return NumberofForward ;
    }

  int GetNumberofReverse ()
    {
      return NumberofReverse ;
    }

  void SetNumberofReverse (int value)
    {
      NumberofReverse = value ;
    }

  void reversePattern () ;

  void SetUserPatternName (char *name)
    {
      UserPatternName = name ;
    }

  const char * GetUserPatternName ()
    {
      return UserPatternName.c_str() ;
    }
  
  void PrintAll() ;

  void PrintTrip(int pos, int & count)  ;
 
 private :
    string PatternName ;
    string UserPatternName ;
    string OriginalPattern  ;
    string ReversePattern ;
    string RegEx ;
    string ReverseRegEx ;
    SpeciesIndex si ;
    int NumberofForward ;
    int NumberofReverse ;
    int PTSums[MAX_MOTIF_LENGTH] ;
    int PTValue[MAX_MOTIF_LENGTH] ;
    int PatUnrolled[MAX_MOTIF_LENGTH][64] ;
    char  Patterns[MAX_MOTIF_LENGTH][4] ;
    int PatUnrolledCount[MAX_MOTIF_LENGTH] ;
    int PatFreqCount[MAX_MOTIF_LENGTH] ;
    int PatternCount ;
} ;


#endif

