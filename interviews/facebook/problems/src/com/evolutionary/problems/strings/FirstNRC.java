package com.evolutionary.problems.strings;

import java.util.HashMap;
import java.util.Map;

public class FirstNRC {

    public static char firstNRC (String str) {

        char [] chrArr = str.toCharArray() ;
        Map<Character, Integer> freq = new HashMap<Character, Integer>() ;
        for (int i = 0 ; i < chrArr.length ; i++) {
            if (freq.containsKey(chrArr[i])) {
                freq.put(chrArr[i], freq.get(chrArr[i]) + 1) ;
            }
            else {
                freq.put(chrArr[i], 1) ;
            }
        }

        for (int i = 0 ; i < chrArr.length ; i++) {
            if (freq.get(chrArr[i]) == 1){
                return (chrArr[i]) ;
            }
        }

        return ' ' ;
    }

    public static void main (String[] args) {
        System.out.println(firstNRC("abcdefghija"));
    }
}
