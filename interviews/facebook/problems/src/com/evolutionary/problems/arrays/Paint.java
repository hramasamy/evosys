package com.evolutionary.problems.arrays;

public class Paint {

    public static int paint (int n, int k) {

        int [] colors = {0, k, k * k, 0} ;
        if (n <= 2 )
            return colors[2] ;
        for (int i = 2 ; i < n ; i++) {
            colors[3] = (k-1) * (colors[2] + colors[1] ) ;
            colors[1] = colors[2] ;
            colors[2] = colors[3] ;
        }
        return colors[3] ;
    }

    public static void main (String[] args) {
        System.out.println (paint(10, 6)) ;
    }
}
