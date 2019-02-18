package com.evolutionary.problems.arrays;

import java.util.Map ;
import java.util.HashMap ;

class Pair {
    public static void findPair (int [] arr, int sum) {
        if (arr == null)
            return  ;

        int len = arr.length ;
        if (len <= 1)
            return ;
        Map <Integer, Integer> pair = new HashMap<Integer,
                Integer>() ;
        for (int i = 0 ; i < len ; i++) {

            if (pair.containsKey(sum - arr[i])) {
                System.out.println ("Pair found (" +
                        pair.get(sum-arr[i]) + "," +
                        i + ")") ;
                return ;
            }
            else {
                pair.put (arr[i], i) ;
            }

        }
        System.out.println ("No pairs found") ;
        return ;
    }


    public static void main (String [] args) {
        int arr [] = {1, 2,45, 63, -27, 4, 5} ;
        findPair(arr, 36) ;

    }



}
