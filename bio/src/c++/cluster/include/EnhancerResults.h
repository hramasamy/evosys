#ifndef ENHANCER_RESULTS
#define ENHANCER_RESULTS

#include <stdio.h>
#include <ctype.h>
#include <string>
#include <vector>
#include <map>
#include <list>
#include <algorithm>
#include <fstream>
#include <iostream>
#include <EnhancerResult.h>
#include <EnhancerParam.h>
#include <Sequence/Fasta.hpp>
#include <Annotation.h>

using namespace std ;

class Results  {

  public :
    
    Results() 
    {

      CurrentIndex = 0 ;
      
    } 

  ~Results ()
    {
      int dsize = ResultList.size();
      vector<ResultClass *>::iterator startIterator;
      for( int i=0; i < dsize; i++ ) {
	startIterator = ResultList.begin();
	ResultClass *temp = *startIterator ;
	ResultList.erase( startIterator );
	delete temp ;
      }

    }

  void PushElement (char Type, char Name, long Pos, int Len, int Over)
    {
      ResultClass *temp ;
      temp = new ResultClass ;
      temp->SetType (Type) ;
      temp->SetName (Name) ;
      temp->SetPosition (Pos) ;
      temp->SetLength (Len) ;
      temp->SetOverlapped (Over) ;
      ResultList.push_back(temp) ;
    }

  vector<ResultClass *> & GetResults () 
    {
      return ResultList ;
    }

  int *GetSitesCount ()
    {
      return (SitesCount) ;
    }
  
  int GetCurrentIndex () {
    return CurrentIndex ;
  }
  
  void PrintResults (EnhancerParam &, int, int, Sequence::Fasta &, string &, int, vector <string> &, fstream &) ;
  void OutputResults (EnhancerParam &, Sequence::Fasta &, vector <Annotation *> &) ;
  private :
  vector<ResultClass *> ResultList ;
  int CurrentIndex  ;
  int SitesCount[26] ;
  
} ;

#endif

