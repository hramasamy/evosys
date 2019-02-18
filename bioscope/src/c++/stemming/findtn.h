// ************************************************************************
// Name:     FINDTN.CC
// Author:   Danqi Song, 11/16/96             
//           Modifed by Kiduk Yang, 11/23/97  
//           Modifed by Kiduk Yang, 05/21/97  
// ------------------------------------------------------------------------
// Description: find a given word in RAB file
//   1. option1 -- simple binary search, when "opt"=0
//   2. option2 -- tab-binary search, when "opt"=1
//   3. if found, return term# (i.e position), 0 otherwise
// ------------------------------------------------------------------------
// INPUT:
//   $idir/ran_tnt -- RAB term#-term file 
// ------------------------------------------------------------------------
// NOTE:  modified from findw2.h of nice stemmer
// ************************************************************************

//#define DEBUG


// Module to search a word in the random access file using either simple
// serach or tab search
//
int findtn(int opt, char word[], fstream &fp)
{
  int i1,i2,k,j,j1,j2,jc;
  int ll, hh, low, hig, middle, jfind, wdpos;
  const int head=8281;
  char s[WL];
  char word2[WL];

  // convert to lowercase
  strcpy(word2,word);
  //for(i=0;i<strlen(word2);i++) word2[i]=tolower(word[i]);

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

  #ifdef DEBUG
    cout << "j1,j2="<<j1<<','<<j2<<"; i1,i2=" << i1 << ',' << i2 << endl;
  #endif

  fp.seekg(50L*i1,ios::beg);  // find the lower limit of the actual location
  fp >> low;                 //
  // potential bug:  if searching for word not in rab, get negative low
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

  #ifdef DEBUG
    cout << "low=" << low << endl;  // debug output
    cout << "hig=" << hig << endl;  //
  #endif

  for(;;)
  {
    middle = (low+hig)/2;     // calculate middle location
    wdpos = middle-head;

  #ifdef DEBUG
    cout << "middle=" << middle << endl;  //
  #endif

    fp.seekg(50L*middle,ios::beg);  // pick the word from the binary file
    fp.getline(s,sizeof(s));       // !! why getline?: phrase handling

    k = strlen(s);                      //  trim the end spaces of the word
    for(j=k; j>0; j--)                  //
        if(s[j] != ' ' && s[j] != '\0') //
           break;                       //
    s[j+1] = '\0';

    jc++;   // increase the counter for comparisons

  #ifdef DEBUG
    cout << "1: word=" << word << "; s=" << s << ";" << endl; // debug output
  #endif

    if(strcmp(word2,s) == 0) // compare if the given word matches the word
    {                       // from the binary file. if found set flag
       jfind = wdpos;       // jfind > 0 (which will be return by function)
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
    wdpos = low-head;
  fp.getline(s,sizeof(s));    //

  k = strlen(s);                      //  trim the end spaces of the word
  for(j=k; j>0; j--)                  //
      if(s[j] != ' ' && s[j] != '\0') //
         break;                       //
  s[j+1] = '\0';                      //
  jc++;   // increase counter for comparisons

  #ifdef DEBUG
    cout << "2: word=" << word << "; s=" << s << ";" << endl; 
  #endif

  if(strcmp(word2,s) == 0)  //
  {                        // compare and if two words match, set jfind=term#
     jfind = wdpos;        // and goto the end
     goto FINISH;          //
  }                        //

  fp.seekg(50L*hig,ios::beg);  // get the word at upper limit
    wdpos = hig-head;
  fp.getline(s,sizeof(s));    //

  trim(s);
  jc++;

  #ifdef DEBUG
    cout << "word=" << word << "; s=" << s << ";" << endl;
  #endif

  if(strcmp(word2,s) == 0) //
  {                       // compare and if two words match, set jfind =1
     jfind = wdpos;       // and goto the end
     goto FINISH;         //
  }                       //

  jfind = 0; // if either word from both lower and upper limits does not match
             // the given word, can not find it, set jfind = 0

FINISH:
  #ifdef DEBUG
    cout << "Total number of comparisons=" << // show how many comparisons
            jc << endl;                       // made so far
  #endif
  return( jfind ); // return the flag which is either 0 or term# (>0)
}
