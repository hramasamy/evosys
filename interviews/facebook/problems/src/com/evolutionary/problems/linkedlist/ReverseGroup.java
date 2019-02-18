package com.evolutionary.problems.linkedlist;

public class ReverseGroup {
    Node head;
    Node cur;

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

    ReverseGroup() {
        head = null;
        cur = null;
    }

    public void printLL() {
        Node pre = getHead();
        while (pre != null) {
            System.out.print(pre.getData() + "--->");
            pre = pre.getNext();
        }
        System.out.println("null");
    }

    public void insert(int data) {
        Node node = new Node(data);
        if (getHead() == null) {
            setHead(node);
            setCur(node);
            return;
        }
        cur.setNext(node);
        cur = node;
        return;
    }

    public Node reverseK(Node head, int k) {
        Node prev = null;
        Node next = null;
        Node current = head;
        int count = 0;
        while (current != null && count < k) {
            next = current.getNext();
            current.setNext(prev);
            prev = current;
            current = next;
            count++;
        }

        if (next != null) {
            head.setNext(reverseK(next, k));
        }
        return prev;
    }

    Node reverse(Node head, int k)
    {
        Node current = head;
        Node next = null;
        Node prev = null;

        int count = 0;

        /* Reverse first k nodes of linked list */
        while (count < k && current != null)
        {
            next = current.next;
            current.next = prev;
            prev = current;
            current = next;
            count++;
        }

       /* next is now a pointer to (k+1)th node
          Recursively call for the list starting from current.
          And make rest of the list as next of first node */
        if (next != null)
            head.setNext(reverse(next, k)) ;

        // prev is now head of input list
        return prev;
    }
    public static void main (String [] args) {
        ReverseGroup test = new ReverseGroup() ;
        test.insert(10) ;
        test.insert(9);
        test.insert(14) ;
        test.insert(23);
        test.insert(3);
        test.insert(16);
        test.insert(95);
        test.insert(84);
        test.printLL();
        //test.reverseK(test.getHead(), 2) ;
        test.setHead(test.reverseK(test.getHead(), 3)) ;
        test.printLL();
    }
}
