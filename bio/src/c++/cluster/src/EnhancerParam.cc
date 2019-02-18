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
  char temp[100] ;
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
	  case OVERLAP :
	    status = getline(readfile, thetext) ;
	    strcpy (temp, thetext.c_str()) ;
	    while (strstr(temp, OverlapTag) == NULL)
	      {
		PatternLists[temp[0] - 0x41]->SetOverlap(temp[2] == '0' ? false : true) ;
		status = getline(readfile, thetext) ;
		if (!status )
		  break ;
		strcpy (temp, thetext.c_str()) ;
	      }
	    break ;
	  case ANYGENE :
	    status = getline(readfile, thetext) ;
	    strcpy (temp, thetext.c_str()) ;
	    while (strstr(temp, AnyGeneTag) == NULL)
	      {
		SetAnyGene(temp[6] == '0' ? false : true) ;
		status = getline(readfile, thetext) ;
		if (!status )
		  break ;
		strcpy (temp, thetext.c_str()) ;
	      }
	    break ;
	  case PRECEDENCE :
	    char rules[300] ;
	    status = getline(readfile, thetext) ;
	    strcpy (temp, thetext.c_str()) ;
	    while (strstr(temp, PrecedenceTag) == NULL)
	      {
		strcpy(rules, &temp[6]) ;
		if (strlen(temp) >  6)
		  strcat (rules, ",") ;
		else
		  rules[0] = '\0' ;
		SetPrecedence (rules) ;
		status = getline(readfile, thetext) ;
		if (!status )
		  break ;
		strcpy (temp, thetext.c_str()) ;
	      }
	    break ;
	  case USER :
	    char user[100] ;
	    status = getline(readfile, thetext) ;
	    strcpy (temp, thetext.c_str()) ;
	    while (strstr(temp, UserTag) == NULL)
	      {
		strcpy(user, &temp[5]) ;
		SetSave (user) ;
		status = getline(readfile, thetext) ;
		if (!status )
		  break ;
		strcpy (temp, thetext.c_str()) ;
	      }
	    break ;
	  case DISPLAY :
	    status = getline(readfile, thetext) ;
	    strcpy (temp, thetext.c_str()) ;
	    while (strstr(temp, DisplayTag) == NULL)
	      {
		SetDisplay(atoi (&temp[6])) ;
		status = getline(readfile, thetext) ;
		if (!status )
		  break ;
		strcpy (temp, thetext.c_str()) ;
	      }
	    break ;
	  case SITES :
	    status = getline(readfile, thetext) ;
	    strcpy (temp, thetext.c_str()) ;
	    while (!strstr(temp, Sites))
	      {
		SetNumberofBindingSites(atoi (&temp[7])) ;
		status = getline(readfile, thetext) ;
		if (!status)
		  break ;
		strcpy(temp, thetext.c_str()) ;
	      }
	    break ;
	  case CONSTRAINT :
	    status = getline(readfile, thetext) ;
	    strcpy(temp, thetext.c_str()) ;
	    while (!strstr(temp, Constraint))
	      {
      		SetWidthConstraint(atoi (&temp[6])) ;
		status = getline(readfile, thetext) ;
		if (!status)
		  break ;
		strcpy(temp, thetext.c_str()) ;
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
	  case NOOVERLAP :
	    status = getline(readfile, thetext) ;
	    strcpy (temp, thetext.c_str()) ;
	    while (!strstr(temp, NoOverLapTag))
	      {
		if (strlen (temp) > 5)
		  SetNoOverLapString(&temp[5]) ;
		else
		  SetNoOverLapString((char *) "") ;
		status = getline(readfile, thetext) ;
		if (!status)
		  break ;
		strcpy(temp, thetext.c_str()) ;
	      }
	    break ;
	  case DIRECTION :
	    status = getline(readfile, thetext) ;
	    strcpy (temp, thetext.c_str()) ;
	    while (!strstr(temp, DirectionTag))
	      {
		SetDirection(temp[6]) ;
		status = getline(readfile, thetext) ;
		if (!status)
		  break ;
		strcpy(temp, thetext.c_str()) ;
	      }
	    break ;
	  case ORGANISM :
	    status = getline(readfile, thetext) ;
	    strcpy (temp, thetext.c_str()) ;
	    while (!strstr(temp, Organism))
	      {
		SetOrganismName(&temp[5]) ;
		status = getline(readfile, thetext) ;
		if (!status)
		  break ;
		strcpy(temp, thetext.c_str()) ;
	      }
	    break ;
	  case DIRECTORY :
	    status = getline(readfile, thetext) ;
	    strcpy (temp, thetext.c_str()) ;
	    while (!strstr(temp, DatabaseRootDir))
	      {
		SetDirectoryName(&temp[5]) ;
		status = getline(readfile, thetext) ;
		if (!status)
		  break ;
		strcpy(temp, thetext.c_str()) ;
	      }
	    break ;
	  case GENENAMES :
	    status = getline(readfile, thetext) ;
	    strcpy (temp, thetext.c_str()) ;
	    while (!strstr(temp, GeneNamesTag))
	      {
		//		fprintf (stderr, "%s gene names\n", &temp[5]) ;
		SetGeneNames(&temp[5]) ;
		status = getline(readfile, thetext) ;
		if (!status)
		  break ;
		strcpy(temp, thetext.c_str()) ;
	      }
	    break ;
	  case BOOLEANCONSTRAINT :
	    status = getline(readfile, thetext) ;
	    strcpy(temp, thetext.c_str()) ;
	    while (!strstr(temp, Boolean))
	      {
		SetConditionConstraint(&temp[10]) ;
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

    //    SetSaveFileName () ;

#ifdef DEBUG
    cout << "<input>" << endl ;
    cout << " ***************** Enhancer Input parameters *****************************" << endl ;
    vector <PatternClass *> DebugPattern ;
    PatternClass *DebugPatClass ;
    int count ;
    DebugPattern = GetPatternLists () ;
    for (count = 0 ; count < DebugPattern.size() ; count++) 
      {
	fprintf (stdout, "The pattern is %c \t %s \n", PatternLists[count]->GetPatternName(), PatternLists[count]->GetPattern()) ;
      }
    fprintf (stdout, "The filename is %s\n", GetDirectoryName()) ;
    fprintf (stdout, "The width is %d\n", GetWidthConstraint()) ;
    fprintf (stdout, "The number of binding sites  is %d\n", GetNumberofBindingSites()) ;
    fprintf (stdout, "The display value   is %d\n", GetDisplay()) ;
    fprintf (stdout, "The boolean condition is %s\n", GetConditionConstraint()) ;
    cout << "</input>" << endl << endl ;
    //    exit (0) ;

#endif
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

  if (strstr(InputToken, UserTag))
    return USER ;

}

EnhancerParam::~EnhancerParam ()
{

   int dsize = PatternLists.size();
   vector<PatternClass *>::iterator startIterator;
   for( int i=0; i < dsize; i++ ) {
     startIterator = PatternLists.begin();
     PatternClass *temp = *startIterator ;
     PatternLists.erase( startIterator );
     delete temp ;
   }
}

bool 
EnhancerParam::FindPrecedence(char *pattern)
{
  int PCount ;
  if (PrecedenceCount == 0)
    return true ;
  for (PCount = 0 ; PCount < PrecedenceCount ;
       PCount++)
    {
      if (CheckRegex (Precedence[PCount], pattern))
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
EnhancerParam::CheckFiles (char *errorfilename)
{

  int DebugCount ;
  char PatternFileName[1000] ;
  namespace fs = boost::filesystem;

  for (DebugCount = 0 ; DebugCount < PatternLists.size() ; DebugCount++)
    {
      strcpy (PatternFileName, GetSaveFileName()) ;
      strcat (PatternFileName, PatternLists[DebugCount]->GetPattern()) ;
      strcat (PatternFileName, "/") ;
      strcat (PatternFileName, GetOrganismName()) ;
      strcat (PatternFileName, "/") ;
      if (!fs::exists(PatternFileName)) 
	{
	   ofstream ErrorFile(errorfilename, ios::app) ;
	   ErrorFile << PatternFileName << "does not exist" << endl ;
	   ErrorFile.close() ;
	}
     }
 }

char * EnhancerParam::GetCacheFileName (int FileCount)

 {

   string PatternFileName ;
   namespace fs = boost::filesystem;

  PatternFileName =  GetSaveFileName() ;
  PatternFileName += PatternLists[FileCount]->GetPattern() ;
  PatternFileName += "/" ;
  PatternFileName += GetOrganismName() ;
  PatternFileName +=  "/" ;
  PatternFileName += GetChName() ;
  PatternFileName += ".res" ;
  if (!fs::exists(PatternFileName))
    {
      return NULL ;
    }
  else
    {
      return ((char *) PatternFileName.c_str()) ;
    }

}

char * EnhancerParam::GetSummaryFileName (int FileCount)

 {

   string PatternFileName ;
   namespace fs = boost::filesystem;

  PatternFileName =  GetSaveFileName() ;
  PatternFileName += PatternLists[FileCount]->GetPattern() ;
  PatternFileName += "/" ;
  PatternFileName += GetOrganismName() ;
  PatternFileName +=  "/" ;
  PatternFileName += GetChName() ;
  PatternFileName += ".sum" ;
  if (!fs::exists(PatternFileName))
    {
      return NULL ;
    }
  else
    {
      return ((char *) PatternFileName.c_str()) ;
    }

}


