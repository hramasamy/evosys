// ------------------------------------------------------------------------
// Name:     HASH.H
// Author:   Kiduk Yang, 05/15/97
// ------------------------------------------------------------------------
// Description: hash functions
//    1. strhash -- hash function for string key  
// ------------------------------------------------------------------------


// --------------------------------------------------------------
// returns a hash value from the first 4 characters of a string
//  -- range 1..17576 mapped to aaa..zzz
//  -- string is assumed to be all in lowercase
//  -- any special or missing characters are mapped to
//      their nearest valid character
// ------ need code below in main module cyc for initialization
// int cycle[AZL];
// cycle[AZL-1]=1;
// for(i=AZL-2;i>=0;i--) {
//     cycle[i]=AZN*cycle[i+1];
// }
// --------------------------------------------------------------

int strhash(char key[], int cyc[]) {

    int i,n,num,missc;
    char x;
                                             
    missc=AZL-strlen(key);
    if (missc>0) missc=AZL-missc;
    else missc=999;

    num=0;
    for(i=0;i<AZL;i++) {
        if (i>=missc) x='a';
        else if (key[i]<'a') {
            missc=i+1;
            x='a';
        }
        else if (key[i]>'z') {
            missc=i+1;
            x='z';
        }
        else x=key[i];
        if (i==AZL-1) { n= (x-'a')*cyc[i]+1; }
        else { n= (x-'a')*cyc[i]; }
        num=num+n;
    }
    return (num);

}

