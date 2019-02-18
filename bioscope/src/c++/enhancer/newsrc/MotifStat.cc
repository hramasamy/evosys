#include <stdio.h>
#include <fstream>
#include <iostream>
#include <map>
#include <vector>
#include <algorithm>
#include <string>
#include <PatternClass.h>
#include <Splitter.h>
#include <SpeciesIndex.h>

using namespace std;
extern int GetOptions(int, char **, PatternClass &) ;
char freqfile[120] ;

int main(int argc, char* argv[])
{
  PatternClass motif ;
  GetOptions(argc, argv, motif) ;
  SpeciesIndex si(freqfile) ;
  si.ReadPositionFile() ;
  return 0;
}


