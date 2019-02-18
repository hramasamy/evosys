// -----------------------------------------------------------------
// Name:     MAKELIST.H
// Author:   Kiduk Yang, 11/08/96
// -----------------------------------------------------------------
// Description: creates a string random access file for binary search
//   1. the header portion will include the file size
//       (total number of words) needed for binary search
//   2. create a random access file from a sorted word list.
// -----------------------------------------------------------------
// INPUT:    a sorted text file
// OUTPUT:   a random access file
// -----------------------------------------------------------------
// NOTE:  Need to run only once.
// -----------------------------------------------------------------


void makelist(ifstream &inf, fstream &ranf) {

    int i;             // loop counter
    char word[50];     // word in the list

    i=0;
    while (! inf.eof()) {
	i++;
	inf>>word;
	// !! assumes max word length of 50: better way?
	ranf.seekp(50L*i, ios::beg);
	ranf<<word<<endl;
    }

    // write header: total # of elements (i.e. words)
    ranf.seekp(50L*0, ios::beg);
    ranf<<(i-1)<<endl;

 //   ranf.close();
    inf.close();

}
