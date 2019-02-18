#ifndef ANNOTATION_H
#define ANNOTATION_H

#include <map>
#include <bool.h>
#include <string>

#include <EnhancerDefines.h>

using namespace std ;

typedef map<long, long> LOCATIONS ;


class Annotation {

  public :

  Annotation () 
    {

      memset (ReferenceSequence, '\0', MAX_NAME_LENGTH) ;
      memset (Source, '\0', MAX_NAME_LENGTH) ;
      memset (FeatureType, '\0', MAX_FEATURE_LENGTH) ;
      StartPosition = 0 ;
      EndPosition = 0 ;
      ForwardStrand = true ;
      memset (Score, '\0', MAX_NUMBER_LENGTH) ;
      memset (Phase, '\0', MAX_NUMBER_LENGTH) ;
      memset (Group, '\0', MAX_NAME_LENGTH) ;
      memset (Notes, '\0', MAX_CHAR_LENGTH) ;
    }

  void SetStrand (char Type)
    {
      StrandType = Type ;
    }

  char GetStrand ()
    {
      return StrandType ;
    }
  
  void SetProduct (char *value)
    {
      Product = value ;
    }

  const char * GetProduct()
    {
      return Product.c_str() ;
    }

  void SetGene (char *value)
    {
      Gene = value ;
    }

  const char * GetGene()
    {
      return Gene.c_str() ;
    }

  void InsertLocations (int Start, int End)
    {
      GeneLocations[count++] = Start ;
      GeneLocations[count++]= End ;
      //      cout << GeneLocations.size() << endl ;
    }

  long * GetLocations ()
    {
      return &(GeneLocations[0]) ;
    }

  long GetEnd ()
    {
      if (count > 0)
	{
	  return (GeneLocations[count - 1]) ;
	}
      else
	{
	  return 0 ;
	}
     }

   long GetStart ()
     {

       //      LOCATIONS::iterator pos ;
       //      pos = GeneLocations.begin() ;
       //      return (pos->first) ;
       return (GeneLocations[0]) ;
    }

  private :

    char ReferenceSequence[MAX_NAME_LENGTH] ;
    char Source [MAX_NAME_LENGTH] ;
    char FeatureType[MAX_FEATURE_LENGTH] ;
    long StartPosition ;
    long EndPosition ;
    char Score[MAX_NUMBER_LENGTH] ;
    bool ForwardStrand ;
    char Phase[MAX_NUMBER_LENGTH] ;
    char StrandType ;
    char Group [MAX_NAME_LENGTH] ;
    char Notes [MAX_CHAR_LENGTH] ;

} ;


#endif
