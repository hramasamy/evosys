#ifndef ENHANCER_PARAM
#define ENHANCER_PARAM
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
#include <ClusterMap.h>
#include <boost/regex.hpp>
#include <boost/filesystem/operations.hpp>
#include <boost/filesystem/path.hpp>
#include <boost/shared_ptr.hpp>
#include <boost/iterator.hpp>

#include <EnhancerDefines.h>

using namespace std ;
using namespace boost ;


class PatternClass  {

  public :
    
    PatternClass() 
    {
      NumberofForward = 0 ;
      NumberofReverse = 0 ;
      Overlap = true ;
    } 
  
  void SetPattern (char * pat) 
    {
      int count ;
      OriginalPattern = pat ;
      
    }

  const char *GetPattern ()
    {
      return OriginalPattern.c_str() ;
    }

  void SetPatternName (char ch) 
    {
      PatternName = ch ;
    }

  char GetPatternName ()
    {
      return PatternName ;
    }

  bool GetOverlap ()
    {
      return Overlap ;
    }

  void SetOverlap (bool Value)
    {
      Overlap = Value ;
    }

  private :
    char PatternName ;
    string OriginalPattern  ;
    bool Overlap ;
    int NumberofForward ;
    int NumberofReverse ;
} ;

class EnhancerParam {

  public :
    EnhancerParam () 
    {
      int Count ;

      for(Count = 0 ; Count < MAX_EXPRESSIONS ; Count++)
	{
	  NoOverLapLists[Count] = false ;
	}
      memset (NoOverLapString, '\0', MAX_EXPRESSIONS+1) ;
    } 

  ~EnhancerParam() ;
  
  void SetDirectoryName (char *name) 
    {
      DirectoryName = name ;
    }

  const char *GetDirectoryName ()
    {
      return (DirectoryName.c_str()) ;
    }

  void SetOrganismName (char *name) 
    {
      OrganismName = name ;
    }

  const char *GetOrganismName ()
    {
      return (OrganismName.c_str()) ;
    }

  void SetSessionName (char *name) 
    {
     SessionName = name ;
    }

  const char *GetSessionName ()
    {
      return (SessionName.c_str()) ;
    }

  void SetPrecedence (char *precedence) 
    {

      char *comma ;
      char ExpBuffer [30] ;
      int CurrentPosition = 0 ;
      int StrCount ;
      int PosCount ;
      PrecedenceCount = 0 ;
      
      if (strlen (precedence) == 0) 
	return ;
      comma = strchr (precedence, ',') ;
      //      cout << precedence ;
      while (comma)
	{
	  memset (ExpBuffer, '\0', 30) ;
	  strncpy(ExpBuffer, precedence + CurrentPosition, comma - precedence - CurrentPosition) ;
	  strcpy(Precedence[PrecedenceCount], ExpBuffer) ;
	  CurrentPosition = comma - precedence + 1 ;
	  comma = strchr (precedence+CurrentPosition, ',') ;
	  PrecedenceCount++ ;
	}
    }

  void SetGeneNames (char *name) 
    {
      GeneNames = name ;
    }

  const char *GetGeneNames ()
    {
      return (GeneNames.c_str()) ;
    }

  void SetChName (char *name) 
    {
      ChName = name ;
    }

  const char *GetChName ()
    {
      return (ChName.c_str()) ;
    }

  void SetWidthConstraint (int value) 
    {
      WidthConstraint = value ;
    }
  
  int GetWidthConstraint () 
    {
      return WidthConstraint ;
    }

  void SetNumberofBindingSites (int value)
    {
      NumberofBindingSites = value ;
    }
  
  int GetNumberofBindingSites ()
    {
      return NumberofBindingSites ;
    }

  void SetSave (char *user ) 
    {
      User = user ;
    }

  char *GetPattern (int count)
    {
      return (char *) PatternLists[count]->GetPattern() ;
    }

  char GetName (int count)
    {
      return PatternLists[count]->GetPatternName() ;
    }

  void SetConditionConstraint (char *value)
    {

      string::size_type pos ;

      ConditionConstraint = value ;
      if (strlen(value) == 0) return ;
      pos = ConditionConstraint.find(" ", 0) ;
      while (pos != string::npos)
	{
	  ConditionConstraint.replace(pos, 1, "") ;
	  pos = ConditionConstraint.find(" ", 0) ;
	}

      //      pos = ConditionConstraint.find("(", 0) ;
      //      while (pos != string::npos)
      //	{
      //	  ConditionConstraint.replace(pos, 1, "") ;
      //	  pos = ConditionConstraint.find("(", 0) ;
      //	}

      //      pos = ConditionConstraint.find(")", 0) ;
      //      while (pos != string::npos)
      //	{
      //	  ConditionConstraint.replace(pos, 1, "") ;
      //	  pos = ConditionConstraint.find(")", 0) ;
      //	}

      for (int count = 0 ; count < ConditionConstraint.length() ; count++)
	{
	  ConditionConstraint[count] = toupper(ConditionConstraint[count]) ;
	}

      pos = ConditionConstraint.find("AND", 0) ;
      while (pos != string::npos)
	{
	  ConditionConstraint.replace(pos, 3, "&") ;
	  pos = ConditionConstraint.find("AND", 0) ;
	}
      pos = ConditionConstraint.find("OR", 0) ;
      while (pos != string::npos)
	{
	  ConditionConstraint.replace(pos, 2, "|") ;
	  pos = ConditionConstraint.find("OR", 0) ;
	}
      pos = ConditionConstraint.find("NOT", 0) ;
      while (pos != string::npos)
	{
	  ConditionConstraint.replace(pos, 2, "!") ;
	  pos = ConditionConstraint.find("NOT", 0) ;
	}

      pos = ConditionConstraint.find_first_of("ABCDEFGHIJ", 0) ;
      while (pos != string::npos)
	{
	  if ((pos == 0)  || (isdigit(ConditionConstraint[pos-1])== 0))
	    {
	      if (!isdigit (ConditionConstraint[pos-2]))
		ConditionConstraint.insert(pos, "1") ;
	      else
		{
		  char ch = ConditionConstraint[pos-2] ;
		  ConditionConstraint[pos-2] = ConditionConstraint[pos-1] ;
		  ConditionConstraint[pos-1] = ch ;
		}
	      pos++ ;
	      while (isalpha (ConditionConstraint[pos]))
		pos++ ;
	    }
	  else
	    {
	      if (isdigit(ConditionConstraint[pos-1]))
		{
		  while (isalpha (ConditionConstraint[pos]))
		    pos++ ;
		}
	      else		  
		pos++ ;
	    }
	  pos = ConditionConstraint.find_first_of("ABCDEFGHIJ", pos) ;
	}
      ConditionConstraint.insert(0, "(") ;
      ConditionConstraint.insert(ConditionConstraint.length(), ")") ;
    }

  char * GetConditionConstraint ()
    {
      return ((char *) ConditionConstraint.c_str()) ;
    }


  void SetDirection (char ch) 
    {
      Direction = ch ;
    }

  char GetDirection ()
    {
      return Direction ;
    }

  vector <PatternClass *> & GetPatternLists ()
    {
      return PatternLists ;
    }

  void SetRecalculate (bool value)
    {
      Recalculate = value ;
    }

  bool GetRecalculate ()
    {
      return Recalculate ;
    }

  void SetAnyGene (bool value)
    {
      AnyGene = value ;
    }

  bool GetAnyGene ()
    {
      return AnyGene ;
    }

  int GetDisplay ()
    {
      return Display ;
    }

  char * GetUser ()
    {
      return (char *) User.c_str() ;
    }

   void SetNoOverLapString (char *Value) 
    {
      strcpy (NoOverLapString, Value) ;
      SetNoOverLapLists() ;
    }

  bool GetNoOverLapValue (char ch)
    {
      return (NoOverLapLists[(int) ch - 0x41]) ;
    }

  char *GetNoOverLapString ()
    {
      return NoOverLapString ;
    }

  void SetNoOverLapLists ()
    {
      int NoOverLapCount = strlen(NoOverLapString) ;

      for (int Count = 0 ; Count < NoOverLapCount ; Count++)
	{
	  NoOverLapLists [NoOverLapString[Count] - 0x41] = true ;
	}
    }

  void SetDisplay (int Value)
    {
      Display = Value ;
    }

  char * GetSaveFileName()
    {
      return (char *) SaveFileName.c_str() ;
    }

  void SetSaveFileName ()
    {
      char dirname[] = "/home/cache/" ;
      namespace fs = boost::filesystem;
      string CompleteFileName ;
      CompleteFileName = dirname  ;
      CompleteFileName += GetUser()  ;
      CompleteFileName += "/" ;
      //      cout << CompleteFileName << endl ;
      if (!fs::exists (CompleteFileName))
	{
	  boost::filesystem::create_directory(CompleteFileName) ;
	}
      SaveFileName = CompleteFileName.c_str() ;
      //      cout << SaveFileName << endl ;
    }

  bool FindPrecedence (char *) ;
  void ReadFile (char *) ;
  bool CheckRegex (char *, char *) ;
  void CheckFiles (char *) ;
  char *GetCacheFileName (int) ;
  char *GetSummaryFileName (int) ;

  private :
    string User ;
    string SaveFileName ;
    string DirectoryName ;
    char Precedence[30][60] ;
    int PrecedenceCount ;
    bool AnyGene ;
    vector <PatternClass *> PatternLists ;
    char NoOverLapString [MAX_EXPRESSIONS+1] ;
    int WidthConstraint ;
    int NumberofBindingSites ;
    string ConditionConstraint ;
    string ChName ;
    string OrganismName ;
    bool Recalculate ;
    bool NoOverLapLists [MAX_EXPRESSIONS] ;
    string SessionName ;
    int Display ;
    char Direction ;
    string GeneNames ;
  
} ;

#endif

