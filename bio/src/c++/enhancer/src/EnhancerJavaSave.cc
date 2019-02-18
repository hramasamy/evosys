#include <stdio.h>
#include <fstream>
#include <iostream>
#include <map>
#include <vector>
#include <list>
 #include <algorithm>
 #include <EnhancerClass.h>
 #include <EnhancerParam.h>
 #include <EnhancerResult.h>
 #include <EnhancerFunc.h>
 #include <EnhancerResults.h>
 #include <Annotation.h>
 #include <boost/filesystem/operations.hpp>
 #include <boost/filesystem/path.hpp>
 #include <boost/shared_ptr.hpp>
 #include <boost/iterator.hpp>
 #include <Sequence/Fasta.hpp>
#include <Annotation.h>
#include <EnhancerDefines.h>
 #include <EnhancerExtern.h>

 bool PositionOrder (const ResultClass *r1, const ResultClass *r2) ;
 void PerformALone (int, char **) ;
 extern int ReadAnnotationList (char *, vector<Annotation *> &) ;
void GetNearByGenes (long Start, long End, string &, list <Annotation *> &Alist, EnhancerParam &, string&, string &, bool &) ;
extern void BuildImageMapStr (long , long , long , long , int ,  bool , string &, string & ) ;


void Check (char *filename, char *chromosome, char *session, bool cache) ;

 void PerformAlone (int argc, char *argv[])
 {

   EnhancerParam Param ;

    if (GetOptions(argc, argv, Param) == 0) 
      {

	cerr << "usage enahncer -f <config file name> " << endl ;
	exit (0) ;
      }
    Param.ReadFile((char *) Param.GetDirectoryName()) ;
    Check ((char *) Param.GetDirectoryName(), (char *) Param.GetChName(), (char *)Param.GetSessionName(), false) ;
    PerformMatches (Param) ;

 }

 void SetAndGo (char *filename, char *chromosome, char *session, bool cache)
 {

   EnhancerParam Param ;

   Param.SetDirectoryName(filename) ;
   Param.SetChName(chromosome) ;
   Param.SetRecalculate (cache) ;
   Param.SetSessionName (session) ;
   Param.ReadFile (filename) ;

   PerformMatches (Param) ;

 }

 void Check (char *filename, char *chromosome, char *session, bool cache)
 {

   EnhancerParam Param ;
   Param.SetDirectoryName(filename) ;
   Param.SetChName(chromosome) ;
   Param.SetRecalculate (cache) ;
   Param.SetSessionName (session) ;
   Param.ReadFile (filename) ;

   if (strlen(Param.GetConditionConstraint()) == 0) 
     return ;
   if (CheckGrammar(Param.GetConditionConstraint()) == 0)
     {
       char errorfilename[1000] ;
	 strcpy (errorfilename,(char *) Param.GetDirectoryName()) ;
	 strcat (errorfilename, "/") ;
	 strcat (errorfilename, Param.GetSessionName()) ;
	 strcat (errorfilename, "/") ;
	 strcat (errorfilename, "config.err") ;
	 ofstream ErrorFile(errorfilename) ;
	 ErrorFile << "Error occurred in Boolean Expression Grammar" << endl ;
	 ErrorFile.close() ;
     }

 }

  void PerformMatches (EnhancerParam& Param) 
  {

    Sequence::Fasta fseq ;
    string DirectoryPath ;
    char CacheFileName [2000] ;
    char FastaFileName [2000] ;
    char GeneFileName [2000] ;
    char  CacheDirectory [1000] ;
    vector<Annotation *> AnnotationList ;
   DirectoryPath = Param.GetDirectoryName() ;
   if (DirectoryPath[DirectoryPath.length()-1] != '/')
     DirectoryPath += "/" ;
   strcpy (CacheDirectory,  CACHE_DIR) ;
   strcat (CacheDirectory, "/") ;
   DirectoryPath += Param.GetSessionName() ;
   if (DirectoryPath[DirectoryPath.length()-1] != '/')
     DirectoryPath += "/" ;
      DirectoryPath += Param.GetSessionName() ;
      DirectoryPath += "_" ; 
     DirectoryPath += Param.GetChName() ;
      if (DirectoryPath[DirectoryPath.length()-1] != '/')
        DirectoryPath += "/" ;

   Param.SetSaveFileName () ;
   Param.WriteConfigFile () ;
   Param.SetDirectoryName((char *) DirectoryPath.c_str()) ;
   strcpy (FastaFileName, (char *) Param.GetDirectoryName()) ;
   strcat (FastaFileName, (char *) Param.GetChName()) ;
   strcat (FastaFileName, ".fa") ;
   strcpy (GeneFileName, (char *) Param.GetDirectoryName()) ;
   strcat (GeneFileName, (char *) Param.GetChName()) ;
   strcat (GeneFileName, ".gff") ;


   map_type m ;
   Results ResultsList ;
   vector<Annotation *> AllResults ;

   vector <PatternClass *> DebugPattern ;
   PatternClass *DebugPatClass ;
   int count ;
   int Total ;
 
  DebugPattern = Param.GetPatternLists () ;

   namespace fs = boost::filesystem;
   int FromPosition = 0 ;

   strcat (CacheDirectory , Param.GetOrganismName()) ;
   
   if (!fs::exists (CacheDirectory))

   {
     boost::filesystem::create_directory(CacheDirectory) ;
     strcat (CacheDirectory, "/") ;
     strcat (CacheDirectory,  Param.GetChName()) ;
     boost::filesystem::create_directory(CacheDirectory) ;
     strcat (CacheDirectory,  "/") ;
   }
   else
     {
       strcat (CacheDirectory,  "/") ;
       strcat (CacheDirectory, Param.GetChName()) ;
       if (!fs::exists (CacheDirectory))
	 boost::filesystem::create_directory(CacheDirectory) ;
       strcat (CacheDirectory,"/") ;
     }

     Total = 0 ;
     char *seq = SingletonSequence::getInstance().GetSequence(
   for (count = 0 ; count < DebugPattern.size() ; count++) 
     {
       memset (CacheFileName, '\0', 2000) ;
       strcpy (CacheFileName, CacheDirectory) ;
       strcat (CacheFileName, (char *) DebugPattern[count]->GetPattern()) ;
       if ((!fs::exists (CacheFileName)) || Param.GetRecalculate())
 	{
 	  SearchPatterns(m, (char *) fseq.GetSeq().c_str(), count, ResultsList, Param, 'f', CacheFileName) ;
	  // 	  SearchPatterns(m, (char *) fseq.GetSeq().c_str(), (char *)DebugPattern[count]->GetRegEx(), NULL, ResultsList, DebugPattern[count]->GetPatternName(), DebugPattern[count]->GetOverlap(), 'f', CacheFileName) ;
 	}
       else
 	{
 	  ReadPatterns (ResultsList, CacheFileName, DebugPattern[count]->GetOverlap()) ;
 	}

       int TempSize = ResultsList.GetResults().size() ;
       DebugPattern[count]->SetNumberofForward( TempSize  - Total ) ;
       Total = TempSize ;

       memset (CacheFileName, '\0', 2000) ;
       strcpy (CacheFileName, (char *) CacheDirectory) ;
       strcat (CacheFileName, (char *) DebugPattern[count]->GetRevPattern()) ;
       if (strcmp ((char *) DebugPattern[count]->GetPattern(), (char *)DebugPattern[count]->GetRevPattern()) != 0) 
	 {
	   if ((!fs::exists (CacheFileName)) || Param.GetRecalculate())
	     {
	       SearchPatterns(m, (char *) fseq.GetSeq().c_str(), count, ResultsList, Param, 'r', CacheFileName)  ;
	    //	       SearchPatterns(m, (char *) fseq.GetSeq().c_str(), (char *) DebugPattern[count]->GetRevRegEx(), (char *) DebugPattern[count]->GetRegEx(), ResultsList, DebugPattern[count]->GetPatternName(), DebugPattern[count]->GetOverlap(), 'r', CacheFileName) ;
	     }
	   else
	     {
	       ReadPatterns (ResultsList, CacheFileName, DebugPattern[count]->GetOverlap()) ;
	     }
	 }
       TempSize = ResultsList.GetResults().size() ;
       DebugPattern[count]->SetNumberofReverse(TempSize- Total ) ;
       Total = TempSize  ;
     }

   ResultsList.OutputResults (Param, fseq, AnnotationList) ;
   int dsize = AnnotationList.size();
      vector<Annotation *>::iterator startIterator;
      startIterator = AnnotationList.begin() ;
      for( int i=0; i < dsize; i++ ) {
        Annotation *temp = *startIterator ;
        delete temp ;
	startIterator = AnnotationList.erase(startIterator) ;
      }
  }

 bool 
 PositionOrder (const ResultClass *r1, const ResultClass *r2)
 {

   long t1 = r1->GetPosition() ;
   long t2 = r2->GetPosition() ;

   if (t1 < t2)
     return true ;
   return false ;
 }

class is_greater_than
{
public:
  is_greater_than (long n)
    : value(n)
  {}

  bool operator() (Annotation *element) const
  {
    if (element->GetForwardStrand())
      {
	return element->GetEndPosition() > value ;
      }
    else
      {
	return element->GetStartPosition() > value ;
      }
  }

private:
  long value;
};

class is_less_than
{
public:
  is_less_than (long n)
    : value(n)
  {}

  bool operator() (Annotation *element) const
  {
    if (element->GetForwardStrand())
      {
	return element->GetStartPosition() < value ;
      }
    else
      {
	return element->GetEndPosition() < value ;
      }
  }

private:
  long value;
};


void GetNearByGenes (long Start, long End, string &GeneDetails, vector <Annotation *> &Alist, EnhancerParam &Param, string &done, string & geneinfo, bool &Found) 
{
  vector<Annotation *>::iterator pos ;
  long Dist = Param.GetDisplay() ;
  char whichway = Param.GetDirection () ;
  char *GeneNames = (char *) Param.GetGeneNames () ;
  vector<Annotation *>::iterator leftbeginpos ;
  vector<Annotation *>::iterator beginpos ;
  char buffer [500000] ;
  GeneDetails = "" ;
  long leftend, rightend ;
  string GeneName ;
  string GeneOrientation ;
  string GeneDistance ;
  string GeneStart ;
  string GeneEnd ;
  string GeneMolFuncs ;
  string GeneModel ;
  int GeneCounts = 0 ;
  int AnyGene = 0 ;
  int fixedleft ;
  int fixedend ;
  int fixedright ; 
  string description ; 
  //  string BestKegg ;

  Found = false  ;


  int TotalDistance = Param.GetDisplay () + Param.GetWidthConstraint() + 200 ;
  fixedleft = Start + (End-Start) / 2 ;
  fixedright = fixedleft + TotalDistance / 2 ;
  fixedleft = fixedleft - TotalDistance / 2 ;

  description = "" ; 
 

  GeneName.reserve(500) ;
  GeneOrientation.reserve (500) ;
  GeneDistance.reserve(500) ;
  GeneStart.reserve(500) ;
  GeneEnd.reserve (500) ;
  GeneModel.reserve (500) ;

  if (strlen(GeneNames) == 0) 
    {
      //      fprintf (stderr, "found is true\n") ;
      Found = true ;
    }

  if (whichway == 'l') 
    {
      leftend = Start - Dist ;
      if (leftend < 0) leftend = 0 ;
      rightend = Start + 100 ; 
    }
  if (whichway == 'r')
    {
      rightend = End + Dist ;
      leftend = Start - 100 ;
      if (leftend < 0) leftend = 0 ;
      
    }
  if (whichway == 'b') 
    {
      leftend = Start - (Dist/2) ;
      if (leftend < 0) leftend = 0 ;
      rightend = End + (Dist/2 ) ;
     }

  memset (buffer, '\0', 500000) ;
  if (whichway == 'l' || whichway == 'b') 
   {


     GeneName = "<tr><td><b>Name</b></td>" ;
     GeneOrientation = "<tr><td><b>Orientation</b></td>" ;
     GeneStart = "<tr><td><b>Start</b></td>" ;
     GeneDistance =  "<tr><td><b>Distance</b></td>" ;
     GeneEnd = "<tr><td><b>End</b></td>" ;
     GeneMolFuncs = "<tr><td><b>Molecular Function</b></td>" ;
     GeneModel = "<tr><td><b>GeneModel</b></td>" ;
     GeneDetails = "<table border=1 WIDTH-100%> <tr><td><table border=1 align=left><caption> <b>West</b> </caption>" ;
     done += "<table border=1 width=100%><tr><td><table border=1 align=left><caption> <b>West</b> </caption>" ;
     //     cout << "Before while the start is " << (*beginpos)->GetStartPosition() << " " << Start << endl ;
     beginpos = Alist.begin() ;
     while (beginpos != Alist.end())
	{
	  bool Add = false ;

	  if (((*beginpos)->GetStartPosition() > leftend) && (((*beginpos)->GetStartPosition() < Start)))
	    {
	      if ((*beginpos)->GetEndPosition() < Start)
		Add = true ;
	      else
		Add = false ;
	    }
	  if (((*beginpos)->GetEndPosition() > leftend) && ((*beginpos)->GetEndPosition() < Start))
	    {
	      if ((*beginpos)->GetStartPosition() < Start)
		Add = true ;
	      else
		Add = false ;
	    }
	  if (Add)
	    {
	      description.erase (description.begin(), description.end()) ;
	      char *NotesInfo = (char *) (*beginpos)->GetNotes("GeneName") ;
	      if (NotesInfo)
		{
		  if (strstr (GeneNames, NotesInfo))
		    Found = true ;
		  sprintf (buffer, "%s<td>%s<br>%s</td>", GeneName.c_str(), NotesInfo, (*beginpos)->GetGroup()) ;
		  description += NotesInfo  ;
		  description += " " ;
		  description +=  (*beginpos)->GetGroup() ;
		  description += " " ;
		}
	      else
		{
		  sprintf (buffer, "%s<td>%s</td>", GeneName.c_str(), (*beginpos)->GetGroup()) ;
		  description +=  (*beginpos)->GetGroup() ;
		  description += " " ;
		}
	      
	      GeneName = buffer ;

	      sprintf (buffer, "%s<td>%.2lf kb</td>", GeneDistance.c_str() , ((double) Start - (double) (*beginpos)->GetStartPosition())/ 1000.0) ;
	      GeneDistance =  buffer ;

	      sprintf (buffer, "%.2lf kb",((double) Start - (double) (*beginpos)->GetStartPosition())/ 1000.0) ;
	      description += buffer ; 

	      sprintf (buffer, "%s<td>%d</td>", GeneStart.c_str(), (*beginpos)->GetStartPosition()) ;
	      GeneStart =  buffer ;
	      sprintf (buffer, "%s<td>%d</td>", GeneEnd.c_str(), (*beginpos)->GetEndPosition()) ;
	      GeneEnd =  buffer ;
	      BuildImageMapStr ((*beginpos)->GetStartPosition(), (*beginpos)->GetEndPosition(),	fixedleft, fixedright, GENE, (*beginpos)->GetForwardStrand(), geneinfo, description) ;

	      if ((*beginpos)->GetForwardStrand())
		{
		  sprintf (buffer, "%s<td><center> <img src=\"http://192.168.1.102/forward.gif\"></center></td>", GeneOrientation.c_str()) ;
		  GeneOrientation = buffer ;
		}
	      else
		{
		  sprintf (buffer, "%s<td><center> <img src=\"http://192.168.1.102/reverse.gif\"></center></td>", GeneOrientation.c_str()) ;
		  GeneOrientation = buffer ;
		}
	      NotesInfo = (char *) (*beginpos)->GetNotes("Blast") ;
	      if (NotesInfo)
		{
		  sprintf (buffer, "%s<td><a href=\"%s\">Click</a></td>",
			   GeneModel.c_str(), NotesInfo) ;
		  GeneModel = buffer ;
		}
	      else
		{
		  sprintf (buffer, "%s<td> . </td>" , GeneModel.c_str()) ;
		  GeneModel = buffer ;
		}

	      NotesInfo = (char *) (*beginpos)->GetNotes("MolecularFunction") ;
	      if (NotesInfo)
		{
		  sprintf (buffer, "%s<td>%s</td>",
			   GeneMolFuncs.c_str(), NotesInfo) ;
		  GeneMolFuncs = buffer ;
		}
	      else
		{
		  sprintf (buffer, "%s<td> . </td>" , GeneMolFuncs.c_str()) ;
		  GeneMolFuncs = buffer ;
		}
	      GeneCounts++ ;
	    }
	  beginpos++ ;
	}

   }

    GeneName += "</tr>" ;
    GeneOrientation += "</tr>" ;
    GeneDistance += "</tr>" ;
    GeneModel += "</tr>" ;
    GeneStart += "</tr>" ;
    GeneEnd += "</tr>" ;
    if (GeneCounts != 0 ) 
      {
	sprintf (buffer, "%s%s%s%s%s%s</table></td>",  GeneName.c_str(), GeneOrientation.c_str(), GeneDistance.c_str(), GeneStart.c_str(),  GeneEnd.c_str(),  GeneModel.c_str()) ;
	GeneDetails += buffer ;
	sprintf (buffer, "%s%s%s</table></td>", GeneName.c_str(), GeneOrientation.c_str(),
		 GeneMolFuncs.c_str()) ;
	done += buffer ;
	AnyGene += GeneCounts ;
      }
    else 
      {
	GeneDetails += "<tr><td>NoResults</td></tr></table></td>" ;
	done  += "<tr><td>NoResults</td></tr></table></td>" ;
      }

    GeneName.erase() ;
    GeneOrientation.erase() ;
    GeneDistance.erase() ;
    GeneModel.erase() ;
    GeneStart.erase() ;
    GeneEnd.erase() ;
    GeneMolFuncs.erase() ;
    GeneCounts = 0 ;

    beginpos = Alist.begin() ;
    if (whichway == 'b') 
      {

	GeneName = "<tr><td><b>Name</b></td>" ;
	GeneOrientation = "<tr><td><b>Orientation</b></td>" ;
	GeneStart = "<tr><td><b>Start</b></td>" ;
	GeneDistance =  "<tr><td><b>Distance</b></td>" ;
	GeneEnd = "<tr><td><b>End</b></td>" ;
	GeneMolFuncs = "<tr><td><b>Molecular Function</b></td>" ;
	GeneModel = "<tr><td><b>GeneModel</b></td>" ;
	GeneDetails = GeneDetails.c_str() ;
	GeneDetails += "<td><table border=1 align=center><caption> <b>Inside</b></caption>" ;
	done += "<td><table border=1 align=center><caption> <b>Inside</b> </caption>" ;
	memset (buffer, '\0', 500000) ;
	while (beginpos != Alist.end())
	  {
	  
	    bool Add = false ;
	    char *NotesInfo, *FlyBaseInfo ;

	    if (((*beginpos)->GetStartPosition() > Start) && (((*beginpos)->GetStartPosition() < End)))
	      Add = true ;
	    if (((*beginpos)->GetEndPosition() > Start) && (((*beginpos)->GetEndPosition() < End)))
	      Add = true ;
	    if (((*beginpos)->GetStartPosition() < Start) && (((*beginpos)->GetEndPosition() > End)))
	      Add = true ;
	    if (((*beginpos)->GetStartPosition() > End) && (((*beginpos)->GetEndPosition() < Start)))
	      Add = true ;
	    if (Add == false) 
	      {
		goto nextpos ;
	      }
	    description.erase (description.begin(), description.end()) ;
	    NotesInfo = (char *) (*beginpos)->GetNotes("GeneName") ;
	    if (NotesInfo)
	      {
		if (strstr (GeneNames, NotesInfo))
		  Found = true ;
		sprintf (buffer, "%s<td>%s<br>%s</td>", GeneName.c_str(), NotesInfo, (*beginpos)->GetGroup()) ;
		  description += NotesInfo  ;
		  description += " " ;
		  description +=  (*beginpos)->GetGroup() ;
		  description += " " ;
		
	      }
	    else
	      {
		sprintf (buffer, "%s<td>%s</td>", GeneName.c_str(), (*beginpos)->GetGroup()) ;
		  description +=  (*beginpos)->GetGroup() ;
		  description += " " ;
	      }
	    GeneName = buffer ;
	    sprintf (buffer, "%s<td>%.2lf kb</td>", GeneDistance.c_str(), ((double) Start - (double) (*beginpos)->GetStartPosition())/ 1000.0) ;
	    GeneDistance =  buffer ;
	    sprintf (buffer, "%.2lf kb", ((double) Start - (double) (*beginpos)->GetStartPosition())/ 1000.0) ;
	    description += buffer ;


	    BuildImageMapStr ((*beginpos)->GetStartPosition(), (*beginpos)->GetEndPosition(), fixedleft, fixedright, GENE, (*beginpos)->GetForwardStrand(), geneinfo, description) ;

	    sprintf (buffer, "%s<td>%d</td>", GeneStart.c_str(), (*beginpos)->GetStartPosition()) ;
	    GeneStart =  buffer ;
	    sprintf (buffer, "%s<td>%d</td>", GeneEnd.c_str(), (*beginpos)->GetEndPosition()) ;
	    GeneEnd =  buffer ;

	    if ((*beginpos)->GetForwardStrand())
	      {
		sprintf (buffer, "%s<td><center> <img src=\"http://192.168.1.102/forward.gif\"></center></td>", GeneOrientation.c_str()) ;
		GeneOrientation = buffer ;
	      }
	    else
	      {
		sprintf (buffer, "%s<td><center> <img src=\"http://192.168.1.102/reverse.gif\"></center></td>", GeneOrientation.c_str()) ;
		GeneOrientation = buffer ;
	      }
	    NotesInfo = (char *) (*beginpos)->GetNotes("Blast") ;
	    if (NotesInfo)
	      {
		sprintf (buffer, "%s<td><a href=\"%s\">Click</a></td>",
			 GeneModel.c_str(), NotesInfo) ;
		GeneModel = buffer ;
	      }
	  else
	    {
	      sprintf (buffer, "%s<td> . </td>" , GeneModel.c_str()) ;
	      GeneModel = buffer ;
	    }

	  NotesInfo = (char *) (*beginpos)->GetNotes("MolecularFunction") ;
	  if (NotesInfo)
	    {
	      sprintf (buffer, "%s<td>%s</td>",
		       GeneMolFuncs.c_str(), NotesInfo) ;
	      GeneMolFuncs = buffer ;
	    }
	  else
	    {
	      sprintf (buffer, "%s<td> . </td>" , GeneMolFuncs.c_str()) ;
	      GeneMolFuncs = buffer ;
	    }
	  GeneCounts++ ;
	  nextpos : ;
	  beginpos++ ;
	}

   }


    GeneName += "</tr>" ;
    GeneOrientation += "</tr>" ;
    GeneDistance += "</tr>" ;
    GeneModel += "</tr>" ;
    GeneStart += "</tr>" ;
    GeneEnd += "</tr>" ;
    if (GeneCounts != 0 ) 
      {
	memset(buffer, '\0', 500000) ;
	sprintf (buffer, "%s%s%s%s%s%s</table></td>",  GeneName.c_str(), GeneOrientation.c_str(), GeneDistance.c_str(), GeneStart.c_str(),  GeneEnd.c_str(),  GeneModel.c_str()) ;
	GeneDetails += buffer ;
	sprintf (buffer, "%s%s%s</table></td>", GeneName.c_str(), GeneOrientation.c_str(),
		 GeneMolFuncs.c_str()) ;
	done += buffer ;
	AnyGene += GeneCounts ;
      }
    else 
      {
	GeneDetails += "<tr><td>NoResults</td></tr></table></td>" ;
	done  += "<tr><td>NoResults</td></tr></table></td>" ;
	//	GeneDetails.erase() ;
	//	done.erase() ;
      }
    next : ;

    GeneName.erase() ;
    GeneOrientation.erase() ;
    GeneDistance.erase() ;
    GeneModel.erase() ;
    GeneStart.erase() ;
    GeneEnd.erase() ;
    GeneMolFuncs.erase() ;
    GeneCounts = 0 ;


    beginpos = Alist.begin() ;

     memset (buffer, '\0', 500000) ;
  if (whichway == 'r' || whichway == 'b') 
    {

      GeneName = "<tr><td><b>Name</b></td>" ;
      GeneOrientation = "<tr><td><b>Orientation</b></td>" ;
      GeneDistance = "<tr><td><b>Distance</b></td>" ;
      GeneStart = "<tr><td><b>Start</b></td>" ;
      GeneEnd = "<tr><td><b>End</b></td>" ;
      GeneMolFuncs = "<tr><td><b>Molecular Function</b></td>" ;
      GeneModel = "<tr><td><b>GeneModel</b></td>" ;
      GeneDetails += "<td><table border=1 align=right><caption><b>East</b></caption>" ;
      done += "<td><table border=1 align=right><caption><b>East</b></caption>" ;
      while (beginpos != Alist.end())
	
	{
	  bool Add = false ;
	  char *NotesInfo, *FlyBaseInfo ;

	  if (((*beginpos)->GetStartPosition() > End) && (((*beginpos)->GetStartPosition() < rightend)))
	    {
	      if ((*beginpos)->GetEndPosition() > End)
		Add = true ;
	      else
		Add = false ;
	    }
	  if (((*beginpos)->GetEndPosition() > End) && ((*beginpos)->GetEndPosition() < rightend))
	    {
	      if ((*beginpos)->GetStartPosition() > End)
		Add = true ;
	      else
		Add = false ;
	    }

	  if (Add == false)
	    {
	      goto nextright ;
	    }

	  description.erase(description.begin(), description.end()) ;

	  NotesInfo = (char *) (*beginpos)->GetNotes("GeneName") ;
	  if (NotesInfo)
	    {
	      if (strstr (GeneNames, NotesInfo))
		Found = true ;
	      sprintf (buffer, "<td>%s<br>%s</td>", NotesInfo, (*beginpos)->GetGroup()) ;
	      description += NotesInfo  ;
	      description += " " ;
	      description +=  (*beginpos)->GetGroup() ;
	      description += " " ;
	    }
	  else
	    {
	      sprintf (buffer, "<td>%s</td>", (*beginpos)->GetGroup()) ;
	      description +=  (*beginpos)->GetGroup() ;
	      description += " " ;
	    }
	  GeneName += buffer ;
	  sprintf (buffer, "<td>%.2lf Kb</td>", ((double) (*beginpos)->GetStartPosition() - (double) End)/ 1000.0) ;
	  GeneDistance +=  buffer ;
	  sprintf (buffer, "%.2lf Kb", ((double) (*beginpos)->GetStartPosition() - (double) End)/ 1000.0) ;
	  description += buffer ;
	  sprintf (buffer, "<td>%d</td>", (*beginpos)->GetStartPosition()) ;
	  GeneStart += buffer ;
	  sprintf (buffer, "<td>%d</td>", (*beginpos)->GetEndPosition()) ;
	  GeneEnd += buffer ;
       
	  BuildImageMapStr ((*beginpos)->GetStartPosition(), (*beginpos)->GetEndPosition(), fixedleft, fixedright, GENE, (*beginpos)->GetForwardStrand(), geneinfo, description) ;

       if ((*beginpos)->GetForwardStrand())
	 {
	   GeneOrientation += "<td><center> <img src=\"http://192.168.1.102/forward.gif\"></center></td>" ;
	 }
       else
       {
	 GeneOrientation += "<td><center> <img src=\"http://192.168.1.102/reverse.gif\"></center></td>" ;
       }
       NotesInfo = (char *) (*beginpos)->GetNotes("Blast") ;
       if (NotesInfo)
	 {
	   sprintf (buffer, "<td><a href=\"%s\">Click</a></td>",
		    NotesInfo) ;
	   GeneModel += buffer ;
	 }
       else
	 {
	   GeneModel += "<td> . </td>" ;
	 }
       NotesInfo = (char *) (*beginpos)->GetNotes("MolecularFunction") ;
       if (NotesInfo)
	 {
	   sprintf (buffer, "<td>%s</td>",
		    NotesInfo) ;
	   GeneMolFuncs += buffer ;
	 }
       else
	 {
	   GeneMolFuncs += "<td> . </td>" ;
	 }
       GeneCounts++ ;
	nextright: ;
       beginpos++ ;
     }
     GeneName += "</tr>" ;
     GeneOrientation += "</tr>" ;
     GeneDistance += "</tr>" ;
     GeneModel += "</tr>" ;
     GeneStart += "</tr>" ;
     GeneEnd += "</tr>" ;
     if (GeneCounts != 0)
       {
	 sprintf (buffer, "%s%s%s%s%s%s</table></td></tr></table>",  GeneName.c_str(), GeneOrientation.c_str(), GeneDistance.c_str(), GeneStart.c_str(),  GeneEnd.c_str(),  GeneModel.c_str()) ;
	 GeneDetails += buffer ;
	 sprintf (buffer, "%s%s%s</table></td></tr></table>", GeneName.c_str(), GeneOrientation.c_str(),
		  GeneMolFuncs.c_str()) ;
	 done += buffer ;
	 AnyGene += GeneCounts ;
       }
     else
       {
	GeneDetails += "<tr><td>NoResults</td></tr></table></td></tr></table>" ;
	done  += "<tr><td>NoResults</td></tr></table></td></tr></table>" ;
       }

  end : ;
    GeneName.erase() ;
    GeneOrientation.erase() ;
    GeneDistance.erase() ;
    GeneModel.erase() ;
    GeneStart.erase() ;
    GeneEnd.erase() ;
    }

  if (Param.GetAnyGene())
    {
      if (AnyGene > 0)
	Found = true ;
      else
	Found = false ;
    }
  
}

