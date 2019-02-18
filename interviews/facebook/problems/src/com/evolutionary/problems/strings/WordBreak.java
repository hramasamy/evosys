package com.evolutionary.problems.strings;

import java.util.*;

public class WordBreak {

    public static boolean  wordBreak (String str, Set<String> dict) {

        int [] arr = new int [str.length()+1] ;
        Arrays.fill(arr, -1);

        arr[0] = 0 ;

        for (int i = 0 ; i < str.length() ; i++) {
            if (arr[i] != -01) {
                for (int j = i + 1;  j <= str.length() ; j++) {
                    String substr = str.substring(i, j) ;
                    if (dict.contains(substr)) {
                        arr[j] = i ;
                    }
                }
            }
        }

        return arr[str.length()] != -1 ;

    }

    public static List<String> wordBreakStrings (String str, Set<String>dict) {

        if (str == null ) {
            return null ;
        }

        ArrayList<String> [] pos = new ArrayList[str.length() +1] ;
        pos[0] = new ArrayList<String>() ;

        for (int i = 0 ; i < str.length() ; i++) {
            if (pos[i] != null ) {
                for (int j = i + 1 ; j <= str.length() ; j++) {
                    String substr = str.substring(i, j) ;
                    if (dict.contains(substr)) {
                        if (pos[j] == null) {
                            ArrayList<String> lis = new ArrayList<String>() ;
                            lis.add(substr) ;
                            pos[j] = lis ;
                        }
                        else {
                            pos[j].add(substr) ;
                        }
                    }
                }
            }
        }

        if (pos[str.length()] == null) {
            return null ;
        }
        else {
            ArrayList<String> res = new ArrayList<String>() ;
            dfs(pos, res, "", str.length()) ;
            return res ;

        }
    }

    public static void dfs (ArrayList<String> [] lis, ArrayList<String> res, String cur, int i) {

        if (i == 0) {
            res.add(cur.trim()) ;
            return ;
        }

        for (String s : lis[i]) {
            String combined = s + " " + cur ;
            dfs(lis, res, combined, i - s.length() ) ;

        }

    }

    public static void main (String [] args) {

        Set <String> words = new HashSet<String>() ;
        words.add("leet" ) ;
        words.add("code") ;
       System.out.println ( wordBreak("leetcode", words)) ;

       words.add("cat" ) ;
       words.add("cats") ;
       words.add("sand") ;
       words.add("and") ;
       words.add("dog") ;
       List<String> res = wordBreakStrings("catsanddog", words) ;
       for (String s : res) {
           System.out.println (s) ;
       }

    }
}
