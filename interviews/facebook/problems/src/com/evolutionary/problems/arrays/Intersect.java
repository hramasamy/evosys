package com.evolutionary.problems.arrays;

import java.util.Map ;
import java.util.HashMap ;
import java.util.List ;
import java.util.ArrayList ;

class Intersect {

    public static int [] intersect (int [] arr1, int [] arr2) {
        Map <Integer, Integer> common = new HashMap <Integer, Integer>() ;
        for (int i : arr1) {
            if (common.containsKey(i)) {
                common.put (i, common.get(i) + 1) ;
            }
            else {
                common.put(i, 1) ;
            }
        }

        List <Integer> comlists = new ArrayList<Integer> () ;
        for (int i : arr2) {
            if (common.containsKey(i)) {
                comlists.add(i) ;
                common.put(i, common.get(i) -1) ;
                if (common.get(i) == 0)
                    common.remove(i) ;
            }
        }

        int [] res = new int[comlists.size()] ;
        int ind = 0 ;
        for (int i : comlists) {
            res[ind] = i ;
            ind++ ;
            System.out.print (i + " ") ;
        }
        System.out.println() ;
        return res ;
    }

    public static void main (String [] args) {
        int [] arr1 = {1, 2, -1, 2, 3, 4, 5, 65} ;
        int [] arr2 = {1, 2, -1, 2, -3, -4, 5, 65} ;
        int [] result = intersect(arr1, arr2) ;

    }


}
