////////////////////////////////////////////////////////////////////////////////
// INLS-161 C++ Programming Team Project - Stemming                           //
//                                                                            //
// Student name: Danqi Song                                                   //
// Date: Nov. 6, 1996                                                         //
//                                                                            //
// This C++ source library contains 4 C++ functions to realize 4 different    //
// functionalities:                                                           //
//                                                                            //
// (1) measure - calculate measure of a given stem                            //
//                                                                            //
// (2) stem_v  - check whether a given stem contains a vowel                  //
//                                                                            //
// (3) stem_d  - check whether a given stem ends in double consonants         //
//                                                                            //
// (4) stem_o  - check whether a given stem ends with a consonant-vowel-      //
//               consonant sequence, where the final consonant is not         //
//               w, x, or y                                                   //
//                                                                            //
////////////////////////////////////////////////////////////////////////////////

#include <iostream.h>   //  include 5 necessary head files
#include <fstream.h>    //
#include <string.h>     //
#include <stdlib.h>     //
#include <ctype.h>      //

#define MAXSQN  50   // parameter for maximum number of C or V sequences
                     // in a stem

int vowel(char p);   // global declaration of function vowel

////////////////////////////////////////////////////////////////////////////////
// function vowel: identify the categories of a given character               //
// input:  char p                                                             //
// function returns - 1, if p is a vowel other than 'y'                       //
//                    2, if p is consonant other than 'y'                     //
//                    9, if p is 'y'                                          //
//                                                                            //
// Note: This function is internally called by the 4 major functions          //
//       (measure, stem_v, stem_d, stem_o) and not prepared for other         //
//       programmers.                                                         //
////////////////////////////////////////////////////////////////////////////////
int vowel(char p)
{
  int k;                                // declare local variable

  p = tolower(p);                       // convert character p to lower case

  if ( p == 'a' || p == 'e' || p == 'i' ||  // if p = a vowel ('a', 'e', 'i',
       p == 'o' || p == 'u'  )              // 'o', or 'u'), then set k = 1
       k = 1;                               //
  else if ( p == 'y' )            // if p = 'y' (a special char), 
       k = 9;                     // then set k = 9
  else                                  //  for the other cases, p must be
       k = 2;                           //  consonant, so set k = 2

  return(k);                      // return value of k
}

////////////////////////////////////////////////////////////////////////////////
// function measure: calculate measure of a given stem                        //
// input: char stem[]                                                         //
// function returns - measure of the given stem                               //
//                                                                            //
// Note: The maximum allowed measure of a stem can be adjusted by             //
//       the parameter MAXSQN                                                 //
////////////////////////////////////////////////////////////////////////////////
int measure(char stem[])
{
  int m,i,j,jlen,jblo;  // declare local variables
  int jpre,jsta;        // jsqn[i] = 1 if the ith sequence is a V sequence
  int jsqn[MAXSQN];     //         = 2 if the ith sequence is a C sequence

  m = 0;                // initialize measure m
  jlen = strlen(stem);  // calculate length of the given stem

  jblo = 0;             // initialize index of C/V squence
  jpre = 0;             // initialize type of the leading char (1 for vowel;
                        // 2 for consonant)
//
// Loop through each char of the given stem to calculate
// (1) total number of C/V sequences (jblo)
// (2) type of each sequence, i.e. array jsqn[] (=1 for vowel sequence;
//     =2 for consonant sequence; =9 for char 'y')
//     
  for(i=0; i<jlen; i++)
  {
    jsta = vowel(stem[i]);  // calculate type of current char

    if( jsta == 9 )             // If the char is 'y', then check:
    {                           // 
        if( i > 0 )             // (1) if this is the 1st char of the stem,
        {                       //     this is a consonant 'y'
          j = vowel(stem[i-1]); // 
          if( j == 2 )           // (2) if this is not the 1st char, then
              jsta = 1;         // 
          else                  // -   if the leading char is a vowel,
              jsta = 2;         //     this is a consonant 'y'
        }                       // -   if the leading char is a consonant,
        else                    //     this is a vowel 'y'
            jsta = 2;           //
    }                           //

    if( jsta != jpre )       // If the current char type is different from
    {                        // the leading char type, then increase index
        jblo++;              // of sequence by 1, and store the current char
        jsqn[jblo] = jsta;   // type in array jsqn[]
    }                        //
    jpre = jsta;  // set leading char type = current char for next iteration
  }

  if( jblo == 1 )   // If there is only one sequence, then m must be 0.
      return( m );  // Return value of m (=0).

  jpre = 0;         // initialize leading sequence type as 0
//
// Go through every pair of adjancent sequences to calculate total 
// number of V-C sequence pairs
//
  for(i=1; i<=jblo; i++)
  {
      jsta = jsqn[i];              // get current sequence type

      if( jpre == 1 && jsta == 2 ) // if the current sequence is consonant
          m++;                     // and the leading sequence is vowel,
                                   // increase measure by 1
      jpre = jsta;  // set leading sequence type = that of current one
  }
  return( m );      // return total number of V-C sequences as measure
                    // of the stem
}

////////////////////////////////////////////////////////////////////////////////
// function stem_v: check whether a given stem contains a vowel               //
// input: char stem[]                                                         //
// function returns - 1, if the stem contains a vowel                         //
//                  - 0, if the stem contains no vowel                        //
////////////////////////////////////////////////////////////////////////////////
int stem_v(char stem[])
{
  int i,j,jsta,jlen, jvow;  // declare local variables

  jlen = strlen(stem);      // calculate length of stem

  jvow = 0;                 // initialize flag jvow = 0, where jvow will be
                            // returned to indicate whether the stem contains
                            // a vowel
//
// Go through every char to find out whether a vowel exists in this stem
//
  for(i=0; i<jlen; i++)
  {
    jsta = vowel(stem[i]); // calculate current char type

    if( jsta == 9 )               // If the char is 'y', then check:
    {                             // 
        if( i > 0 )               // (1) if this is the 1st char of the stem,
        {                         //     this is a consonant 'y'
            j = vowel(stem[i-1]); // 
            if( j == 2 )           // (2) if this is not the 1st char, then
                jsta = 1;         // 
            else                  // -   if the leading char is a vowel,
                jsta = 2;         //     this is a consonant 'y'
        }                         // -   if the leading char is a consonant,
        else                      //     this is a vowel 'y'
            jsta = 2;             //
    }                             //
    if( jsta == 1 )   //  
    {                 //  if the current char is determined as a vowel,
        jvow = 1;     //  set the flag jvow = 1 and break the for loop
        break;        //
    }                 //
  }
  return( jvow );     //  return value of the flag jvow
}


////////////////////////////////////////////////////////////////////////////////
// function stem_d: check whether a given stem ends in double consonant       //
// input: char stem[]                                                         //
// function returns - 1, if the stem ends in double consonant                 //
//                  - 0, otherwise                                            //
////////////////////////////////////////////////////////////////////////////////
int stem_d(char stem[])
{
  int jdc,jlen,j1,j2,j3;  // declare local variables

  jlen = strlen(stem);    // calculate length of the stem

  j2 = vowel(stem[jlen-2]);  // get type of the char before the last
  j1 = vowel(stem[jlen-1]);  // get type of the last char

  if( jlen > 2 )                 // if the stem has more than 2 chars,
      j3 = vowel(stem[jlen-3]);  // get type of the 3rd char from the last;
  else                           // otherwise, set type of the 3rd char
      j3 = 0;                    // from the last = 0

  if( j1 == 2 && j2 == 2 &&      // if both the last two chars are non-'y'
    stem[jlen-2]==stem[jlen-1] ) // and they are identical consonant,
      jdc = 1;                   // set jdc = 1

  else if( j3 != 2 && j2 == 9 && j1 == 9 ) // if the last three chars are
      jdc = 1;                             // in the sequence X-'y'-C where
                                           // X != C, it means that 'y' here
                                           // is a consonant. Set jdc = 1
  else             // in all the other cases, the last two chars can not
      jdc = 0;     // be consonant at the same time

  return( jdc );   // return value of the flag jdc
}

////////////////////////////////////////////////////////////////////////////////
// function stem_o: check whether a given stem ends with consonant-vowel-     //
//                  consonant sequence and the final consonant is not w, x,   //
//                  or y                                                      //
// input: char stem[]                                                         //
// function returns - 1, if the stem ends with C-V-C sequence and the last C  //
//                       is not w, x, or y                                    //
//                  - 0, otherwise                                            //
////////////////////////////////////////////////////////////////////////////////
int stem_o(char stem[])
{
  int jo,jlen,j1,j2,j3; // declare local variables
  char q;               //

  jlen = strlen(stem);  // calculate length of the stem
  
  q = tolower(stem[jlen-1]); // translate the last char in the stem to
                             // lower case

  if( q == 'w' || q == 'x' || q == 'y' )  // if the last char is eith 'w',
      jo = 0;                             // 'x', or 'y', then set the
                                          // flag jo = 0 
  else                                    
  {                              //
      j1 = vowel(stem[jlen-1]);  // calculate type of the last 3 chars
      j2 = vowel(stem[jlen-2]);  //
      j3 = vowel(stem[jlen-3]);  //
//
// if the last 3 chars are in the sequence of C-V-C or C-'y'-C, where C stands
// for non-'y' consonant and V stands for non-'y' vowel, set the flag jo = 1;
// otherwise, set jo = 0
//
      if( (j3 == 2) && (j2 == 1 || j2 == 9) && (j1 == 2) )
          jo = 1;
      else
          jo = 0;
  }
  return( jo ); // return value of the flag jo
}

////////////////////////////////////////////////////////////////////////////////
// The following main function is written for my testing purpose only,        //
// which is commented and can be ignored by other programmers.                //
////////////////////////////////////////////////////////////////////////////////
//void main()
//{
//  int j;
//  char a[50];
//
//  int measure(char stem[]);
//  int stem_v(char stem[]);
//  int stem_d(char stem[]);
//  int stem_o(char stem[]);
//
//  strcpy(a, "Y");
//  j = measure(a);
//  cout << a << " m = " << j << endl;
//
//  strcpy(a, "by");
//  j = measure(a);
//  cout << a << " m = " << j << endl;
//
//  strcpy(a, "trouble");
//  j = measure(a);
//  cout << a << " m = " << j << endl;
//
//  strcpy(a, "PRIVATE");
//  j = measure(a);
//  cout << a << " m = " << j << endl;
//
//  strcpy(a, "oaten");
//  j = measure(a);
//  cout << a << " m = " << j << endl;
//
//  strcpy(a, "TREES");
//  j = measure(a);
//  cout << a << " m = " << j << endl;
//}
