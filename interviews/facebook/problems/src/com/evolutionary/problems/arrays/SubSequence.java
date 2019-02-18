package com.evolutionary.problems.arrays;

public class SubSequence {
    public static boolean subSequence (int [] arr) {
        if (arr == null || arr.length < 3) {
            return false ;
        }
        int x, y, z ;
        x = Integer.MAX_VALUE ;
        y = Integer.MAX_VALUE ;

        for (int i = 0 ; i < arr.length ; i++) {

            int num = arr[i] ;

            if (x >= num) {
                x = num ;
            }
            else if (y >= num) {
                y = num ;
            }
            else {
                return true ;
            }
        }

        return false ;
    }

    public static void main (String [] args) {
        int[] arr = {1,2,3,4,5} ;
        int [] rev = {5,4,3,2,1} ;
        System.out.println (subSequence(arr)) ;
        System.out.println(subSequence(rev));
    }
}
