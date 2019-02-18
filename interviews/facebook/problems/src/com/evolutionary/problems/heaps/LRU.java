package com.evolutionary.problems.heaps;

import java.util.HashMap;
import java.util.Map;

public class LRU {

    int capacity ;
    Map <Integer, Node> lru ;
    Node head ;
    Node tail ;

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public Map<Integer, Node> getLru() {
        return lru;
    }

    public void setLru(Map<Integer, Node> lru) {
        this.lru = lru;
    }

    public Node getHead() {
        return head;
    }

    public void setHead(Node head) {
        this.head = head;
    }

    public Node getTail() {
        return tail;
    }

    public void setTail(Node tail) {
        this.tail = tail;
    }

    LRU (int capacity) {

        this.capacity = capacity ;
        this.lru = new HashMap<Integer, Node>() ;
        head = null ;
        tail = null ;

    }

    public void insert () {

    }
    
}
