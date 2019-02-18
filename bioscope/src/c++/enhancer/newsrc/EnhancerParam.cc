 #include <XmlDefines.h>
 #include <SeqCodeDefines.h>

 #include <EnhancerParam.h>
  #include <EnhancerClass.h>

 int GetToken (const char *) ;

 void 
 EnhancerParam::ReadFile (char *filename) 
 {

   PatternClass *PatternInput ;
   ifstream readfile(filename); 
   string thetext; 
   char temp[1000] ;
   void *status ;

   status = getline(readfile, thetext) ;
   while (status)
       { 
	 switch (GetToken (thetext.c_str())) 
	   {
	   case EXPRESSION :
	     status = getline(readfile, thetext) ;
	     strcpy (temp, thetext.c_str()) ;
	     while (strstr(temp, ExpressionTag) == NULL)
	       {
		 PatternInput = new PatternClass ;
		 PatternInput->SetPatternName(temp[0]) ;
		 PatternInput->SetPattern(&temp[2]) ;
		 PatternLists.push_back(PatternInput) ;
		 status = getline(readfile, thetext) ;
		 if (!status )
		   break ;
		 strcpy (temp, thetext.c_str()) ;
	       }
	     break ;
	   case MOTIFNAMES :
	     status = getline(readfile, thetext) ;
	     strcpy (temp, thetext.c_str()) ;
	     while (strstr(temp, MotifNamesTag) == NULL)
	       {
		 InsertPatternName (temp[0], &temp[2])  ;
		 status = getline(readfile, thetext) ;
		 if (!status )
		   break ;
		 strcpy (temp, thetext.c_str()) ;
	       }
	     break ;
	   case CHROMOSOME :
	     status = getline(readfile, thetext) ;
	     strcpy (temp, thetext.c_str()) ;
	     while (!strstr(temp, Chromosome))
	       {
		 SetChName(&temp[5]) ;
		 status = getline(readfile, thetext) ;
		 if (!status)
		   break ;
		 strcpy(temp, thetext.c_str()) ;
	       }
	     break ;
	   default :
	     break ;
	   }
	 status = getline(readfile, thetext) ;
       }

     readfile.close(); 
 }

 void 
 PatternClass::reversePattern () 
     {
       int PatternLength = OriginalPattern.length () ;
       int start = 0 ;
       int count = 1 ;
       char *c_pattern = (char *) OriginalPattern.c_str() ;

       char tempstr[1000] ;
       char revstr[1000] ;

       memset (tempstr, '\0', 1000) ;
       memset (revstr, '\0', 1000) ;
       while (count < PatternLength) 
	 {

	   while ((!(isalpha (c_pattern[count]))) || isdigit(c_pattern[count]))
	     {
	       count++ ;
	       if (count >= PatternLength) 
		 break ;
	     }

	   strncpy (tempstr, &c_pattern[start], count - start) ;
	   strcat (tempstr, revstr) ;
	   strcpy (revstr, tempstr) ;

	   memset(tempstr, '\0', 1000) ;
	   start = count ;
	   count++ ;
	 }
       if (strlen(revstr) < strlen(c_pattern ) )
       {
	 tempstr[0] = c_pattern[strlen(c_pattern)-1] ;
	 strcat (tempstr, revstr) ;
	 strcpy (revstr, tempstr) ;
       }

       for (count = 0 ; count < strlen(revstr) ; count++)
	 {
	   if (isalpha(revstr[count]))
	       revstr[count] = ComplementCode[revstr[count] - 0x41][0] ;
	 }
       ReversePattern = revstr ;
     }

 void 
 PatternClass::TranslateToRegex ( string &PatternString, string &Destination) 
     {

       int PatternLength ;
       char tempbuffer[1000] ;
       char *c_pattern = (char *) PatternString.c_str() ;

       for (PatternLength = 0 ; PatternLength < strlen(c_pattern) ;
	    PatternLength++) 
	 {
	   if (isalpha(c_pattern[PatternLength]) )
	     {
	       Destination += TransformCode[c_pattern[PatternLength] - 0x41 ] ;
	     }
	   else if (isdigit(c_pattern[PatternLength]))
	     {
	       sprintf (tempbuffer, "{0,%c}", c_pattern[PatternLength]) ;
	       Destination += tempbuffer ;
	     }
	   else
	     {
	       sprintf (tempbuffer, "%c", c_pattern[PatternLength]) ;
	       Destination += tempbuffer ;
	     }
	 }
     }

 int GetToken (const char *InputToken) 
 {

   if (strstr(InputToken, ExpressionTag)) 
     return EXPRESSION ;

   if (strstr(InputToken, Sites))
     return SITES ;

   if (strstr(InputToken, Constraint)) 
     return CONSTRAINT ;

   if (strstr(InputToken, Chromosome))
     return CHROMOSOME ;

   if (strstr(InputToken, DatabaseRootDir))
     return DIRECTORY ;

   if (strstr(InputToken, Boolean))
     return BOOLEANCONSTRAINT ;

   if (strstr(InputToken, Organism))
     return ORGANISM ;

   if (strstr(InputToken, NoOverLapTag))
     return NOOVERLAP ;

   if (strstr(InputToken, OverlapTag))
     return OVERLAP ;

   if (strstr(InputToken, DisplayTag))
     return DISPLAY ;

   if (strstr(InputToken, DirectionTag))
     return DIRECTION ;

   if (strstr(InputToken, GeneNamesTag))
     return GENENAMES ;

   if (strstr(InputToken, AnyGeneTag))
     return ANYGENE ;

   if (strstr(InputToken, PrecedenceTag))
     return PRECEDENCE ;

   if (strstr(InputToken, SaveTag))
     return SAVE ;

   if (strstr(InputToken, MotifNamesTag))
     return MOTIFNAMES ;

   if (strstr(InputToken, MinTag))
     return MIN ;

   if (strstr(InputToken, MaxTag))
     return MAX ;

 }

 EnhancerParam::~EnhancerParam ()
 {

    int dsize = PatternLists.size();
    vector<PatternClass *>::iterator startIterator;
    for( int i=0; i < dsize; i++ ) {
      startIterator = PatternLists.begin();
      PatternClass *temp = *startIterator ;
      PatternLists.erase( startIterator );
      if (temp)
	delete temp ;
    }
 }

 bool 
 EnhancerParam::FindPrecedence(char *pattern)
 {
   int PCount ;
   if (PrecedenceCount == 0)
     {
       return true ;
     }
   for (PCount = 0 ; PCount < PrecedenceCount ;
	PCount++)
     {

       if (CheckRegex (Precedence[PCount], pattern))
	 {
	   
	   return true ;
	 }
       if (CheckRegex (PrecedenceRev[PCount], pattern))
	 {
	   return true ;
	 }
     }

   return false ;

 }

 bool EnhancerParam::CheckRegex (char *regex, char *pat)
 {

   std::string re (regex) ;
   boost::regex expression (regex) ;
   unsigned long  offset ;
   int PrecedenceCount ;
   std::string::const_iterator start, end;
   std::string seq (pat) ;

   start = seq.begin() ;
   end = seq.end() ;
   boost::match_results<std::string::const_iterator> what ;
   unsigned int flags = boost::match_default;
   if (boost::regex_search (start, end, what, expression, (boost::regex_constants::_match_flags) flags))
     {
       seq.erase () ;
       re.erase () ;
       return true ;
     }
   else
     {
       seq.erase () ;
       re.erase () ;
       return false ;
     }
 }

void 
 EnhancerParam::WriteConfigFile ()

 {

   char CompleteConfigFileName [1000] ;
   int PatCount ;
  ofstream ConfigFile ;

  strcpy(CompleteConfigFileName, GetSaveFileName()) ;
  strcat (CompleteConfigFileName, "config.txt") ;
  ConfigFile.open (CompleteConfigFileName, ios::out) ;

  ConfigFile << "<expressions>" << endl ;
  for (PatCount = 0 ; PatCount < PatternLists.size() ; PatCount++)
    {
      ConfigFile << PatternLists[PatCount]->GetPatternName() << "=" <<
	PatternLists[PatCount]->GetPattern() << endl ;
    }
  ConfigFile << "</expressions>" << endl ;
  ConfigFile << "<overlap>" <<  endl ;
  for (PatCount = 0 ; PatCount < PatternLists.size() ; PatCount++)
    {
      ConfigFile << PatternLists[PatCount]->GetPatternName() << "=" <<
	(PatternLists[PatCount]->GetOverlap() ? "1" : "0") << endl ;
    }
  ConfigFile << "</overlap>" <<  endl ;
  ConfigFile << "<organism>" << endl ;
  ConfigFile << "name=" << GetOrganismName() << endl ;
  ConfigFile << "</organism>" << endl ;
  ConfigFile << "<directory>" << endl ;
  ConfigFile << "name=" << GetDirectoryName() << endl ;
  ConfigFile << "</directory>" << endl ;
  ConfigFile << "<sites>" << endl ;
  ConfigFile << "number=" << GetNumberofBindingSites() << endl ;
  ConfigFile << "</sites>" << endl ;
  ConfigFile << "<constraint>" << endl ;
  ConfigFile << "width=" << GetWidthConstraint() << endl ;
  ConfigFile << "</constraint>" << endl ;
  ConfigFile << "<boolean>" << endl ;
  ConfigFile << "condition=" << GetConditionConstraint() << endl ;
  ConfigFile << "</boolean>" << endl ;
  ConfigFile << "<display>" << endl ;
  ConfigFile << "width=" << GetDisplay() << endl ;
  ConfigFile << "</display>" << endl ;
  ConfigFile << "<direction>" << endl ;
  ConfigFile << "value=" << GetDirection() << endl ;
  ConfigFile << "</direction>" << endl ;
  ConfigFile << "<nooverlap>" << endl ;
  ConfigFile << "list=" << GetNoOverLapString() << endl ;
  ConfigFile << "</nooverlap>" << endl ;

  ConfigFile.close() ;
}
