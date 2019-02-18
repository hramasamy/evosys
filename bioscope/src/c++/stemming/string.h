// ************************************************************************
// Name:     STRING.H
// Author:   Kiduk Yang, 11/12/96
//             modified, 01/23/98
//           (suffix & replace functions by Rong Tang)
// ------------------------------------------------------------------------
// Description:  miscellenous string manipulation functions
//   1. SUFFIX(S1,S2)     :  returns 1 if suffix S2 is found in S1
//   2. REPLACE(S1,S2,S3) :  replaces suffix S2 with S3 in S1
//   3. CONCAT(S1,S2,S3)  :  S3 = S1 concat S2
//   4. SPLIT(S,x,S1,S2)  :  splits S into S1 & S2 at delimiter x
//      (returns null to S2 if no delimiter or delimiter is at the end.)
//   5. SUBSTR(S1,n,m,S2) :  S3 = S1[n-1]..S1[n+m-1]
//   6. LEFT(S)           :  compresses leading blanks
//   7. TRIM(S)           :  trims off trailing blanks
//   8. PUNCT(S)          :  delete special characters at word boundry.
//   9. PUNCT2(S)         :  execute PUNCT until there are no special 
//                           characters at word boundry.
//   10. ITOA(int,S)      :  converts number to string
// ------------------------------------------------------------------------
// NOTE:
// ************************************************************************



const int STL=200;      // maximum string length
const int STL2=400;     // maximum concatenated string length



int suffix(char *sword, char send[])
{
  //positioning sword to the beginning of the send
   sword=sword+strlen(sword)-strlen(send);

  //compare sword with send and return 1 or 0 if they are/not the same
  if (strcmp(send,sword)==0) return(1);
  else return(0);
}



void replace(char rword[], char rend1[], char rend2[])
{
  int pos1, i;

  //pos1 the position at the beginning of the rend1
  pos1=strlen(rword)-strlen(rend1);

  //within the string length of rend2, the part of the rword starting from
  //the beginning of the rend1 is replaced by rend2
  for(i=0;i<strlen(rend2);i++) rword[pos1+i]=rend2[i];
  rword[pos1+i]='\0'; // puts the reminder of rword into null
}



void concat(char *s1, char *s2, char *s3) {
    char temp[STL];
    strcpy(temp,s1);
    strcat(temp,s2);
    strcpy(s3,temp);
}


void split(char *s, char x, char *s1, char *s2) {
    char *p1;
    char *p2;
    char temp[BWL];
    strcpy(temp,s);
    p1=temp;
    p2=strchr(temp,x);
    if (p2=='\0') *s2='\0';
    else {
	*p2='\0';
	p2++;
	strcpy(s2,p2);
    }
    strcpy(s1,p1);
}


void substr(char *s1, int a, int b, char *s2) {
    char temp[STL];
    char *tp;
    s1+=a-1;
    strcpy(temp,s1);
    tp=temp;
    tp+=b;
    *tp='\0';
    strcpy(s2,temp);
}
  
  
// compress leading blanks
void left(char word[])
{
    int j,k;
    char *wd;
    wd=word;
    k = strlen(wd);     //  trim the end spaces of the word
    for(j=0; j<k; j++) {   //
        if(wd[j] != ' ') break;
        wd++;
    }
    strcpy(word,wd);
}

// Strip off trailing blanks
void trim(char word[])
{
    int j,k;
    k = strlen(word);     //  trim the end spaces of the word
    for(j=k; j>0; j--)    //
        if(word[j] != ' ' && word[j] != '\0')
           break;
    word[j+1] = '\0';
}


void punct(char word[]){                                     
     int i;                                                  
     char sword[STL];                                        
     char psigns[30]="~!^&*()|{}<>[];,?'\"-+=:./\\";         
     char psign2[3]="'s";                                    
     if (suffix(word,psign2)) replace(word,psign2,"\0");     
     strcpy(sword,word);                                     
     for(i=0;i<strlen(psigns);i++) {                         
         if (sword[0]==psigns[i]) {                          
             substr(sword,2,strlen(word),word);              
             strcpy(sword,word);                             
             break;                                          
         }                                                   
     }                                                       
     for(i=0;i<strlen(psigns);i++) {                         
         if (sword[strlen(sword)-1]==psigns[i]) {            
             substr(sword,1,strlen(word)-1,word);            
             strcpy(sword,word);                             
             break;                                          
         }                                                   
     }                                                       
}                                                            
                                                             
                                                        
void punct2(char word2[]) {                              
    char inword[STL];                                   
    int done=0;                                         
    while (done==0) {                                   
        strcpy(inword,word2);                            
        punct(word2);                                    
        if (strcmp(inword,word2)==0) done=1;             
    }                                                   
}                                                       
                                                        
                                                        
// modified Bob Sumner's code

void itoa(int num, char wd[]) {

    int expon, digit, rest;
    int index=0;
    short first_done;

    first_done = 0;
    rest = num;
    expon=100000; /* max. number = 999999 */
    while (expon >= 1) {
       if ((rest >= expon) || (first_done == 1)) {
	  digit = rest / expon;
	  wd[index] = digit + '0';
	  rest = rest - digit * expon;
	  index = index + 1;
	  first_done = 1;
       }
       expon = expon/10;
    }
    wd[index] = '\0';
    
}


