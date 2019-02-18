package com.evolutionary.problems.strings;

import java.util.Stack;

public class ReversePolish {

    public static int reversePolish(String[] tokens) {

        String operators = "+-/*";

        Stack<String> expr = new Stack<String>();
        for (String t : tokens) {

            if (!operators.contains(t)) {
                expr.push(t);
                continue;
            }
            Integer a = Integer.parseInt(expr.pop());
            Integer b = Integer.parseInt(expr.pop());
            int type;
            type = 3;
            if (t.equals("+")) type = 0;
            if (t.equals("-")) type = 1;
            if (t.equals("*")) type = 2;
            switch (type) {
                case 0:
                    expr.push(String.valueOf(a + b));
                    break;
                case 1:
                    expr.push(String.valueOf(b - a));
                    break;
                case 2:
                    expr.push(String.valueOf(a * b));
                    break;
                case 3:
                    expr.push(String.valueOf(b / a));
                    break;
            }


        }

        Integer val = Integer.parseInt(expr.pop());
        return val;

    }

    public static void main(String[] args) {
        String[] tokens = new String[]{"2", "1", "+", "3", "*", "3" , "/"};
        System.out.println(reversePolish(tokens));
    }
}
