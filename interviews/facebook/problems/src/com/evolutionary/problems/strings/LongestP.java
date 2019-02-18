package com.evolutionary.problems.strings;


// Java implementation of O(n^2) time and O(1) space method
// to find the longest palindromic substring
public class LongestP {
    // A utility function to print a substring str[low..high]
    static void printSubStr(String str, int low, int high) {
        System.out.println(str.substring(low, high + 1));
    }

    // This function prints the longest palindrome substring
    // (LPS) of str[]. It also returns the length of the
    // longest palindrome
    static int longestPalSubstr(String str) {
        int maxLength = 1; // The result (length of LPS)

        int start = 0;
        int len = str.length();

        int low, high;

        // One by one consider every character as center
        // point of even and length palindromes
        for (int i = 1; i < len; ++i) {
            // Find the longest even length palindrome with
            // center points as i-1 and i.
            low = i - 1;
            high = i;
            while (low >= 0 && high < len
                    && str.charAt(low) == str.charAt(high)) {
                if (high - low + 1 > maxLength) {
                    start = low;
                    maxLength = high - low + 1;
                }
                --low;
                ++high;
            }

            // Find the longest odd length palindrome with
            // center point as i
            low = i - 1;
            high = i + 1;
            while (low >= 0 && high < len
                    && str.charAt(low) == str.charAt(high)) {
                if (high - low + 1 > maxLength) {
                    start = low;
                    maxLength = high - low + 1;
                }
                --low;
                ++high;
            }
        }

        System.out.print("Longest palindrome substring is: ");
        printSubStr(str, start, start + maxLength - 1);

        return maxLength;
    }

    public static void longestPalin (String str){

        int [][] mat = new int [str.length() +1 ][str.length()+1] ;

        for (int i = 0 ; i < mat[0].length ; i++) {
            mat[i][0] = 0 ;
            mat[0][i] = 0 ;
        }
        int max = Integer.MIN_VALUE ;
        StringBuilder sb = new StringBuilder() ;
        String revstr  = sb.append(str).reverse().toString() ;
        int row = -1 ;
        int col = -1 ;
        for (int i = 0 ; i < str.length() ; i++) {
            for (int j = 0 ; j < str.length() ; j++ ) {

                if (str.charAt(i) == revstr.charAt(j)) {
                    mat[i+1][j+1] = mat[i][j] + 1 ;
                    if (max < mat[i+1][j+1]) {
                        max = mat[i+1][j+1] ;
                        row = i ;
                        col = j ;
                    }
                }
                else {
                    mat[i+1][j+1] = 0 ;
                }
            }
        }
        System.out.println(max + " length of longest" + " " + row + " " + col);
        System.out.println (revstr.substring(row- max +1, row+1)) ;



    }

    // Driver program to test above function
    public static void main(String[] args) {

        String str = "forgeeksskeegfor";
        System.out.println("Length is: " +
                longestPalSubstr(str));
        System.out.println( " new mod") ;
        longestPalin(str) ;
    }

}

