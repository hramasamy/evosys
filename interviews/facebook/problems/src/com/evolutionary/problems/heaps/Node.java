package com.evolutionary.problems.heaps;

public class Node {

    int data ;
    int key ;
    Node next ;
    Node pre ;

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }

    public Node getPre() {
        return pre;
    }

    public void setPre(Node pre) {
        this.pre = pre;
    }
}


