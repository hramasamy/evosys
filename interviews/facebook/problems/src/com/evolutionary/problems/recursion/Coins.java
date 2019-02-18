package com.evolutionary.problems.recursion;

public class Coins {

    public static int noOfWays (int [] coins, int denom) {
        int [] arr = new int[denom+1] ;
        arr[0] = 1 ;

        for (int val : coins) {
            for (int j = val ; j <= denom ; j++) {
                arr[j] = arr[j] + arr[j - val];
            }
        }

        return arr[denom] ;
    }

    public static void main (String [] args) {
        int [] arr = {1,5, 10, 25, 50, 100} ;
        System.out.println(noOfWays(arr, 100));
    }

}
