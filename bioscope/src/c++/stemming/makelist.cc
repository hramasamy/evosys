// -----------------------------------------------------------------
// Name:     MAKELIST.CC
// Author:   Kiduk Yang, 11/08/96
//             modified, 11/23/96
// -----------------------------------------------------------------
// Description: creates a string random access file for binary search
//   1. the header portion will include the file size
//       (total number of words) needed for binary search
//   2. create a random access file from a sorted word list.
// -----------------------------------------------------------------
// INPUT:    sorted lists
// OUTPUT:   random access binary file to be used with findw.h
// -----------------------------------------------------------------
// NOTE:  Need to run only once.
// -----------------------------------------------------------------

#include <iostream.h>
// constant declarations: adjust if necessary.
#define NL 20      // maximum filename length
#define WL 50      // maximum word length

#define SWF   "stoplist"        // input file of stopword list
#define RSWF  "ran_stoplist"    // random access file name

#define XWF   "xlist"           // input file of exception word list
#define RXWF  "ran_xlist"       // random access file name

#include <iomanip.h>
#include <stdio.h>
#include <fstream.h>
#include <stdlib.h>
#include <ctype.h>
#include "makelist.h"
#include "findw.h"



// display file open error message.
void fin_err(char *file) {
    cerr<<"Cannot open "<<file<<"!"<<endl;
    exit(8);
}


// -------------------------------------------
// create the random access file of stopwords.
// -------------------------------------------

main() {

int i;
char word[WL];           // user input.
char fin[WL];            // input (text) file name.
char fout[WL];           // output (binary) file name.
fstream swlist;          // random access file for stopword
fstream xlist;           // random access file for exception list
fstream list;            // generic random access file


MENU:                   // Danqi's makelist2.cc modified
cout << endl
     << "Select an option:" << endl
     << "  1 = create the Random Access Binary file of stopwords;\n"
     << "  2 = create the Random Access Binary file of exception list;\n"
     << "  3 = create any Random Access Binary file;\n"
     << "  4 = perform a single word search;\n"
     << "  0 = exit.\n";

cin>>word;
cout<<endl;
if(word[0] < '0' || word[0] > '4') goto MENU;


//----------------------------
// create stopword search file
//----------------------------

if (word[0]=='1') {

    cout<<"Please enter the name of stopword file to use in the Nice Stemmer,\n"
        <<"  or enter xxx to use the default stopword file.\n"
        <<"(The words in this file must be sorted in alphabetical order.)\n";
    cin>>fin;
    cout<<endl;

    if (strcmp(fin,"xxx")==0)  strcpy(fin,SWF);

    // open input and output file
    ifstream inf(fin);
    if (!inf) fin_err(fin);
    swlist.open(RSWF,ios::out | ios::in | ios::trunc);
  
    makelist(inf,swlist);

    // display total number of words and
    // first 10 words from the random file.

    swlist.seekg(50L*0,ios::beg);
    swlist>>word;
    cout<<"Random access file for \""<<fin<<"\" have been created!!\n"
        <<"Please remember that you only need to create this file ONCE.\n\n"
        <<"Random Access File name = "<<RSWF<<endl
        <<"total number of words = "<<word<<endl<<endl;

    for(i=1;i<=10;i++) {
        swlist.seekg(50L*i,ios::beg);
        swlist>>word;
        cout<<i<<"th word = "<<word<<endl;
    }

    swlist.close();
    goto MENU;

}  // end option=1


//-----------------------------------
// create exception list search file
//-----------------------------------

else if (word[0]=='2') {

    cout<<"Please enter the name of exception list to use in the Nice Stemmer,\n"
        <<"  or enter xxx to use the default exception list.\n"
        <<"(The words in this file must be sorted in alphabetical order.)\n";
    cin>>fin;
    cout<<endl;

    if (strcmp(fin,"xxx")==0) strcpy(fin,XWF);
    // open input and output file
    ifstream inf(fin);
    if (!inf) fin_err(fin);
    xlist.open(RXWF,ios::out | ios::in | ios::trunc);
 
    makelist(inf,xlist);

    // display total number of words and
    // first 10 words from the random file.

    xlist.seekg(50L*0,ios::beg);
    xlist>>word;
    cout<<"Random access file for \""<<fin<<"\" have been created!!\n"
        <<"Please remember that you only need to create this file ONCE.\n\n"
        <<"Random Access File name = "<<RXWF<<endl
        <<"total number of words = "<<word<<endl<<endl;

    for(i=1;i<=10;i++) {
        xlist.seekg(50L*i,ios::beg);
        xlist>>word;
        cout<<i<<"th word = "<<word<<endl;
    }

    xlist.close();
    goto MENU;

}  // end option=2


//-----------------------------------
// create any search file
//-----------------------------------

else if (word[0]=='3') {

    cout<<"Please enter the file name from which to create the search file.\n"
        <<"(The words in this file must be sorted in alphabetical order.)\n";
    cin>>fin;
    cout<<endl;

    cout<<"Please enter the name of the binary search file to create.\n";
    cin>>fout;
    cout<<endl;

    // open input and output file
    ifstream inf(fin);
    if (!inf) fin_err(fin);
    list.open(fout,ios::out | ios::in | ios::trunc);

    makelist(inf,list);

    // display total number of words and
    // first 10 words from the random file.

    list.seekg(50L*0,ios::beg);
    list>>word;
    cout<<"Random access file for \""<<fin<<"\" have been created!!\n"
        <<"Please remember that you only need to create this file ONCE.\n\n"
        <<"Random Access File name = "<<fout<<endl
        <<"total number of words = "<<word<<endl<<endl;

    for(i=1;i<=10;i++) {
        list.seekg(50L*i,ios::beg);
        list>>word;
        cout<<i<<"th word = "<<word<<endl;
    }

    list.close();
    goto MENU;

}  // end option=3


//-----------------------------------
// search for words.
//-----------------------------------

else if (word[0]=='4') {

    cout<<"Please enter the name of random access file to be searched.\n";
    cin>>fin;
    cout<<endl;
    list.open(fin,ios::in);
    if (!list) fin_err(fin);

    while (1) {
        cout<<"\nPlease enter the word to search for or\n"
            <<"  enter xxx to stop.\n";
        cin>>word;
        if (strcmp(word,"xxx")==0) break;
        if(findw(1,word,list)) cout<<word<<" is found!\n";
        else cout<<word<<" is NOT found!\n";
     }

     list.close();
     goto MENU;

}


//-----------------------------------
// search for words.
//-----------------------------------

else if (word[0]=='0') exit(1);

}
