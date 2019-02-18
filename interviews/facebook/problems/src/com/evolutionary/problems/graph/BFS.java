package com.evolutionary.problems.graph;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class BFS {
    int V ;
    List<List<Integer>> adjList ;

    BFS (int v) {
        this.V = v ;
        adjList = new ArrayList (V) ;
        for (int i = 0 ; i < this.V ; i++) {
            adjList.add(new LinkedList<Integer>() );
        }
    }

    public void addEdge (int source, int dest) {
        adjList.get(source).add(dest) ;
    }



}
