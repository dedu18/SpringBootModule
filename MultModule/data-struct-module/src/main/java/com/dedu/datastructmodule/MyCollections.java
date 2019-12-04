package com.dedu.datastructmodule;

import org.springframework.util.StringUtils;

import java.util.*;
import java.util.concurrent.*;

public class MyCollections {

    public static void main2(String[] args) {
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

        // LinkedBlockingQueue
        LinkedBlockingQueue linkedBlockingQueue = new LinkedBlockingQueue();

        // PriorityBlockingQueue
        PriorityBlockingQueue priorityBlockingQueue = new PriorityBlockingQueue();

        // HashMap
        HashMap<String, Object> hashMap = new HashMap<>(1<<29);
        hashMap.put("", "");
        String a = "AAA";
        System.out.println(a.hashCode());
        System.out.println(a.hashCode());
        Map<String, Object> stringObjectMap = Collections.synchronizedMap(hashMap);
        stringObjectMap.put("1",1);

        Hashtable hashtable = new Hashtable();
        hashtable.put("", "");

        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put(null, null);

        TreeMap treeMap = new TreeMap();
        treeMap.put("", "");

        int[] aa=null;
    }

    public int[] findDiagonalOrder(int[][] matrix) {
        if (null == matrix || matrix.length == 0) {
            return new int[0];
        }
        int rowlength = matrix.length;
        int columnlength = matrix[0].length;
        int[] result = new int[rowlength * columnlength];
        int row = 0, column = 0;
        for (int i=0; i<result.length; i++) {
            result[i]=matrix[row][column];
            // 针对row+column的奇偶性判断游标移动和方向转换，
            // 左下 ： row++ column--  右上：row-- column++
            if ((row+column)%2==0) { //偶数右上
                //边界判断 转向
                if (column == columnlength - 1) {
                    row++;
                } else if (row == 0) {
                    column++;
                } else {
                    row--;
                    column++;
                }
            } else { //奇数左下
                if (row == rowlength - 1) {
                    column++;
                } else if(column == 0) {
                    row++;
                } else {
                    row++;
                    column--;
                }
            }
        }
        return result;
    }

    public static List<Integer> spiralOrder(int[][] matrix) {
        if (null == matrix || matrix.length == 0) {
            return new ArrayList<>();
        }
        int row = matrix.length;
        int column = matrix[0].length;
        List<Integer> result = new ArrayList<>(row * column);
        boolean right = true, down = false, left = false, up = false;
        int rightlength = 0, downlength = 0, leftlength = 0, uplength = 0;
        int x = 0, y = 0;
        for (int i=0; i < row*column; i++) {
            //右行 x++  下行 y++  左行x-- 上行y--
            result.add(matrix[x][y]);
            if (right) {
                if (y == (column - rightlength - 1)) {
                    right=false;
                    down=true;
                    uplength++;
                    x++;
                } else {
                    y++;
                }
            } else if (down) {
                if (x == (row - downlength - 1)) {
                    down=false;
                    left=true;
                    rightlength++;
                    y--;
                } else {
                    x++;
                }
            } else if (left) {
                if (y == leftlength) {
                    left=false;
                    up=true;
                    downlength++;
                    x--;
                } else {
                    y--;
                }
            } else if (up) {
                if (x == uplength) {
                    up=false;
                    right=true;
                    leftlength++;
                    y++;
                } else {
                    x--;
                }
            }
        }
        return result;
    }


    public static List<List<Integer>> generate(int numRows) {
        List<List<Integer>> result = new ArrayList<>();
        if (numRows == 0) {
            return result;
        } else if (numRows == 1) {
            List<Integer> row1 = new ArrayList<>(2);
            row1.add(1);
            result.add(row1);
            return result;
        } else if (numRows == 2) {
            List<Integer> row1 = new ArrayList<>(2);
            List<Integer> row2 = new ArrayList<>(2);
            row1.add(1);
            row2.add(1);
            row2.add(1);
            result.add(row1);
            result.add(row2);
            return result;
        } else { //至少3行
            for (int i=0; i<numRows; i++) {
                Integer[] rowArr = new Integer[i+1];
                for (int j=0; j<rowArr.length; j++) {
                    if (j==0 || j==rowArr.length-1) {
                        rowArr[j] = 1;
                    } else {
                        rowArr[j] = result.get(i-1).get(j-1) + result.get(i-1).get(j);
                    }
                }
                result.add(Arrays.asList(rowArr));
            }
            return result;
        }
    }

    @org.jetbrains.annotations.NotNull
    public static String addBinary(String a, String b) {
        StringBuffer asb = new StringBuffer(a);
        StringBuffer areverse = asb.reverse();
        StringBuffer bsb = new StringBuffer(b);
        StringBuffer breverse = bsb.reverse();
        int offset = Math.abs(areverse.length() - breverse.length());
        StringBuffer osb = new StringBuffer();
        for (int i=0; i<offset; i++) {
            osb.append('0');
        }
        if (areverse.length() < breverse.length()) {
            areverse.append(osb);
        } else {
            breverse.append(osb);
        }
        boolean carry = false;
        for (int i=0; i<areverse.length(); i++) {
            if (areverse.charAt(i) == '1' && breverse.charAt(i) == '1') {
                if (carry) {
                    areverse.setCharAt(i, '1');
                    carry = true;
                } else {
                    areverse.setCharAt(i, '0');
                    carry = true;
                }
            } else if (areverse.charAt(i) == '1' || breverse.charAt(i) == '1'){
                if (carry) {
                    areverse.setCharAt(i, '0');
                } else {
                    areverse.setCharAt(i, '1');
                }
            } else {
                if (carry) {
                    areverse.setCharAt(i, '1');
                    carry = false;
                } else {
                    areverse.setCharAt(i, '0');
                }
            }
        }
        if (carry) {
            areverse.append(1);
        }
        return areverse.reverse().toString();
    }

    public static void reverseString(char[] s) {
        if (null == s || s.length == 0 || s.length == 1) {
            System.out.println(s);
        }
        int l = 0;
        int r = s.length - 1;
        while (l != r && (l-r) != 1) {
            swap(s, l, r);
            l++;
            r--;
        }
        System.out.println(s);
    }

    public static void swap(char[] s, int i, int j) {
        char tmp = s[i];
        s[i] = s[j];
        s[j] = tmp;
    }

    public static void main(String[] args) {
        char[] s = {'h','e','l','l','o'};
        reverseString(s);
    }


    public static int strStr(String haystack, String needle) {
        if (needle == null || "".equals(needle) || haystack == null || "".equals(haystack)) {
            return -1;
        }
        return haystack.indexOf(needle);
    }

    public static String longestCommonPrefix3(String[] strs) {
        if (strs.length == 0) {
            return "";
        }
        if (strs.length == 1) {
            return strs[0];
        }
        String min = strs[0] ;
        for (int i = 0; i < strs.length; i++) {
            if (strs[i].isEmpty()) {
                return "";
            }
            if (strs[0].charAt(0) != strs[i].charAt(0)) {
                return "";
            }
            if (strs[i].length() <= min.length()) {
                min = strs[i];
            }
        }
        for (int i = 0; i < strs.length; i++) {
            if (min.equals(strs[i])) {
                continue;
            }
            for (int j = min.length()-1; j > 0 ; j--) {
                if (min.charAt(j) != strs[i].charAt(j)) {
                    min = min.substring(0, j);
                }
            }
        }
        return min;
    }

    public static String longestCommonPrefix(String[] strs) {
        if (null == strs || strs.length == 0) {
            return "";
        }
        if (strs.length == 1) {
            return strs[0];
        }
        if (strs.length == 0) return "";
        String prefix = strs[0];
        for (int i = 1; i < strs.length; i++) {
            String leftPrefix = prefix;
            String rightPrefix = prefix;
            while (strs[i].indexOf(leftPrefix) == -1) {
                leftPrefix = leftPrefix.substring(0, leftPrefix.length() - 1);
                if (leftPrefix.isEmpty()) break;
            }
            while (strs[i].indexOf(rightPrefix) == -1) {
                rightPrefix = rightPrefix.substring(0, rightPrefix.length() - 1);
                if (rightPrefix.isEmpty()) break;
            }
            if (leftPrefix.isEmpty() && rightPrefix.isEmpty()) {
                return "";
            } else {
                prefix = leftPrefix.length() > rightPrefix.length() ? leftPrefix : rightPrefix;
            }
        }
        return prefix;
    }
}