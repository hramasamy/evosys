#include <stdio.h>
#include <fstream>
#include <iostream>
#include <map>
#include <vector>
#include <algorithm>
#include <EnhancerClass.h>
#include <EnhancerParam.h>
#include <EnhancerResult.h>
#include <EnhancerFunc.h>
#include <EnhancerResults.h>
#include <Genes.h>
#include <boost/filesystem/operations.hpp>
#include <boost/filesystem/path.hpp>
#include <boost/shared_ptr.hpp>
#include <boost/iterator.hpp>

 #include <Sequence/Fasta.hpp>

 #include <EnhancerExtern.h>

 bool PositionOrder (const ResultClass *r1, const ResultClass *r2) ;
 int *SitesTotal ;
extern int ReadGeneList (char *, vector<Genes *> &) ;

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

 void PerformMatches (EnhancerParam& Param) 
 {

   Sequence::Fasta fseq ;
   string DirectoryPath ;
   char CacheFileName [2000] ;
   char FastaFileName [2000] ;
   char GeneFileName [2000] ;
   char  CacheDirectory [1000] ;
   vector<Genes *> GenesList ;
   DirectoryPath = Param.GetDirectoryName() ;
   if (DirectoryPath[DirectoryPath.length()-1] != '/')
     DirectoryPath += "/" ;
   strcpy (CacheDirectory,  CACHE_DIR) ;
   strcat (CacheDirectory, "/") ;
   DirectoryPath += Param.GetSessionName() ;
   if (DirectoryPath[DirectoryPath.length()-1] != '/')
     DirectoryPath += "/" ;
   //   DirectoryPath += Param.GetSessionName() ;
   //   DirectoryPath += "_" ; 
   //   DirectoryPath += Param.GetChName() ;
   //   if (DirectoryPath[DirectoryPath.length()-1] != '/')
   //     DirectoryPath += "/" ;

   Param.SetDirectoryName((char *) DirectoryPath.c_str()) ;
   strcpy (FastaFileName, (char *) Param.GetDirectoryName()) ;
   strcat (FastaFileName, (char *) Param.GetChName()) ;
   strcat (FastaFileName, ".fa") ;
   strcpy (GeneFileName, (char *) Param.GetDirectoryName()) ;
   strcat (GeneFileName, (char *) Param.GetChName()) ;
   strcat (GeneFileName, ".gene") ;
   ifstream InFasta (FastaFileName) ;

   map_type m ;
   Results ResultsList ;
   vector<ResultClass *> AllResults ;

   InFasta >> fseq ;

   vector <PatternClass *> DebugPattern ;
   PatternClass *DebugPatClass ;
   int count ;
 
  DebugPattern = Param.GetPatternLists () ;

  ReadGeneList (GeneFileName, GenesList)  ;

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


   for (count = 0 ; count < DebugPattern.size() ; count++) 
     {
       memset (CacheFileName, '\0', 2000) ;
       strcpy (CacheFileName, CacheDirectory) ;
       strcat (CacheFileName, (char *) DebugPattern[count]->GetPattern()) ;
       if (!fs::exists (CacheFileName))
 	{
 	  SearchPatterns(m, (char *) fseq.GetSeq().c_str(), (char *)DebugPattern[count]->GetRegEx(), ResultsList, DebugPattern[count]->GetPatternName(), 'f', CacheFileName) ;
 	  AllResults = ResultsList.GetResults() ;
 	}
       else
 	{
 	  ReadPatterns (ResultsList, CacheFileName) ;
 	}
       memset (CacheFileName, '\0', 2000) ;
       strcpy (CacheFileName, (char *) CacheDirectory) ;
       strcat (CacheFileName, (char *) DebugPattern[count]->GetRevPattern()) ;
       if (strcmp ((char *) DebugPattern[count]->GetPattern(), (char *)DebugPattern[count]->GetRevPattern()) != 0) 
	 {
	   if (!fs::exists (CacheFileName))
	     {
	       SearchPatterns(m, (char *) fseq.GetSeq().c_str(), (char *) DebugPattern[count]->GetRevRegEx(), ResultsList, DebugPattern[count]->GetPatternName(), 'r', CacheFileName) ;
	     }
	   else
	     {
	       ReadPatterns (ResultsList, CacheFileName) ;
	     }
	 }
     }
  
   SitesTotal = ResultsList.GetSitesCount()  ;
   ResultsList.OutputResults (Param, fseq, GenesList) ;

   int dsize = GenesList.size();
   vector<Genes *>::iterator startIterator;
   for( int i=0; i < dsize; i++ ) {
     startIterator = GenesList.begin();
     GenesList.erase( startIterator );
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

void GetNearbyGenes (int start, int end, string &result, vector <Genes *> &Glist) 
{

  int GeneCount = Glist.size ()  ;
  int FindIndex ;
  int GetEndPosition ;
  int GetPosition ;
  int NextHalf ;
  char GeneBuffer[1000] ;
  char *p ;
  
  FindIndex = GeneCount - 1 ;
  result.erase() ;
  NextHalf = GeneCount/2 ;
  while (NextHalf > 1) 

    {
      GetEndPosition = Glist[FindIndex]->GetEnd () ;
      if (GetEndPosition < 0) 
	break ;
      if (GetEndPosition >  start)
	FindIndex = FindIndex - NextHalf ;
      else
	{
	  FindIndex = FindIndex + NextHalf ;
	  if (FindIndex > GeneCount)
	    return ;
	}
      NextHalf = NextHalf / 2 ;

    }
  if (FindIndex > 1)
    {
      strcpy (GeneBuffer, "Gene in 5\'region :    ") ; 
      strcat (GeneBuffer, (char *) Glist[FindIndex]->GetGene()) ;
      strcat (GeneBuffer, (char *) Glist[FindIndex]->GetProduct()) ;
      while ((p = strchr(GeneBuffer, ' ')) != NULL)
      	*p = '*' ;
      result = GeneBuffer ;
    }

  result + "\n" ;

  FindIndex = 0 ;
  NextHalf = GeneCount/2 ;
  while (NextHalf > 1) 

    {
      if (FindIndex < 0) 
	break ;
      GetPosition = Glist[FindIndex]->GetStart () ;
      if (GetPosition  > (GeneCount - 1) )
	break ;
      if (GetPosition >  end)
	FindIndex = FindIndex - NextHalf ;
      else
	{
	  FindIndex = FindIndex + NextHalf ;
	  if (FindIndex > (GeneCount - 1))
	    return ;
	}
      NextHalf = NextHalf / 2 ;

    }

  if (FindIndex < 0)
    FindIndex = 0 ;
  if (FindIndex > 0)
    {
      strcpy (GeneBuffer, "Gene close to the 3' region  ") ; 
      strcat (GeneBuffer, (char *) Glist[FindIndex]->GetGene()) ;
      strcat (GeneBuffer, (char *) Glist[FindIndex]->GetProduct()) ;
      while ((p = strchr(GeneBuffer, ' ')) != NULL)
      	*p = '*' ;
      result = GeneBuffer ;
    }

}
