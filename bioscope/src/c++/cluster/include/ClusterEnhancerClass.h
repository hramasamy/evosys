#ifndef CLUSTER_ENHANCER_CLASS
#define CLUSTER_ENHANCER_CLASS

#include <EnhancerParam.h>
#include <EnhancerClass.h>
#include <EnhancerParam.h>
#include <Sequence/Fasta.hpp>
#include <Annotation.h>

using namespace std ;
using namespace boost ;

class ClusterEnhancerClass {
  
 public: 

  ~ClusterEnhancerClass ()
    {
      int dsize = EnhancerLists.size();
      vector<EnhancerClass *>::iterator startIterator;
      for( int i=0; i < dsize; i++ ) {
	startIterator = EnhancerLists.begin();
	 EnhancerClass *temp = *startIterator ;
	 delete temp ;
	EnhancerLists.erase( startIterator );
      }
    }      

  void ReadPatterns (EnhancerParam &, int) ;
  void OutputResults(EnhancerParam&, Sequence::Fasta &, vector <Annotation *> &) ;
  void PrintResults (EnhancerParam &, int, int, Sequence::Fasta &, string &, int, vector<string> &, int, string &) ;

  private :
    
  vector <EnhancerClass *> EnhancerLists ;
  vector<int> ClusterCounts ;
  int SitesCount[26] ;
  
} ;

#endif

