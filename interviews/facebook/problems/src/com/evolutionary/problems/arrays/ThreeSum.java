package com.evolutionary.problems.arrays;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class ThreeSum {
    public static List<List<Integer>> threeSum (int [] arr, int target) {
        if (arr == null || arr.length < 3)  {
            System.out.println ("array length insufficient") ;
            return null ;
        }
        List<List<Integer>> lis = new ArrayList<List<Integer>> () ;
        Arrays.sort(arr) ;

        for (int i = 0 ; i < arr.length - 2 ; i++) {
            if (i == 0 || arr[i] > arr[i - 1]) {
                int j = i + 1;
                int k = arr.length - 1;

                while (j < k) {
                    if (arr[i] + arr[j] + arr[k] == target) {
                        List<Integer> match = new ArrayList<Integer>();
                        match.add(arr[i]);
                        match.add(arr[j]);
                        match.add(arr[k]);
                        j++;
                        k--;
                        while (arr[j] == arr[j - 1]) j++;
                        while (arr[k] == arr[k + 1]) k--;
                        lis.add(match);
                    } else {
                        if (arr[i] + arr[j] + arr[k] > target) {
                            k--;
                        } else {
                            j++;
                        }
                    }
                }
            }
        }
        System.out.println (lis.size()) ;
        return lis;
    }

    public static void main (String [] args) {
        int [] arr = { -1, 0, 1, 2, -1,  -4} ;
        for (List lis : threeSum(arr, 0) ) {
            System.out.print ("(") ;
            for (Object i : lis) {
                Integer val = (Integer) i ;
                System.out.print (val + " ") ;

            }
            System.out.println (")") ;
        }
        System.out.println() ;
    }
}
