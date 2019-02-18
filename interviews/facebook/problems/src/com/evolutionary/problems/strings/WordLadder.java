package com.evolutionary.problems.strings;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class WordLadder {
    public int ladderLength (String begin, String end, Set<String> dict) {
        LinkedList<WordNode>  queue = new LinkedList<WordNode>() ;
        queue.add(new WordNode(begin, 1)) ;

        dict.add(end) ;

        while (! (queue.isEmpty())) {
            WordNode top = queue.remove() ;
            if (top.getWord().equals(end)) {
                return top.getNumOfSteps() ;
            }
            char [] str = top.getWord().toCharArray();
            for (int i = 0 ; i < str.length ; i++) {

                for (char x = 'a' ; x <= 'z' ; x++) {
                    char temp = str[i] ;
                    if (str[i] != x ) {
                        str[i] = x ;
                    }
                    String newword = new String (str) ;
                    if (dict.contains(newword)) {
                        queue.add(new WordNode(newword, top.getNumOfSteps() + 1)) ;
                        dict.remove(newword) ;
                    }
                    str[i] = temp ;
                }
            }
        }
        return 0 ;
    }

    public static void main (String [] args) {
        Set <String> words = new HashSet<String>() ;
        words.add ("hot") ;
        words.add("dot") ;
        words.add("dog") ;
        words.add ("log") ;
        words.add("lot") ;

        WordLadder ladder = new WordLadder() ;
        System.out.println (ladder.ladderLength("hit", "cog", words)) ;

    }
}
