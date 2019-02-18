#include <stdio.h>
#include <fstream>
#include <iostream>
#include <Sequence/Fasta.hpp>

using namespace std ;

int main (int argc, char *argv[])
{

  
  Sequence::Fasta fseq ;
  ifstream Infasta (argv[1]) ;
  
  Infasta >> fseq ;
  
  //  cout << fseq.GetName() << "\tcig\tComponent\t1\t" << fseq.length() << "\t.\t+\t.\t" << "Sequence \"" << fseq.GetName() << "\"" << endl ;
  if (fseq.length() < 3000)
    //    cout << fseq.GetName() << " " <<  fseq.length() <<  endl ;
    cout << argv[1] <<  endl ;
  Infasta.close() ;

}
