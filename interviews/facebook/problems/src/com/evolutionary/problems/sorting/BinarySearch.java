package com.evolutionary.problems.sorting;

public class BinarySearch {

    public static int binarySearch (int [] arr, int l, int r, int x) {

        if (l > r ) return -1 ;
        int mid =  l + (r - l) /2 ;

        if (arr[mid] == x ) return mid ;
        if (arr[mid] < x) {
            l  = mid + 1 ;
        }else {
            r = mid-1 ;
        }
        return binarySearch(arr, l, r, x) ;

    }

    public static int pivotSearch (int [] arr, int n, int target) {
        int pivot = pivot(arr, 0, arr.length-1) ;
        //System.out.println(pivot);
        if (pivot == -1) {
            return binarySearch(arr, 0, arr.length-1, target) ;
        }
        if (arr[pivot] == target)
            return pivot ;
        if (arr[0] <= target)
            return binarySearch(arr, 0, pivot-1, target) ;
        return binarySearch(arr, pivot+1, arr.length-1, target) ;
    }

    public static int pivot (int [] arr, int low, int high) {
        System.out.println( low + " lh " + high);
        if (low > high) {
            return -1 ;
        }
        if (high == low )
            return low ;

        int mid = low + (high - low) / 2 ;
        if (mid < high && arr [mid] > arr[mid+1])
            return mid ;
        if (mid > low && arr[mid] < arr[mid-1])
            return (mid -1) ;
        if (arr[low] >= arr[mid])
            return pivot(arr, low, mid-1) ;
        return pivot(arr, mid+1, high) ;

    }

    public static void main (String [] args) {
        int[] arr = {2, 5, 8, 12, 16, 23, 38, 56, 72, 91};
        System.out.println(binarySearch(arr, 0, arr.length - 1, 23));
        System.out.println(binarySearch(arr, 0, arr.length - 1, 100));
        System.out.println(binarySearch(arr, 0, arr.length - 1, 2));
        System.out.println(binarySearch(arr, 0, arr.length - 1, 91));
        int [] arr1 = { 5,6,7,8, 9, 19, 21, 22, 1,2,3,4} ;
        System.out.println(pivotSearch(arr1, arr1.length-1, 3)) ;

    }
}
