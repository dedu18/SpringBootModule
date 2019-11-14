package com.dedu.datastructmodule;

import java.util.*;

public class MyLinkedList {
//    单链表反转
//    链表中环的检测
//    两个有序的链表合并
//    删除链表倒数第n个结点
//    求链表的中间结点

    class Node<T> {
        private T t;
        private Node next;

        public Node() {
        }

        public Node(T t) {
            this.t = t;
        }

        @Override
        public String toString() {
            return "【" + t + "】";
        }
    }

    public static void main(String[] args) {
        /**
         * 单链表反转 Java API
         */
//        List<String> rowList = createRowList();
//        System.out.println(rowList);
//        Collections.reverse(rowList);
//        System.out.println(rowList);

        /**
         * 单链表反转 Custom Impl
         */
//        Node<String> node = createNodeArr();
//        node = reverse(node);
//        printNodes(node);

        /**
         * 链表中环的检测 快慢指针
         */
//        boolean result = checkLoop(node);
//        System.out.println(result ? "有循环" : "没有循环");

        /**
         * 两个有序的链表合并
         */
//        Node<Integer> node = createNumNodeArr(5);
//        Node<Integer> node1 = createNumNodeArr(10);
//        Node result = mergeSortedList(node, node1);
//        printNodes(result);

        /**
         * 删除链表倒数第n个结点
         */
//        Node<Integer> node = createNumNodeArr(5);
//        printNodes(node);
//        System.out.println("*******************");
//        Node deletedNode = deleteFromBottom(node, 4);
//        printNodes(node);
//        System.out.println("*******************");
//        printNodes(deletedNode);

        /**
         * 求链表的中间结点
         */
        Node<Integer> node = createNumNodeArr(5);
        printNodes(node);
        System.out.println("******************");
        Node middleNode = getMiddleNode(node);
        printNodes(middleNode);
    }

    private static Node getMiddleNode(Node node) {
        Node slow = node;
        Node quick = node;
        while (null != quick && null != quick.next) {
            slow = slow.next;
            quick = quick.next.next;
        }
        return slow;
    }

    private static Node deleteFromBottom(Node node, int offset) {
        Node preNode = node;
        Node deletedNode = node;
        Node currentNode = node;
        while (null != currentNode && null != currentNode.next ) {
            for (int i = 1; i < offset; i++) {
                if (null == currentNode.next) {
                    return null;
                }
                currentNode = currentNode.next;
            }
            if (null == currentNode.next) {
                break;
            }
            if (preNode != deletedNode) {
                preNode = preNode.next;
            }
            deletedNode = deletedNode.next;
            currentNode = deletedNode;
        }
        preNode.next = deletedNode.next;
        return deletedNode;
    }

    private static Node mergeSortedList(Node<Integer> node, Node<Integer> node1) {
        MyLinkedList list = new MyLinkedList();
        Node<Integer> result = list.new Node<>();
        Node cur = result;
        Node<Integer> q = node;
        Node<Integer> p = node1;
        while (q != null && p != null) {
            if (q.t <= p.t) {
                cur.next = q;
                cur = cur.next;
                q = q.next;
            } else {
                cur.next = p;
                cur = cur.next;
                p = p.next;
            }
        }
        while (q != null) {
            cur.next = q;
            cur = cur.next;
            q = q.next;
        }
        while (p != null) {
            cur.next = p;
            cur = cur.next;
            p = p.next;
        }
        return result;
    }

    private static boolean checkLoop(Node node) {
        if (null == node) {
            return false;
        }
        Node slow = node;
        Node quick = node.next;
        while (null != quick && null != quick.next) {
            if (slow == quick) {
                return true;
            } else if (null == quick.next) {
                return false;
            }
            slow = slow.next;
            quick = quick.next.next;
        }
        return false;
    }

    private static Node reverse(Node node) {
        if (null == node) {
            return null;
        }
        Node cur = node;
        Node result = null;
        Node next = node.next;
        while (cur != null) {
            cur.next = result;
            result = cur;
            cur = next;
            if (null != cur)  {
                next = cur.next;
            }
        }
        return result;
    }

    private static void printNodes(Node node) {
        if (node != null) {
            System.out.println(node);
            printNodes(node.next);
        }
    }

    private static Node createNodeArr() {
        MyLinkedList list = new MyLinkedList();
        Node<String> result = list.new Node<>("A");
        Node<String> t1 = list.new Node<>("B");
        Node<String> t2 = list.new Node<>("C");
        Node<String> t3 = list.new Node<>("D");
        Node<String> t4 = list.new Node<>("E");
        Node<String> t5 = list.new Node<>("F");

        result.next = t1;
        t1.next = t2;
        t2.next = t3;
        t3.next = t4;
        t4.next = t5;
        t5.next = t2;
        return result;
    }

    private static Node createNumNodeArr(Integer salt) {
        MyLinkedList list = new MyLinkedList();
        Node<Integer> result = list.new Node<>(1);
        Node<Integer> t1 = list.new Node<>(2);
        Node<Integer> t2 = list.new Node<>(3);
        Node<Integer> t3 = list.new Node<>(4);
        Node<Integer> t4 = list.new Node<>(5);
        Node<Integer> t5 = list.new Node<>(6);

        result.next = t1;
        t1.next = t2;
        t2.next = t3;
        t3.next = t4;
        t4.next = t5;
        return result;
    }

    private static List<String> createRowList() {
        List<String> result = new LinkedList<>();
        result.add("A");
        result.add("B");
        result.add("Z");
        result.add("D");
        result.add("D1");
        result.add("D2");
        result.add("D3");
        result.add("D4");
        result.add("D5");
        result.add("D4");
        result.add("D9");
        result.add("D6");
        result.add("D0");
        result.add("Dsda");
        result.add("Ddsd");
        result.add("Ddsad");
        result.add("Ddsadasd");
        result.add("Dddd");
        result.add("Deee");
        result.add("E");
        return result;
    }
}
