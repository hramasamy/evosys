package com.evolutionary.problems.sorting;

public class ClosestPair {

    public static void closestPair (int [] arr, int n, int sum ) {
        int l = 0 ; int r = n ;
        int diff = Integer.MAX_VALUE ;
        int res_l = -1, res_r = -1 ;
        while ( l < r ) {
            if (Math.abs(arr[l] + arr[r] - sum) < diff) {
                res_l = l ;
                res_r = r ;
                diff = Math.abs(arr[l] + arr[r] - sum );
            }

            if ((arr[l] + arr[r]) > sum)
                r-- ;
            else
                l++ ;
        }

        System.out.println (res_l + "," + res_r + "=" +
        arr[res_l] + "," + arr[res_r]) ;
    }


    public static void main (String[] args) {

       int []  arr = {10, 22, 28, 29, 30, 40} ;
        closestPair(arr, arr.length-1, 60) ;

    }

}
