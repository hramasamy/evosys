/*=============================================================================
    Spirit v1.6.1
    Copyright (c) 2001-2003 Daniel Nuffer
    http://spirit.sourceforge.net/

    Permission to copy, use, modify, sell and distribute this software is
    granted provided this copyright notice appears in all copies. This
    software is provided "as is" without express or implied warranty, and
    with no claim as to its suitability for any purpose.
=============================================================================*/
#include <map>
#include "boost/spirit/include/classic_core.hpp"
#include "boost/spirit/home/classic/tree/ast.hpp"
#include <boost/spirit/home/classic/utility/confix.hpp>
#include <boost/spirit/home/classic/actor/assign_actor.hpp>
#include <boost/spirit/home/classic/utility/chset.hpp>

//#include <boost/spirit/core.hpp>
//#include <boost/spirit/tree/ast.hpp>
//#include <boost/spirit/utility/confix.hpp>
//#include <boost/spirit/actor/assign_actor.hpp>
//#include <boost/spirit/utility/chset.hpp>
#include <string.h>
#include <iostream>
#include <fstream>
#include <stack>
#include <functional>
#include <string>
#include <list>
#include <ctype.h>
#include <Annotation.h>

using namespace boost::spirit::classic;

int SortAnnotation (Annotation *a, Annotation *b) ;

int ReadAnnotationList (char *filename, vector<Annotation *> & Glist)
{

     ifstream InAnnotation (filename);
     string tempBuffer ;
     Annotation *Gl ;
     void *status ;
     char RefSequence[MAX_NAME_LENGTH] ;
     char Source [MAX_NAME_LENGTH] ;
    char FeatureType [MAX_FEATURE_LENGTH] ;
     long StartPosition ;
     long EndPosition ;
     char Score[MAX_NUMBER_LENGTH] ;
     char StrandType[MAX_CHAR_LENGTH] ;
     char Phase[MAX_NUMBER_LENGTH] ;
     char Group[MAX_NAME_LENGTH] ;
     char GroupName[MAX_NAME_LENGTH] ;
     char GroupDescription[MAX_NAME_LENGTH] ;
     char Notes[MAX_CHAR_LENGTH] ;
     char MolecularFunction[MAX_CHAR_LENGTH] ;
     char BestKeggHit[MAX_CHAR_LENGTH] ;

     status = getline(InAnnotation, tempBuffer) ;
     memset (RefSequence, '\0', MAX_NAME_LENGTH) ;
     memset (Source, '\0', MAX_NAME_LENGTH) ;
     memset (FeatureType, '\0', MAX_FEATURE_LENGTH) ;
     StartPosition = 0 ;
     EndPosition = 0 ;
     memset (Score, '\0', MAX_NUMBER_LENGTH) ;
     memset (StrandType, '\0', MAX_CHAR_LENGTH) ;
     memset (Phase, '\0', MAX_NUMBER_LENGTH) ;
     memset (Group, '\0', MAX_NAME_LENGTH) ;
     memset (GroupName, '\0', MAX_NAME_LENGTH) ;
     memset (GroupDescription, '\0', MAX_NAME_LENGTH) ;
     memset (Notes, '\0', MAX_CHAR_LENGTH) ;
     
     while (status)
       {
	 if (!status)
	   break ;
	 //	 fprintf (stdout, "%s\n\n", mRnaBuffer.c_str()) ;
	 Gl = new Annotation ;
	 char *NotePosition = strstr((char *) tempBuffer.c_str(), "; ") ;
	 sscanf ((char *) tempBuffer.c_str(), "%s%s%s%d%d%s%s%s%s%s", RefSequence, Source, FeatureType, 
		 &StartPosition, &EndPosition, Score, StrandType, Phase, 
		  GroupName, GroupDescription) ;

	 if (strcmp(FeatureType, "gene") != 0)
	   goto next ;

	 Gl->SetReferenceSequence(RefSequence) ;
	 Gl->SetSource(Source) ;
	 Gl->SetFeatureType(FeatureType) ;
	 Gl->SetStartPosition(StartPosition) ;
	 Gl->SetEndPosition(EndPosition) ;
	 if (StrandType[0] == '-')
	   Gl->SetForwardStrand(false) ;
	 else
	   Gl->SetForwardStrand(true) ;
	 Gl->SwapPositionIfNeeded() ;
	 Gl->SetPhase(Phase) ;
	 strcpy (Group, GroupName) ;
	 strcat (Group, " ") ;
	 strcat (Group, GroupDescription) ;
	 Gl->SetGroup(Group) ;
	 if (NotePosition)
	   {
	     char *SymbolPosition, *QuotePosition, *FlyBasePosition ;
	     SymbolPosition = strstr(NotePosition, "Symbol") ;
	     if (SymbolPosition)
	       {
		 char Value[300] ;
		 memset(Value, 300, '\0') ;
		 if (SymbolPosition + 7)
		   QuotePosition = strstr(SymbolPosition+7, "\"") ;
		 if (QuotePosition)
		   {
		     strncpy (Value, SymbolPosition + 7, QuotePosition - (SymbolPosition + 7)) ;
		     Gl->InsertNotes("Symbol", Value) ;
		   }
	       }
	     FlyBasePosition = strstr(NotePosition, "FlyBase") ;
	     if (FlyBasePosition)
	       {
		 char Value[300] ;
		 memset(Value, 300, '\0') ;
		 if (FlyBasePosition + 8)
		   QuotePosition = strstr(FlyBasePosition+8, "\"") ;
		 if (QuotePosition)
		   {
		     strncpy (Value, FlyBasePosition + 8, QuotePosition - (FlyBasePosition + 8)) ;
		     Gl->InsertNotes("FlyBase", Value) ;
		   }
	       }
	   }
	 if (NotePosition)
	   {
	     NotePosition = strstr (NotePosition, "@") ;
	     char *EqualTo, *quote ;
	     if (NotePosition)
	       {
		 EqualTo = strstr(NotePosition, "=") ;
	       }
	     else goto cont ;
	     if (EqualTo)
	       {
		 quote = strstr(EqualTo+2, "\"") ;
	       }
	     while (EqualTo && quote)
	       {
		 char name[100] ;
		 char value[1000] ;
		 memset (name, '\0', 100) ;
		 memset (value, '\0', 1000) ;
		 strncpy (name, NotePosition+1, (EqualTo - (NotePosition+1))) ;
		 strncpy (value, EqualTo+2, (quote - (EqualTo+2))) ;
		 if (strlen(value) != 0)
		   {
		     if (strstr (name, "GeneName")) 
		       {
			 char Val[1000] ;
			 int count ;
			 for (count = 0 ; count < strlen(value) ; count++)
			   Val[count] =toupper(value[count]) ;
			 Val[count] = '\0' ;
			 Gl->InsertNotes(name, Val) ;
		       }
		     else
		       {
			 Gl->InsertNotes(name, value) ;
		       }
		   }
		 EqualTo = strstr(quote+1, "=") ;
		 if (quote+1)
		   {
		     NotePosition = quote+1 ;
		   }
		 if (EqualTo)
		   {
		     quote = strstr(EqualTo+2, "\"") ;
		   }
	       }
	   }
	 cont : ;
	 Glist.push_back(Gl) ;
	 tempBuffer.clear() ;
#ifdef DEBUG
	 //	 fprintf (stderr, "%s\t%s\t%s\t%d\t%d\n",
	 //		  Gl->GetReferenceSequence(),
	 //		  Gl->GetSource(),
	 //		  Gl->GetFeatureType(),
	 //		  Gl->GetStartPosition(),
	 //		  Gl->GetEndPosition()) ;
#endif
	 next : ;
	 status = getline(InAnnotation, tempBuffer) ;
       }
     stable_sort(Glist.begin(), Glist.end(), SortAnnotation) ;
     InAnnotation.close() ;
#ifdef DEBUG
     
     vector<Annotation *>::iterator pos ;

     for (pos = Glist.begin() ; pos != Glist.end() ; ++pos)
       {
	 //	 fprintf (stderr, "THe start is %d\n",
	 //		  (*pos)->GetStartPosition()) ;
       }
#endif
}

int SortAnnotation (Annotation *a, Annotation *b)
{
  if (a->GetStartPosition() > b->GetStartPosition())
    {
      return 0 ;
    }
  else
    return 1 ;
}
