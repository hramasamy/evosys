#include <unistd.h>
#include <stdio.h>
#include <iostream>
#include <stdbool.h>
#include <PatternClass.h>

extern char freqfile[] ;

int GetOptions(int, char **, PatternClass &) ;
   
int GetOptions (int argc, char **argv, PatternClass &mot)
{
  int c;
  int aflg = 0;
  int bflg = 0;
  int errflg = 0;
  char ofile = '\0' ;

  if (argc == 1) 
    {
      return 0 ;
    }
   
  while ((c = getopt(argc, argv, "f:i:m:")) != EOF)
    switch (c) {
    case 'p' :
      break ;
    case 'f':
      mot.SetFile(freqfile) ;
      strcpy(freqfile, optarg) ;
      break;
    case 'i' :
      break ;
    case 'm' :
      mot.SetPattern(optarg) ;
      break ;
    default:
      cerr << "usage: motifstat p <pattern>" << endl ;
      return (0) ;
    }

  return 1;
}

