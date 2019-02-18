package com.evolutionary.problems.arrays;

import java.util.Map ;
import java.util.HashMap ;
import java.util.List ;
import java.util.ArrayList ;

class SubArray {

    private static <K, V> void insertMap (Map <K, List<V>> sumMap, K key, V value) {
        if (!sumMap.containsKey(key)){
            List<V> list = new ArrayList<V>() ;
            sumMap.put(key, list) ;
        }
        sumMap.get(key).add(value) ;
    }

    public static void printAllSubArray (int [] arr, int desiredsum) {
        Map <Integer, List <Integer>> sumMap =
                new HashMap <Integer, List<Integer>>() ;
        int sum = desiredsum ;
        insertMap (sumMap, sum, -1) ;
        for (int i = 0 ; i < arr.length ; i++) {
            sum += arr[i] ;
            if (sumMap.containsKey(sum)) {
                List<Integer> list = sumMap.get(sum) ;

                for (Integer val : list) {
                    System.out.println ("[" + (val+1) +"," + i +"]") ;
                }
            }
            insertMap(sumMap, sum, i) ;
        }
    }

    public  static void main (String [] args) {
        int [] A = { 3, 4, -7, 3, 1, 3, 1, -4, -2, -2} ;
        printAllSubArray (A,0) ;
    }


}
