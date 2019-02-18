// ************************************************************************
// Name:     MAKELIST0.H
// Author:   Woosub Jeoung, 11/13/96
// ------------------------------------------------------------------------
// Description: 
//    makes a word file interactively by writing out
//    words inputted by the user into the default file.
// ------------------------------------------------------------------------
// INPUT:  no input parameter for the function,
//         but command line input of words/strings by the user within function  
// OUTPUT: default word file referenced in #DEFINE PWFILE0 statement.  
// ------------------------------------------------------------------------
// NOTE:   used to create the protected word file interactively.    
// ************************************************************************




void makelist0(){

	char line[200];
	int i;

	ofstream outfile(PWFILE0);
	i=0;

	while(i==0){
	    cout<<"\nEnter the words you want not to be stemmed seperated by blanks.\n ";
                cin.getline(line,sizeof(line));
		outfile<<line<<" ";
		cout<<endl<<"Continue to input[y/n]?\n";
                cin.getline(line,sizeof(line));
		if(line[0]=='y' || line[0]=='Y')i=0;
		else i=1;
	}
     
        outfile.close();
	
}

