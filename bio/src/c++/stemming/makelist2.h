////////////////////////////////////////////////////////////////////////
// File Name : makelist2.h
// Author : Danqi Song
//
// Description:
//   	1. read words from ASCII dictionary file "infname"
//	2. write words to binary random access file "ranf" with word tags
/////////////////////////////////////////////////////////////////////////


#include <ctype.h>

#define CTAG  8281

//
// Function to create a random access binary file
// Input: ifstream &inf = input ASCII dictionary file (such as dict.dat)
//        fstream &ranf = file pointer of the random access binary file
//
// Structure of the random access file:
//  (1) Create 91*91 (=8281) word tag; each tag ID (1-8281) corresponds to
//      a two-char word tag, i.e. "aa", "in", etc, which is the 1st two-char
//      of a word. (assume valid characters to be from blank to 'Z').
//  example: given word "information", the 1st 2 chars are "in", so
//      the tag ID = ('i'-32)*91+('n'-32)+1 = (105-32)*91+(110-32)+1 = 6722.
//      If "information" is the 1st word (of couse not) with tag "in",
//      then at the location 6754*50, we store the location of "information".
//  (2) The ith tag contains the actual location of the 1st word with this
//      tag. The location number is stored in the location starting
//      from 50*i (i=1 to 8281).
//  (3) The location starting from 0*50 stores the total number of words
//  (4) The actual location of words is from (8281+1)*50 to
//      (8281+total number of words)*50
//

void makelist2(ifstream &inf, fstream &ranf) 
{
  int i,j,j1,j2;     //
  int tagid,acttag;  // local variables declaration
  int tagadd,tagpre; //
  int tagnull;
  char word[WL];     //
  char linebuf[200]; //
  char p1,p2;        //

  i=0;              //  index of word to be read and writen
  tagid=1;          //  ID of word tag (the 1st two chars of a word)
  tagpre=CTAG+1;    //  location of the 1st word
  tagnull=0;        //  missing tags indicator

  while (!inf.eof())
  {
    i++;                                   // increase word index by 1
    inf.getline(linebuf,sizeof(linebuf));  // read from ASCII dictionary file
    strcpy(word,linebuf);                  // copy to variable "word"
    trim(word);

    tagadd = CTAG+i;                   // set location of the ith word
    ranf.seekp(50L*tagadd, ios::beg);   // set seekp pointer to this location
    ranf << word << endl;              // write to random access binary file

//
// Assume that the dictionary covers words with characters from [space](32)
// to z(122). Build a mapping from these chars to integer number (0-90), i.e.
// 0 corresponds to 32, 90 corresponds to 122, and so on.
//
    j1 = word[0]-32;  // translate the 1st char into number (0-90)
    j2 = word[1]-32;  //           the 2nd
    if(j1 < 0)        // if any char mapping number is beyond 0-90,
       j1 = 0;        //  set it as 0 or 90.
    else if(j1 > 90)  //  Q> what happens?
       j1 = 90;       //
    if(j2 < 0)        //
       j2 = 0;        //
    else if(j2 > 90)  //
       j2 = 90;       //
    acttag = j1*91 + j2 + 1; // calculate tag ID this word matches

// if tag ID of current word is larger than the given tag ID "tagid",
// then at locations from tagid*50L to (acttag-1)*50, we store actual
// location of previous word tag and set the given tag ID = current tag ID
    if( tagid < acttag )
    {
        for(j=tagid; j<acttag; j++)
        {
            ranf.seekp(50L*j, ios::beg);
        // !! Is this really necessary? (Kiduk, 11/24/96)
        // !! how about putting value that indicate absence of word?
        //  ranf << tagpre << endl;
            ranf << tagnull << endl;
        }                                 //
        tagid = acttag;                   //
        ranf.seekp(50L*tagid, ios::beg);
        ranf << tagadd << endl;
        tagid++;
    }

//
// if tag ID of current word is equal to the given tag ID "tagid",
// we store actual location of current word and increase given tag ID
// by 1, also set "tagpre"="tagadd"
//
    else if( tagid == acttag )
    {
        ranf.seekp(50L*tagid, ios::beg);
        ranf << tagadd << endl;
        tagid++;
    }
    tagpre = tagadd;

  }  // end while loop

//
// After read and write all the words, if the given tag ID "tagid" is still
// smaller than total number of tags, set actual location of word tags equal
// to that of the last word tag
//
  if(tagid < CTAG)
  {
     for(j=tagid+1; j<=CTAG; j++)
     {
        ranf.seekp(50L*j, ios::beg);
        ranf << tagnull << endl;

        j1 = 32 + int((j-1)/91);
        j2 = 32 + (j-1) - 91*(j1-32);
        p1 = j1;
        p2 = j2;
     }
  }

  ranf.seekp(50L*0, ios::beg);
  ranf << (i-1) << endl;

  inf.close();

}


