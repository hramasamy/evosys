package com.evolutionary.problems.arrays;

import java.util.ArrayList;
import java.util.List;

public class Profit {

    public static void buySell(int [] arr) {

        List<BuySell> buysell = new ArrayList<BuySell>();
        int i = 0;
        int count = 0;
        while (i < arr.length-1) {
            System.out.println( "beginning " + i);
            while ((i < arr.length - 1) && (arr[i + 1] <= arr[i])) {
                System.out.println(i + "," + arr[i]);
                i++;
            }
            System.out.println( "beginning " + i);
            if (i == arr.length - 1) break;

            BuySell b = new BuySell();
            b.setBuy(i);
            i++;
            while (i < arr.length&& arr[i] >= arr[i - 1]) {
                i++;
            }

            b.setSell(i - 1);
            buysell.add(b);
            count++;
            System.out.println(b.getBuy() + "," + " " + i + " " + b.getSell());

        }


        if (count == 0)
            System.out.println("nothing found ");
        else {
            for (BuySell b : buysell) {
                System.out.println (b.getBuy() + " : "  + b.getSell()) ;
            }
        }
    }

    public static void main (String [] args) {
        // stock prices on consecutive days
        int price[] = {100, 180, 260, 310, 40, 535, 695};
        int n = price.length;

        // fucntion call
        // STOPSHIP: 5/28/18 c
        buySell(price) ;
    }
}
