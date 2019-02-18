#include <stdio.h>
#include <cstdlib>
#include <iostream>  
#include <string>
#include <map>
#include <EnhancerClass.h>
#include <ClusterEnhancerClass.h>
#include <string.h>
#include <WebServer.h>
#include <EnhancerDefines.h>


// purpose:
// takes the contents of a file in the form of a string
// and searches for all the C++ class definitions, storing
// their locations in a map of strings/int's

int DetermineOverlap (unsigned long &, int &, unsigned long , int ) ;
bool DetermineAnyOverLap (char *, boost::match_results<std::string::const_iterator> &, int, string &, long) ;

extern bool EvaluateConditions(char *, int *) ;
extern bool PositionOrder (EnhancerClass *, EnhancerClass *) ;
extern void GetNearByGenes (long, long, string &, vector <Annotation *> &, EnhancerParam &, string&, string &, string &, bool &) ;
char *color[] = {"red", "orange", "green", "cyan", "brown", "purple", "black", "violet", "peachbuf", "magenta"} ;

void BuildImageMapStr (long , long , long , long , int ,  bool , string &, string & ) ;

void 
ClusterEnhancerClass::OutputResults (EnhancerParam &Param, Sequence::Fasta  &Sequence, vector <Annotation *> & Glist)

{

  int NumberofSites = Param.GetNumberofBindingSites() ;
  int Width = Param.GetWidthConstraint() ;
  vector <string> ClusterInfo(5) ;
  vector <string> WestGeneInfo ;
  vector <string> EastGeneInfo ;
  string BooleanConstraints = Param.GetConditionConstraint() ;
  int start, TotalResults ;
  int Count ;
  int LastSite ;
  int ClusterCount = 1 , ClusterLogicCount = 1 ;
  int ClusterSatisfying = 1 ;
  string html ;
  char buffer [2000] ;
  char donefilename [1000] ;
  char gfffilename [1000] ;
  char menufilename [1000] ;
  char xmlfilename [1000] ;
  char noresult[1000] ;
  string xml ;
  string GeneXmlStr ;

  string donebuffer ;
  string description ;
  string ImageStr ;

  strcpy (donefilename,(char *) Param.GetDirectoryName()) ;
  strcat (donefilename, (char *) Param.GetChName()) ;
  strcat (donefilename, ".done") ;
  strcpy (gfffilename,(char *) Param.GetDirectoryName()) ;
  strcat (gfffilename, (char *) Param.GetChName()) ;
  strcat (gfffilename, ".gff") ;
  strcpy (menufilename,(char *) Param.GetDirectoryName()) ;
  strcat (menufilename, "index.html") ;
  strcpy (xmlfilename,(char *) Param.GetDirectoryName()) ;
  strcat (xmlfilename, "results.xml") ;
  strcpy (noresult,(char *) Param.GetDirectoryName()) ;
  strcat (noresult, (char *) Param.GetChName()) ;
  strcat (noresult, ".nores") ;

  ClusterInfo[0].reserve(8192) ;
  ClusterInfo[1].reserve(8192) ;
  ClusterInfo[2].reserve(8192) ;
  ClusterInfo[3].reserve(8192) ;
  ClusterInfo[4].reserve(8192) ;


  xml = "" ;
  GeneXmlStr = "" ;

  ofstream donefile (donefilename) ;
  ofstream gfffile (gfffilename, ios::app) ;
  ofstream menufile (menufilename) ;
  ofstream xmlfile (xmlfilename, ios::out) ;
  string Results ;
  char ChromosomeName[100] ;
  const char *underscore ;
  char WebBuffer[1000] ;

  description = "" ;
  underscore = strchr (Param.GetChName(), '_') ;
  if (strstr(Param.GetChName(), Param.GetOrganismName()))
      strcpy (ChromosomeName, &(underscore[1])) ;
  else
    strcpy (ChromosomeName, Param.GetChName()) ;
  xml += "<?xml version=\"1.0\" standalone=\"yes\"?>\n" ;

  stable_sort (EnhancerLists.begin(), EnhancerLists.end(), PositionOrder)  ;
  TotalResults = EnhancerLists.size() ;
  int ResultCount ;
  ResultCount = 0 ;
  xml += "<supercluster>\n" ;
  //  for (ResultCount = 0 ; ResultCount < TotalResults ; ResultCount++)
  while (ResultCount < TotalResults)
    {
      start = ResultCount ;
      if ((start + NumberofSites) > TotalResults)
	break ;
      LastSite = NumberofSites ;
      while (((start + LastSite) < TotalResults) &&
	     ((EnhancerLists[start+ LastSite]->GetPosition() -
	       (EnhancerLists[start]->GetPosition()) <= Width)))
	LastSite++ ;
      ResultCount = ResultCount + (LastSite - NumberofSites) + 1 ;

      string PrecedencePattern ;
      if ((EnhancerLists[start+ (LastSite - 1)]->GetPosition() - 
 	  (EnhancerLists[start]->GetPosition()) <= Width))
	{
	  ClusterSatisfying++ ;
	  memset(SitesCount, 0, sizeof(int) * 26) ;
	  for (Count = start ; Count < (start + LastSite) ; Count++)
	    {
	      char buff[2] ;
	      (SitesCount[EnhancerLists[Count]->GetName() - 0x41])++ ;
	      buff[0] = EnhancerLists[Count]->GetName() ;
	      buff[1] = '\0' ;
	      PrecedencePattern += buff ;
	    }
 	  if ((BooleanConstraints.length() == 0) || EvaluateConditions((char *) BooleanConstraints.c_str(), SitesCount))
	    {
	      string temp ;
	      long start_gene, end_gene ;
	      
	      if (Param.GetDirection() == 'b')
		{
		  start_gene = EnhancerLists[start]->GetPosition() - (Param.GetDisplay()/2) ;
		  end_gene = EnhancerLists[start]->GetPosition() + (Param.GetDisplay()/2)  ;
		  if (start_gene < 0) start_gene = 0 ;
		  if (end_gene > Sequence.length()) end_gene = Sequence.length() - 1 ;
		}
	      if (Param.GetDirection() == 'l')
		{
		  start_gene = EnhancerLists[start]->GetPosition() - Param.GetDisplay() ;
		  end_gene = EnhancerLists[start]->GetPosition() + 100 ;
		  if (start_gene < 0) start_gene = 0 ;
		  if (end_gene > Sequence.length()) end_gene = Sequence.length() - 1 ;
		}
	      if (Param.GetDirection() == 'r')
		{
		  start_gene = EnhancerLists[start]->GetPosition() - 100 ;
		  end_gene = EnhancerLists[start]->GetPosition() + Param.GetDisplay() ;
		  if (start_gene < 0) start_gene = 0 ;
		  if (end_gene > Sequence.length()) end_gene = Sequence.length() - 1 ;
		}

	      bool FoundValue ;
	      string tempdonebuffer ;
	      GeneXmlStr += "<genes>\n" ;
	      GetNearByGenes (EnhancerLists[start]->GetPosition(),
			      EnhancerLists[start + (LastSite-1)]->GetPosition(),
			      temp, Glist, Param, tempdonebuffer, GeneXmlStr, ImageStr, FoundValue) ;
	      GeneXmlStr += "</genes>\n" ;
	      if (FoundValue)
		{
		  if (Param.FindPrecedence ((char *)PrecedencePattern.c_str()))
		  {
		    int TotalSites = LastSite - start ;
		    
		    sprintf (buffer, "<h3>%s, Cluster-%d, Nearby Gene Information </h3>", Param.GetChName(), ClusterCount) ;
		    donebuffer += buffer ;
		    donebuffer += tempdonebuffer.c_str() ;
		    tempdonebuffer.erase() ;
		    donebuffer += "<br>" ;

		    sprintf (WebBuffer, "%s%s%s_%s?name=%s:%d..%d&span=%d", WebAddress, EnhancerAddress, Param.GetSessionName(), Param.GetChName(), ChromosomeName, start_gene, end_gene, end_gene-start_gene) ;

	      // removed true temporarily in the sprintf 
		    sprintf (buffer, "<a href=\"%s\"><h3>Cluster %d (graphical view in Generic Browser)</h3></a>", WebBuffer, ClusterCount) ;
		    html += buffer ; 
		    char *Ch = strchr (Param.GetChName(), '_') ;
		    ++Ch ;
		    sprintf (buffer,  "%s\tcig\tcluster\t%d\t%d\t.\t.\t.\tCluster %d\n", Ch, EnhancerLists[start]->GetPosition()+1,(EnhancerLists[start+LastSite-1]->GetPosition() + EnhancerLists[start+LastSite-1]->GetLength()), ClusterCount) ;
		    Results += buffer ;
		    html += "<center><table border=1>" ;
		    for (int ClusterMotif = start ; ClusterMotif < (start + LastSite) ; 
			 ClusterMotif++)
		    {
		      vector <MotifClass *> Mot = EnhancerLists[ClusterMotif]->GetMotifs() ;
		      ClusterInfo[0] = "<tr><td><b>Name</b></td>" ;
		      ClusterInfo[1] = "<tr><td><b>Pattern</b></td>" ;
		      ClusterInfo[2] = "<tr><td><b>Orientation</b></td>" ;
		      ClusterInfo[3] = "<tr><td><b>Distance<br>(Next Site)</b></td>" ;
		      ClusterInfo[4]  = "" ;
		      
		      PrintResults (Param, 0, Mot.size(), Sequence, Results, ClusterCount, ClusterInfo, ClusterMotif, xml) ;
                    sprintf (buffer,  "%s-%d", EnhancerLists[ClusterMotif]->GetFileName(), ClusterCount) ;
                    description = "" ;
                    description += buffer ;
                    BuildImageMapStr (Mot[0]->GetPosition(),
                                   Mot[Mot.size()-1]->GetPosition() +
                                   Mot[Mot.size() -1]->GetLength(),
                                   start_gene,
                                   end_gene,
                                   ENHANCER,
                                   false,
                                   ImageStr,
                                   description   ) ;
		    description.erase (description.begin(), description.end()) ;
		      ClusterInfo[0] += "</tr>" ;
		      ClusterInfo[1] += "</tr>" ;
		      ClusterInfo[2] += "</tr>" ;
		      ClusterInfo[3] += "</tr>" ;
		      ClusterInfo[4] += "</td></tr></table></td></tr></table>" ;
		      html += "<tr><td><table border=1><caption>" ;
		      html += EnhancerLists[ClusterMotif]->GetFileName() ;
		      html += "</caption>" ;
		      html +=  ClusterInfo[0].c_str() ;
		      html +=  ClusterInfo[1].c_str() ;
		      html +=  ClusterInfo[2].c_str() ;
		      html +=  ClusterInfo[3].c_str() ;
		      html += "</table></td></tr>" ;
		      //		      html +=  ClusterInfo[4].c_str() ;
		      ClusterInfo[0].erase() ;
		      ClusterInfo[0] = "" ;
		      ClusterInfo[1].erase() ;
		      ClusterInfo[1] = "" ;
		      ClusterInfo[2].erase() ;
		      ClusterInfo[2] = "" ;
		      ClusterInfo[3].erase() ;
		      ClusterInfo[3] = "" ;
		      ClusterInfo[4].erase() ;
		      ClusterInfo[4] = "" ;
		    }
		    html += "</table></center>" ;
		    html+= "<table border=1> <caption> <b> Graphics View of Super Cluster and Genes Nearly</b><tr><td><table>" ;
		    html+= ImageStr.c_str() ;
		    html+= "</table></td></tr></table>" ;
		    ImageStr.erase() ; 
		    //		    html += temp.c_str() ;
		    temp.erase() ;
		    ClusterCount++ ;
		  }
               else
		  {
		  }
		}
	      else
		{
		}
		  ClusterLogicCount++ ;
		}
	    }
      PrecedencePattern.erase() ;
    }
   if (ClusterCount == 1) 
    {
      ofstream nores(noresult) ;
      nores << "noresult" << endl ;
      nores.close() ;
    }

  html += "</body></html>" ;
  gfffile << Results.c_str() ;
  gfffile.close() ;

  vector <PatternClass *> PatternLists ;
    int count ;
    PatternLists = Param.GetPatternLists () ;

    donefile << "<table border=1><tr>" ;
    for (count = 0 ; count < PatternLists.size() ; count++) 
      {
	donefile << "<td>" ;
	fstream PatternFile (Param.GetSummaryFileName(count), ios::in) ;
	string LineBuffer ;
	getline (PatternFile, LineBuffer) ;
	if (PatternFile.eof())
	  {
	    PatternFile.close() ;
	  }
	else 
	  {
	    while (!PatternFile.eof())
	      {
		donefile << LineBuffer ;
		getline(PatternFile, LineBuffer) ;
	      }

	    PatternFile.close() ;
	  }
	
	donefile << "</td>" ;
      }

    donefile << "</tr><tr><td><table border=1>" << endl ;
    for (count = 0 ; count < PatternLists.size() ; count++) 
      {
      	donefile << "<tr><td><font color=" << color[count] << ">" << PatternLists[count]->GetPatternName() << "-" <<  PatternLists[count]->GetPattern() << "</font></td><td>" <<  ClusterCounts[count] << "</td></tr>" << endl ;

      }

   donefile << "<tr><td>No-of-" << Param.GetNumberofBindingSites() << "-Sites-within-" << Param.GetWidthConstraint() << "-bases</td><td>" << (ClusterSatisfying-1) << "</td></tr>" << endl ;
    donefile << "<tr><td> Total after satisfying " << Param.GetConditionConstraint() << "</td><td>" << ClusterLogicCount-1 << "</td></tr>" << endl  ;
    donefile << "<tr><td> FinalTotal after satisfying Gene Specific Constraints</td><td>" << ClusterCount-1 << "</td></tr></table></td></tr></table>" << endl  ;

    if (ClusterCount <= 5) 
      donefile << donebuffer.c_str() << "</table>" ;

  donefile.close() ;
  menufile << html ;
  menufile.close() ;
  
}


void 
ClusterEnhancerClass::ReadPatterns(EnhancerParam & Param, int which) 
{

 ifstream CacheFile ;
 char filename[1000] ;
 int NumberofSites ;
 char type, name ;
 long offset ;
 int PatLength ;
 int Overlapped ;
 EnhancerClass *Enhancer ;
 int SitesCount ;
 int Count = 0 ;

 strcpy (filename, Param.GetCacheFileName(which)) ;
 CacheFile.open (filename, ios::in | ios::binary) ;
 while(!CacheFile.eof())
    {
      CacheFile.read ((char *) &NumberofSites, sizeof (int))  ;
      if (CacheFile.eof())
	break ;
      Enhancer = new EnhancerClass(Param.GetPattern(which), Param.GetName(which)) ;
      for (SitesCount = 0 ; SitesCount < NumberofSites ; SitesCount++)
	{
	  CacheFile.read ((char *) &name, sizeof (char))  ;
	  CacheFile.read ((char *) &type, sizeof (char))  ;
	  CacheFile.read ((char *) &PatLength, sizeof (int))  ;
	  CacheFile.read ((char *) &offset, sizeof (unsigned long)) ;
	  Enhancer->AddMotif (name, type, offset, PatLength) ;
	}
      Count++ ;
      Enhancer->SetParameters() ;
      EnhancerLists.push_back (Enhancer) ;
    }
 ClusterCounts.push_back (Count) ;
  CacheFile.close() ;
}

bool DetermineAnyOverLap (char *pat, boost::match_results<std::string::const_iterator>& what , int PatLength, string &seq, long offset )

{

  unsigned int flags = boost::match_default;
  std::string reoverlap (pat) ;
  boost::regex eoverlap (pat) ;
  boost::match_results<std::string::const_iterator> ewhat ;
  std::string::const_iterator revstart, revend;
  if ((offset - (4 * PatLength)) > 0)
      revstart = what[0].first - (4 * PatLength) ;
  else
    revstart = seq.begin() ;
  if ((offset + (4 * PatLength)) < seq.length())
    revend = what[0].first + (4 * PatLength) ; 
  else
    revend = seq.end() ;
  while (boost::regex_search (revstart, revend, ewhat, eoverlap, (boost::regex_constants::_match_flags) flags))
    {
      if ((ewhat[0].first >= what[0].first) && (ewhat[0].first <= what[0].second))
	{
	  return true ;
	}
      if ((ewhat[0].second >= what[0].first) && (ewhat[0].second <= what[0].second))
	{
	  return true ;
	}
      if ((ewhat[0].first <= what[0].first) && (ewhat[0].second >= what[0].second))
	{
	  return true ;
	}
      revstart = ewhat[0].first + 1 ;
      flags |= boost::match_prev_avail;
      flags |= boost::match_not_bob;
    }

  return false ;
}


bool DetermineAnyFileOverLap (char *pat, char *sequence, unsigned long LocateOffset, int PatLength )

{

  std::string seq(sequence) ;
  std::string::const_iterator start, end;
  std::string re(pat) ;
  boost::regex expression (pat) ;
  unsigned long  offset ;
  unsigned long Current ;

  start = seq.begin() + LocateOffset ;
  end = start+ PatLength ;
  unsigned int flags = boost::match_default;
  std::string reoverlap (pat) ;
  boost::regex eoverlap (pat) ;
  boost::match_results<std::string::const_iterator> ewhat ;
  std::string::const_iterator revstart, revend;
  if (offset > (4*PatLength))
    revstart = start  - (4 * PatLength) ;
  else
    revstart = seq.begin() ;
  if ((LocateOffset + PatLength) < (seq.length() - 4 * PatLength))
    revend = end  + (4 * PatLength) ; 
  else
    revend = seq.end() ;
  
  while (boost::regex_search (revstart, revend, ewhat, eoverlap, (boost::regex_constants::_match_flags) flags))
    {
      if ((ewhat[0].first >= start) && (ewhat[0].first <= end))
	{
	  return true ;
	}
       if ((ewhat[0].second >= start) && (ewhat[0].second <= end))
	{
	  return true ;
	}
      revstart = ewhat[0].first + 1 ;
      flags |= boost::match_prev_avail;
      flags |= boost::match_not_bob;
    }

  return false ;
}

void 
ClusterEnhancerClass::PrintResults (EnhancerParam &Param, int start, int end, Sequence::Fasta &fastaseq, string &results, int cluster, vector<string> &htm, int MotIndex, string &xml)

{

  int PrintCount ;
  int Position ;
  static string seq ;
  int from, to ;
  char ch ;
  char buffer [1000] ;
  char htmlbuffer[1000] ;
  long BeginPosition, FillLengthPosition, LengthPosition ;
  int BreakPosition ;
  string tempxmlbuffer ;

  vector <MotifClass *> Mot = EnhancerLists[MotIndex]->GetMotifs() ;

  BeginPosition = 0 ; 
  LengthPosition = 0 ;
  FillLengthPosition = 0 ;
  int PageCut = 20 ;

  tempxmlbuffer = "" ;

  xml += "<motifs>\n" ;
  if (!seq.length()) 
    {
      seq = fastaseq.GetSeq().c_str() ;
    }
  

    int BeginSubstr ;
    BeginSubstr = Mot[start]->GetPosition() - 20 ;
    if (BeginSubstr < 0)
      BeginSubstr = 0 ;
    tempxmlbuffer += "<sequence>\n<motifleft>\n" ;
    sprintf (buffer, "<TABLE BORDER=\"1\" CELLPADDING=\"2\" CELLSPACING=\"0\" WIDTH=\"200\" BGCOLOR=\"#FF0000\"><caption> <b> Cluster Sequence </b></caption><tr><td><TABLE BORDER=\"1\" CELLPADDING=\"2\" CELLSPACING=\"0\" WIDTH=\"100%\" BGCOLOR=\"#FFFFFF\"><tr><td><font color=black>%s</font>", (seq.substr(BeginSubstr, 20).c_str())) ;
   htm[4] +=  buffer ;
    sprintf (buffer, "%s", (seq.substr(BeginSubstr, 20).c_str())) ;
    tempxmlbuffer += buffer ;

   for (PrintCount = start ; PrintCount < end ; PrintCount++)
     {

       if (PrintCount == start)
	 {
	 }
       else
	 {
	   from = Mot[PrintCount-1]->GetPosition()+ Mot[PrintCount-1]->GetLength() ;
	   to = Mot[PrintCount]->GetPosition() - (Mot[PrintCount-1]->GetPosition() + Mot[PrintCount-1]->GetLength()) ;
 #ifdef DEBUG	  
	   if (to >= 0)
	     cout << seq.substr(from, to) << "  " ;
 #endif
	 }

       // Missing code 
       if (PrintCount == start)
	 {
	   BeginPosition = Mot[PrintCount]->GetPosition() ;
	   LengthPosition  =  Mot[PrintCount]->GetLength() ;
	 }
       else
	 {
	   if ((Mot[PrintCount-1]->GetPosition()+ Mot[PrintCount-1]->GetLength()) >
	       Mot[PrintCount]->GetPosition())
	   {

	     BeginPosition = Mot[PrintCount-1]->GetPosition() + Mot[PrintCount-1]->GetLength() ;
	     LengthPosition = Mot[PrintCount]->GetPosition() + Mot[PrintCount]->GetLength() - BeginPosition ;
	     if ((Mot[PrintCount]->GetPosition() + 
		  Mot[PrintCount]->GetLength()) < 
		 (Mot[PrintCount-1]->GetPosition()+ Mot[PrintCount-1]->GetLength()))
	       {
		 LengthPosition = 0 ;
	       }
	   }
	 }

       if (PrintCount+1 != end)
	 {
	   if ((Mot[PrintCount]->GetPosition() + 
		Mot[PrintCount]->GetLength()) > Mot[PrintCount+1]->GetPosition())
	     {
	       BeginPosition = Mot[PrintCount]->GetPosition() ;
	       LengthPosition = Mot[PrintCount+1]->GetPosition() - 
		 Mot[PrintCount]->GetPosition() ;
	     }
	   else
	     {
	       BeginPosition = Mot[PrintCount]->GetPosition() ;
	       LengthPosition = Mot[PrintCount]->GetLength() ;
	     }
	 }
       else
	 {
	       BeginPosition = Mot[PrintCount]->GetPosition() ;
	       LengthPosition = Mot[PrintCount]->GetLength() ;
	 }

       if (PrintCount != (end -1))
	 {
	   FillLengthPosition = Mot[PrintCount+1]->GetPosition() - 
	     (Mot[PrintCount]->GetPosition() + Mot[PrintCount]->GetLength()) ;
	 }
       else
	 {
	   FillLengthPosition = 20 ;
	 }


	   sprintf (htmlbuffer,  "<font color=%s>", color[(int)(Mot[PrintCount]->GetName()) - 0x41] ) ; 
       htm[4] += htmlbuffer ;
       if ((PageCut + LengthPosition) > 60)
	 {
	   BreakPosition = PageCut + LengthPosition - 60 ;
	   PageCut = LengthPosition - BreakPosition ;
	 }
       else
	 {
	   BreakPosition = -1 ;
	   PageCut += LengthPosition ;
	 }
       if (BreakPosition != -1)
	 {
	   sprintf (buffer, "%s<br>%s</font>", (seq.substr(BeginPosition, PageCut)).c_str(), (seq.substr(BeginPosition+PageCut, BreakPosition)).c_str()) ;
	   htm[4] += buffer ;
	   sprintf (buffer, "%s\n%s", (seq.substr(BeginPosition, PageCut)).c_str(), (seq.substr(BeginPosition+PageCut, BreakPosition)).c_str()) ;
	   tempxmlbuffer += buffer ;
	     //seq.substr(BeginPosition, PageCut) + "<br>" + 
	     //seq.substr(BeginPosition+PageCut, BreakPosition) + "</font>" ;
	   PageCut = BreakPosition ;
	 }
       else
	 {
	   sprintf (buffer, "%s</font>", (seq.substr(BeginPosition, LengthPosition)).c_str()) ;
	   htm[4] += buffer ;
	   sprintf (buffer, "%s", (seq.substr(BeginPosition, LengthPosition)).c_str()) ;
	   tempxmlbuffer += buffer ;
	 }

      if (FillLengthPosition > 0)
	{
	  if ((PageCut + FillLengthPosition) > 60)
	    {
	      int Advance = 0 ;
	      BreakPosition = PageCut + FillLengthPosition  ;
	      htm[4] += "<font color=black>" ;
	      while (BreakPosition > 60)
		{
		  sprintf (buffer, "%s<br>", seq.substr(BeginPosition + LengthPosition + Advance, 60 - PageCut).c_str()) ;
		  htm[4] +=  buffer ; 
		  sprintf (buffer, "%s\n", seq.substr(BeginPosition + LengthPosition + Advance, 60 - PageCut).c_str()) ;
		  tempxmlbuffer += buffer ;
 //seq.substr(BeginPosition + LengthPosition + Advance,
 // 60 - PageCut) + "<br>" ;
		  Advance += 60 - PageCut ;
		  BreakPosition -= (60 - PageCut)  ;
		  PageCut = 0 ;
		}

	      Advance -= 60 ;
	      sprintf (buffer, "%s</font>",
		       (seq.substr(BeginPosition + LengthPosition + Advance,BreakPosition)).c_str()) ;
	      htm[4] +=  buffer ;
	      sprintf (buffer, "%s",
		       (seq.substr(BeginPosition + LengthPosition + Advance,BreakPosition)).c_str()) ;
	      tempxmlbuffer += buffer ;
		//(seq.substr(BeginPosition + LengthPosition + Advance, 
		//				   BreakPosition)).c_str() + "</font>" ;
	      PageCut = BreakPosition ;
	    }

	  else 
	    {
	      sprintf (buffer, "<font color=black>%s</font>",
		       (seq.substr(BeginPosition + LengthPosition,FillLengthPosition)).c_str()) ;
	      htm[4] +=  buffer ;
	      sprintf (buffer, "%s",
		       (seq.substr(BeginPosition + LengthPosition,FillLengthPosition)).c_str()) ;
	      tempxmlbuffer += buffer ;
		//"<font color=black>" +  (seq.substr(BeginPosition + LengthPosition,
		//		   FillLengthPosition)).c_str() + "</font>" ;
	      PageCut += FillLengthPosition ;
	    }
	}


       sprintf (htmlbuffer, "<td><font color=%s>%c</font></td>",
		color[(int)Mot[PrintCount]->GetName() - 0x41], Mot[PrintCount]->GetName()) ;
       htm[0] += htmlbuffer ;
       sprintf (buffer, "<td>%s</td>", seq.substr(Mot[PrintCount]->GetPosition(),
						  Mot[PrintCount]->GetLength()).c_str()) ;
       htm[1] += buffer ;
 #ifdef DEBUG	  
       cout << "<" << Mot[PrintCount]->GetName() << ">" ;
 #endif
       BeginPosition = 0 ; 
       LengthPosition = 0 ;
       FillLengthPosition = 0 ;
       if (PrintCount < (end -1))
	 {
	   sprintf (htmlbuffer, "<td>%d</td>", 
		    Mot[PrintCount+1]->GetPosition() - (Mot[PrintCount]->GetPosition() +  Mot[PrintCount]->GetLength())) ;
	   htm[3] += htmlbuffer ;
	 }

      if (Mot[PrintCount]->GetType() == 'r')
	{
#ifdef DEBUG	  
	  cout << "   <----  " ;
#endif
	  htm[2] += "<td><img src=\"http://192.168.1.102/reverse.gif\"></td>" ;
	  ch = '-' ;
	}
      else
	{
#ifdef DEBUG	  
	  cout << "   ---->  " ;
#endif
	  htm[2] += "<td><img src=\"http://192.168.1.102/forward.gif\"></td>" ;
	  ch = '+' ;
	}

       char *Ch ;
       if (strstr(Param.GetChName(), Param.GetOrganismName()))
	 {
	   Ch = strchr (Param.GetChName(), '_') ;
	   ++Ch ;
	 }
       else
	 {
	   Ch = (char *) Param.GetChName () ;
	 }
       sprintf (buffer,  "%s\tcig\tsites\t%d\t%d\t.\t%c\t.\tcluster %d-%s-%c\n", Ch, Mot[PrintCount]->GetPosition()+1,(Mot[PrintCount]->GetPosition() + Mot[PrintCount]->GetLength()), ch, cluster, EnhancerLists[MotIndex]->GetFileName(), Mot[PrintCount]->GetName()) ;
       results += buffer ;
     }

  cluster++ ;
  
}


void BuildImageMapStr (long start, long end, long leftend, long rightend, int type, 
		       bool orientation, string & imagestr, string &info)
{

  char imagebuffer[2000] ;

  double  steps  = (double) MAX_WIDTH_PIXELS / (double) (rightend - leftend) ;
  int leftwidth ;

  //  cout << (rightend - leftend) << endl ;
  int width ; 
      imagestr += "<tr><td>" ;
  switch (type)
    {
    case ENHANCER :
      width = (int) ((double) (start - leftend) * steps) ;
      leftwidth = width ;
      sprintf (imagebuffer, "<img src=\"./clear.gif\" width=\"%d\" height=\"4\" />",
	       width) ;
      imagestr += imagebuffer ;

      width = (int) ((double) (end  - start) * steps) ;
      sprintf (imagebuffer, "<img src=\"./1.gif\" width=\"%d\" height=\"4\" />",
	   width) ;
      imagestr += imagebuffer ;
      sprintf (imagebuffer, "<img src=\"./clear.gif\" width=\"%d\" height=\"4\" />",
	       (MAX_WIDTH_PIXELS - width - leftwidth)) ;
      imagestr += imagebuffer ;
      imagestr += "</td></tr>" ;
      break ;
    case GENE :
      if (orientation)
	{
	  if (start > leftend ) 
	    width = (int) ((double) (start - leftend) * steps) ;
	  if (start < leftend)
	    width =  0 ;
	  sprintf (imagebuffer, "<img src=\"./clear.gif\" width=\"%d\" height=\"4\" />",
		   width) ;
	  imagestr += imagebuffer ;
	  leftwidth = width ;

	  if ((start > leftend) && (end < rightend))
 	    width = (int) ((double) (end  - start) * steps) ;
	  if ((start <leftend ) && (end < rightend))
	    width = (int) ((double) (end  - leftend) * steps) ;
	  if ((start < leftend ) && (end >  rightend))
	    width = (int) ((double) (rightend  - leftend) * steps) ;
	  if ((start > leftend) && (end > rightend))
 	    width = (int) ((double) (rightend  - start) * steps) ;

	  sprintf (imagebuffer, "<img src=\"./forward.gif\"/>") ;
	  imagestr += imagebuffer ;
	  sprintf (imagebuffer, "<img src=\"./2.gif\" width=\"%d\" height=\"4\" />",
		   width) ;
	  imagestr += imagebuffer ;
	}
      else
	{
	  if (end > leftend ) 
	    width = (int) ((double) (end - leftend) * steps) ;
	  if (end < leftend)
	    width =  0 ;
	  sprintf (imagebuffer, "<img src=\"./clear.gif\" width=\"%d\" height=\"4\" />",
		   width) ;
	  imagestr += imagebuffer ;
	  leftwidth = width ;
	  if ((end > leftend) && (start < rightend))
 	    width = (int) ((double) (start  - end) * steps) ;
	  if ((end <leftend ) && (start < rightend))
	    width = (int) ((double) (start  - leftend) * steps) ;
	  if ((end < leftend ) && (start >  rightend))
	    width = (int) ((double) (rightend  - leftend) * steps) ;
	  if ((end > leftend) && (start > rightend))
 	    width = (int) ((double) (rightend  - end) * steps) ;

	  sprintf (imagebuffer, "<img src=\"./2.gif\" width=\"%d\" height=\"4\" />",
		   width) ;
	  imagestr += imagebuffer ;
	  	  sprintf (imagebuffer, "<img src=\"./reverse.gif\"/>") ;
	  	  imagestr += imagebuffer ;
	}

      sprintf (imagebuffer, "<img src=\"./clear.gif\" width=\"%d\" height=\"4\" />",
	       (MAX_WIDTH_PIXELS - width - leftwidth)) ;
      imagestr += imagebuffer ;
      imagestr += "</td></tr>" ;
      break ;
    default : 
      ;
    }


  imagestr += "<tr><td>" ;
  if (width > 20) 
    width -=20 ;
  sprintf (imagebuffer, "<img src=\"./clear.gif\" width=\"%d\" height=\"4\" />",
	   leftwidth) ;
  imagestr += imagebuffer ;
  sprintf (imagebuffer, "<font size=\"2\"><b>%s</b></font></td></tr>", info.c_str()) ;
  imagestr += imagebuffer ;
  return ;
}
