#include <iostream>
#include <string.h>
#include <string>
#include <fstream>
#include <cctype>
#include <algorithm>
#include <stdlib.h>
#include <SequenceClass.h>
#include <Sequence/Fasta.hpp>


extern "C" void ReadOrganism (char *datadir, char *organism) ;
extern int ReadAnnotationList (char *, vector<Annotation *> &, Exon &) ;


void ReadOrganism (char *datadir, char *organism)

{

  string SequenceListFilename = datadir ;
  int DataDirLength = strlen (datadir) ;
  string FastaFilename ;
  string GffFilename ;
  Sequence::Fasta fseq ;  

  std::cout << datadir << " " << organism << std::endl ;
  if (SequenceListFilename[SequenceListFilename.length()-1] != '/')
    {
      SequenceListFilename += "/" ;
    }
  SequenceListFilename += organism ;
  char buffer[256];

  ifstream examplefile (SequenceListFilename.c_str());
  
  std::transform(SequenceListFilename.begin() + DataDirLength, 
		 SequenceListFilename.end(), 
		 SequenceListFilename.begin() + DataDirLength,
		 (int(*)(int)) toupper);

  
  std::cout << SequenceListFilename << std::endl ;

  if (! examplefile.is_open())
    { 
      cout << "Error opening file"; exit (1);
    }
  while (! examplefile.eof() )
    {
      examplefile.getline (buffer,100);
      if (examplefile.eof())
	break ;
      FastaFilename = SequenceListFilename.c_str()  ;
      FastaFilename += "/" ;
      FastaFilename += buffer ;
      FastaFilename += "/" ;
      FastaFilename += buffer ;
      GffFilename = FastaFilename.c_str() ;
      FastaFilename += ".fa" ;
      GffFilename += ".gff" ;
      ifstream InFasta (FastaFilename.c_str()) ;
      InFasta >> fseq ;
      vector<Annotation *> AnnotationList ;
      Exon Elist ;
      SingletonSequences::getInstance().InsertSequence(buffer, (char *) fseq.GetSeq().c_str()) ;
      std::cout << strlen(SingletonSequences::getInstance().GetSequence(buffer)) << " " << AnnotationList.size() << std::endl ;
      ReadAnnotationList ((char *) GffFilename.c_str(), AnnotationList, Elist) ;
      SingletonSequences::getInstance().InsertAnnotation(buffer, AnnotationList) ;
      std::cout << strlen(SingletonSequences::getInstance().GetSequence(buffer)) << " " << AnnotationList.size() << std::endl ;
      InFasta.close() ;
      FastaFilename.clear() ;
      GffFilename.clear() ;
    }
  examplefile.close() ;
}
