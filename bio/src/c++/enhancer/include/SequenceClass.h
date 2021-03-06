#ifndef SEQUENCE_CLASS
#define SEQUENCE_CLASS

#include <string>
#include <vector>
#include <map>
#include <stdbool.h>

#include <EnhancerDefines.h>
#include <Annotation.h>
#include <Exon.h>

using namespace std ;

typedef map<string, string> SEQUENCE ;
typedef map<string, vector<Annotation *> > ALIST ;

class SingletonSequence ;

void killSingletonSequences(void);

class SingletonSequences
{
public:
  enum Type {INPUT, OUTPUT};
  
  static SingletonSequences& getInstance() // unique point of access
  {
    if (!instance)
      {
	if (flagDestroyed)
	  onDeadReference();
	else
	  create();
      }

    return *instance;
  }

  virtual ~SingletonSequences()
  {
    instance = 0;
    flagDestroyed = true;
    ALIST::iterator iter = SequenceAnnotation.begin() ;
    while (iter != SequenceAnnotation.end())
      {
	int dsize = (*iter).second.size() ;
	vector<Annotation *>::iterator startIterator;
	startIterator = (*iter).second.begin() ; 
	for( int i=0; i < dsize; i++ ) {
	  Annotation *temp = *startIterator ;
	  delete temp ;
	  startIterator = (*iter).second.erase(startIterator) ;
	}
	iter++ ;
      }
  }

  const char * GetSequence ( char * name ) 
    {
      SEQUENCE::iterator iter = GenomicSequence.find(name);
      if (iter != GenomicSequence.end()) {
	return ((*iter).second).c_str() ;
      }
      else
	return NULL ;
      
    }

  void InsertSequence (char *name, char *seq)

    {
      GenomicSequence.insert(make_pair(name, seq)) ;
    }

  void InsertAnnotation (char *name,  vector <Annotation *> & Annot)

    {
      SequenceAnnotation.insert(make_pair(name, Annot)) ;
    }
      

  vector <Annotation *> & GetAnnotation( char * name)
    {
      ALIST::iterator iter = SequenceAnnotation.find(name);
      if (iter != SequenceAnnotation.end()) {
	return ((*iter).second)  ;
      }
    }

private:

  static void create()
  {
    static SingletonSequences localInstance;
    instance = &localInstance;
  }
  static void onDeadReference()
  {
    create();
    // now instance points to the "ashes" of the singleton
    // create a new singleton at that address
    new(instance) SingletonSequences;
    atexit(killSingletonSequences);
    flagDestroyed = false;
  }

  SingletonSequences() {}
  
  static SingletonSequences* instance;
  static bool flagDestroyed;

  SEQUENCE GenomicSequence ;
  ALIST SequenceAnnotation ;
  Exon Elist ;


  SingletonSequences(const SingletonSequences&) {}
  SingletonSequences& operator=(const SingletonSequences&);
};

SingletonSequences* SingletonSequences::instance = 0;
bool SingletonSequences::flagDestroyed = false;

void killSingletonSequences()
{
  SingletonSequences::getInstance().~SingletonSequences();
}

#endif
