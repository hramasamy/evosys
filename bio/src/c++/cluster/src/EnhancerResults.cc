#include <EnhancerResults.h>
#include <string.h>
#include <WebServer.h>

void 
ClusterEnhancerClass::PrintResults (EnhancerParam &Param, int start, int end, Sequence::Fasta &fastaseq, string &results, int cluster, vector<string> &htm, fstream &SaveCluster)

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

  //  return ;
      BeginPosition = 0 ; 
      LengthPosition = 0 ;
      FillLengthPosition = 0 ;
      int PageCut = 20 ;


  if (!seq.length()) 
    {
      seq = fastaseq.GetSeq().c_str() ;
    }
  

#ifdef DEBUG	  
  cout << "******************* <Result> Match found at " << EnhancerLists[start]->GetPosition() << "********************" << endl ;
#endif

  htm[4] +=  "<TABLE BORDER=\"1\" CELLPADDING=\"2\" CELLSPACING=\"0\" WIDTH=\"200\" BGCOLOR=\"#FF0000\"><caption> <b> Cluster Sequence </b></caption><tr><td><TABLE BORDER=\"1\" CELLPADDING=\"2\" CELLSPACING=\"0\" WIDTH=\"100%\" BGCOLOR=\"#FFFFFF\"><tr><td><font color=black>" +  seq.substr(EnhancerLists[start]->GetPosition() - 20, 20) + "</font>" ;
  
  for (PrintCount = start ; PrintCount < end ; PrintCount++)
    {

      if (PrintCount == start)
	{
#ifdef DEBUG	  
	  cout << seq.substr(EnhancerLists[start]->GetPosition() - 100, 100) << "  " ;
#endif
	}
      else
	{
	  from = EnhancerLists[PrintCount-1]->GetPosition()+ EnhancerLists[PrintCount-1]->GetLength() ;
	  to = EnhancerLists[PrintCount]->GetPosition() - (EnhancerLists[PrintCount-1]->GetPosition() + EnhancerLists[PrintCount-1]->GetLength()) ;
#ifdef DEBUG	  
	  if (to >= 0)
	    cout << seq.substr(from, to) << "  " ;
#endif
	}
      
      if (PrintCount == start)
	{
	  BeginPosition = EnhancerLists[PrintCount]->GetPosition() ;
	  LengthPosition  =  EnhancerLists[PrintCount]->GetLength() ;
	}
      else
	{
	  if ((EnhancerLists[PrintCount-1]->GetPosition()+ EnhancerLists[PrintCount-1]->GetLength()) >
	      EnhancerLists[PrintCount]->GetPosition())
	  {

	    BeginPosition = EnhancerLists[PrintCount-1]->GetPosition() + EnhancerLists[PrintCount-1]->GetLength() ;
	    LengthPosition = EnhancerLists[PrintCount]->GetPosition() + EnhancerLists[PrintCount]->GetLength() - BeginPosition ;
	    if ((EnhancerLists[PrintCount]->GetPosition() + 
		 EnhancerLists[PrintCount]->GetLength()) < 
		(EnhancerLists[PrintCount-1]->GetPosition()+ EnhancerLists[PrintCount-1]->GetLength()))
	      {
		LengthPosition = 0 ;
	      }
	  }
	}
      
      if (PrintCount != end)
	{
	  if ((EnhancerLists[PrintCount]->GetPosition() + 
	       EnhancerLists[PrintCount]->GetLength()) > EnhancerLists[PrintCount+1]->GetPosition())
	    {
	      BeginPosition = EnhancerLists[PrintCount]->GetPosition() ;
	      LengthPosition = EnhancerLists[PrintCount+1]->GetPosition() - 
		EnhancerLists[PrintCount]->GetPosition() ;
	    }
	  else
	    {
	      BeginPosition = EnhancerLists[PrintCount]->GetPosition() ;
	      LengthPosition = EnhancerLists[PrintCount]->GetLength() ;
	    }
	}

      if (PrintCount != (end -1))
	{
	  FillLengthPosition = EnhancerLists[PrintCount+1]->GetPosition() - 
	    (EnhancerLists[PrintCount]->GetPosition() + EnhancerLists[PrintCount]->GetLength()) ;
	}
      else
	{
	  FillLengthPosition = 20 ;
	}

      
	  sprintf (htmlbuffer,  "<font color=%s>", color[(int)(EnhancerLists[PrintCount]->GetName()) - 0x41] ) ; 
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
	  htm[4] += seq.substr(BeginPosition, PageCut) + "<br>" + seq.substr(BeginPosition+PageCut, BreakPosition) + "</font>" ;
	  PageCut = BreakPosition ;
	}
      else
	{
	  htm[4] += seq.substr(BeginPosition, LengthPosition) + "</font>" ;
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
		 htm[4] +=  seq.substr(BeginPosition + LengthPosition + Advance,
						       60 - PageCut) + "<br>" ;
		 Advance += 60 - PageCut ;
		 BreakPosition -= (60 - PageCut)  ;
		 PageCut = 0 ;
	       }

	     Advance -= 60 ;
	     htm[4] +=  seq.substr(BeginPosition + LengthPosition + Advance, 
				   BreakPosition) + "</font>" ;
	     PageCut = BreakPosition ;
	   }
	     
	 else 
	   {
	     htm[4] +=  "<font color=black>" +  seq.substr(BeginPosition + LengthPosition,
							   FillLengthPosition) + "</font>" ;
	     PageCut += FillLengthPosition ;
	   }
       }

      sprintf (htmlbuffer, "<td><font color=%s>%c</font></td>",
	       color[(int)EnhancerLists[PrintCount]->GetName() - 0x41], EnhancerLists[PrintCount]->GetName()) ;
      htm[0] += htmlbuffer ;
      htm[1] += "<td>" +  seq.substr(EnhancerLists[PrintCount]->GetPosition(),
					 EnhancerLists[PrintCount]->GetLength()) + "</td>" ;
#ifdef DEBUG	  
      cout << "<" << EnhancerLists[PrintCount]->GetName() << ">" ;
#endif
      if (EnhancerLists[PrintCount]->GetType() == 'r')
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
      BeginPosition = 0 ; 
      LengthPosition = 0 ;
      FillLengthPosition = 0 ;
      if (PrintCount < (end -1))
	{
	  sprintf (htmlbuffer, "<td>%d</td>", 
		   EnhancerLists[PrintCount+1]->GetPosition() - (EnhancerLists[PrintCount]->GetPosition() +  EnhancerLists[PrintCount]->GetLength())) ;
	  htm[3] += htmlbuffer ;
	  //	  sprintf (htmlbuffer, "<td>%d</td>", 
	  //		   EnhancerLists[PrintCount]->GetPosition() + EnhancerLists[PrintCount]->GetLength()) ;
      //	  htm[4] += htmlbuffer ;
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
      EnhancerLists[PrintCount]->SaveIntoFile (SaveCluster) ;
      sprintf (buffer,  "%s\tcig\tsites\t%d\t%d\t.\t%c\t.\tcluster %d-%c\n", Ch, EnhancerLists[PrintCount]->GetPosition()+1,(EnhancerLists[PrintCount]->GetPosition() + EnhancerLists[PrintCount]->GetLength()), ch, cluster, EnhancerLists[PrintCount]->GetName()) ;
      results += buffer ;
    }
#ifdef DEBUG	  

  cout << seq.substr(EnhancerLists[end -1]->GetPosition(), 100)  << "  " << endl ;
  
  cout << "******************************         </Result>             ********************" << endl ;
#endif

  cluster++ ;
#ifdef DEBUG	  
  cout << endl << endl ;
#endif
  
}

