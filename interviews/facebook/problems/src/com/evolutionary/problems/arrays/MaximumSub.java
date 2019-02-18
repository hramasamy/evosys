package com.evolutionary.problems.arrays;

public class MaximumSub {

    public static int maximumSub (int [] arr) {
        if (arr == null || arr.length == 0) {
            return -1 ;
        }
        int max = arr[0] ;
        int [] sum = new int [arr.length] ;
        sum [0] = max ;
        for (int i = 1 ; i < arr.length ; i++) {
            sum[i] = Math.max(arr[i] , sum[i-1] + arr[i]) ;
            max = Math.max (max, sum[i]) ;
        }
        return max ;
    }

    public static int maxSum (int [] arr) {
        if (arr == null || arr.length == 0) {
            return -1 ;
        }
        int newsum = arr[0] ;
        int max = arr[0] ;
        for (int i = 1 ; i < arr.length ; i++ ) {
            newsum = Math.max (arr[i], newsum + arr[i]) ;
            max = Math.max(max, newsum) ;
        }
        return max ;
    }

    public static void main (String[] args) {
        int [] arr = {-2, 1, -3, 4, -1, 2, 1, -5, 4} ;
        System.out.println(maximumSub(arr));
        System.out.println(maxSum(arr));
    }
}
