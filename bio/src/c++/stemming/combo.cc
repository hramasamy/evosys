// ************************************************************************
// Name:     COMBO.CC
// Author:   Kiduk Yang, 11/26/96
// ------------------------------------------------------------------------
// Description: COMBINATION STEMMER
//   1. execute all 3 stemmers.
//   2. select shorted resulting stem that is in dictionary.
// ------------------------------------------------------------------------
// INPUT:    word to be stemmed
//           dictionary search file
//           excepton list
// OUTPUT:   stemmed word
// ------------------------------------------------------------------------
// NOTE:     INLS 161 C++ team project
// ************************************************************************



#define BWL 5000// Maximum length of words allowed
#define WL 50// Maximum length of words allowed
#define CTAG  8281// Number of tags used built in random access file
#define RXLIST "ran_xlist"  // RAB exception list
#define RDICT "ran_dict"   // RAB dictionary file.

#include <iostream.h>  //  standard head files
#include <fstream.h>   //
#include <stdlib.h>    //
#include <string.h>    //
#include <ctype.h>     //
#include "string.h"    
#include "simple.h"
#include "pporter.h"
#include "findw.h"
#include "findw2.h"
#include "porter.h"
#include "class.h"
#include "krovetz.h"


void combostem(char inword[], fstream &dict, fstream &xlist){

char word[3][WL];
int wf[3], wl[3], min, minlen, i;
 

strcpy(word[0],inword);
strcpy(word[1],inword);
strcpy(word[2],inword);

Simple(word[0]);
Porter(word[1]);
Krovetz_i(word[2],dict,xlist,0);

minlen=WL;
min=2;
for(i=0;i<3;i++) {
    wf[i]=findw2(1,word[i],dict);
    wl[i]=strlen(word[i]);
    if (wf[i] && wl[i]<minlen){
	minlen=wl[i];
	min=i;
    }
}
	  
if (wf[2] && suffix(word[2],"e") && 
    (suffix(inword,"es") || suffix(inword,"ed") || suffix(inword,"ing"))) 
   strcpy(inword,word[2]);
else strcpy(inword,word[min]);

cout<<"Simple Stemmer= "<<word[0]<<endl;  
cout<<"Porter Stemmer= "<<word[1]<<endl;  
cout<<"Krovetz Stemmer= "<<word[2]<<endl;  
cout<<"Combo Stemmer= "<<inword<<endl;  
  
dict.clear();
  
}


// test the modules

main () {

char word[50];
fstream xlist;  // exception list
fstream dict;   // dictionary

xlist.open(RXLIST,ios::in);
dict.open(RDICT,ios::in);


while(1) {

    cout<<"\nPlease enter a word to be stemmed\n"
        <<"or 'xxx' to exit\n\n";
    cin>>word;
    if (strcmp(word,"xxx")==0) break;
    cout<<endl;
    combostem(word,dict,xlist);

}

}
