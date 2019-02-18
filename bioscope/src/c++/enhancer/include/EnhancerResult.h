#ifndef ENHANCER_RESULT
#define ENHANCER_RESULT

#include <stdio.h>
#include <ctype.h>
#include <string>
#include <vector>
#include <iostream>
#include <fstream>

using namespace std ;

//struct TableHit 
//{
//  char Name ;
//  char Type ;
//  int PatLength ;
//  unsigned long offset ;
//  int Overlapped ;
//} ;

//typedef struct TableHit TABLEHIT ;

class ResultClass  {

  public :
    
    ResultClass() 
    {
      Overlapped = 0 ; 
    }

  int GetOverlapped ()
    {
      return Overlapped ;
    }

  void SetOverlapped (int Value)
    {
      Overlapped = Value ;
    }

  void SetPosition (unsigned long value) 
    {
      Position = value ;
    }

  unsigned long GetPosition () const 
    {
      return Position ;
    }

  void SetLength (int value)
    {
      Length = value ;
    }

  int  GetLength ()
    {
      return Length ;
    }

  void SetName (char value)
    {
      Name = value ;
    }

  char GetName ()
    {
      return Name ;
    }

  void SetType (char value)
    {
      Type = value ;
    }

  char GetType ()
    {
      return Type ;
    }

  void SaveIntoFile (fstream &savefile)
    {
      savefile.write ((char *) &Name, sizeof (char)) ;
      savefile.write ((char *) &Type, sizeof (char)) ;
      savefile.write ((char *) &Position, sizeof (unsigned long)) ;
      savefile.write ((char *) &Length, sizeof (int)) ;
      savefile.flush() ;
    }
  
  private :
    char Name ;
    char Type ;
    int Length ;
    unsigned long Position ;
    int Overlapped ;
  
} ;

#endif

