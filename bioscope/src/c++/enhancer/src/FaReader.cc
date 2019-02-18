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
  cout << fseq.substr(10693569, 10693867 - 10693569).c_str() << endl << endl ;
  cout << fseq.substr(10693126,  10693303 - 10693126).c_str() << endl << endl ;
  cout << fseq.substr(10692705, 10693060 - 10692705).c_str() << endl << endl ;
  cout << fseq.substr(10692499, 10692636 - 10692499).c_str() << endl << endl ;
  cout << fseq.substr(10691814, 10692440 - 10691814).c_str() << endl << endl ;

}
