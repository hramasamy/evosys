// ************************************************************************
// Name:     NSTEMB.CC
// Author:   Kiduk Yang, 12/02/96
//             modified, 03/02/98
//             modified, 04/30/98
// ------------------------------------------------------------------------
// Description: NICE STEMMER IN BATCH MODE
//   Executes the appropriate stemmer from the command line.
// ------------------------------------------------------------------------
// ARGUMENT: a letter indicating the stemmer algorithm to apply
//           name of the file to be stemmed
//           name for the output file
//  optional arguments --
//           flag to activate sentence recognition
// INPUT:    stopword list (random access file)
//           dictionary (random access file)
//           exception list (random access file)
// OUTPUT:   a file containing stemmed input file
// ------------------------------------------------------------------------
// NOTE1:    this is a command line stemmer for large file stemming.
//           to use non-default dictionary or stoplist
// NOTE2:    max. word length for nice stemmer is 50 characters
// NOTE3:    this version of stemmer excludes all numeric word, 
//           word that starts with special character,
//           word that includes special-character (exception: .'-_\/),
//           word longer than 25 characters and all alphanumeric
// ************************************************************************

#include <ctype.h>
#include <fstream.h>
#include <iomanip.h>
#include <iostream.h>
#include <string>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "define.h"        // global constants declarations
#include "string.h"        // string functions
#include "simple.h"        // simple stemmer modules
#include "display.h"       // screen display modules
#include "findw.h"         // binary word search module
#include "findw2.h"        // tab-binary word search module
#include "pporter.h"       // porter stem conditons modules
#include "porter.h"        // porter stemmer modules
#include "class.h"         // class declaration
#include "krovetz.h"       // inflectional stemmer modules
#include "combo.h"         // combination stemmer modules
#include "hash.h"          //  hash function
#include "lists.h"         //  linked list classes
#include "com_jeteye_utils_Stemming.h" 

void perform_stemming (char *, string &) ;


 // display input error message 
 void errmsg() {
   cout<<"\nArguments missing: correct argument order is\n"
       <<"stemmer selection, infile name, outfile name.\n"
       <<"( a = Simple stemmer,  b = Porter stemmer)\n"
       <<"( c = Krovetz stemmer, d = Combo stemmer)\n"
       <<"optional arguments: \n" 
       <<"arg4= 1 for syntactic processing, 0 otherwise (default=0)\n"
       <<"if you want to use non-default random access files,\n"
       <<"   please use nmenu to replace the defalut files first.\n\n";
   exit(8);
 }


 // returns 1 if words should be excluded from indexing
 // -- all numeric word
 // -- starts with special character
 // -- includes special-character (exception: .'-_\/)
 // -- more than 25 characters and all alphanumeric
 // NOTE: should be applied after 
 //       all word-boundry punctuations have been removed.
 int number(char *wd) {
     int type=0, allnum=1;
     int wl;
     wl=strlen(wd)-1;
     for(int i=0;i<=wl;i++) {
	 if ((wd[i]>='a' && wd[i]<='z')) type=1;
	 else if ((wd[i]>='A' && wd[i]<='Z')) type=2;
	 else if ((wd[i]>='0' && wd[i]<='9')) type=3;
	 else if (wd[i]=='\'' || wd[i]=='_' || wd[i]=='.' ||
		  wd[i]=='-' || wd[i]=='/' || wd[i]=='\\' ) type=4;
	 else type=5;
	 // exclude if word starts with special char
	 if (i==0 && (type>=4)) return(1);
	 // exclude if word embedds special char (except .'-_/\)
	 else if (i>0 && type==5) return(2);
	 // check for NUM
	 if (type<=2) allnum=0;
     }
     if (allnum || (wl>25 && type<=3)) return(1);
     return(0);
 }


JNIEXPORT jstring JNICALL Java_com_jeteye_utils_Stemming_stemming
  (JNIEnv *env , jobject object, jstring query)
{

    char buf[1000];
    string Total ;
    const char *str = env->GetStringUTFChars(query, 0);
    perform_stemming ((char *) str, Total) ;
    env->ReleaseStringUTFChars(query, str);
    return env->NewStringUTF(Total.c_str());

}


void perform_stemming (char *line, string & TotalWords) 
{

char menu[2];                // menu selection input
char infile[FFNL];           // input file name
char outfile[FFNL];          // output file name
char map[FFNL];              // mapped directory
char word[WL];               // word to be stemmed.
char rest[BWL];              // input line holder
int process;                 // word process flag
int fword=0;                 // 1 if begining of a sentence
char pmark[2];               // sentence boundary type
int indict;                  // 1 if dictionary word
int syntactic;               // sentence recognition flag
int wdcnt;                   // number of words outputed per document
fstream swlist;              // random access file for stopwords
fstream dict;                // random access file for dictionary
fstream xlist;               // random access file for exception list

 char dirpath[1000] ;
 char dircpy[1000] ;

 if (getenv("TOMCAT_HOME") == NULL)
 {
     strcpy (dirpath, "./") ;
 }
 else
 {
     strcpy (dirpath, getenv("TOMCAT_HOME")) ;
     strcat (dirpath, "/webapps/jeteye/resources/") ;
 }

    strcpy (menu, "d") ;
    indict = 0 ;

    //-----------------------------------------------
    // process input file by word,
    // 1. remove stopword
    // 2. call the appropriate stemmer
    //-----------------------------------------------

    // open random access files for stopword list, dictionary, & exception list.
    strcpy (dircpy, dirpath) ;
    swlist.open(strcat (dircpy, RSWLIST),ios::in);
    strcpy (dircpy, dirpath) ;
    dict.open(strcat (dircpy, RDICT),ios::in);
    strcpy (dircpy, dirpath) ;
    xlist.open(strcat (dircpy, RXLIST),ios::in);

    syntactic=0;
    wdcnt=0;
    strcpy(rest,"dummy");
    
    while (strlen(rest)>1) {
	
	// get the next word
	split(line,' ',word,rest);
	if (strlen(word)==0) {
	    strcpy(line,rest);
	    continue;
	}
	
	// reset process flag for each word.
	process=1;
	
	// strip off punctuation after "sentence" recognition
	// for noun-noun phrase construction
	fword++;
	if ( suffix(word,",") || suffix(word,".") || suffix(word,"!") || 
	     suffix(word,"?") || suffix(word,";") ) {
	    // paragraph break: ".." created by indx1 modue
	    // abbreviations: first word ending with period
	    // initials: a single character with period
	    if (suffix(word,"..")) {
		fword=0; 
		strcpy(pmark,"..");
	    }
	    else if (fword>1 && strlen(word)>2 && strcmp(word,"vs.")!=0) {
		fword=0; 
		pmark[0]= word[strlen(word)-1];
		pmark[1]= ' ';
	    }
	}
	punct2(word);
	
	// skip word if stopword, a single character word, or 
	// bad word (see NUMBER function)
	if (findw(1,word,swlist) || (strlen(word)<2) || (number(word)>0)) 
	    process=0;

	// stem the word.
	if (process==1) {
	    if (menu[0]=='a') Simple(word);
	    else if (menu[0]=='b') Porter(word);
	    else if (menu[0]=='c') 
		indict=Krovetz_i(word,dict,xlist,fword);
	    else if (menu[0]=='d') combostem(word,dict,xlist,fword);
	    dict.clear();
	}
	

	if (process) {
	
	    // eliminate non-capitalized 2-character words not in Dictionary
	    if (strlen(word)<=2 && !indict)
	    {
		if (!(word[1]>='A' && word[1]<='Z'))
		{
		    if (fword==1 && wdcnt>0 && syntactic) 
		    {
		    }
		}
		
	    }
	    else 
	    {
		if (fword==1 && wdcnt>0 && syntactic)
		{
		}
		else 
		{
		    if (TotalWords.length () == 0) 
		    {
			TotalWords = word ;
		    }
		    else
		    {
			TotalWords += " " ;
			TotalWords += word ;
		    }

		    wdcnt++;
		}
	    }
	}
	
	strcpy(line,rest);
	
    }
    swlist.close();
    
}  // end main
