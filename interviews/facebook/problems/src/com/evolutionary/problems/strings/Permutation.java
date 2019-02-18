package com.evolutionary.problems.strings;

public class Permutation {
    public static void permute (String str, int l, int r)  {
        if (l == r) {
            System.out.print (str + " ") ;
        }
        for (int i = l ; i < r ; i++) {
            System.out.println (l + "," + i) ;
            str = swap(str, l, i) ;
            permute(str, l+1, r) ;
            str = swap(str, l, i);
        }
    }

    public static String swap (String str, int i, int j) {

        char [] arr = str.toCharArray() ;
        char temp = arr[i] ;
        arr[i] = arr[j] ;
        arr[j] = temp ;
        return String.valueOf(arr) ;
    }

    public static void main (String [] args) {
        permute ("ABCD", 0, 4) ;
    }

}
