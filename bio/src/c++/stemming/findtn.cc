
#include <ctype.h>
#include <fstream.h>
#include <iomanip.h>
#include <iostream.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>
#include "define.h"        // global constants declarations
#include "string.h"        // string functions
#include "display.h"       // error message
#include "findw.h"         // binary word search module
#include "findw2.h"        // tab-binary word search module
#include "findn.h"         // find term freq module
#include "findtn.h"        // find term # module



main (int argc, char *argv[]) {

char word[WL];
char rtnt[NL];
int tn;
fstream tnt;

strcpy(rtnt,argv[1]);

// open the random access binary file: TNT
tnt.open(rtnt, ios::in);
if (! tnt) fin_err(rtnt);

while(cin>>word) {
  tn=findtn(1,word,tnt);
  cout<<"term="<<word<<", tn="<<tn<<endl;
}



}
