package com.evolutionary.problems.strings;

import java.util.HashMap;
import java.util.Map;

public class MinWindow {

    public static String minWindow (String source, String target) {
        String result = "";
        if (target.length() > source.length()) {
            return null ;
        }
        Map <Character, Integer> tc = new HashMap<Character, Integer>() ;
        for (int i = 0 ; i < target.length() ; i++ ) {
            if (tc.containsKey(target.charAt(i))){
                tc.put(target.charAt(i), tc.get(target.charAt(i)) + 1 ) ;
            }
            else {
                tc.put(target.charAt(i), 1) ;
            }
        }

        Map <Character, Integer> sc = new HashMap<Character, Integer>() ;
        int left = 0 ;
        int count = 0 ;
        int minLen = source.length() + 1 ;
        for (int i = 0 ; i < source.length() ; i++) {
            Character c = source.charAt(i);
            if (tc.containsKey(c)) {
                if (sc.containsKey(c)) {
                    if (sc.get(c) < tc.get(c)) {
                        count++;
                    }
                    sc.put(c, sc.get(c) + 1);
                } else {
                    sc.put(c, 1);
                    count++;
                }
            }

            if (count == target.length()) {
                Character ch = source.charAt(left);
                while (!sc.containsKey(ch) || sc.get(ch) > tc.get(ch)) {
                    if (sc.containsKey(ch) && sc.get(ch) > tc.get(ch)) {
                        sc.put(ch, sc.get(ch) - 1);
                    }
                    left++;
                    ch = source.charAt(left);
                }


                if (i - left + 1 < minLen) {
                    //System.out.println(i + " " + left);
                    result = source.substring(left, i + 1);
                    minLen = i - left + 1;
                }
            }

        }
        return result ;
    }

    public static void main (String [] args) {

        System.out.println (minWindow("ADOBECODEBANC", "ABC") );
    }
}
