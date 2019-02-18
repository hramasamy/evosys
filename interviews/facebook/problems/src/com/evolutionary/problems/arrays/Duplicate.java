package com.evolutionary.problems.arrays;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Duplicate {

    public static int duplicate (int [] arr) {

        int expectedsum = (arr.length -1) * (arr.length) / 2 ;
        int actualsum = 0 ;

        for (int i = 0 ; i < arr.length; i++) {
            actualsum += arr[i] ;
        }

        return (actualsum -expectedsum) ;
    }

    public static boolean duplicateExists (int [] arr) {
        Set<Integer> eles = new HashSet<Integer>() ;

        for (int i = 0 ; i < arr.length ; i++) {
            if (eles.contains(arr[i])) {
                return true ;
            }
            else {
                eles.add(arr[i]) ;
            }
        }
        return false ;
    }

    public static boolean nearbyDuplicate (int [] arr, int k) {
        Map<Integer, Integer> eles = new HashMap<Integer, Integer>() ;
        for (int i = 0 ; i < arr.length ; i++) {
            if (eles.containsKey(arr[i])) {
                if ((i - eles.get(arr[i])) < k)  {
                    return true ;
                }
            }
            eles.put(arr[i], i) ;
        }
        return false ;
    }

    public static int duplicate2 (int [] arr) {
        int [] newarr = new int [arr.length -1] ;

        for (int i = 0 ; i < arr.length ; i++) {
            if (newarr[arr[i]-1] <= 0) {
                newarr[arr[i]-1] = 1 ;
            }
            else {
                return (arr[i]) ;
            }
        }
        return -1 ;
    }

    public static void main (String [] args) {
        int [] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 9, 10} ;
        System.out.println (duplicate(arr)) ;
        System.out.println (duplicate2(arr)) ;
        System.out.println (duplicateExists(arr)) ;
        int [] arr1 = {1, 2, 3, 8, 5, 6, 7, 8, 9, 10} ;
        System.out.println (nearbyDuplicate(arr1, 5)) ;
    }
}
