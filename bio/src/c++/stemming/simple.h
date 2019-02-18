// -----------------------------------------------------------------
// Name:     Simple.cc
// Author:   Rong Tang  11/07/96
// -----------------------------------------------------------------
// Description: Using suffix and replace functions to code the simple
//              stemmer
// -----------------------------------------------------------------
// Note: 
// -----------------------------------------------------------------




void Simple(char word[]) {


    if (suffix(word,"ies") &! (suffix(word, "eies") ||
        suffix(word,"aies"))) {
    replace(word,"ies","y"); }
    else if (suffix(word, "es") &! (suffix(word,"aes") ||
              suffix(word, "ees") || suffix(word, "oes"))){
         replace(word,"es","e");}
    else if (suffix(word, "s") &! (suffix(word,"us") ||
              suffix(word, "ss"))) {
         replace(word,"s","\0");}

}
