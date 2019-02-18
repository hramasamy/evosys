package com.evolutionary.problems.arrays;

public class Majority {

    public static int majority (int [] arr) {

        if (arr == null || arr.length == 0 ) {
            return -1 ;
        }
        int count = 0 ;
        int pre = arr[0] ;
        count++ ;
        for (int i = 1 ; i < arr.length ; i++) {
            if (arr[i] == pre) {
                count++ ;
                if (count > arr.length/2) {
                    return pre ;
                }
            }
            else {
                pre = arr[i] ;
                count = 1;
            }
        }
        if (count > arr.length/2) {
            return pre ;
        }
        else return -1 ;
    }

    public static void main (String[] args) {

        int  [] arr = {1,3,4,5,5,5,5,5,5,7} ;
        System.out.println (majority(arr)) ;
    }
}
