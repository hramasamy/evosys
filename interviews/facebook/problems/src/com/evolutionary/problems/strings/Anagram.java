package com.evolutionary.problems.strings;

import java.util.HashMap;
import java.util.Map;

public class Anagram {

    public static boolean anagram (String str1, String str2) {
        if (str1 == null && str2 == null)
            return true ;
        if (str1 == null) return false ;
        if (str2 == null ) return false ;
        str1 = str1.replace(" ", "").toLowerCase();
        str2 = str2.replace(" ", "") .toLowerCase() ;
        if (str1.length() != str2.length()){
            return false ;
        }
        Map <Character, Integer> freq = new HashMap<Character, Integer>() ;

        for (int i = 0 ; i < str1.length() ; i++ ) {
            if (freq.containsKey(str1.charAt(i))) {
                freq.put(str1.charAt(i), freq.get(str1.charAt(i)) + 1);
            }
            else {
                freq.put(str1.charAt(i), 1) ;
            }
        }
        for (int i = 0 ; i < str2.length() ; i++) {
            if (freq.containsKey(str2.charAt(i))) {
                int j = freq.get(str2.charAt(i)) -1 ;
                if (j == 0)
                    freq.remove(str2.charAt(i)) ;
                else
                    freq.put(str2.charAt(i), j) ;
            }
            else {
                return false ;
            }
        }
        if (freq.size() == 0)
            return true ;
        else
            return false ;
    }

    public static void main (String [] args) {
        System.out.println( anagram("word", "wrdo") ) ;
        System.out.println (anagram( "slee p", "sl e ep")) ;
    }
}
