package com.evolutionary.problems.strings;

public class LongestNR {
    public static String longestNR (String str) {
        String res = "" ;
        boolean [] flag = new boolean[256] ;
        int result = 0 ;
        int start = 0 ;
        char [] arr = str.toCharArray() ;


        for (int i = 0 ; i < arr.length ; i++) {
            char ch = arr[i] ;
            if (flag[ch]) {
                result = Math.max(result, i - start) ;
                if (result == i-start) {
                   // System.out.println (str.substring(start, i)) ;
                    res = str.substring(start, i) ;
                }

                for (int k = start ; k < i ; k++) {
                    if (arr[k] == ch){
                        start = k + 1 ;
                        break ;
                    }
                    flag[arr[k]] = false ;
                }
            }
            else{
                flag[ch] = true ;
            }
        }
        result = Math.max(result, arr.length - start) ;
        if (result == arr.length - start) {
            res = str.substring(start, arr.length) ;
        }
        return res ;
    }

    public static void main (String []args) {
        System.out.println( longestNR("abcddefddccabcdefbb" ));
    }
}
