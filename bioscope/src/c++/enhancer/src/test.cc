#include <iostream.h>
#include <fstream.h>
#include <io.h>
 

void main()

{

  ofstream File1("file2.txt"); //create file2.txt

  File1.close();

 

  //this bellow, will return error, because I use the ios::noreplace 

  //open_mode, which returns error if the file already exists.

  ofstream Test("file2.txt",ios::noreplace); 

 

  //The error that the last line returned is ios::failbit, so let.s show it

  if(Test.rdstate() == ios::failbit)

    cout << "Error...!\n";

 

  Test.clear(ios::goodbit); //set the current status to ios::goodbit

 

  if(Test.rdstate() == ios::goodbit) //check if it was set correctly

    cout << "Fine!\n";

 

  Test.clear(ios::eofbit); //set it to ios::eofbit. Useless.

 

  if(Test.rdstate() == ios::eofbit) //and check again if it is this flag indeed

    cout << "EOF!\n";

 

  Test.close();

  return (1) ;

}

