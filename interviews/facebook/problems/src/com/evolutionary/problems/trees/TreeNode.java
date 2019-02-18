package com.evolutionary.problems.trees;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

public class TreeNode {

    private int data ;
    public TreeNode left ;
    public TreeNode right ;
    public TreeNode parent;
    private int size = 0 ;

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public TreeNode getLeft() {
        return left;
    }

    public void setLeft(TreeNode left) {
        this.left = left;
        if (left!= null) {
            left.setParent(this) ;
        }
    }

    public TreeNode getRight() {
        return right;
    }

    public void setRight(TreeNode right) {
        this.right = right;
        if (right != null) {
            right.setParent(this) ;
        }
    }

    public TreeNode getParent() {
        return parent;
    }

    public void setParent(TreeNode parent) {
        this.parent = parent;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public TreeNode (int data){

        this.data = data ;
        size = 1 ;
        setLeft(null) ;
        setRight(null) ;
    }

    public void insertInOrder (int data) {

        if (data <= this.getData()){

            if (this.getLeft() == null) {
                this.setLeft(new TreeNode(data)) ;
            }
            else {
                this.getLeft().insertInOrder(data) ;
            }
        }
        else {
            if (this.getRight() == null) {
                this.setRight(new TreeNode(data)) ;
            }
            else {
                this.getRight().insertInOrder(data) ;
            }
        }
    }

    public boolean isBST (TreeNode root, int leftVal, int rightVal) {
        if (root == null)
            return true ;
        if (getData() < leftVal || getData() > rightVal)
            return false ;
        return ((isBST(root.getLeft(), leftVal, getData()-1)) &&
                (isBST(root.getRight(), getData()-1, rightVal)));

    }

    public int height () {
        int leftHeight = (getLeft() != null) ? getLeft().height() : 0 ;
        int rightHeight = (getRight() != null) ? getRight().height() : 0 ;
        return (1+ Math.max(leftHeight, rightHeight)) ;
    }

    public TreeNode find (int d) {

        if (getData() == d) {
            return this ;
        }
        if (getData() < d) {
            TreeNode left = getLeft() ;
            return ((left != null) ?  left.find(d) : null) ;
        }
        else {
            return ((getRight() != null)  ? getRight().find(d) : null );
        }

    }

    public static TreeNode createMinimalBST(int[] array, int start, int end) {

        if (end < start) {
            return null ;
        }

        int mid = (end + start) / 2 ;
        TreeNode n = new TreeNode (array[mid]) ;
        n.setLeft(createMinimalBST(array, start, mid-1)) ;
        n.setRight(createMinimalBST(array, mid+1, end)) ;
        return n ;
    }

    public static TreeNode createMinimalBST(int[] array) {
        Arrays.sort(array) ;
        return (createMinimalBST(array, 0, array.length -1)) ;

    }

    public void printTree () {
        BTreePrinter.printNode(this);
    }

    public void printLevelTree () {

        if (this == null) {
            return ;
        }
        Queue<TreeNode> queue = new ArrayDeque<TreeNode>(10) ;

        queue.add(this) ;

        while (!queue.isEmpty()) {

            TreeNode cur = queue.poll() ;
            System.out.print(cur.getData() + " ") ;
            if (cur.getLeft() != null) {
                queue.add(cur.getLeft()) ;
            }

            if (cur.getRight() != null) {
                queue.add(cur.getRight()) ;
            }

        }
    }
}
