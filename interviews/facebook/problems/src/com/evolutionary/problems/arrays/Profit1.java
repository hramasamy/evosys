package com.evolutionary.problems.arrays;

// Program to find best buying and selling days
import java.util.ArrayList;

// Solution structure
class Interval
{
    int buy, sell;
}

class Profit1
{
    // This function finds the buy sell schedule for maximum profit
    void stockBuySell(int price[], int n)
    {
        // Prices must be given for at least two days
        if (n == 1)
            return;

        int count = 0;

        // solution array
        ArrayList<Interval> sol = new ArrayList<Interval>();

        // Traverse through given price array
        int i = 0;
        while (i < n - 1)
        {
            // Find Local Minima. Note that the limit is (n-2) as we are
            // comparing present element to the next element.
            System.out.println ("beginning " + i) ;
            while ((i < n - 1) && (price[i + 1] <= price[i]))
                i++;
            System.out.println ("end  " + i) ;
            // If we reached the end, break as no further solution possible
            if (i == n - 1)
                break;

            Interval e = new Interval();
            e.buy = i++;
            // Store the index of minima


            // Find Local Maxima.  Note that the limit is (n-1) as we are
            // comparing to previous element
            while ((i < n) && (price[i] >= price[i - 1]))
                i++;

            // Store the index of maxima
            e.sell = i-1;
            sol.add(e);
            System.out.println (i) ;
            // Increment number of buy/sell
            count++;
        }

        // print solution
        if (count == 0)
            System.out.println("There is no day when buying the stock "
                    + "will make profit");
        else
            for (int j = 0; j < count; j++)
                System.out.println("Buy on day: " + sol.get(j).buy
                        +"        " + "Sell on day : " + sol.get(j).sell);

        return;
    }

    public static void main(String args[])
    {
        Profit1 stock = new Profit1();

        // stock prices on consecutive days
        int price[] = {100, 180, 260, 310, 40, 535, 695};
        int n = price.length;

        // fucntion call
        stock.stockBuySell(price, n);
    }
}

// This code has been contributed by Mayank Jaiswal