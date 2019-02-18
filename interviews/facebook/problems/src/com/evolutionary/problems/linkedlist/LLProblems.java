package com.evolutionary.problems.linkedlist;

import org.omg.CORBA.OMGVMCID;

import java.util.HashMap;
import java.util.Map;

public class LLProblems {
    Node head ;
    Node cur ;

    public static int  strCmp (LLProblems a, LLProblems b) {
       Node str1 = a.getHead() ;
       Node str2 = b.getHead() ;

       if (str1 == null && str2 == null)  return 0 ;

       if (str2 == null) return 1 ;
       if (str1 == null) return -1 ;

       while (str1 != null && str2 !=  null) {
           if (str1.getData() == str2.getData()) {
               str1 = str1.getNext() ;
               str2 = str2.getNext() ;
               continue ;
           }
           else {
               if (str1.getData() > str2.getData())
                   return 1;
               else
                   return -1;
           }
       }
       if (str1 == null && str2 != null) return -1 ;
       if (str2 == null && str1 != null) return  1 ;
        return 0 ;
    }

    LLProblems () {
        head = null ;
        cur = null ;
    }

    public Node getHead() {
        return head;
    }

    public void setHead(Node head) {
        this.head = head;
    }

    public Node getCur() {
        return cur;
    }

    public void setCur(Node cur) {
        this.cur = cur;
    }

    public void insertLL (int data){

        if (getHead() == null) {
            Node node = new Node(data) ;
            head = node ;
            cur = head ;
            return ;
        }

        if (getHead().getData() >= data) {
            Node node = new Node(data) ;
            node.setNext(head);
            setHead(node);
            return ;
        }
        Node prev = getHead() ;
        Node pre = getHead().getNext() ;
        while (pre != null) {
            if (pre.getData() >= data) {
                Node node = new Node(data) ;
                prev.setNext(node);
                node.setNext(pre);
                return ;
            }
            pre = pre.getNext() ;
            prev = prev.getNext() ;
        }

        Node node = new Node(data) ;
        prev.setNext(node);
        setCur(node);
        return ;
    }

    public Node middle () {

        Node slow = getHead() ;
        if (slow == null) return null ;
        Node fast = getHead() ;

        while (fast!= null) {

            fast = fast.getNext() ;
            if (fast == null) return slow ;
            slow = slow.getNext() ;
            fast = fast.getNext() ;
        }

        return slow ;

    }

    public void deleteNode (Node node) {

        if (node == null) return ;
        Node del = getHead() ;

        if (del == node) {
            head = head.getNext() ;
            if (head == null ) cur = null ;
            return ;
        }
        Node prev = null ;
        while (del != null && del != node) {
            prev = del ;
            del = del.getNext() ;
        }

        if (del == null) {
            System.out.println ("node with value " + node.getData() + " not found") ;
            return ;
        }
        if (del.getNext() == null) {
            cur = prev ;
            cur.setNext(null);
            return ;
        }
        del.setData(del.getNext().getData());
        del.setNext(del.getNext().getNext());
    }

    public void insert(int data)  {
        Node node = new Node(data) ;
        if (getHead() == null) {
            setHead(node);
            setCur(node);
            return;
        }

        cur.setNext(node);
        cur = node ;
        return;

    }

    public void printLL () {
        Node pre = getHead() ;
        while (pre != null ) {
            System.out.print(pre.getData() + "--->");
            pre = pre.getNext() ;
        }
        System.out.println("null");
    }

    public void printThis (Node pre) {

        while (pre != null ) {
            System.out.print(pre.getData() + "--->");
            pre = pre.getNext() ;
        }
        System.out.println("null");
    }

    public void reverseList (int k) {

        Node at = getHead() ;
        Node prev = null ;
        Node next = null ;
        boolean done = false ;
        while (!done) {
            int i = 0;
            while (at != null &&  i < k) {
                next = at.getNext() ;
                at.setNext(prev);
                prev = at ;
                at = next ;
                k++ ;
            }
             //at = at.getNext() ;
            //prev = null ;
            //next = null ;
        }
    }

    public Node getMiddle (Node mid) {
        Node slow = mid ;
        if (slow == null) return null ;
        Node fast = mid ;

        while (fast!= null) {

            fast = fast.getNext() ;
            if (fast == null) return slow ;
            slow = slow.getNext() ;
            fast = fast.getNext() ;
        }

        return slow ;

    }

    public Node sortedMerge(Node left,Node right) {
        Node result = null ;
        if (left == null) return right ;
        if (right == null) return left ;

        if (left.getData() <= right.getData()) {
            result = left ;
            result.setNext(sortedMerge(left.getNext(), right));
        }
        else {
            result = right ;
            result.setNext(sortedMerge(left, right.getNext())) ;
        }
      return  result ;
    }

    public  Node mergeSort (Node sort) {
        if (sort == null || sort.getNext() == null)
            return sort ;
        Node middle = getMiddle(sort) ;
        Node middlenext  = middle.getNext() ;
        middle.setNext(null);
        if (sort.getNext() == middle) {
            if (middlenext == null) {
                middlenext = middle;
                sort.setNext(null);
            }
        }

        Node left = mergeSort(sort) ;
        Node right = mergeSort(middlenext)  ;

        Node res = sortedMerge(left, right) ;
        System.out.println("merge sort");
        printThis(res);
        System.out.println("ends");
        return res ;
    }

    public static Node union(LLProblems l1, LLProblems l2){

        Map<Integer, Integer> list1 = new HashMap<Integer, Integer>() ;
        Map<Integer, Integer> list2 = new HashMap<Integer, Integer>() ;
        Node node1 = l1.getHead() ;
        while (node1 != null) {
            list1.put(node1.getData(), 1) ;
            node1 = node1.getNext() ;
        }
        Node node2 = l2.getHead() ;
        LLProblems res = new LLProblems() ;
        while (node2 != null) {
            if (list1.containsKey(node2.getData())) {
                res.insert(node2.getData()) ;
            }
            node2 = node2.getNext() ;
        }
        return  res.getHead() ;

    }

    public static void main (String [] args) {

        LLProblems ll = new LLProblems() ;

        ll.insertLL(2);
        ll.insertLL(1);
        ll.insertLL(0);
        ll.printLL();

        ll.insertLL(8);
        ll.insertLL(9);
        ll.insertLL(10);
        ll.insertLL(3);
        ll.insertLL(4);
        ll.insertLL(5);
        ll.insertLL(6);
        ll.insertLL(7);
        ll.insertLL(12);
        ll.printLL();

        Node mid = ll.middle() ;
        System.out.println(mid.getData());
        ll.deleteNode(mid);
        ll.printLL();
        ll.deleteNode(ll.getHead());
        ll.printLL();
        ll.deleteNode(ll.getCur());
        ll.printLL();

        LLProblems l1 = new LLProblems() ;
        l1.insertLL(1);
        l1.insertLL(2);
        l1.insertLL(3);
        LLProblems l2 = new LLProblems() ;
        l2.insertLL(1);
        l2.insertLL(2);
        l2.insertLL(3);
        System.out.println(strCmp(l1, l2));

        LLProblems test = new LLProblems() ;
        test.insert(10) ;
        test.insert(9);
        test.insert(14) ;
        test.insert(23);
        test.insert(3);
        test.insert(16);
        test.printLL();
        test.setHead(test.mergeSort(test.getHead()));
        test.printLL();
    }
}
