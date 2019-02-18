package com.evolutionary.problems.search;

public class RotatedArray {

    public int search (int [] arr, int target) {

        int left = 0 ;
        int right = arr.length ;

        while (left < right) {
            int mid = left + (right - left) / 2 ;
            if (target == arr[mid]) {
                return mid ;
            }

            if (arr[left] < arr[mid]) {
                if ((arr[left] <= target) && (target < arr[mid])) {
                    right = mid-1 ;
                }
                else {
                    left = mid+1 ;
                }
            }
            else {

                if ((arr[mid] < target ) && (target <= arr[right])) {
                    left = mid +1 ;
                }
                else {
                    right = mid -1 ;
                }
            }

        }
        return -1 ;
    }
}
