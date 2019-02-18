////////////////////////////////////////////////////////////////////////////////
// INLS-161 C++ Programming Team Project - Stemming                           //
//                                                                            //
// Student name: Danqi Song                                                   //
// Date: Nov. 16, 1996                                                        //
// Modified by Kiduk Yang, 11/23/96
//                                                                            //
//                                                                            //
// (1) void makelist2(ifstream &inf, fstream &ranf)                          //
//     - read words from ASCII dictionary file "infname"                      //
//     - write words to binary random access file "ranf" with word tags       //
//                                                                            //
//                                                                            //
////////////////////////////////////////////////////////////////////////////////

#define WL 50              // Maximum length of words allowed
#define BWL 5000              // Maximum length of words allowed
#define XL1   "xlist1"     // Preliminary exception list1
#define XL2   "xlist2"     // Preliminary exception list2
#define DICT  "dict.dat"   // ASCII dictionary file.
#define RDICT "ran_dict"   // RAB dictionary file.

#include <iostream.h>  //  standard head files
#include <fstream.h>   //
#include <stdlib.h>    //
#include <string.h>    //
#include <ctype.h>     //
#include "string.h"
#include "makelist2.h"
#include "findw2.h"


// display file open error message.
void fin_err(char *file) {
    cerr<<"Cannot open "<<file<<"!"<<endl;
    exit(8);
}

//
// The follwoing is the testing "main" function.
//
int  main()
{
  fstream fp, ranf;                       //
  char word[WL];                          // local variables declaration
  char inpfil[30];                        //
  int opt;                                //

ENTERAGA:
  cout << endl
       << "Select an option:" << endl
       << "  1 = create a Random Access Binary file of a large word list;\n"
       << "  2 = perform a single word search;" << endl
       << "  0 = exit." << endl
       << "  ";
  cin.getline(word,sizeof(word));
  if(word[0] < '0' || word[0] > '3')
     goto ENTERAGA;

  if(tolower(word[0]) == '1')
  {
   cout << endl
        << "Enter the file name of the word list or\n"
        << " enter xxx to use the default dictionary.\n"
        << "(The file must be sorted alphabetically.)\n";
   cin.getline(inpfil,sizeof(inpfil));
   if (strcmp(inpfil,"xxx")==0) strcpy(inpfil,DICT);
  ifstream inf(inpfil);  //
  if (!inf) fin_err(inpfil);   // show error if can not open
  // open the random access binary file
  fp.open(RDICT, ios::out | ios::in | ios::trunc);

//
// call function makelist2 to read words from the ASCII file "inpfil"
// and write to binary file "fp"
     makelist2(inf,fp);
//
// close the binary file argv[2]
     fp.close();
   cout << endl
        << "RAB dictionary file has been created in \""<<RDICT<<"\", and\n"
        << "  Preliminary Exception List has been created in \""<<XL2<<"\".\n\n";
     goto ENTERAGA;
  }
//---------------------------------------------------------------------
  else if(tolower(word[0]) == '2')
  {
   cout << endl
        << "Enter the Tab-Binary Search file name or\n"
        << " enter xxx to use the default dictionary.\n";
   cin.getline(inpfil,sizeof(inpfil));
   if (strcmp(inpfil,"xxx")==0) strcpy(inpfil,RDICT);
 
//
// open the random access binary file
//
     fp.open(inpfil, ios::in);
     if (!fp) fin_err(inpfil);
//
// ask user to select search method (simple or tab search ?)
//

     cout<< "\nSelect search option (0=simple; 1=tab): ";
     cin.getline(word,sizeof(word));
     opt = atoi(word);
//
// loop to allow user to search words one by one
//

     for(;;)
     {
//
// ask user to enter word to search (enter two dots .. to stop the process)
//
       cout << endl << "Enter a word to search (enter .. to stop): ";
       cin.getline(word,sizeof(word));
       if (strcmp(word,"..")==0)
           break;
//
// call function findw2 to perform binary search
//
       if(findw2(opt,word,fp))
          cout << word << " is found !" << endl;
       else
          cout << word << " is NOT found !" << endl;
     }

     fp.close();
     goto ENTERAGA;
  }
  else if(tolower(word[0]) == '0')
  {
     exit(1);
  }
}
