// ************************************************************************
// Name:     PORTER.H
// Author:   Woosub Jeong, 11/08/96
// ------------------------------------------------------------------------
// Description: implements Porter stemmer
//   1.
//   2.
// ------------------------------------------------------------------------
// INPUT:
// OUTPUT:
// ------------------------------------------------------------------------
// NOTE:
// ************************************************************************




// uses modules from PPORTER to apply them to the STEM
//  instead of word, when appropriate.

class stemword {

private:
    char word[WL];
    char stem[WL];
    char sfx[20];
    char sfx2[10];

public:

    // 1. input word, suffix and replacement.
    // 2. identify the stem.
    // 3. always return 1. (to be used inside of IF)
    int in(char inword[],char suffix[],char suffix2[]) {
        strcpy(word,inword);
        strcpy(sfx,suffix);
        strcpy(sfx2,suffix2);
	strcpy (stem, word) ;
        replace(stem,sfx,"\0");
        return(1);
    }

    // return 1, if suffix is found.  0 otherwise.
    int sf() { return(suffix(word,sfx)); }

    // replace suffix with replacement.
    void rp(char outword[]) {
        replace(word,sfx,sfx2);
        strcpy(outword,word);
    }

    // return the measure value of the STEM
    int m() { return(measure(stem)); }

    // return 1, if STEM contains a vowel. 0 otherwise.
    int v() { return(stem_v(stem)); }

    // return 1, if STEM ends in a double consonant. 0 otherwise.
    int d() { return(stem_d(stem)); }

    // return 1, if STEM ends with a consonant-vowel-consonat, sequence,
    //  where the final consonant is not w, x, or y.  0 otherwise.
    int o() { return(stem_o(stem)); }

    // return 1, if STEM ends in a given letter. 0 otherwise.
    int x(char t[]) { return(suffix(stem,t)); }

};



void step1a(char word[])
{

    int t;
    t=strlen(word)-1;

    if(suffix(word, "sses")==1)replace(word, "sses", "ss");
    else if(suffix(word, "ies")==1)replace(word, "ies", "i");
    else if(suffix(word, "ss")==1)replace(word, "ss", "ss");
    else if(suffix(word, "s")==1)replace(word, "s", "\0");

}


void step1b(char word[], int &flag)
{

    stemword wd;

    if (wd.in(word,"eed","ee") && wd.sf() && wd.m()>0) wd.rp(word);
    else if (wd.in(word,"ed","\0") && wd.sf() && wd.v()) {
        flag=1;
        wd.rp(word);
    }
    else if (wd.in(word,"ing","\0") && wd.sf() && wd.v()) {
        flag=1;
        wd.rp(word);
    }

}

void step1b1(char word[])
{
    int t;
    t=strlen(word)-1;
    stemword wd;

    if (wd.in(word,"at","ate") && wd.sf()) wd.rp(word);
    else if (wd.in(word,"bl","ble") && wd.sf()) wd.rp(word);
    else if (wd.in(word,"iz","ize") && wd.sf()) wd.rp(word);
    else if (stem_d(word)==1 && suffix(word, "z")==0 &&
        suffix(word, "s")==0 && suffix(word, "l")==0) word[t]='\0';
    else if(measure(word)==1 && stem_o(word)==1) replace(word,"\0","e");

}

void step1c(char word[])
{
    stemword wd;
    if (wd.in(word,"y","i") && wd.sf() && wd.v()) wd.rp(word);
}

void step2(char word[])
{

    stemword wd;

    if (wd.in(word,"ational","ate") && wd.sf() && wd.m()>0) wd.rp(word);
    else if (wd.in(word,"tional","tion") && wd.sf() && wd.m()>0) wd.rp(word);
    else if (wd.in(word,"enci","ence") && wd.sf() && wd.m()>0) wd.rp(word);
    else if (wd.in(word,"anci","ance") && wd.sf() && wd.m()>0) wd.rp(word);
    else if (wd.in(word,"izer","ize") && wd.sf() && wd.m()>0) wd.rp(word);
    else if (wd.in(word,"abli","able") && wd.sf() && wd.m()>0) wd.rp(word);
    else if (wd.in(word,"alli","al") && wd.sf() && wd.m()>0) wd.rp(word);
    else if (wd.in(word,"entli","ent") && wd.sf() && wd.m()>0) wd.rp(word);
    else if (wd.in(word,"eli","e") && wd.sf() && wd.m()>0) wd.rp(word);
    else if (wd.in(word,"ousli","ous") && wd.sf() && wd.m()>0) wd.rp(word);
    else if (wd.in(word,"ization","ize") && wd.sf() && wd.m()>0) wd.rp(word);
    else if (wd.in(word,"ation","ate") && wd.sf() && wd.m()>0) wd.rp(word);
    else if (wd.in(word,"ator","ate") && wd.sf() && wd.m()>0) wd.rp(word);
    else if (wd.in(word,"alism","al") && wd.sf() && wd.m()>0) wd.rp(word);
    else if (wd.in(word,"iveness","ive") && wd.sf() && wd.m()>0) wd.rp(word);
    else if (wd.in(word,"fulness","ful") && wd.sf() && wd.m()>0) wd.rp(word);
    else if (wd.in(word,"ousness","ous") && wd.sf() && wd.m()>0) wd.rp(word);
    else if (wd.in(word,"aliti","al") && wd.sf() && wd.m()>0) wd.rp(word);
    else if (wd.in(word,"iviti","ive") && wd.sf() && wd.m()>0) wd.rp(word);
    else if (wd.in(word,"biliti","ble") && wd.sf() && wd.m()>0) wd.rp(word);

}

void step3(char word[])
{

    stemword wd;

    if (wd.in(word,"icate","ic") && wd.sf() && wd.m()>0) wd.rp(word);
    else if (wd.in(word,"ative","\0") && wd.sf() && wd.m()>0) wd.rp(word);
    else if (wd.in(word,"alize","al") && wd.sf() && wd.m()>0) wd.rp(word);
    else if (wd.in(word,"iciti","al") && wd.sf() && wd.m()>0) wd.rp(word);
    else if (wd.in(word,"ical","ic") && wd.sf() && wd.m()>0) wd.rp(word);
    else if (wd.in(word,"ful","\0") && wd.sf() && wd.m()>0) wd.rp(word);
    else if (wd.in(word,"ness","\0") && wd.sf() && wd.m()>0) wd.rp(word);

}

void step4(char word[])
{

    stemword wd;

    if (wd.in(word,"al","\0") && wd.sf() && wd.m()>1) wd.rp(word);
    else if (wd.in(word,"ance","\0") && wd.sf() && wd.m()>1) wd.rp(word);
    else if (wd.in(word,"ence","\0") && wd.sf() && wd.m()>1) wd.rp(word);
    else if (wd.in(word,"er","\0") && wd.sf() && wd.m()>1) wd.rp(word);
    else if (wd.in(word,"ic","\0") && wd.sf() && wd.m()>1) wd.rp(word);
    else if (wd.in(word,"able","\0") && wd.sf() && wd.m()>1) wd.rp(word);
    else if (wd.in(word,"ible","\0") && wd.sf() && wd.m()>1) wd.rp(word);
    else if (wd.in(word,"ant","\0") && wd.sf() && wd.m()>1) wd.rp(word);
    else if (wd.in(word,"ement","\0") && wd.sf() && wd.m()>1) wd.rp(word);
    else if (wd.in(word,"ment","\0") && wd.sf() && wd.m()>1) wd.rp(word);
    else if (wd.in(word,"ent","\0") && wd.sf() && wd.m()>1) wd.rp(word);
    else if (wd.in(word,"ion","\0") && wd.sf() && wd.m()>1 &&
             (wd.x("s") || wd.x("t"))) wd.rp(word);
    else if (wd.in(word,"ou","\0") && wd.sf() && wd.m()>1) wd.rp(word);
    else if (wd.in(word,"ism","\0") && wd.sf() && wd.m()>1) wd.rp(word);
    else if (wd.in(word,"ate","\0") && wd.sf() && wd.m()>1) wd.rp(word);
    else if (wd.in(word,"iti","\0") && wd.sf() && wd.m()>1) wd.rp(word);
    else if (wd.in(word,"ous","\0") && wd.sf() && wd.m()>1) wd.rp(word);
    else if (wd.in(word,"ive","\0") && wd.sf() && wd.m()>1) wd.rp(word);
    else if (wd.in(word,"ize","\0") && wd.sf() && wd.m()>1) wd.rp(word);

}


void step5a(char word[])
{
    stemword wd;
    if (wd.in(word,"e","\0") && wd.sf() && wd.m()>1) wd.rp(word);
    else if (wd.in(word,"e","\0") && wd.sf() && wd.m()==1 && wd.o()==0) wd.rp(word);
}

void step5b(char word[])
{

    int t;
    t=strlen(word)-1;

    if(measure(word)>1 && stem_d(word)==1 && word[t]=='l') replace(word, "l", "\0");

}



void Porter(char word[]) {

    int st1b=0;

    step1a(word);
    step1b(word,st1b);
    if(st1b==1)step1b1(word);
    step1c(word);
    step2(word);
    step3(word);
    step4(word);
    step5a(word);
    step5b(word);

}
