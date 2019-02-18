package com.evolutionary.problems.strings;

import java.util.HashMap;
import java.util.Map;

public class LongestUC {
    public static String longestUC (String str, int k) {

        if (str == null || str.length() < k) {
            return null ;
        }
        String res = "" ;
        int start = 0 ;
        Map<Character, Integer> unique = new HashMap<Character, Integer>() ;

        for (int i = 0 ; i < str.length() ; i++) {

            char ch = str.charAt(i);

            if (unique.containsKey(ch)) {
                unique.put(ch, unique.get(ch) + 1);
            } else {
                unique.put(ch, 1);
            }

            if (unique.size() > k) {
                if (i - start > res.length() && (i - start < k)){

                    res = str.substring(start, i) ;
                    System.out.println (res) ;
                }
                while (unique.size() > k) {
                    char rem = str.charAt(start);
                    if (unique.get(rem) > 1) {
                        unique.put(rem, unique.get(rem) - 1);
                    } else {
                        unique.remove(rem);
                    }
                    start++;
                }
            }
        }
        if (str.length() - start > res.length()) {
            res = str.substring(start, str.length()) ;
        }

        return res ;

    }

    public static void main (String [] args) {
        System.out.println(longestUC("abcadcacacacea", 20));
    }
}
