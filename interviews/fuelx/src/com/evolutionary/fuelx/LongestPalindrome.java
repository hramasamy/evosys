package com.evolutionary.fuelx;

public class LongestPalindrome {

    public static int longestPalindrome (String str) {

        if (str == null) return 0 ;

        if (str.length() <= 1 ) return 1 ;
        StringBuilder str2 = new StringBuilder() ;
        str2.append(str) ;
        String revstr = str2.reverse().toString() ;

        int max = -100 ;
        int row = -1 ;
        int col = -1 ;

        int [][] matrix = new int[str.length() +1 ][str.length()+1] ;

        for (int i = 0 ; i < str.length() ; i++) {
            matrix[i][0] = 0 ;
            matrix[0][i] = 0 ;
        }

        for (int i = 0 ; i < str.length() ; i++) {

            for (int j = 0 ; j < str.length() ; j++ ) {
                if (str.charAt(i) == str.charAt(str.length() - j)) {
                    matrix[i+1][j+1]= 1 + matrix[i][j] ;
                    if (matrix[i+1][j+1] > max) {
                        max = matrix[i+1][j+1] ;
                        row = i ;
                        col = j ;
                    }
                }
            }
        }

        return max ;
    }
}
