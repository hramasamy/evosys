package com.evolutionary.problems.trees;

public class Wrapper {

    int size ;
    int lower ;
    int upper ;
    boolean isBST ;

    Wrapper () {
        size = 0 ;
        lower = Integer.MAX_VALUE ;
        upper = Integer.MIN_VALUE ;
        isBST = false ;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getLower() {
        return lower;
    }

    public void setLower(int lower) {
        this.lower = lower;
    }

    public int getUpper() {
        return upper;
    }

    public void setUpper(int upper) {
        this.upper = upper;
    }

    public boolean isBST() {
        return isBST;
    }

    public void setBST(boolean BST) {
        isBST = BST;
    }
}
