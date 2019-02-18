package com.evolutionary.problems.arrays;

import java.util.Set ;
import java.util.HashSet ;

class SubArrayExists {

    public static void printAllSubArray (int [] arr, int desiredsum) {
        Set<Integer> sumset = new HashSet<Integer>() ;

        int sum = desiredsum ;
        sumset.add(sum) ;

        for (int i = 0 ; i < arr.length ; i++) {
            sum += arr[i] ;
            if (sumset.contains(sum)) {
                System.out.println ("sum exists") ;
            }
            sumset.add(sum) ;
        }
    }

    public  static void main (String [] args) {
        int [] A = { 3, 4, -7, 3, 1, 3, 1, -4, -2, -2} ;
        printAllSubArray (A,0) ;
    }



}
