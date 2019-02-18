#include <stdio.h>
#include <fstream>
#include <iostream>
#include <map>
#include <vector>
#include <algorithm>
#include <string>
#include <SeqCodeDefines.h>
using namespace std;

void ReadPositionFile(char *) ;

int main(int argc, char* argv[])
{
  
  //  ReadPositionFile(argv[1]) ;
  for (int 
  return 0;
}


void ReadPositionFile(char *filename)
{

    string line ;
    ifstream myfile (filename);
    if (myfile.is_open())
      {
	while ( myfile.good() )
	  {
	    getline (myfile,line);
	    cout << line << endl;
	  }
	myfile.close();
      }

    else cout << "Unable to open file"; 

    return ;

}
