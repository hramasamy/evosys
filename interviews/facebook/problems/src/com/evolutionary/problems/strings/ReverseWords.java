package com.evolutionary.problems.strings;

public class ReverseWords {
    public static String reverseWords (String words) {

        String [] revWords = words.split(" ") ;

        StringBuilder sb = new StringBuilder() ;
        int i = revWords.length -1 ;
        while (i >= 0 ) {
            String str1 = revWords[i].trim() ;
            if (str1.length() != 0) {
                sb.append(str1) ;
                sb.append(" ") ;
            }
            i-- ;
        }
        String res = sb.toString().trim() ;
        return res ;
    }

    public static void reverseWords (char [] s) {
        int i = 0 ;
        for (int j = 0 ; j < s.length ; j++) {
            if (s[j] == ' ') {
                revWords(s, i, j-1) ;
                //while (s[j] == ' ') j++ ;
                i = j+1 ;
            }
        }
        revWords(s, i, s.length-1);
        revWords(s, 0, s.length-1);
        for (i = 0 ; i < s.length ; i++) {
            System.out.print(s[i]) ;
        }
        System.out.println() ;

    }

    public static void revWords(char[] s, int left, int right) {
        while (left < right) {
            char temp = s[left] ;
            s[left] = s[right] ;
            s[right] = temp ;
            left++ ;
            right-- ;
        }
    }

    public static void main (String [] args) {
        System.out.println(reverseWords("Jack and  Jill   went up the hill"));
        reverseWords(("Jack and  Jill   went up the hill").toCharArray()) ;

    }
}
