package com.evolutionary.problems.linkedlist;

public class PartitionLL {

    Node head ;
    Node cur ;

    PartitionLL() {
        head = null ;
        cur = null ;
    }

    PartitionLL (int data) {

        Node node = new Node(data) ;
        head = node ;
        cur = head ;
    }

    public void insertNode (int data) {
        Node node = new Node(data) ;
        if (head == null) {
            head = node ;
            cur = head ;
        }
        else {
            cur.setNext(node) ;
            cur = cur.getNext() ;
        }
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

    public void partitionLL (int data) {
        Node fake1 = new Node(0) ;
        Node fake2 = new Node(0) ;
        Node head2 = fake2 ;
        Node head1 = fake1 ;

        Node prev = fake1 ;
        Node pre = this.getHead() ;
        fake1.setNext(this.getHead());

        while (pre != null) {
            if (pre.getData() <= data) {
                    pre = pre.getNext();
                    prev = prev.getNext();
            }
            else {
                fake2.setNext(pre) ;
                fake2 = fake2.getNext() ;
                prev.setNext(pre.getNext()) ;
                pre = pre.getNext() ;
            }

        }
        fake2.setNext(null );
        prev.setNext(head2.getNext()) ;

        this.setHead(head1.getNext());
    }

    public void printLL () {

        Node pre = this.getHead() ;
        while (pre != null) {
            System.out.print (pre.getData() + " ---> ") ;
            pre = pre.getNext() ;
        }
        System.out.println (" null") ;
    }

    public static void main (String [] args) {
        PartitionLL pll = new PartitionLL() ;
        pll.insertNode(10);
        pll.insertNode(5) ;
        pll.insertNode(7) ;
        pll.insertNode(15);
        pll.insertNode(20);
        pll.insertNode(4);
        pll.insertNode(11);
        pll.insertNode(25);
        pll.printLL();
        pll.partitionLL(12);
        pll.printLL();
        pll.partitionLL(9);
        pll.printLL();
    }
}
