package com.evolutionary.problems.strings;

import java.io.CharConversionException;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class ValidP {

    public static boolean validParens (String parens) {

        Map<Character, Character> parenmatch = new HashMap <Character, Character>() ;
        parenmatch.put ('(', ')') ;
        parenmatch.put ('{', '}') ;
        parenmatch.put ('[', ']') ;
        Stack<Character> st = new Stack<Character>() ;
        for (int i = 0 ; i < parens.length() ; i++) {
            Character ch = parens.charAt(i) ;
            if (parenmatch.containsKey(parens.charAt(i))) {
               st.push(parens.charAt(i)) ;
            } else if (parenmatch.values().contains(parens.charAt(i))) {
                if (!st.isEmpty() && parenmatch.get(st.peek()) == parens.charAt(i)) {
                    Character ch1 = st.pop() ;
                } else
                    return false ;
            }
        }
        return (st.isEmpty()) ;

    }

    public static void main (String[] args) {

        System.out.println (validParens("()()[]((()))[{}]")) ;
        System.out.println (validParens("()()[]((()))[{}")) ;
    }
}
