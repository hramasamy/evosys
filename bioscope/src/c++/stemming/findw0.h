/////////////////////////////////////////////////////////////
// Name: findw0.h 
// Author : Wooseob Jeong
// --------------------------------------------------------
// Description : Look up the protected words list
//				 if yes, return 1
//				 if no, return 0
///////////////////////////////////////////////////////////




int findw0(char inword[], char fname[]){
        
	char pwword[50];
	int n=0;
        ifstream list(fname);

	while(list>>pwword){
		if(strcmp(pwword, inword)==0)n=1;
		}
        list.close();
	return(n);
 
}

