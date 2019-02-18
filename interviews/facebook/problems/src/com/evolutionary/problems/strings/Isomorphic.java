package com.evolutionary.problems.strings;

import java.util.HashMap;
import java.util.Map;

public class Isomorphic {

    public static boolean isIsomorphic (String s1, String s2) {

        if (s1 == null && s2 == null) {
            return true ;
        }
        if ((s1 == null && s2 != null) && (s1 != null && s2 == null)) {
            return false ;
        }
        if (s1.length() != s2.length()) return false ;
        Map<Character, Character> c2c = new HashMap<Character, Character>() ;

        for (int i = 0 ; i < s1.length() ; i++) {
            char c1 = s1.charAt(i) ;
            char c2 = s2.charAt(i) ;
            if (c2c.containsKey(c1)) {
                if (c2c.get(c1) != c2) {
                    return false ;
                }
            }
            else {
                if (c2c.containsValue(c2)) {
                    return false ;
                }
                c2c.put(c1, c2) ;
            }
        }

        return true ;
    }

    public static void main (String [] args) {
        System.out.println(isIsomorphic("egg", "add"));
        System.out.println(isIsomorphic("foo", "bar"));
    }
}
