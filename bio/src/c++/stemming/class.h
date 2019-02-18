// ************************************************************************
// Name:     CLASS.H
// Author:   Kiduk Yang, 11/29/96
//             modified, 11/13/96
// ------------------------------------------------------------------------
// Description: class declarations
// ------------------------------------------------------------------------
// NOTE:    1. called by krovetz.cc and combo.cc
// ************************************************************************


// perform various funtions to word, including
//  checking of the dictionary.

class stemwd {

private:
    int allcaps;
    char word[WL];
    char stem[WL];
    char temp[WL];  // holder of intermediate word forms

public:

    //int pnoun;      // 1 if proper noun or acronym

    // constructor
    stemwd(char *inword, int fword) { 
	if ( inword[1] == toupper(inword[1]) ) allcaps=1;
	// convert to lowercase unless 
	// -- acronyms:  2-, 3- & 4-letter all-capitalized words
	// -- proper nouns: capitalized first letter & not first sentence word 
	if ( strlen(inword)>4 ) {
	    if ( fword==1 || allcaps==1 )
		inword[0]=tolower(inword[0]);
	    for(int i=1;i<strlen(inword);i++) {
		inword[i]=tolower(inword[i]);
	    }
	}
	// convert first sentence word to lowercase unless it's an acronym
	else if ( fword==1 && allcaps!=1 ) inword[0]=tolower(inword[0]);
	//if ( inword[0] == toupper(inword[0]) ) pnoun=1;
	//else pnoun=0;
        strcpy(word,inword); 
    }

    // check the dictionary for the word, and
    //   return 1 if it found, 0 otherwise.
    int sdict(fstream &dict) { return(findw2(1,word,dict)); }

    // check the exception list for the word, and
    //   return 1 if it found, 0 otherwise.
    int sxlst(fstream &xlist) { return(findw(1,word,xlist)); }

    // return 1, if suffix is found.  0 otherwise.
    int sf(char *sfx) { return(suffix(word,sfx)); }

    // replace suffix with replacement.
    void rp(char *outword, char *sfx, char *sfx2) {
        strcpy(temp,word);
        replace(word,sfx,sfx2);
        strcpy(outword,word);
    }

    // restore word to before replacement.
    void before(char *inword) {
        strcpy(word,temp);
        strcpy(inword,temp);
    }

    // delete the last character.
    void xd(char *inword) {
        word[strlen(word)-1]='\0';
        strcpy(inword,word);
    }

    // return 1, if STEM ends in a double consonant. 0 otherwise.
    int d() { return(stem_d(word)); }


};
