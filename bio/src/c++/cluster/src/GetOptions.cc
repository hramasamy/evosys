#include <unistd.h>
#include <stdio.h>
#include <iostream>
#include <EnhancerParam.h>
#include <stdbool.h>
   
int GetOptions (int argc, char **argv, EnhancerParam& Param)
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
   
  while ((c = getopt(argc, argv, "f:c:s:r")) != EOF)
    switch (c) {
    case 'f':
      Param.SetDirectoryName(optarg) ;
      break;
    case 'c' :
      Param.SetChName(optarg) ;
      break ;
    case 'r' :
      Param.SetRecalculate (true) ;
      break ;
    case 's' :
      Param.SetSessionName (optarg) ;
      break ;
    default:
      cerr << "usage: enhancer -f <filename> -p <pattern>" << endl ;
      return (0) ;
    }

  if (!(Param.GetChName() || Param.GetDirectoryName()))
    {
      cerr << " Incorrect arguments : <enhancer -f <config> -c <chromosome>" << endl ;
    }

#ifdef DEBUG
  cout << Param.GetDirectoryName() << endl ;
#endif

  return 1;
}

