package com.evolutionary.problems.heaps;

import java.util.LinkedList;

public class SlidingW {
    public static int[] maxSlidingWindow(int[] nums, int k) {
        if(nums==null||nums.length==0)
            return new int[0];
        int[] result = new int[nums.length-k+1];
        LinkedList<Integer> deque = new LinkedList<Integer>();
        for(int i=0; i<nums.length; i++){
            if(!deque.isEmpty()&&deque.peekFirst()==i-k) {
                System.out.println(i + "  peek first " + deque.peekFirst());
                deque.poll();
            }
            System.out.println(deque.size() + " " + deque.peekFirst());
            while(!deque.isEmpty()&&nums[deque.peekLast()]<nums[i]){
                deque.removeLast();
            }
            deque.offer(i);
            if(i+1>=k)
                result[i+1-k]=nums[deque.peek()];
        }
        return result;
    }

    public static void main (String[] args) {

        int [] arr = {4, 2, 5, 3, 7, 9 , 11, 15, 22, 8, 6, 30, 25} ;

        int [] res = maxSlidingWindow(arr, 3) ;
        for (int re : res) {
            System.out.print (re + " ");
        }
        System.out.println();

    }
}
