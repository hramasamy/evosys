package com.evolutionary.problems.arrays;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Reachable {

    public static boolean isReachable (ArrayList<Integer> list, int target) {
        if (list == null) {
            return false ;
        }
        ArrayList<Integer> result = getResults (list, 0, list.size()-1, target) ;
        for (Integer num : result) {
            if (num == target) {
                return true;
            }
        }
        return false;

    }
    
    public static ArrayList<Integer> getResults (ArrayList<Integer> lis, int left,
                                                 int right, int target) {
        if (left > right) {
            return null ;
        }
        ArrayList<Integer> result = new ArrayList<Integer>() ;
        if (left == right) {
            result.add(lis.get(left)) ;
            return result ;
        }
        System.out.println (result.toArray().toString()) ;
        for (int i = left ; i < right ; i++) {

            ArrayList<Integer> result1 = getResults(lis, left, i, target) ;
            ArrayList<Integer> result2 = getResults(lis, i+1, right, target) ;

            for (Integer a : result1) {
                for (Integer b : result2) {
                    result.add(a + b);
                    result.add(a - b);
                    result.add(a * b);
                    if (b != 0) {
                        result.add(a / b);
                    }
                }
            }
        }
        return result ;
    }

    public static void main (String [] args) {
        int [] arr = {1,2,3,4} ;
        ArrayList<Integer> ques = new ArrayList<Integer>() ;
        ques.add(1) ;
        ques.add(2) ;
        ques.add(3) ;
        ques.add(4) ;
        System.out.println (isReachable(ques, 21)) ;
    }
  }
