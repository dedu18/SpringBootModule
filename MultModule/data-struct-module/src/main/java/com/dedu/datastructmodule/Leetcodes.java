package com.dedu.datastructmodule;

public class Leetcodes {


    static class ListNode {
        int val;
        ListNode next;
        ListNode(int x) {
            val = x;
            next = null;
        }
    }

    public static class Node<Integer> {

        public Integer val;

        public Node pre, next, child;

        public Node(Integer e) {
            this.val = e;
        }
    }

    private  static  Node head;

    /** Get the value of the index-th node in the linked list. If the index is invalid, return -1. */
    public static  int get(int index) {
        if (index < 0) {
            return -1;
        }
        Node<Integer> cur = head;
        for (int i=0; i<index; i++) {
            if (null == cur) {
                return -1;
            }
            cur = cur.next;
        }
        if (null != cur) {
            return cur.val;
        }
        return -1;
    }

    /** Add a node of value val before the first element of the linked list. After the insertion, the new node will be the first node of the linked list. */
    public static  void addAtHead(int val) {
        if (null == head) {
            head = new Node(val);
            return;
        }
        Node newHead = new Node(val);
        newHead.next = head;
        head.pre = newHead;
        head = newHead;
    }

    /** Append a node of value val to the last element of the linked list. */
    public static  void addAtTail(int val) {
        if (null == head) {
            addAtHead(val);
            return;
        }
        Node cur = head;
        while (null != cur.next) {
            cur = cur.next;
        }
        Node tail = new Node(val);
        tail.pre = cur;
        cur.next = tail;
    }

    /** Add a node of value val before the index-th node in the linked list. If index equals to the length of linked list, the node will be appended to the end of linked list. If index is greater than the length, the node will not be inserted. */
    public static  void addAtIndex(int index, int val) {
        if (index <= 0) {
            addAtHead(val);
            return;
        }
        Node cur = head;
        for (int i=0; i<index-1; i++) {
            if (null == cur.next) {
                break;
            }
            cur = cur.next;
        }
        if (null == cur.next) {
            Node tail = new Node(val);
            tail.pre = cur;
            cur.next = tail;
        } else {
            Node tmp = new Node(val);
            Node next = cur.next;
            tmp.pre = cur;
            tmp.next = next;
            cur.next = tmp;
            next.pre = tmp;
        }
    }

    /** Delete the index-th node in the linked list, if the index is valid. */
    public static  void deleteAtIndex(int index) {
        if (null == head || index < 0) {
            return;
        }
        if (index == 0) {
            head = head.next;
            if (null == head) {
                return;
            }
            head.pre = null;
            return;
        }
        Node cur = head;
        for (int i=0; i<index; i++) {
            if (null == cur.next) {
                return;
            }
            cur = cur.next;
        }
        Node pre = cur.pre;
        Node next = cur.next;
        pre.next = next;
        if (next != null) {
            next.pre = pre;
        }
    }

    public static ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode result = new ListNode(-1);
        ListNode cur = result;
        while (null != l1 && null != l2) {
            if (l1.val <= l2.val) {
                cur.next = l1;
                l1 = l1.next;
                cur = cur.next;
            } else {
                cur.next = l2;
                l2 = l2.next;
                cur = cur.next;
            }
        }
        while (null != l1) {
            cur.next = l1;
            l1 = l1.next;
            cur = cur.next;
        }
        while (null != l2) {
            cur.next = l2;
            l2 = l2.next;
            cur = cur.next;
        }
        cur.next = null;
        return result.next;
    }

    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        if (null == l1) {
            return l2;
        } else if (null == l2) {
            return l1;
        }
        ListNode result = new ListNode(-1);
        ListNode cur = result;
        boolean isCarry = false; //是否进位
        while (l1 != null || l2 != null) {
            int v1 = 0;
            int v2 = 0;
            if (null != l1) {
                v1 = l1.val;
                l1 = l1.next;
            }
            if (null != l2) {
                v2 = l2.val;
                l2 = l2.next;
            }
            int val = v1 + v2;
            if (isCarry) {
                val++;
                isCarry = false;
            }
            ListNode tmp = new ListNode(val % 10);
            cur.next = tmp;
            cur = cur.next;
            if (val > 9) {
                isCarry = true;
            }
        }
        if (isCarry) {
            ListNode tmp = new ListNode(1);
            cur.next = tmp;
            cur = cur.next;
        }
        return result.next;
    }

    public static Node flatten(Node head) {
        if (null == head) {
            return head;
        }
        Node cur = head;
        Node next = head.next;
        

        return head;
    }

    public static void main(String[] args) {
//        addAtHead(4);
//        System.out.println(get(1));
//        addAtHead(1);
//        addAtHead(5);
//        deleteAtIndex(3);
//        addAtHead(7);
//        System.out.println(get(3));
//        System.out.println(get(3));
//        System.out.println(get(3));
//        addAtHead(1);
//        deleteAtIndex(4);
//        print(head);
//        addAtHead(3);
//        addAtHead(2);
//        addAtHead(5);
//        addAtTail(5);
//        deleteAtIndex(6);

//        Leetcodes m = new Leetcodes();
//        ListNode list = m.new ListNode(1);
//        list.next = m.new ListNode(2);
//        list.next.next = m.new ListNode(3);
//
//        ListNode list2 = m.new ListNode(1);
//        list2.next = m.new ListNode(2);
//        list2.next.next = m.new ListNode(3);
//        list.next.next.next = m.new ListNode(3);
//        list.next.next.next.next = m.new ListNode(2);
//        list.next.next.next.next.next = m.new ListNode(1);
//        list.next.next.next.next.next.next = m.new ListNode(7);
//        print(mergeTwoLists(list, list2));

        ListNode list = new ListNode(1);
        list.next = new ListNode(8);
//        list.next.next = new ListNode(3);

        ListNode list2 = new ListNode(0);
//        list2.next = new ListNode(6);
//        list2.next.next = new ListNode(4);
        print(addTwoNumbers(list, list2));


    }

    public static void print(ListNode head) {
        while (head!=null) {
            System.out.println(head.val);
            head = head.next;
        }
    }

    public static void print(Node head) {
        while (head!=null) {
            System.out.println(head.val);
            head = head.next;
        }
    }
}
