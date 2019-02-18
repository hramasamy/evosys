package com.evolutionary.problems.puduku;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class MergeKLists {

     class ArrayContainer implements Comparable<ArrayContainer> {
         int val ;
         int ind ;
         int k ;

         public ArrayContainer(int val, int k, int ind) {
             this.val = val ;
             this.k= k ;
             this.ind = ind ;
         }

         @Override
         public int compareTo (ArrayContainer o) {
             return this.val - o.val  ;
         }
     }

     public  void mergeK (int[][] arr) {

         PriorityQueue<ArrayContainer> queue = new PriorityQueue<ArrayContainer>() ;

         int total = arr.length ;

         for (int i = 0 ; i < arr.length ; i++) {
             queue.add(new ArrayContainer(arr[i][0], i, 0) ;
         }

         
     }
}
