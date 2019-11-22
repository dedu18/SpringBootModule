package com.dedu.datastructmodule;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class MyCollections {

    public static void main(String[] args) {
        // ArrayList
        ArrayList arrayList = new ArrayList();
        arrayList.add("A");
        arrayList.add(null);
        arrayList.add("B");
        arrayList.add(null);
        arrayList.add("C");
        System.out.println(arrayList);
        arrayList.remove(1);
        System.out.println(arrayList);
        arrayList.clear();
        System.out.println(arrayList);
        System.out.println(arrayList.indexOf(null));
        System.out.println(arrayList.lastIndexOf(null));

        // LinkedList
        LinkedList linkedList = new LinkedList();
        linkedList.add(null);

        // Vector
        Vector v = new Vector();
        v.add(null);


        // HashSet
        HashSet hashSet = new HashSet();
        // TreeSet
        TreeSet treeSet = new TreeSet();
        treeSet.add(1);
        treeSet.add(2);
        treeSet.add(3);
        treeSet.add(0);
        System.out.println(treeSet.first());
        System.out.println(treeSet.last());
        System.out.println(treeSet);
        // LinkedHashSet
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        linkedHashSet.add(1);
        linkedHashSet.add(2);
        linkedHashSet.add(3);
        linkedHashSet.add(0);
        System.out.println(linkedHashSet);

        // ArrayBlockingQueue
        ArrayBlockingQueue arrayBlockingQueue = new ArrayBlockingQueue(5);
        arrayBlockingQueue.add(1);
        arrayBlockingQueue.add(2);
        arrayBlockingQueue.add(3);
        arrayBlockingQueue.add(4);
        arrayBlockingQueue.add(5);
        arrayBlockingQueue.remove(3);
        System.out.println(arrayBlockingQueue.offer(3));

        //
        LinkedBlockingQueue linkedBlockingQueue = new LinkedBlockingQueue();


    }
}
