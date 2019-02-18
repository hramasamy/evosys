package com.evolutionary.problems.arrays;

public class MinSubArray {

    public static int minSubArray (int [] arr, int target) {

        int sum = 0;
        int start = 0;
        int result = arr.length;
        boolean exists = false;

        int i = 0;
        while (i <= arr.length) {

            if (sum >= target) {
                exists = true;
                if (start == i - 1) {
                    return 1;
                }

                result = Math.min(result, i - start);
                System.out.println (start + " " + i + " " + sum + " " + result) ;
                sum = sum - arr[start];
                start++;
            } else {
                if (i >= arr.length)
                    break;
                sum = sum + arr[i];
                i++;
            }
        }
        return result;
    }

    public static void main (String [] args) {
        int [] arr = {2,3,1,2,4,3} ;
        System.out.println(minSubArray(arr, 7));
    }
}
