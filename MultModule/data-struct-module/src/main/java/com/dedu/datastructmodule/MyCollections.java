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

    public static int strStr(String haystack, String needle) {
        if (needle == null || "".equals(needle) || haystack == null || "".equals(haystack)) {
            return -1;
        }
        return haystack.indexOf(needle);
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

    public static String commonString(String a, String b) {
        int acur=0, bcur=0, offset=0;
        List<String> lists = new LinkedList<>();
        while (acur!=a.length() || bcur!=b.length()) {
            if (a.charAt(acur) == b.charAt(bcur)) {
                acur++;
                bcur++;
                offset++;
            } else {
                if (offset!=0) {
                    lists.add(a.substring(acur-offset+1, acur));
                }
                acur = acur-offset+1;
                acur = acur-offset+1;
                offset=0;
            }
        }
        if (!lists.isEmpty()) {
            int max=0;
            for (int i=0; i<lists.size(); i++) {
                if (lists.get(i).length() > lists.get(max).length()) {
                    max = i;
                }
            }
            return lists.get(max);
        } else {
            return "";
        }
    }

//    public String longestCommonPrefix(String[] strs) {
//        if (strs.length == 0) return "";
//        String prefix = strs[0];
//        for (int i = 1; i < strs.length; i++)
//            while (strs[i].indexOf(prefix) != 0) {
//                prefix = prefix.substring(0, prefix.length() - 1);
//                if (prefix.isEmpty()) return "";
//            }
//        return prefix;
//    }

    public static String longestCommonPrefix2(String[] strs) {
        if (null == strs || strs.length == 0) {
            return "";
        }
        String commonString = strs[0];
        for (int i=1; i<strs.length; i++) {
            while (strs[i].indexOf(commonString) == -1) {
                commonString = commonString.substring(0, commonString.length() - 1);
                if (commonString.isEmpty()) {
                    return "";
                }
            }
        }
        return commonString;
    }

    public static int[] twoSum(int[] numbers, int target) {
        int left = 0, right = numbers.length-1;
        int[] result = new int[2];
        // 因为有序，所以用两个指针移动查找，左指针右移则变大，右指针左移则变小
        while (left < right) {
            if (numbers[left] + numbers[right] == target) {
                result[0] = ++left;
                result[1] = ++right;
                break;
            } else if (numbers[left] + numbers[right] < target) { // 比目标小，则左标增大
                left++;
            } else { // 比目标大，则右标变小
                right--;
            }
        }
        return result;
    }

    public static int removeElement(int[] nums, int val) {
        int i=0, length = nums.length-1;
        while(i <= length) {
            // 覆盖交换法，这样就不用频繁移动元素
            if (nums[i] == val) {
                nums[i] = nums[length];
                length--;
            } else {
                i++;
            }
        }
        return length + 1;
    }

    public static int findMaxConsecutiveOnes(int[] nums) {
        if (null == nums || nums.length == 0) {
            return 0;
        }
        int slow=0, max=0;
        for (int quick = 0; quick<nums.length; quick++) {
            if (nums[quick] == 1) {
                slow++;
            } else {
                if (max < slow) {
                    max = slow;
                }
                slow = 0;
            }
        }
        if (max < slow) {
            return slow;
        }
        return max;
    }

    public static int minSubArrayLen(int s, int[] nums) {
        int result = 0;
        if (null == nums || nums.length == 0) {
            return result;
        }
        for (int i=0; i<nums.length; i++) {
            int total = 0;
            int j=i;
            while (total < s && j<nums.length) {
                total+=nums[j];
                j++;
            }
            if (total < s) {
                continue;
            }
            result = result < j-i  && result != 0 ? result : j-i;
        }
        return result;
    }

    public static void rotate(int[] nums, int k) {
        if (null == nums || nums.length < 2 || k % nums.length == 0) {
            return ;
        }
        k = k % nums.length; //去除重复移动
        for (int i=0; i<k; i++) { //每次移动1步
            int tmp = nums[nums.length-1]; //数组最后一个元素
            for (int j = nums.length-1; j>0; j--) {
                nums[j] = nums[j-1];
            }
            nums[0] = tmp;
        }
    }

    public static void rotate2(int[] nums, int k) {
        if (null == nums || nums.length < 2 || k % nums.length == 0) {
            return ;
        }
        k = k % nums.length; //去除重复移动
        reverse(nums, 0, nums.length-1);
        reverse(nums, 0, k-1);
        reverse(nums, k, nums.length-1);
    }

    public static void reverse(int[] nums, int start, int end) {
        while (start < end) {
            int temp = nums[start];
            nums[start] = nums[end];
            nums[end] = temp;
            start++;
            end--;
        }
    }

    public static List<Integer> getRow(int rowIndex) {
        List<Integer> cache = new ArrayList<>();
        for (int i=0; i<=rowIndex; i++) {
            Integer[] t = new Integer[i+1];
            for (int j=0; j<t.length; j++) {
                if (j==0 || j==t.length-1) {
                    t[j]=1;
                } else {
                    t[j] = cache.get(j-1) + cache.get(j);
                }
            }
            cache = Arrays.asList(t);
        }
        return cache;
    }

    public static String reverseWords(String s) {
        if (null == s || Objects.equals("", s.trim())) {
            return "";
        }
        String[] strArr = s.trim().split("\\s+");
        StringBuffer sb = new StringBuffer();
        for (int i=strArr.length-1; i>=0; i--) {
            if (!Objects.equals("", strArr[i].trim())) {
                sb.append(strArr[i].trim());
                sb.append(" ");
            }
        }
        return sb.toString().substring(0, sb.toString().length() - 1);
    }

    public static String reverseWords2(String s) {
        if (null == s || Objects.equals("", s.trim())) {
            return "";
        }
        String[] strArr = s.trim().split("\\s+");
        StringBuffer sb = new StringBuffer();
        for (int i=0; i<strArr.length; i++) {
            sb.append(new StringBuffer(strArr[i]).reverse()).append(" ");
        }
        return sb.toString().trim();
    }

    public static int removeDuplicates(int[] nums) {
        if (null == nums || nums.length == 0) {
            return 0;
        }
        int slow = 0;
        for (int quick = 1; quick < nums.length; quick++) {
            if (nums[quick] != nums[slow]) {
                slow++;
                nums[slow] = nums[quick];
            }
        }
        return slow+1;
    }

    public static void moveZeroes(int[] nums) {
        if (null == nums || nums.length < 2) {
            return;
        }
        int slow=0;
        while (slow < nums.length && nums[slow] != 0) {
            slow++;
        }
        for (int quick=slow+1; quick<nums.length; quick++) {
            if (nums[quick] != 0) {
                nums[slow] = nums[quick];
                nums[quick] = 0;
                slow++;
            }
        }
    }

    private class Node<E> {
        E item;
        Node next;
        public Node(E e) {
            this.item = e;
        }
    }

    private Node head;

    /** Get the value of the index-th node in the linked list. If the index is invalid, return -1. */
    public int get(int index) {
        if (index < 0 || null == head) {
            return -1;
        }
        Node<Integer> cur = head;
        for (int i=0; i<index; i++) {
            if (null == cur.next) {
                return -1;
            }
            cur = cur.next;
        }
        return cur.item;
    }

    /** Add a node of value val before the first element of the linked list. After the insertion, the new node will be the first node of the linked list. */
    public void addAtHead(int val) {
        if (head == null) {
            head = new Node(val);
        } else {
            Node h = new Node(val);
            h.next = head;
            head = h;
        }
    }

    /** Append a node of value val to the last element of the linked list. */
    public void addAtTail(int val) {
        if (head == null) {
            head = new Node(val);
        } else {
            Node cur = head;
            while (cur.next != null) {
                cur = cur.next;
            }
            Node h = new Node(val);
            cur.next = h;
        }
    }

    /** Add a node of value val before the index-th node in the linked list. If index equals to the length of linked list, the node will be appended to the end of linked list. If index is greater than the length, the node will not be inserted. */
    public void addAtIndex(int index, int val) {
        if (index <= 0) {
            addAtHead(val);
        }
        Node<Integer> cur = head;
        for (int i=1; i<index; i++) {
            if (null == cur.next) {
                return ;
            }
            cur = cur.next;
        }
        if (null == cur.next) {
            addAtTail(val);
            return;
        }
        Node newNode = new Node(val);
        newNode.next = cur.next;
        cur.next = newNode;
    }

    /** Delete the index-th node in the linked list, if the index is valid. */
    public void deleteAtIndex(int index) {
        if (index < 0 || head == null) {
            return;
        }
        if (index==0) {
            head = head.next;
            return;
        }
        Node<Integer> pre = head;
        for (int i=0; i<index-1; i++) {
            if (null == pre.next) {
                return ;
            }
            pre = pre.next;
        }
        Node cur = pre.next;
        if (null == cur || null == cur.next) {
            pre.next = null;
            return;
        }
        pre.next = cur.next;
    }

    class ListNode {
          int val;
          ListNode next;
          ListNode(int x) {
              val = x;
              next = null;
          }
      }

    public boolean hasCycle(ListNode head) {
        if (null == head) {
            return false;
        }
        ListNode quick = head.next;
        ListNode slow = head;
        while (null != slow.next && null != quick.next && null != quick.next.next) {
            if (slow == quick) {
                return true;
            }
            slow = slow.next;
            quick = quick.next.next;
        }
        return false;
    }

    public static ListNode detectCycle(ListNode head) {
        if (head == null || null == head.next) {
            return null;
        }
        ListNode slow = head;
        ListNode quick = head.next;
        while (null != slow.next && null != quick.next && null != quick.next.next) {
            if (slow == quick) {
                break;
            }
            slow = slow.next;
            quick = quick.next.next;
        }
        if (slow != quick) {
            return null;
        }
        slow = head;
        quick = quick.next;
        while (slow != quick) {
            slow = slow.next;
            quick = quick.next;
        }
        return slow;
    }

    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        ListNode a = headA;
        ListNode b = headB;
        while (a != b) {
            if (null == a) {
                a = headB;
            }
            if (null == b) {
                b = headA;
            }
            a = a.next;
            b = b.next;
        }
        return a;
    }

    public static ListNode removeNthFromEnd(ListNode head, int n) {
        if (head == null || n <= 0) {
            return null;
        }
        // 注意处理倒数第一个元素和倒数最后一个元素
        ListNode slow = head;
        ListNode quick = head;
        for (int i=0; i<n; i++) {
            quick = quick.next;
        }
        while (null != quick && null != quick.next) {
            quick = quick.next;
            slow = slow.next;
        }
        if (null == quick && n == 1) {
            return null;
        }  else if (null == quick) {
            return slow.next;
        }
        slow.next = slow.next.next;
        return head;
    }
    public static void print(ListNode head) {
        while (head!=null) {
            System.out.println(head.val);
            head = head.next;
        }
    }

    public static ListNode reverseList(ListNode head) {
        if (null == head || null == head.next) {
            return head;
        }
        ListNode result = null;
        ListNode cur = head;
        while (null != cur) {
            ListNode next = removeNextItemFromList(cur);
            addItemToList(result, next);
        }
        return result;
    }

    private static void addItemToList(ListNode result, ListNode next) {
        if (next == null) {
            return;
        }
        if (null == result) {
            result = next;
            return;
        }
        next.next = result;
        result = next;
    }

    private static ListNode removeNextItemFromList(ListNode cur) {
        ListNode next = cur.next;
        if (next != null) {
            cur = cur.next.next;
        } else {
            cur = null;
        }
        return next;
    }

    public static void main(String[] args) {
        MyCollections m = new MyCollections();
        ListNode list = m.new ListNode(1);
        list.next = m.new ListNode(2);
//        list.next.next = m.new ListNode(3);
//        list.next.next.next = m.new ListNode(4);
//        list.next.next.next.next = m.new ListNode(5);
        print(reverseList(list));
    }
}