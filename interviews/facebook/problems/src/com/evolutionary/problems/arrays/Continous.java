package com.evolutionary.problems.arrays;

import java.util.HashSet;
import java.util.Set;

public class Continous {

    public static Set<Integer> continuous (int [] arr) {

        if (arr == null || arr.length == 0) {
            return null ;
        }

        Set <Integer> cont = new HashSet<Integer>() ;

        for (int i = 0 ; i < arr.length ; i++) {
            if (cont.contains(arr[i]) == false)  {
                cont.add(arr[i]) ;
            }
        }

        Set<Integer> res = new HashSet<Integer>() ;

        for (Integer ele : arr) {
            int left = ele-1 ;
            int right = ele+1 ;
            Set <Integer> newset = new HashSet<Integer>() ;
            newset.add(ele) ;
            while (cont.contains(left)) {
                newset.add(left) ;
                cont.remove(left) ;
                left-- ;
            }
            while (cont.contains(right)) {
                newset.add(right) ;
                cont.remove(right) ;
                right++ ;
            }
            if (res.size() < newset.size()) {
                res = newset ;
            }

        }

        return res ;

    }

    public static void main (String [] args) {
        int [] arr = {100, 4, 200, 1, 3, 2, 101, 102, 103, 104, 105, 99} ;
        System.out.print ("[") ;
        for (Integer ele : continuous(arr)) {
            System.out.print (ele + " " ) ;
        }
        System.out.println ("]") ;
    }
}
