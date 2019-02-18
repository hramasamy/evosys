// ************************************************************************
// Name:     DISPLAY.H
// Author:   Kiduk Yang, 11/05/96
// ------------------------------------------------------------------------
// Description: screen display modules (USER INTERFACE & ERROR MESSAGING)
//   1. SHOWMENU:  displays the menu.
//   2. CHK_LN:    check for valid menu selection.
//   3. EXIT:      exit program if exit condition is met. (by Rong Tong)
//   4. REPROMPT:  displays the input error message & repromts for input.
//   5. FNAME_ERR: display filename error message, & reprompt
//   6. FULLPATH:  checks the possible mapping of '~' for valid full path.
//   7. OPENFILE:  check for valid filename & reprompts if needed.
//                 (recursive function. max recursion set by MXER
//   8. FIN_ERR:   displays the file open error message.
// ------------------------------------------------------------------------
// NOTE:
// ************************************************************************


#define MXER 3                // input error limit
int loopcnt=0;                // recursive loop exit counter.


void showmenu() {
    cout<<"This program can stem your input file by using\n"
        <<"     a. Simple stemmer:  simple plural stemmer\n"
        <<"     b. Porter stemmer:  suffix stemmer\n"
        <<"     c. Krovetz stemmer: inflectional stemmer\n"
        <<"     d. Combo stemmer:   combination stemmer\n"
        <<"and can be applied to following types of files\n"
        <<"     1. text without protected words\n"
        <<"     2. text with protected words\n"
        <<"     3. inverted index\n\n"
        <<"---------------------------------------------------------\n"
        <<"Please select a stemmer (a,b,c,d) and a file type (1,2,3)\n"
        <<"  by entering a letter followed by a number. (e.g. a1)\n"
        <<"If you want to quit, type 'quit'.\n"
        <<"---------------------------------------------------------\n\n";
}



//check for valid menu selection
int chk_ln (char ln[]) {
    if ( (ln[0]=='a' || ln[0]=='b' || ln[0]=='c' || ln[0]=='d') &&
       (ln[1]=='1' || ln[1]=='2' || ln[1]=='3') )
       return(1);
    else return(0);
};



// exit the program if exit condition is met.
// (by Rong Tong, 11/10/96)
void exit(char input[]) {
    if (strcmp(input,"quit")==0 || strcmp(input,"Quit")==0) {
        cout<<"\nThank you for using the Nice Stemmer.\n"
            <<"Have a nice day!\n";
        exit(8);
    }
}



// display the menu selection error message & reprompt.
void reprompt() {
    cout<<"\nYou have entered an invalid menu choice.\n"
        <<"\nPlease enter a, b, c, or d, "
        <<"immediately follwed by 1, 2, or 3.\n"
        <<"  (i.e. a1,a2,a3,b1,b2,b3,c1,c2,c3,d1,d2,d3)\n"
        <<"To redisplay the menu, type 'menu'\n";
}



// display filename error message, & reprompt
void fname_err(char file[]) {
    if (file[0]=='~')
        cout<<"\nYou have either typed in an incorrect filename,\n"
            <<"or program could not interprete '~' reference of your input.\n"
            <<"Please try again (maybe with the full path name),\n"
            <<"or type 'quit' to exit the program.\n";
    else
        cout<<"\nProgram could not open "<<file<<".\n"
            <<"Please check your file name and try again,\n"
            <<"or type 'quit' to exit the program.\n";
}



//--------------------------------------------------------
// checks the possible mapping of '~' for valid full path.
//--------------------------------------------------------

void fullpath(char file[], char map[], int &found) {

    char pmap[FFNL]=PMAP;  // holds the possible full path mapping for '~'
    char fname[FFNL];      // file name without '~'
    char root[FFNL];       // holds the first portion of PMAP
    char rest[FFNL];       // holds the rest of PMAP
    char tfile[FFNL];      // holds the test full file name

    // strip off the first character of input file name
    substr(file,2,strlen(file),fname);

    while (strlen(pmap)>0) {

        // test the full file name.
        split(pmap,' ',root,rest);
        concat(root,fname,tfile);
        ifstream inf(tfile);

        //   if found, pass back the correct path & mapping,
        //   set the found flag, and exit the loop.
        if (inf) {
            found=1;
            strcpy(file,tfile);
            strcpy(map,root);
            break;
        }

        // if not found, go on to next path mapping.
        strcpy(pmap,rest);

    }

}



//--------------------------------------------------------
// checks the possible mapping of '-' for valid full path.
//--------------------------------------------------------

void fullpath2(char file[], char map[], int &found) {

    char pmap[FFNL]=UMAP;  // user directory for data
    char fname[FFNL];      // file name without '-'
    char tfile[FFNL];      // holds the test full file name

    // strip off the first character of input file name
    substr(file,2,strlen(file),fname);

    concat(pmap,fname,tfile);
    ifstream inf(tfile);

    //   if found, pass back the correct path & mapping,
    //   set the found flag, and exit the loop.
    if (inf) {
	found=1;
	strcpy(file,tfile);
	strcpy(map,pmap);
    }

}



//-----------------------------------------------
// check for invalid filename: recursive function
//-----------------------------------------------

void openfile(char file[], char map[], int type) {

    exit(file);               // check for exit condition
    int found=0;              // file open flag

    // if INPUT file,
    if (type==1) {

        ifstream inf(file);   //  open the file.
        if (!inf) {           //  if cannot open,

            // try to resolve '~' if applicable.
            if (file[0]=='~') fullpath(file,map,found);
            // try to resolve '-' if applicable.
            else if (file[0]=='-') fullpath2(file,map,found);

            //   and reprompt until input error limit is exceeded.
            if (!found) {
                loopcnt++;
                if (loopcnt>MXER) {
                    cerr<<"\nInput error limit exceeded.  Goodbye!\n";
                    exit(8);
                }
                else {
                    fname_err(file);
                    cin>>file;
                    openfile(file,map,1);
                }
            }

        }

    }   //endif type=1


    // if OUTPUT file,
    else if (type==2) {

        // if it starts with '~', write out the full path.
        if ((file[0]=='~') || (file[0]=='-')) {
           substr(file,2,strlen(file),file);
           concat(map,file,file);
        }

        ofstream outf(file);  //  open the file.
        if (!outf) {          //  if cannot open,

            // display error message and reprompt
            //   until input error limit is exceeded.
            loopcnt++;
            if (loopcnt>MXER) {
                cerr<<"\nInput error limit exceeded.  Goodbye!\n";
                exit(8);
            }
            else {
                fname_err(file);
                cin>>file;
                openfile(file,map,2);
            }

        }

    }   // endif type=2

}

// display file open error message.
void fin_err(char *file) {
    cerr<<"Cannot open "<<file<<"!"<<endl;
    exit(8);
}
