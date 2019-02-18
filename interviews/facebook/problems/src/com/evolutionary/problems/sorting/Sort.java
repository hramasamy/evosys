package com.evolutionary.problems.sorting;

import java.util.Arrays;
import java.util.PriorityQueue;

public class Sort {

    public static  void insertionSort(int [] arr) {
        for (int i = 0 ; i < arr.length ; i++) {
            int value = arr[i] ;
            int j = i ;
            while (j > 0 && arr[j-1] > value) {
                arr[j] = arr[j-1] ;
                j-- ;
            }
            arr[j] = value ;
        }
    }

    public static void heapifySort (int [] arr, int n, int i) {

        int largest = i ;
        int l = 2 *i ;
        int r = 2 * i +1 ;

        if (l < n && arr [l] > arr[largest])
            largest = l ;

        if (r < n && arr[r] > arr[largest])
            largest = r ;

        if (largest != i) {
            int temp = arr[i] ;
            arr[i] = arr[largest] ;
            arr[largest] = temp ;
            heapifySort(arr, n, largest) ;
        }
    }

    public static void heapify (int [] arr) {

        int n = arr.length ;
        for (int i = n/2 -1 ; i >= 0 ; i--) {
            heapifySort(arr, n, i);
        }

        for (int i = n -1 ; i >= 0 ; i--) {
            int temp = arr[0] ;
            arr[0] = arr[i] ;
            arr[i] = temp ;
            heapifySort(arr, i, 0);
        }

    }

    public static void bubbleSort (int [] arr) {

        int len = arr.length ;

        for (int i = 0 ; i < len-1 ; i++) {
            int max = i ;
            for (int j = 0 ; j < len -1 - i ; j++) {
                if (arr[j] > arr[j+1]) {
                    int temp = arr[j] ;
                    arr[j] = arr[j+1] ;
                    arr[j+1] = temp ;
                }
            }
        }

    }

    public static void merge (int [] arr, int [] aux, int low, int mid, int high) {

        int k = low, i = low, j = mid+1 ;
        while (i <= mid && j <= high) {
            if (arr[i] <= arr[j]) {
                aux[k] = arr[i];
                ++k;
                ++i;
            }
            else {
                aux[k] = arr[j] ;
                ++k ; ++j ;
            }
        }

        while (i <= mid) {
            aux[k] = arr[i] ;
            ++k ; ++i ;
        }

        for (i = low ; i <= high ; i++) {
            arr[i] = aux[i] ;
        }
    }

    public static void mergeSort (int [] arr, int [] aux, int low, int high) {

        if (low == high) return ;

        int mid = low + ((high -low) >> 1 );

        mergeSort(arr,aux, low, mid) ;
        mergeSort (arr, aux, mid+1, high) ;

        merge(arr, aux, low, mid, high) ;
    }

    public static int findKthLargest (int [] arr, int k) {

        PriorityQueue<Integer> q = new PriorityQueue<Integer>(k) ;

        for (int i = 0 ; i < arr.length ; i++) {

            q.offer(arr[i]) ;

            if (q.size() > k) {
                q.poll() ;
            }
        }

        return q.peek() ;
    }

    public static void mergeItrSort (int [] arr) {

        int low = 0 ;
        int high = arr.length - 1 ;
        int [] temp = Arrays.copyOf(arr, arr.length) ;
        for (int i = 1 ; i <= (high - low) ; i = 2*i) {
            for (int j = low ; j < high ; j += 2*i) {
                int from = j ;
                int mid = j +  i- 1 ;
                int to = Math.min (j + 2 * i -1, high) ;
                merge (arr, temp, from, mid, to) ;
            }
        }

    }

    public static int pivot (int [] arr, int low, int high) {
        int pivot = arr[high] ;
        int i = low - 1 ;
       // System.out.println (high + " " + pivot) ;
        for (int j = low ; j < high ; j++) {
            if (arr[j] <= pivot) {
                i++;
                int temp = arr[j];
                arr[j] = arr[i];
                arr[i] = temp;
            }
        }
        System.out.println (high + " " + i + " " + pivot) ;
        int temp = arr[i+1] ;
        arr[i+1] = arr[high] ;
        arr[high] = temp ;
        return (i+1) ;
    }

    public static void sort (int[] arr, int low, int high) {

        if (low < high) {
            int pi = pivot(arr, low, high) ;

            sort (arr, low, pi -1) ;
            sort (arr, pi+1, high) ;
        }
    }

    public static void printArray (int [] arr) {
        for (int i = 0 ; i < arr.length ; i++) {
            System.out.print(arr[i] + " " ) ;
        }
        System.out.println() ;
    }

    public static void main (String[] args) {
        int [] arr = {3, 8, 5, 4, 1, 9, -2} ;
        printArray(arr) ;
        //insertionSort(arr) ;
        //bubbleSort(arr);
        int [] aux = Arrays.copyOf(arr, arr.length) ;
       // mergeSort(arr, aux, 0, arr.length-1) ;
       //mergeItrSort(arr);
        //heapify(arr) ;
        sort(arr, 0, arr.length-1) ;
        printArray(arr) ;
        System.out.println (findKthLargest(arr, 4)) ;
    }
}
