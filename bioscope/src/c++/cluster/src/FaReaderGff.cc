#include <stdio.h>
#include <fstream>
#include <iostream>
#include <Sequence/Fasta.hpp>

using namespace std ;

int main (int argc, char * argv[])

{

  Sequence::Fasta fseq ;
  ifstream Fasta(argv[1]) ;

  Fasta >> fseq ;
  cout << argv[2] << "\tcig\tComponent\t1\t" << fseq.length() << "\t.\t+\t.\tSequence \"" << argv[2] << "\"" << endl ;

}
