// ************************************************************************
// Name:     COMBO.H
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



void combostem(char inword[], fstream &dict, fstream &xlist, int first){

char word[3][WL];
int wf[3], wl[3], min, minlen, i;

strcpy(word[0],inword);
strcpy(word[1],inword);
strcpy(word[2],inword);

Simple(word[0]);
Porter(word[1]);
Krovetz_i(word[2],dict,xlist,first);

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


dict.clear();
  
}
