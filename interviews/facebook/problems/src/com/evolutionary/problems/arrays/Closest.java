package com.evolutionary.problems.arrays;

import java.util.Arrays;
import java.util.List;

public class Closest {

    public static int  closest (int [] arr, int target) {

        if (arr == null || arr.length < 3) return -1000 ;
        int min = Integer.MAX_VALUE;
        int res = 0 ;

        Arrays.sort(arr);
        for (int i = 0; i < arr.length - 2; i++) {

            int j = i + 1;
            int k = arr.length - 1;

            while (j < k) {
                int sum = arr[i] + arr[j] + arr[k];
                int diff = sum - target;
                if (diff == 0) {
                    return sum;
                }
                if (Math.abs(diff) < min) {
                    min = Math.abs(diff);
                    res = sum;
                }
                if (diff > 0) {
                    k--;
                } else {
                    j++;
                }
            }

        }
        return res ;

    }

    public static void main (String [] args) {
        int [] arr = {-1, 2, 1, -4} ;
        System.out.println ( closest(arr, 1)) ;
    }
}
