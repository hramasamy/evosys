// -----------------------------------------------------------------
// Name:     FINDW.H
// Author:   Kiduk Yang, 11/08/96
// -----------------------------------------------------------------
// Description: included in STEMMER SHELL
//   1. searches for a given word in a random access list.
//   2. returns 1 if found, 0 otherwise.
//      (use binary search)
// -----------------------------------------------------------------
// INPUT:    word to look for
//           random access search list
// OUTPUT:   1 if found, 0 otherwise.
// -----------------------------------------------------------------
// NOTE:  INLS 161 C++ team project
// -----------------------------------------------------------------
using namespace std ;

#include <string>

char *getw(int i, fstream &f) {
    string s ;
    f.seekg(50L*i,ios::beg);
    f>>s;
    return((char *) s.c_str());
};

// if word is to be skipped, set flag to 0
// -- flg=1 to convert to lowercase before searching
int findw(int flg, char word2[], fstream &list) {

    int low, high;  // low, upper bound
    int middle;     // middle of search range
    int i;
    char word[WL];  // word in lowercase

    strcpy(word,word2);
    // convert to lowercase
    if (flg==1) {
        for(i=0;i<strlen(word);i++) {
            word[i]=tolower(word[i]);
        }
    }  
  
    // binary search

    low=1;
    // get total word count from the header.
    list.seekg(50L*0,ios::beg);
    list>>high;  // !! why does this work? 
    while (1) {
        if (low>=high) {
	    if (low==high && strcmp(word,getw(low,list))==0) return(1);
	    else return(0); 
            break;
        }
        middle= (low+high)/2;
        if (middle==low) middle=high;
        if (strcmp(word,getw(middle,list))==0) {
            return(1);
            break;
        }
        // strcmp returns 1 if first string is larger,
        //  -1 if second string is larger.
        if (strcmp(word,getw(middle,list))>=1) low=middle;
        else high=middle-1;
    }

}
