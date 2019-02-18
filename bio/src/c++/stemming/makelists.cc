//-------------------------------------------------------------------
// Name:     MAKELISTS.CC
// Author:   Kiduk Yang,         04/20/98
//-------------------------------------------------------------------
// Description:
//   creates random access file from default dictionary, stopword,
//    and exception list
//-------------------------------------------------------------------
// INPUT:    dict.dat -- dictionary (text)
//           stoplist-- stopword list (text)
//           xlist-- exception list (text)
// OUTPUT:   ran_dict, ran_stoplist, ran_xlist
//-------------------------------------------------------------------
// NOTE:  Must be run before using Nice Stemmer
//-------------------------------------------------------------------


#define WL 50              // Maximum length of words allowed
#define BWL 5000              // Maximum length of words allowed
#define CTAG  8281         // Number of tags used in random access file
#define XL1   "xlist1"     // Preliminary exception list1
#define XL2   "xlist2"     // Preliminary exception list2
#define DICT  "dict.dat"   // ASCII dictionary file.
#define RDICT "ran_dict"   // RAB dictionary file.
#define SWF   "stoplist"        // input file of stopword list
#define RSWF  "ran_stoplist"    // random access file name
#define XWF   "xlist"           // input file of exception word list
#define RXWF  "ran_xlist"       // random access file name


#include <iostream.h>  //  standard head files
#include <fstream.h>   //
#include <stdlib.h>    //
#include <string.h>    //
#include <ctype.h>     //
#include "string.h"
#include "makelist.h"
#include "makelist2.h"
#include "findw2.h"



// display file open error message.
void fin_err(char *file) {
    cerr<<"Cannot open "<<file<<"!"<<endl;
    exit(8);
}


int  main()
{
  char word[WL];                         
  char fin[30],outfile[30];        
  fstream dict;            // random access file for dictionary
  fstream swlist;          // random access file for stopword
  fstream xlist;           // random access file for exception list

  strcpy(fin,DICT);
  ifstream inf1(fin);  //
  if (!inf1) fin_err(fin);   // show error if can not open

  // open the random access binary file
  dict.open(RDICT, ios::out | ios::in | ios::trunc);

  // make random access dictionary file
  makelist2(inf1,dict);
  dict.close();
  cout<<endl
      <<"RAB dictionary file has been created in \""<<RDICT<<"\", and\n"
      <<"  Preliminary Exception List has been created in \""<<XL2<<"\".\n\n";


  strcpy(fin,SWF);
  ifstream inf2(fin);  //
  if (!inf2) fin_err(fin);   // show error if can not open

  // open the random access binary file
  swlist.open(RSWF, ios::out | ios::in | ios::trunc);

  // make random access file
  makelist(inf2,swlist);
  swlist.close();
  cout<<endl
      <<"RAB stopword file has been created in \""<<RSWF<<"\"\n\n";


  strcpy(fin,XWF);
  ifstream inf3(fin);  //
  if (!inf3) fin_err(fin);   // show error if can not open

  // open the random access binary file
  xlist.open(RXWF, ios::out | ios::in | ios::trunc);

  // make random access file
  makelist(inf3,xlist);
  xlist.close();
  cout<<endl
      <<"RAB exception list file has been created in \""<<RXWF<<"\"\n\n";

}
