package com.evolutionary.problems.arrays;

public class SmallSubSum {

    public static int smallSubset( int [] arr, int target ) {

        int current_sum = 0 ;
        int start = 0 ;
        int end = 0 ;
        int max_length = arr.length ;
        int sum = 0 ;

        for (int i = 0 ; i < arr.length ; i++) {
            current_sum = current_sum + arr[i];
            end = i;
            if (current_sum < 0) {
                current_sum = 0;
                start = i++;
            }
            if (current_sum >= target) {
                int len = end - start +1 ;
                max_length = Math.min(len, max_length);                                                                                                                                                                                                                                                                                                                                         
            }

        }

        System.out.println (current_sum + " " + target + " " + start + "," + end) ;
        return max_length ;
    }


    public static void main (String[] args) {
        int [] arr =  {1, 4, 45, 6, 0, 19} ;
        System.out.println (smallSubset(arr, 50)) ;
    }


}
