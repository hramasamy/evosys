//-------------------------------------------------------------------
// Name:     FINDW2.H
// Author:   Danqi Song, 11/16/96
//            modified by Kiduk Yang, 03/02/98
//-------------------------------------------------------------------
// Description:
//   1. searches for a given word in a random access list.
//   2. returns 1 if found, 0 otherwise.
//      (use tab-binary search)
//   3. two options provided:
//       (a) use simple binary search, when "opt" = 0
//       (b) use tab-binary search,       when "opt" = 1
//-------------------------------------------------------------------
// INPUT:    word to look for
//           random access search list
// OUTPUT:   1 if found, 0 otherwise.
//-------------------------------------------------------------------
// NOTE:     random access search file must first be created by makelist2.cc
//-------------------------------------------------------------------



//#include "string.h"

// Module to search a word in the random access file using either simple
// serach or tab search
//
int findw2(int opt, char word[], fstream &fp)
{
  int i1,i2,k,j,j1,j2,jc;
  int ll, hh, low, hig, middle, jfind;
  char s[WL];
  char word2[WL];

  // convert to lowercase
  strcpy(word2,word);
  //if (fword==1) word2[0]=tolower(word[0]);
  //for(i0=1;i0<strlen(word);i0++) {
  //    word2[i0]=tolower(word[i0]);
  //}

  jc = 0;  // initialize counter for total number of comparisons

  ll = CTAG+1;    // set the location of the 1st word, i.e ll*50

  fp.seekg(50L*0, ios::beg); // get the location of the last word, i.e. hh*50
  fp >> hh;                 //
  hh = hh + CTAG;           //

  if( opt == 0 )   // if opt = 0, this is simple bisection search,
  {                // set low = ll, hig = hh and goto the place
      low = ll;    // to start bisection search
      hig = hh;    //
      goto BISEC;  //
  }                //

  k = strlen(word);   // calculate length of the given word
  j1 = j2 = 0;        // initialize integer numbers for the 1st two chars

  if( k == 1 )          // if this is a single char word, only need to
  {                     // calculate j1
      j1 = word2[0]-32;  //
  }
  else if( k >= 2 )     // if this is a word with 2 or more chars, need to
  {                     // calculate both j1 and j2
      j1 = word2[0]-32;  //
      j2 = word2[1]-32;  //
  }
  if(j1 < 0)        //
     j1 = 0;        // if any char mapping number is beyond 0-90,
  else if(j1 > 90)  //  set it as 0 or 90.
     j1 = 90;       //
  if(j2 < 0)        //
     j2 = 0;        //
  else if(j2 > 90)  //
     j2 = 90;       //

  i1 = j1*91 + j2 + 1; // calculate tag ID this word matches
  i2 = i1 + 1;         // set next tag ID

//  cout << "i1,i2=" << i1 << ',' << i2 << endl;

  fp.seekg(50L*i1,ios::beg);  // find the lower limit of the actual location
  fp >> low;                 //
  if (low<=0) {              // if value is 0, it means no such word.
      jfind=0;
      fp.clear();
      goto FINISH;
  }

  hig=0;
  while (hig==0 && i2<=CTAG) {
      fp.seekg(50L*i2,ios::beg);  // find the upper limit of the actual location
      fp >> hig;                 //
      i2++;
  }

  // for last tab word search
  if(hig < low)  // if somehow the upper limit is less than the lower one
     hig = hh;   // set the upper limit = the last word tag location

  fp.clear();

//
// start bisection search
// the difference of simple search and tab search is that the tab search
// starts with a very small range of word tags while simple search starts
// from the 1st to the last word.
//
BISEC:

  //cout << "low=" << low << endl;  // debug output
  //cout << "hig=" << hig << endl;  //

  for(;;)
  {
    middle = (low+hig)/2;     // calculate middle location

    fp.seekg(50L*middle,ios::beg);  // pick the word from the binary file
    fp.getline(s,sizeof(s));       // !! why getline?: phrase handling

    k = strlen(s);                      //  trim the end spaces of the word
    for(j=k; j>0; j--)                  //
        if(s[j] != ' ' && s[j] != '\0') //
           break;                       //
    s[j+1] = '\0';

    jc++;   // increase the counter for comparisons

  //cout << "word=" << word << "; s=" << s << ";" << endl; // debug output

    if(strcmp(word2,s) == 0) // compare if the given word matches the word
    {                       // from the binary file. if found set flag
       jfind = 1;           // jfind = 1 (which will be return by function)
       goto FINISH;         // and goto the end of function
    }                       //
    else
    {
       if(low == hig)         // if upper limit and lower limit become
       {                      // equal, we could not find this word
          jfind = 0;          // set jfind = 0 and goto the end of function
          goto FINISH;        //
       }
       else if(hig == low+1)  // if upper limit = lower limit + 1
          goto FINAL;         // goto the place to do the final comparison

       if(strcmp(word2,s) < 0)   // if the given word is less than word from
          hig = middle;         // the file, set upper limit = middle,
       else                     // else set lower limit = middle and
          low = middle;         // continue the search process
    }
  }

FINAL:
  fp.seekg(50L*low,ios::beg);  // get the word at lower limit
  fp.getline(s,sizeof(s));    //

  k = strlen(s);                      //  trim the end spaces of the word
  for(j=k; j>0; j--)                  //
      if(s[j] != ' ' && s[j] != '\0') //
         break;                       //
  s[j+1] = '\0';                      //
  jc++;   // increase counter for comparisons

//  cout << "word=" << word << "; s=" << s << ";" << endl; // debug output

  if(strcmp(word2,s) == 0)  //
  {                        // compare and if two words match, set jfind =1
     jfind = 1;            // and goto the end
     goto FINISH;          //
  }                        //

  fp.seekg(50L*hig,ios::beg);  // get the word at upper limit
  fp.getline(s,sizeof(s));    //

  trim(s);
  jc++;
  //cout << "word=" << word << "; s=" << s << ";" << endl;

  if(strcmp(word2,s) == 0) //
  {                       // compare and if two words match, set jfind =1
     jfind = 1;           // and goto the end
     goto FINISH;         //
  }                       //

  jfind = 0; // if either word from both lower and upper limits does not match
             // the given word, can not find it, set jfind = 0

FINISH:
//  cout << "Total number of comparisons=" << // show how many comparisons
//          jc << endl;                       // made so far
  return( jfind ); // return the flag which is either 0 or 1
}
