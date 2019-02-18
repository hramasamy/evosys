// ************************************************************************
// Name:     NSTEM.CC
// Author:   Kiduk Yang, 11/05/96
//             modified, 11/13/96
// ------------------------------------------------------------------------
// Description: STEMMER SHELL
//   1. Displays the User Interface (Program description and Menu)
//   2. Executes the appropriate stemmer based on the user input.
// ------------------------------------------------------------------------
// INPUT:    name of the file to be stemmed
//           name for the output file
//           name of the file containing words to be removed (default given)
//           name of the file containing words to remain untouched (optional)
//           a number indicating the type of file to be stemmed
//           a letter indicating the stemmer algorithm to apply
// OUTPUT:   a file containing stemmed input file
// ------------------------------------------------------------------------
// NOTE:     Nice Stemmer main module
// NOTE2:    max. word length for nice stemmer is 50 characters
// ************************************************************************


#include <ctype.h>
#include <fstream.h>
#include <iomanip.h>
#include <iostream.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "define.h"        // constants declarations
#include "string.h"        // string functions
#include "simple.h"        // simple stemmer modules
#include "display.h"       // screen display modules
#include "findw0.h"        // sequential word search module
#include "findw.h"         // binary word search module
#include "findw2.h"        // tab-binary word search module
#include "makelist0.h"     // make protect word file interactively.
#include "pporter.h"       // porter stem conditons modules
#include "porter.h"        // porter stemmer modules
#include "class.h"         // class delaration
#include "krovetz.h"       // inflectional stemmer modules
#include "combo.h"         // combination stemmer modules



main ()
{

char menu[3];                // menu selection input
char infile[NL];             // input file
char outfile[NL];            // output file
char pwfile[NL];             // protected word file
char word[WL];               // word to be stemmed.
char word2[BWL];             // inverted index entry
char rest[BWL-WL];           // inverted index word statistics
char map[WL]="/home1/";      // default ~ directory
char temp[NL];               // temporary input holder
char sep;                    // index word delimiter
char yn;                     // temporary input holder for y/n
int process;                 // word process flag
int i=0;                     // loop counter
fstream swlist;              // random access file for stopwords
fstream dict;                // random access file for dictionary
fstream xlist;               // random access file for exception list


//-----------------------------------------------
// User Interface:
// 1. display instructions and menu choices.
// 2. check for valid input.
//-----------------------------------------------

// display instructions and menu choices
cout<<endl<<"***   Welcome to the Nice Stemmer Program   ***\n\n";
showmenu();

// input the menu selection from the user.
cin>>menu;
cout<<endl;

// check for valid menu selection.
// if invalid, reprompt.
// (redisplay menu, if asked)
// (exit, if asked)
while (chk_ln(menu)==0) {
    exit(menu);
    reprompt();
    cin>>menu;
    while (strcmp(menu,"menu")==0) {
        cout<<endl;
        showmenu();
        cin>>menu;
    }
    cout<<endl;
}

// prompt for further input.
// -- input file, output file.
// -- protected word file, index word delimiter: if appropriate.

cout<<"Please enter the name of file to be stemmed.\n";
cin>>infile;
openfile(infile,map,1);
ifstream inf(infile);

cout<<"\nPlease enter the name of output file (i.e. stemmed file).\n";
cin>>outfile;
openfile(outfile,map,2);
ofstream outf(outfile);

if (menu[1]=='2') {
    while (!(yn=='y' || yn=='Y' || yn=='n' || yn=='N')) {
	cout<<"\nDo you have a text file of Protected Words? [y/n]\n";
	cin>>temp;
	yn=temp[0];
	// get the external pwfile from the user,
	if (yn=='y' || yn=='Y') {
	    cout<<"\nPlease enter the filename of Protected Words\n";
	    cin>>pwfile;
	    openfile(pwfile,map,1);
	}
	// or make the pwfile interactively.
	else if (yn=='n' || yn=='N') {
	    cin.get();  // read in the eol marker
	    makelist0();
	    strcpy(pwfile,PWFILE0);
	}
    }
}
else if (menu[1]=='3') {
    // !! better way?
    cout<<"\nPlease type in the delimiter character that separates\n"
        <<"the index word from occurence statistic information.\n";
    cin>>sep;
}


//-----------------------------------------------
// process input file by word,
// 1. if index file is to be stemmed, separate index word.
// 2. skip over protected word, if appropriate
// 3. remove stopword
// 4. call the appropriate stemmer
//-----------------------------------------------

// open random access files for stopword list, dictionary, & exception list.
swlist.open(RSWLIST,ios::in);
dict.open(RDICT,ios::in);
xlist.open(RXLIST,ios::in);


cout<<"Stemming in Progress!!\n";

while (! inf.eof()) {

    cout<<".";  // show progress indicator.

    // reset process flag for each word.
    process=1;

    // inverted index entry can be extremely long,
    // !!!!!!!! how to read in a very long input?
    if (menu[1]=='3') {
        inf>>word2;
        split(word2,sep,word,rest);
    }

    else inf>>word;

    // if protected word, set process flag to 2.
    if (menu[1]=='2' && findw0(word,pwfile)) process=2;
    else {
        // strip off punctuation
        punct(word);
        // if stopword, set process flag to 0.
        if (findw(1,word,swlist)) process=0;
    }

    // stem the word.
    if (process==1) {
        if (menu[0]=='a') Simple(word);
        else if (menu[0]=='b') Porter(word);
        else if (menu[0]=='c') Krovetz_i(word,dict,xlist,0);
        else if (menu[0]=='d') combostem(word,dict,xlist,0);
    }

    // write to the output file.
    if (process) {
        // put protected word on a line by itself.
        if (process==2) {
            outf<<endl<<word<<endl;
            i=0;
        }
        // put the inverted index entry back together.
        else if (menu[1]=='3') outf<<word<<sep<<rest<<endl;
        else {
            outf<<word<<' ';
            // start a new ouput line every 8 word.
            i++;
            if (i%8==0) outf<<endl;
        }
    }

}

cout<<endl;
inf.close();
outf.close();
swlist.close();
xlist.close();
dict.close();


}
