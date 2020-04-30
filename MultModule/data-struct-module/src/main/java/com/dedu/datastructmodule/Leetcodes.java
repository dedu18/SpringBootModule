package com.dedu.datastructmodule;

import javafx.util.Pair;
import org.springframework.util.StringUtils;

import java.lang.ref.Reference;
import java.util.*;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;

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

        public int val;

        public Node pre, prev, next, child, left, right;
        public Node random;

        public Node() {
        }

        public Node(int integer) {
        }

        public Node(int _val, Node _prev, Node _next, Node _child) {
            val = _val;
            prev = _prev;
            next = _next;
            child = _child;
        }

    }

    private static Node head;

    /**
     * Get the value of the index-th node in the linked list. If the index is invalid, return -1.
     */
    public static int get(int index) {
        if (index < 0) {
            return -1;
        }
        Node<Integer> cur = head;
        for (int i = 0; i < index; i++) {
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

    /**
     * Add a node of value val before the first element of the linked list. After the insertion, the new node will be the first node of the linked list.
     */
    public static void addAtHead(int val) {
        if (null == head) {
            head = new Node(val);
            return;
        }
        Node newHead = new Node(val);
        newHead.next = head;
        head.pre = newHead;
        head = newHead;
    }

    /**
     * Append a node of value val to the last element of the linked list.
     */
    public static void addAtTail(int val) {
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

    /**
     * Add a node of value val before the index-th node in the linked list. If index equals to the length of linked list, the node will be appended to the end of linked list. If index is greater than the length, the node will not be inserted.
     */
    public static void addAtIndex(int index, int val) {
        if (index <= 0) {
            addAtHead(val);
            return;
        }
        Node cur = head;
        for (int i = 0; i < index - 1; i++) {
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

    /**
     * Delete the index-th node in the linked list, if the index is valid.
     */
    public static void deleteAtIndex(int index) {
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
        for (int i = 0; i < index; i++) {
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
        while (null != cur) {
            if (null != cur.child) {
                Node childLast = getChildLastNode(cur.child);
                Node next = cur.next;
                Node child = cur.child;
                cur.next = child;
                child.prev = cur;
                childLast.next = next;
                if (null != next)
                    next.prev = childLast;
                cur.child = null;
                cur = next;
            } else {
                cur = cur.next;
            }
        }
        return head;
    }

    public static Node getChildLastNode(Node childHead) {
        Node cur = childHead;
        while (null != cur) {
            if (null != cur.child) {
                Node childLast = getChildLastNode(cur.child);
                Node next = cur.next;
                Node child = cur.child;
                cur.next = child;
                child.prev = cur;
                childLast.next = next;
                cur.child = null;
                if (next != null) {
                    next.prev = childLast;
                } else {
                    return childLast;
                }
                cur = next;
            } else {
                if (null == cur.next) {
                    return cur;
                }
                cur = cur.next;
            }
        }
        return null;
    }

    public static Node copyRandomList(Node head) {
        if (null == head) {
            return null;
        }
        Node cur = head;
        while (null != cur) {
            Node tmp = new Node();
            tmp.val = cur.val;
            Node next = cur.next;
            cur.next = tmp;
            tmp.next = next;
            cur = cur.next.next;
        }
        cur = head;
        while (null != cur) {
            Node copyNode = cur.next;
            copyNode.random = cur.random.next;
            cur = copyNode.next;
        }
        cur = head.next;
        while (null != cur) {
            if (null == cur.next) {
                break;
            }
            cur.next = cur.next.next;
            cur = cur.next;
        }
        return head.next;
    }

    public static ListNode rotateRight(ListNode head, int k) {
        if (head == null || k == 0) {
            return head;
        }
        ListNode cur = head;
        ListNode tail = null;
        int size = 0;
        while (cur.next != null) {
            size++;
            cur = cur.next;
        }
        tail = cur;
        tail.next = head;
        int offset = k % (size + 1);
        for (int i = 0; i <= (size - offset); i++) {
            head = head.next;
            tail = tail.next;
        }
//        ListNode result = head.next;
        tail.next = null;
        return head;
    }

    public static void print(ListNode head) {
        while (head != null) {
            System.out.println(head.val);
            head = head.next;
        }
    }

    public static void print(Node head) {
        while (head != null) {
            System.out.println(head.val);
            head = head.next;
        }
    }

    /********************************************************/
    class MyHashSet {

        private final int DEFAULT_SIZE = 10;

        private ListNode[] items;

        /**
         * Initialize your data structure here.
         */
        public MyHashSet() {
            items = new ListNode[DEFAULT_SIZE];
        }

        public void add(int key) {
            if (contains(key)) {
                return;
            }
            int hash = hash(key);
            ListNode item = new ListNode(key);
            if (null == items[hash]) {
                items[hash] = item;
            } else {
                ListNode cur = items[hash];
                while (null != cur.next) {
                    cur = cur.next;
                }
                cur.next = item;
            }
        }

        public void remove(int key) {
            int hash = hash(key);
            if (null == items[hash]) {
                return;
            } else if (key == items[hash].val) {
                items[hash] = items[hash].next;
            } else {
                ListNode cur = items[hash];
                while (null != cur.next && key != cur.next.val) {
                    cur = cur.next;
                }
                if (null != cur.next && key == cur.next.val) {
                    cur.next = cur.next.next;
                }
            }
        }

        /**
         * Returns true if this set contains the specified element
         */
        public boolean contains(int key) {
            int hash = hash(key);
            if (null == items[hash]) {
                return false;
            } else if (key == items[hash].val) {
                return true;
            } else {
                ListNode cur = items[hash];
                while (null != cur.next && key != cur.next.val) {
                    cur = cur.next;
                }
                if (null != cur.next && key == cur.next.val) {
                    return true;
                }
                return false;
            }
        }

        private int hash(int key) {
            return Math.abs(key) % DEFAULT_SIZE;
        }
    }

    class MyHashMap {

        class Item {
            int key;
            int val;
            Item next;

            Item(int x, int y) {
                key = x;
                val = y;
            }
        }

        private final int DEFAULT_SIZE = 10;

        private Item[] items;

        /**
         * Initialize your data structure here.
         */
        public MyHashMap() {
            items = new Item[DEFAULT_SIZE];
        }

        /**
         * value will always be non-negative.
         */
        public void put(int key, int value) {
            int hash = hash(key);
            if (null == items[hash]) {
                items[hash] = new Item(key, value);
            } else if (key == items[hash].val) {
                items[hash].val = value;
            } else {
                Item cur = items[hash];
                while (null != cur.next && key != cur.next.key) {
                    cur = cur.next;
                }
                if (null == cur.next) {
                    cur.next = new Item(key, value);
                }
                cur.next.val = value;
            }
        }

        /**
         * Returns the value to which the specified key is mapped, or -1 if this map contains no mapping for the key
         */
        public int get(int key) {
            int hash = hash(key);
            if (null == items[hash]) {
                return -1;
            } else if (key == items[hash].key) {
                return items[hash].val;
            } else {
                Item cur = items[hash];
                while (null != cur.next && key != cur.next.key) {
                    cur = cur.next;
                }
                if (null == cur.next) {
                    return -1;
                }
                return cur.next.val;
            }
        }

        /**
         * Removes the mapping of the specified value key if this map contains a mapping for the key
         */
        public void remove(int key) {
            int hash = hash(key);
            if (null == items[hash]) {
                return;
            } else if (key == items[hash].key) {
                items[hash] = items[hash].next;
            } else {
                Item cur = items[hash];
                while (null != cur.next && key != cur.next.key) {
                    cur = cur.next;
                }
                if (null == cur.next) {
                    return;
                }
                cur.next = cur.next.next;
            }
        }

        private int hash(int key) {
            return Math.abs(key) % DEFAULT_SIZE;
        }
    }

    public boolean containsDuplicate(int[] nums) {
        if (null == nums || nums.length < 2) {
            return false;
        }
        HashSet cache = new HashSet(nums.length);
        for (int i : nums) {
            if (cache.contains(i)) {
                return true;
            }
            cache.add(i);
        }
        cache = null;
        return false;
    }

    public static int singleNumber(int[] nums) {
        Arrays.sort(nums);
        int slow = 0, quick = 0;
        while (quick < nums.length) {
            while (quick < nums.length && nums[slow] == nums[quick]) {
                quick++;
            }
            if (quick - slow <= 1) {
                return nums[slow];
            }
            slow = quick;
        }
        return -1;
    }

    public static int[] intersection(int[] nums1, int[] nums2) {
        if (null == nums1 || null == nums2) {
            return new int[0];
        }
        int size = nums1.length < nums2.length ? nums1.length : nums2.length;
        HashSet cache = new HashSet();
        for (int i = 0; i < nums1.length; i++) {
            if (!cache.contains(nums1[i])) {
                cache.add(nums1[i]);
            }
        }
        HashSet<Integer> cache2 = new HashSet<>();
        int offset = 0;
        int[] tmp = new int[size];
        for (int i = 0; i < nums2.length; i++) {
            if (cache.contains(nums2[i]) && !cache2.contains(nums2[i])) {
                tmp[offset++] = nums2[i];
                cache2.add(nums2[i]);
            }
        }
//        cache.retainAll()
        int[] result = new int[offset];
        for (int i = 0; i < offset; i++) {
            result[i] = tmp[i];
        }
        return result;
    }

    private static HashSet<Integer> cache = new HashSet<>();

    public static boolean isHappy(int n) {
        if (n == 1) {
            return true;
        }
        List<Integer> nums = new LinkedList<>();
        while (n > 0) {
            int carry = n % 10;
            n = (n - carry) / 10;
            nums.add(carry);
        }
        int total = 0;
        for (Integer num :
                nums) {
            total += num * num;
        }
        if (total == 1) {
            return true;
        } else if (cache.contains(total)) {
            return false;
        } else {
            cache.add(total);
            return isHappy(total);
        }
    }

    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> cache = new HashMap<>(nums.length);
        for (int i = 0; i < nums.length; i++) {
            int distance = target - nums[i];
            if (cache.containsKey(distance)) {
                return new int[]{i, cache.get(distance)};
            }
            cache.put(nums[i], i);
        }
        return null;
    }

    /**
     * s = "egg", t = "add"
     *
     * @param s
     * @param t
     * @return
     */
    public static boolean isIsomorphic(String s, String t) {
        if (s.length() < 2) {
            return true;
        }
        char[] sChars = s.toCharArray();
        char[] tChars = t.toCharArray();
        HashMap<Character, Character> cache = new HashMap<>(sChars.length);
        for (int i = 0; i < sChars.length; i++) {
            char sChar = sChars[i];
            char tChar = tChars[i];
            if (cache.containsKey(sChar)) {
                if (!Objects.equals(tChar, cache.get(sChar))) {
                    return false;
                }
            } else {
                if (cache.containsValue(tChar)) {
                    return false;
                }
                cache.put(sChar, tChar);
            }
        }
        return true;
    }

    public static String[] findRestaurant(String[] list1, String[] list2) {
        HashMap<String, Integer> cache = new HashMap<>(list1.length > list2.length ? list2.length : list1.length);
        String[] lessList = list1.length > list2.length ? list2 : list1;
        String[] moreList = list1.length > list2.length ? list1 : list2;
        for (int i = 0; i < lessList.length; i++) {
            cache.put(lessList[i], i);
        }
        int indexTotal = moreList.length + lessList.length;
        List<String> resultList = new ArrayList<>();
        for (int i = 0; i < moreList.length; i++) {
            if (cache.containsKey(moreList[i])) {
                if (indexTotal >= i + cache.get(moreList[i])) {
                    indexTotal = i + cache.get(moreList[i]);
                    resultList.add(moreList[i]);
                }
            }
        }
        String[] result = new String[resultList.size()];
        for (int i = 0; i < resultList.size(); i++) {
            result[i] = resultList.get(i);
        }
        return result;
    }

    public static int firstUniqChar(String s) {
        char[] chars = s.toCharArray();
        HashMap<Character, Integer> cache = new HashMap<>(chars.length);
        for (int i = 0; i < chars.length; i++) {
            if (cache.containsKey(chars[i])) {
                Integer integer = cache.get(chars[i]);
                cache.put(chars[i], ++integer);
            } else {
                cache.put(chars[i], 1);
            }
        }
        List<Character> resultCharas = new ArrayList<>();
        cache.forEach((c, i) -> {
            if (i == 1) {
                resultCharas.add(c);
            }
        });
        if (resultCharas.size() == 0) {
            return -1;
        }
        int index = chars.length;
        for (Character c : resultCharas) {
            int tmp = s.indexOf(c);
            if (index > tmp) {
                index = tmp;
            }
        }
        return index;
    }


    public int[] intersect(int[] nums1, int[] nums2) {
        HashMap<Integer, Integer> cache = new HashMap<>(nums1.length);
        List<Integer> rstList = new ArrayList<>();
        for (int i = 0; i < nums1.length; i++) {
            if (cache.containsKey(nums1[i])) {
                cache.put(nums1[i], cache.get(nums1[i]) + 1);
            } else {
                cache.put(nums1[i], 1);
            }
        }
        for (int i = 0; i < nums2.length; i++) {
            if (cache.containsKey(nums2[i])) {
                Integer number = cache.get(nums2[i]);
                if (number - 1 == 0) {
                    cache.remove(nums2[i]);
                } else {
                    cache.put(nums2[i], number - 1);
                }
                rstList.add(nums2[i]);
            }
        }
        int[] result = new int[rstList.size()];
        for (int i = 0; i < rstList.size(); i++) {
            result[i] = rstList.get(i);
        }
        return result;
    }

    public boolean containsNearbyDuplicate(int[] nums, int k) {
        if (null == nums || nums.length < 1) {
            return false;
        }
        Map<Integer, List<Integer>> cache = new HashMap<>(nums.length); // K为num，V为下标列表
        for (int i = 0; i < nums.length; i++) {
            if (cache.containsKey(nums[i])) {
                List<Integer> indexs = cache.get(nums[i]);
                for (Integer index :
                        indexs) {
                    if (Math.abs(index - i) == k) {
                        return true;
                    }
                }
                cache.get(nums[i]).add(i);
            } else {
                List<Integer> tmp = new ArrayList<>();
                tmp.add(i);
                cache.put(nums[i], tmp);
            }
        }
        return false;
    }

    public static List<List<String>> groupAnagrams(String[] strs) {
        if (null == strs || strs.length == 0) {
            return new ArrayList<>();
        }
        HashMap<String, List<String>> cache = new HashMap<>();
        for (String str : strs) {
            char[] chars = str.toCharArray();
            Arrays.sort(chars);
            String charStr = new String(chars);
            if (cache.containsKey(charStr)) {
                cache.get(charStr).add(str);
            } else {
                List<String> tmp = new ArrayList<>();
                tmp.add(str);
                cache.put(charStr, tmp);
            }
        }
        return new ArrayList<>(cache.values());
    }

    public static boolean isValidSudoku(char[][] board) {
        HashMap<Character, Integer>[] row = new HashMap[9];
        HashMap<Character, Integer>[] column = new HashMap[9];
        HashMap<Character, Integer>[] gongge = new HashMap[9];

        for (int i = 0; i < 9; i++) {
            row[i] = new HashMap<>(9);
            column[i] = new HashMap<>(9);
            gongge[i] = new HashMap<>(9);
        }

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (!Objects.equals('.', board[i][j])) {
                    // 行检测
                    if (row[i].containsKey(board[i][j])) {
                        return false;
                    } else {
                        row[i].put(board[i][j], 1);
                    }
                    // 列检测
                    if (column[j].containsKey(board[i][j])) {
                        return false;
                    } else {
                        column[j].put(board[i][j], 1);
                    }
                    //宫格检测
                    int gonggeindex = i / 3 * 3 + j / 3;
                    if (gongge[gonggeindex].containsKey(board[i][j])) {
                        return false;
                    } else {
                        gongge[gonggeindex].put(board[i][j], 1);
                    }
                }
            }
        }
        return true;
    }

    public static class TreeNode {
        public int val;
        public TreeNode left;
        public TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    public static List<TreeNode> findDuplicateSubtrees(TreeNode root) {
        List<TreeNode> result = new ArrayList<>();
        if (null == root) {
            return result;
        }
        Map<String, Integer> cache = new HashMap<>();
        findSubtrees(root, cache, result);
        return result;
    }

    public static String findSubtrees(TreeNode root, Map<String, Integer> cache, List<TreeNode> result) {
        if (null == root) {
            return "";
        }
        // 通过（根,左,右）保存子树结构
        String subTreeStr = root.val + "," + findSubtrees(root.left, cache, result) + "," + findSubtrees(root.right, cache, result);
        if (null != cache.get(subTreeStr) && cache.get(subTreeStr) == 1) {
            result.add(root);
        }
        cache.put(subTreeStr, cache.getOrDefault(subTreeStr, 0) + 1);
        return subTreeStr;
    }

    public int numJewelsInStones(String J, String S) {
        if (null == J || J.trim().length() == 0) {
            return 0;
        }
        HashSet<Character> npc = new HashSet<>(J.length());
        for (Character chara : J.toCharArray()) {
            npc.add(chara);
        }
        int result = 0;
        for (Character chara : S.toCharArray()) {
            if (npc.contains(chara)) {
                result++;
            }
        }
        return result;
    }

    //abcabcbb
    public int lengthOfLongestSubstring(String s) {
        if (null == s || s.length() == 0) {
            return 0;
        }
        char[] chars = s.toCharArray();
        HashSet cache = null;
        int result = 0;
        for (int slow = 0; slow < s.length(); slow++) {
            cache = new HashSet();
            int turn = 0;
            for (int quick = slow; quick < s.length(); quick++) {
                if (cache.contains(chars[quick])) {
                    break;
                }
                turn++;
                cache.add(chars[quick]);
            }
            if (turn > result) {
                result = turn;
            }
        }
        return result;
    }

    public int lengthOfLongestSubstring2(String s) {
        Map<Character, Integer> cache = new HashMap<>();
        int result = 0;
        for (int start = 0, end = 0; end < s.length(); end++) {
            Character curChar = s.charAt(end);
            if (cache.containsKey(curChar)) {
                start = Math.max(start, cache.get(curChar));
            }
            result = Math.max(result, end - start + 1);
            cache.put(s.charAt(end), end + 1);
        }
        return result;
    }

    public int fourSumCount(int[] A, int[] B, int[] C, int[] D) {
        int result = 0;
        HashMap<Integer, Integer> cache = new HashMap<>();
        for (int i = 0; i < A.length; i++) { //将A和B数组中的数组合缓存起来
            for (int j = 0; j < B.length; j++) {
                int sum = A[i] + B[j];
                cache.put(sum, cache.getOrDefault(sum, 0) + 1);
            }
        }

        for (int k = 0; k < C.length; k++) {
            for (int l = 0; l < D.length; l++) {
                int sum = C[k] + D[l];
                result += cache.getOrDefault(-1 * sum, 0);
            }
        }
        return result;
    }

    public static List<Integer> topKFrequent(int[] nums, int k) {
        List<Integer> result = new ArrayList<>();
        HashMap<Integer, Integer> cache = new HashMap<>();
        for (Integer i :
                nums) {
            cache.put(i, cache.getOrDefault(i, 0) + 1);
        }
        HashMap<Integer, List<Integer>> statisticsMap = new HashMap<>();
        Integer maxStatistics = 0;
        for (Map.Entry<Integer, Integer> entry : cache.entrySet()) { //k为数字,v为次数
            maxStatistics = maxStatistics > entry.getValue() ? maxStatistics : entry.getValue();
            if (statisticsMap.containsKey(entry.getValue())) {
                statisticsMap.get(entry.getValue()).add(entry.getKey());
            } else {
                List<Integer> t = new ArrayList<Integer>();
                t.add(entry.getKey());
                statisticsMap.put(entry.getValue(), t);
            }
        }
        for (int t = maxStatistics; t >= 0; t--) {
            if (statisticsMap.containsKey(t)) {
                for (Integer i :
                        statisticsMap.get(t)) {
                    result.add(i);
                    if (result.size() == k) {
                        return result;
                    }
                }
            }
        }
        return result;
    }

    HashMap<Integer, Integer> cacheMap = new HashMap<>();
    List<Integer> cacheList = new ArrayList<>();
    int size = 0;

    /**
     * Inserts a value to the set. Returns true if the set did not already contain the specified element.
     */
    public boolean insert(int val) {
        if (cacheMap.containsKey(val)) {
            return false;
        } else {
            cacheMap.put(val, size);
            cacheList.add(size, val);
            size++;
            return true;
        }
    }

    /**
     * Removes a value from the set. Returns true if the set contained the specified element.
     */
    public boolean remove(int val) {
        if (cacheMap.containsKey(val)) {
            Integer index = cacheMap.remove(val);
            cacheList.add(index, Integer.MIN_VALUE);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Get a random element from the set.
     */
    public int getRandom() {
        int random = new Random().nextInt(size);
        while (Integer.MIN_VALUE == cacheList.get(random)) {
            random = new Random().nextInt(size);
        }
        return cacheList.get(random);
    }

    static class MyCircularQueue {

        private int[] queue;

        private int head, tail;

        /**
         * Initialize your data structure here. Set the size of the queue to be k.
         */
        public MyCircularQueue(int k) {
            queue = new int[k];
            head = 0;
            tail = 0;
        }

        /**
         * Insert an element into the circular queue. Return true if the operation is successful.
         */
        public boolean enQueue(int value) {
            if (isFull()) {
                return false;
            }
            queue[tail] = value;
            tail = (tail + 1) % queue.length;
            return true;
        }

        /**
         * Delete an element from the circular queue. Return true if the operation is successful.
         */
        public boolean deQueue() {
            if (head == tail) {
                return false;
            }
            head = (head + 1) % queue.length;
            return true;
        }

        /**
         * Get the front item from the queue.
         */
        public int Front() {
            if (isEmpty()) {
                return -1;
            }
            return queue[head];
        }

        /**
         * Get the last item from the queue.
         */
        public int Rear() {
            if (isEmpty()) {
                return -1;
            }
            return queue[(tail - 1 + queue.length) % queue.length];
        }

        /**
         * Checks whether the circular queue is empty or not.
         */
        public boolean isEmpty() {
            return head == tail;
        }

        /**
         * Checks whether the circular queue is full or not.
         */
        public boolean isFull() {
            return (tail + 1) % queue.length == head;
        }
    }

    public static class Pair<K, V> {
        K k;
        V v;

        public Pair(K k, V v) {
            this.k = k;
            this.v = v;
        }

        public K getKey() {
            return k;
        }

        public V getValue() {
            return v;
        }
    }

    public static int numIslands(char[][] grid) {
        if (null == grid || grid.length == 0) {
            return 0;
        }
        int row = grid.length;
        int column = grid[0].length;
        int numIslands = 0;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                if (grid[i][j] == '1') {
                    numIslands++;
                    Queue<Pair<Integer, Integer>> queue = new LinkedList<>();
                    Pair<Integer, Integer> curPair = new Pair<>(i, j);
                    isLands(grid, row, column, curPair, queue);
                    while (!queue.isEmpty()) {
                        Pair<Integer, Integer> pair = queue.poll();
                        isLands(grid, row, column, pair, queue);
                    }
                }
            }
        }
        return numIslands;
    }

    public static void isLands(char[][] grid, int row, int column, Pair<Integer, Integer> pair, Queue<Pair<Integer, Integer>> queue) {
        int i = pair.getKey();
        int j = pair.getValue();
        if (i - 1 > -1 && grid[i - 1][j] == '1') { //上
            queue.add(new Pair<>(i - 1, j));
            grid[i - 1][j] = '0';
        }
        if (j - 1 > -1 && grid[i][j - 1] == '1') { //左
            queue.add(new Pair<>(i, j - 1));
            grid[i][j - 1] = '0';
        }
        if (j + 1 < column && grid[i][j + 1] == '1') { //右
            queue.add(new Pair<>(i, j + 1));
            grid[i][j + 1] = '0';
        }
        if (i + 1 < row && grid[i + 1][j] == '1') { //下
            queue.add(new Pair<>(i + 1, j));
            grid[i + 1][j] = '0';
        }
        grid[i][j] = '0';
    }

    /**
     * 深度优先遍历
     *
     * @param grid
     * @return
     */
    public static int numIslandsDFS(char[][] grid) {
        if (null == grid || grid.length == 0) {
            return 0;
        }
        int row = grid.length;
        int column = grid[0].length;
        int numIslands = 0;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                if (grid[i][j] == '1') {
                    numIslands++;
                    isLandsDFS(grid, row, column, new Pair<>(i, j));
                }
            }
        }
        return numIslands;
    }

    public static void isLandsDFS(char[][] grid, int row, int column, Pair<Integer, Integer> pair) {
        int i = pair.getKey();
        int j = pair.getValue();
        grid[i][j] = 0;
        if (i - 1 > -1 && grid[i - 1][j] == '1') { //上
            grid[i - 1][j] = '0';
            isLandsDFS(grid, row, column, new Pair<>(i - 1, j));
        }
        if (j - 1 > -1 && grid[i][j - 1] == '1') { //左
            grid[i][j - 1] = '0';
            isLandsDFS(grid, row, column, new Pair<>(i, j - 1));
        }
        if (j + 1 < column && grid[i][j + 1] == '1') { //右
            grid[i][j + 1] = '0';
            isLandsDFS(grid, row, column, new Pair<>(i, j + 1));
        }
        if (i + 1 < row && grid[i + 1][j] == '1') { //下
            grid[i + 1][j] = '0';
            isLandsDFS(grid, row, column, new Pair<>(i + 1, j));
        }
    }


    public static int openLock(String[] deadends, String target) {
        if ("0000".equals(target)) {
            return 1;
        }
        HashSet<String> deadEndsCache = new HashSet<>(Arrays.asList(deadends));
        if (deadEndsCache.contains("0000")) {
            return -1;
        }
        Queue<String> queue = new LinkedList<>();
        queue.offer("0000");
        int trun = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                String curStr = queue.poll();
                if (target.equals(curStr)) {
                    return trun;
                }
                for (int i = 0; i < 4; i++) { //一次把4个位上的下一个可拨到字符放入队列中
                    char[] curChars = curStr.toCharArray();
                    int curNum = curChars[i] - '0';
                    curChars[i] = (char) ((curNum + 1) % 10 + '0'); // 当前位正向转
                    String s1 = new String(curChars);
                    if (!deadEndsCache.contains(s1)) {
                        queue.offer(s1);
                        deadEndsCache.add(s1);
                    }

                    curChars[i] = (char) ((curNum - 1 + 10) % 10 + '0'); // 当前位反向转
                    String s2 = new String(curChars);
                    if (!deadEndsCache.contains(s2)) {
                        queue.offer(s2);
                        deadEndsCache.add(s2);
                    }
                }
            }
            trun++; //每遍历一层就递增
        }
        return -1;
    }

    private List<Integer> stackCache = new ArrayList<>();

    private final Object obj = new Object();

    private TreeMap<Integer, Object> sortedCache = new TreeMap<>();

    public void push(int x) {
        stackCache.add(x);
        sortedCache.put(x, obj);
    }

    public void pop() {
        if (stackCache.size() > 0) {
            Integer remove = stackCache.get(stackCache.size() - 1);
            if (stackCache.indexOf(remove) == stackCache.lastIndexOf(remove)) {
                sortedCache.remove(remove);
            }
            stackCache.remove(stackCache.size() - 1);
        }
    }

    public int top() {
        if (stackCache.size() > 0) {
            return stackCache.get(stackCache.size() - 1);
        }
        return -1;
    }

    public int getMin() {
        return sortedCache.firstKey();
    }

    public static boolean isValid(String s) {
        if (null == s || s.length() % 2 == 1) {
            return false;
        }
        if ("".equals(s)) {
            return true;
        }
        char[] chars = s.toCharArray();
        Stack<Character> charStack = new Stack<>();
        for (Character chara :
                chars) {
            if (charStack.isEmpty()) {
                charStack.push(chara);
            } else {
                if (chara.equals('(')) {
                    if (charStack.peek().equals(')')) {
                        charStack.pop();
                    } else {
                        charStack.push(chara);
                    }
                } else if (chara.equals(')')) {
                    if (charStack.peek().equals('(')) {
                        charStack.pop();
                    } else {
                        charStack.push(chara);
                    }
                } else if (chara.equals('[')) {
                    if (charStack.peek().equals(']')) {
                        charStack.pop();
                    } else {
                        charStack.push(chara);
                    }
                } else if (chara.equals(']')) {
                    if (charStack.peek().equals('[')) {
                        charStack.pop();
                    } else {
                        charStack.push(chara);
                    }
                } else if (chara.equals('{')) {
                    if (charStack.peek().equals('}')) {
                        charStack.pop();
                    } else {
                        charStack.push(chara);
                    }
                } else if (chara.equals('}')) {
                    if (charStack.peek().equals('{')) {
                        charStack.pop();
                    } else {
                        charStack.push(chara);
                    }
                }
            }
        }
        return charStack.isEmpty();
    }

    /**
     * [73, 74, 75, 71, 69, 72, 76, 73]
     *
     * @param T
     * @return
     */
    public static int[] dailyTemperatures(int[] T) {
        int[] result = new int[T.length];
        Stack<Integer> tmpStack = new Stack<>();
        for (int i = 0; i < T.length; i++) {
            if (tmpStack.isEmpty()) {
                tmpStack.push(i);
            } else {
                while (true) {
                    if (tmpStack.isEmpty() || T[tmpStack.peek()] >= T[i]) {
                        tmpStack.push(i);
                        break;
                    } else {
                        Integer pop = tmpStack.pop();
                        result[pop] = i - pop;
                    }
                }
            }
        }
        while (!tmpStack.isEmpty()) {
            Integer pop = tmpStack.pop();
            result[pop] = 0;
        }
        return result;
    }

    /**
     * 从左到右的顺序压入栈中，并且按照遇到运算符就把栈顶两个元素出栈，执行运算，得到的结果再入栈的原则来进行处理
     *
     * @param tokens
     * @return
     */
    public static int evalRPN(String[] tokens) {
        Stack<String> rpnStack = new Stack<>();
        for (int i = 0; i < tokens.length; i++) {
            String tmp = tokens[i];
            if (tmp.equals("+")) {
                Integer pop1 = new Integer(rpnStack.pop());
                Integer pop2 = new Integer(rpnStack.pop());
                Integer rst = pop1 + pop2;
                rpnStack.push(rst.toString());
            } else if (tmp.equals("-")) {
                Integer pop1 = new Integer(rpnStack.pop());
                Integer pop2 = new Integer(rpnStack.pop());
                Integer rst = pop1 - pop2;
                rpnStack.push(rst.toString());
            } else if (tmp.equals("*")) {
                Integer pop1 = new Integer(rpnStack.pop());
                Integer pop2 = new Integer(rpnStack.pop());
                Integer rst = pop1 * pop2;
                rpnStack.push(rst.toString());
            } else if (tmp.equals("/")) {
                Integer pop1 = new Integer(rpnStack.pop());
                Integer pop2 = new Integer(rpnStack.pop());
                Integer rst = pop1 / pop2;
                rpnStack.push(rst.toString());
            } else {
                rpnStack.push(tmp);
            }
        }
        return new Integer(rpnStack.pop()).intValue();
    }

    public static boolean canVisitAllRooms(List<List<Integer>> rooms) {
        HashSet<Integer> canVisitRooms = new HashSet<>();
        Queue<List<Integer>> keysQueue = new LinkedList<>();
        keysQueue.offer(rooms.get(0));
        while (!keysQueue.isEmpty()) {
            List<Integer> keys = keysQueue.poll();
            for (Integer key :
                    keys) {
                if (!canVisitRooms.contains(key)) {
                    canVisitRooms.add(key);
                    keysQueue.offer(rooms.get(key));
                }
            }
        }
        for (int i = 1; i < rooms.size(); i++) {
            if (!canVisitRooms.contains(i)) {
                return false;
            }
        }
        return true;
    }

    public static int search(int[] nums, int target) {
        if (null == nums) {
            return -1;
        }
        int left = 0;
        int right = nums.length - 1;
        int mid = (left + right) / 2;
        while (left <= right) {
            if (target < nums[mid]) {
                right = mid - 1;
                mid = (left + right) / 2;
            } else if (target > nums[mid]) {
                left = mid + 1;
                mid = (left + right) / 2;
            } else {
                return mid;
            }
        }
        return -1;
    }

    public static int mySqrt(int x) {
        if (x == 0 || x == 1) {
            return x;
        }
        int left = 0;
        int right = x;
        int mid = (left + right) / 2;
        while (left <= right) {
            long tmp = (long) Math.pow(mid, 2);
            if (tmp < x) {
                if ((long) Math.pow(mid + 1, 2) > x) {
                    return mid;
                }
                left = mid + 1;
                mid = (right + left) / 2;
            } else if (tmp > x) {
                if ((long) Math.pow(mid - 1, 2) < x) {
                    return mid - 1;
                }
                right = mid - 1;
                mid = (right + left) / 2;
            } else {
                return mid;
            }
        }
        return left;
    }

    /**
     * 你调用一个预先定义好的接口 guess(int num)，它会返回 3 个可能的结果（-1，1 或 0）
     *
     * @param n
     */
    public static int guessNumber(int n) {
        int left = 1;
        int right = n;
        int mid = (n + 1) >> 1;
        while (left <= right) {
            int guessRst = guess(mid);
            if (guessRst == 0) { //猜中
                return mid;
            } else if (guessRst == -1) {//数字较小
                left = mid + 1;
                mid = (left + right) >>> 1;
            } else if (guessRst == 1) { //数字较大
                right = mid - 1;
                mid = (left + right) >>> 1;
            }
        }
        // throw new Exception("Your GuessNumber Is Nothing!");
        return 0;
    }

    private static int guess(long mid) {
        if (mid == 1702766719) {
            return 0;
        }
        return mid > 1702766719 ? 1 : -1;
    }

    public static int search1(int[] nums, int target) {
        if (isEmpty(nums)) {
            return -1;
        }
        int left = 0, right = nums.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] == target) {
                return mid;
            }
            if (nums[left] <= nums[mid]) { // 左边有序
                if (nums[mid] > target && nums[left] <= target) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            } else { // 右边有序
                if (nums[mid] < target && nums[right] >= target) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
        }
        return -1;
    }

    public static boolean isEmpty(int[] nums) {
        return null == nums ? true : nums.length == 0 ? true : false;
    }

    public static int binarySearch(int[] nums, int target, int start, int end) {
        if (start > end) {
            return -1;
        }
        int middle = start + (end - start) / 2;
        if (nums[middle] == target) {
            return middle;
        }
        if (nums[start] <= target && nums[middle] >= target) { //目标值在左边
            return binarySearch(nums, target, start, middle - 1);
        } else {
            return binarySearch(nums, target, middle + 1, end);
        }
    }

    public static void main(String[] args) {
/*        addAtHead(4);
        System.out.println(get(1));
        addAtHead(1);
        addAtHead(5);
        deleteAtIndex(3);
        addAtHead(7);
        System.out.println(get(3));
        System.out.println(get(3));
        System.out.println(get(3));
        addAtHead(1);
        deleteAtIndex(4);
        print(head);
        addAtHead(3);
        addAtHead(2);
        addAtHead(5);
        addAtTail(5);
        deleteAtIndex(6);

        Leetcodes m = new Leetcodes();
        ListNode list = m.new ListNode(1);
        list.next = m.new ListNode(2);
        list.next.next = m.new ListNode(3);

        ListNode list2 = new ListNode(1);
        list2.next = new ListNode(2);
        list2.next.next = new ListNode(3);
        list2.next.next.next = new ListNode(4);
        list2.next.next.next.next = new ListNode(5);
        list2.next.next.next.next.next = new ListNode(1);
        print(rotateRight(list2, 2));
        list.next.next.next.next.next.next = m.new ListNode(7);
        print(mergeTwoLists(list, list2));

        ListNode list = new ListNode(1);
        list.next = new ListNode(8);
        list.next.next = new ListNode(3);

        ListNode list2 = new ListNode(0);
        list2.next = new ListNode(6);
        list2.next.next = new ListNode(4);
        print(addTwoNumbers(list, list2));

        Node a1 = new Node();
        Node a11 = new Node();
        Node a111 = new Node();
        Node a2 = new Node();
        Node a22 = new Node();
        Node a222 = new Node();

        Node a3 = new Node();
        Node a33 = new Node();
        Node a333 = new Node();
        a1.val = 1;
        a11.val = 11;
        a111.val = 111;
        a2.val = 2;
        a22.val = 22;
        a222.val = 222;
        a3.val = 3;
        a33.val = 33;
        a333.val = 333;


        a1.next = a11;
        a11.next = a111;
        a111.prev = a11;
        a11.prev = a1;

        a11.child = a2;

        a2.next = a22;
        a22.next = a222;
        a222.prev = a22;
        a22.prev = a2;

        a3.next = a33;
        a33.next = a333;
        a333.prev = a33;
        a33.prev = a3;

        a22.child = a3;

        print(flatten(a1));

        int[] request = {2,2,1};
        singleNumber(request);
        int[] request = {1,2,2,1};
        int[] request2 = {2,2};
        intersection(request, request2); */
//        System.out.println(firstUniqChar("loveleetcode"));
//        String[] r = {"eat","tea","tan","ate","nat","bat"};
//        groupAnagrams(r);

//        char[][] r = {{'5','3','.','.','7','.','.','.','.'},{'6','.','.','1','9','5','.','.','.'},{'.','9','8','.','.','.','.','6','.'},{'8','.','.','.','6','.','.','.','3'},{'4','.','.','8','.','3','.','.','1'},{'7','.','.','.','2','.','.','.','6'},{'.','6','.','.','.','.','2','8','.'},{'.','.','.','4','1','9','.','.','5'},{'.','.','.','.','8','.','.','7','9'}};
//        isValidSudoku(r);
//        Leetcodes l = new Leetcodes();
//        TreeNode root1 = l.new TreeNode(1);
//        TreeNode root2 = l.new TreeNode(2);
//        TreeNode root3 = l.new TreeNode(3);
//        TreeNode root4 = l.new TreeNode(4);
//        TreeNode root5 = l.new TreeNode(2);
//        TreeNode root6 = l.new TreeNode(4);
//        TreeNode root7 = l.new TreeNode(4);
//
//        root1.left = root2;
//        root2.left = root4;
//        root1.right = root3;
//        root3.right = root6;
//        root3.left = root5;
//        root5.left = root7;
//        findDuplicateSubtrees(root1);
//          int[] nums = {-1,-1};
//          topKFrequent(nums, 1);

//        MyCircularQueue m = new MyCircularQueue(3);
//        m.enQueue(1);
//        m.enQueue(2);
//        m.enQueue(3);
//        m.enQueue(4);
//        m.Rear();
//        m.isFull();
//        m.deQueue();
//        m.enQueue(4);
//        m.Rear();

//        char[] a0 = {'1','1','1','1','1','0','1','1','1','1','1','1','1','1','1','0','1','0','1','1'
//        };
//        char[] a1 = {'0','1','1','1','1','1','1','1','1','1','1','1','1','0','1','1','1','1','1','0'
//        };
//        char[] a2 = {'1','0','1','1','1','0','0','1','1','0','1','1','1','1','1','1','1','1','1','1'
//        };
//        char[] a3 = {'1','1','1','1','0','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1'
//        };
//        char[] a4 = {'1','0','0','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1'
//        };
//        char[] a5 = {'1','0','1','1','1','1','1','1','0','1','1','1','0','1','1','1','0','1','1','1'
//        };
//        char[] a6 = {'0','1','1','1','1','1','1','1','1','1','1','1','0','1','1','0','1','1','1','1'
//        };
//        char[] a7 = {'1','1','1','1','1','1','1','1','1','1','1','1','0','1','1','1','1','0','1','1'
//        };
//        char[] a8 = {'1','1','1','1','1','1','1','1','1','1','0','1','1','1','1','1','1','1','1','1'
//        };
//        char[] a9 = {'1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1'
//        };
//        char[] a10 = {'0','1','1','1','1','1','1','1','0','1','1','1','1','1','1','1','1','1','1','1'
//        };
//        char[] a11 = {'1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1'
//        };
//        char[] a12 = {'1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1'
//        };
//        char[] a13 = {'1','1','1','1','1','0','1','1','1','1','1','1','1','0','1','1','1','1','1','1'
//        };
//        char[] a14 = {'1','0','1','1','1','1','1','0','1','1','1','0','1','1','1','1','0','1','1','1'
//        };
//        char[] a15 = {'1','1','1','1','1','1','1','1','1','1','1','1','0','1','1','1','1','1','1','0'
//        };
//        char[] a16 = {'1','1','1','1','1','1','1','1','1','1','1','1','1','0','1','1','1','1','0','0'
//        };
//        char[] a17 = {'1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1'
//        };
//        char[] a18 = {'1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1'
//        };
//        char[] a19 = {'1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1'
//        };
//
//        char[][] grad = new char[20][20];
//        grad[0]= a0;
//        grad[1]= a1;
//        grad[2]= a2;
//        grad[3]= a3;
//        grad[4]= a4;
//        grad[5]= a5;
//        grad[6]= a6;
//        grad[7]= a7;
//        grad[8]= a8;
//        grad[9]= a9;
//        grad[10]= a10;
//        grad[11]= a11;
//        grad[12]= a12;
//        grad[13]= a13;
//        grad[14]= a14;
//        grad[15]= a15;
//        grad[16]= a16;
//        grad[17]= a17;
//        grad[18]= a18;
//        grad[19]= a19;
//
//
//        System.out.println(numIslandsDFS(grad));

//        String[] req = {"0000"};
//        String target = "8888";
//        System.out.println(openLock(req, target));

//        List<Integer> a = new LinkedList<>();
//        a.add(1);
//        a.add(0);
//        a.add(8);
//        a.add(1);
//        System.out.println(a.indexOf(1));
//        System.out.println(a.lastIndexOf(1) == a.indexOf(1));

//        System.out.println(isValid("()[]{}"));
//        String[] tokens = {"4","13","5","/","+"};
//        int i = evalRPN(tokens);

//        List<List<Integer>> req = new ArrayList<>();
//        List<Integer> r0 = new ArrayList<>();
//        r0.add(1);
//        List<Integer> r1 = new ArrayList<>();
//        r1.add(2);
//        List<Integer> r2 = new ArrayList<>();
//        r2.add(3);
//
//        req.add(r0);
//        req.add(r1);
//        req.add(r2);
//        req.add(new ArrayList<>());
//
//        canVisitAllRooms(req);

//        int[] req = {-1,0,3,5,9,12};
//        search(req, 9);
//        System.out.println(mySqrt(2147395599));
//        System.out.println(guessNumber(2126753390));
        int[] nums1 = {3, 9, 20, 15, 7};
        int[] nums2 = {9, 3, 15, 20, 7};
//        System.out.println(findDuplicate(nums));
//        System.out.println(nextGreatestLetter(nums, 'c'));
//        System.out.println(search1(nums, 3));
//        System.out.println(isPerfectSquare(808201));
//        System.out.println(findMin(nums));
//        System.out.println(buildTree(nums1, nums2));
//        List<Integer> a = Arrays.asList(1);
//        System.out.println(a.toString().substring(1, a.toString().length() - 1));
//        System.out.println(a.toString());
//        Stack<TreeNode> stack = new Stack<>();
//        int a = 5;
//        while (a != 9) {
//            a++;
//            if (a == 7) {
//                break;
//            }
//            System.out.println(a);
//        }
//        System.out.println(a);
//        TreeNode root = new TreeNode(3);
//        root.right = new TreeNode(4);
//        root.right.right = new TreeNode(5);
//        root.left.left = new TreeNode(2);
//        root.left.right = new TreeNode(4);
//        deleteNode(root, 3);
        int k = 3;
        int[] arr = {4, 5, 8, 2};
        Leetcodes a = new Leetcodes();
        KthLargest kthLargest = a.new KthLargest(3, arr);
        kthLargest.add(3);   // returns 4
        kthLargest.add(5);   // returns 5
        kthLargest.add(10);  // returns 5
        kthLargest.add(9);   // returns 8
        kthLargest.add(4);   // returns 8
    }

    class KthLargest {

        private class BST {

            private class TreeNode {

                private int val;
                // 结点的count包含自己，所以默认是1
                private int count = 1;
                private TreeNode left;
                private TreeNode right;

                TreeNode(int x) { val = x; }
            }

            private TreeNode root;

            public void add(int val) {
                root = add(root, val);
            }

            private TreeNode add(TreeNode node, int val) {
                if (node == null) {
                    return new TreeNode(val);
                }
                if (node.val > val) {
                    node.left = add(node.left, val);
                } else if (node.val < val) {
                    node.right = add(node.right, val);
                }
                // 元素重复 不添加进树但是count++
                node.count++;
                return node;
            }

            public TreeNode search(int k) {
                return search(root, k);
            }

            private TreeNode search(TreeNode node, int k) {
                if (node == null) {
                    return node;
                }
                int leftNodeCount = node.left != null ? node.left.count : 0;
                int rightNodeCount = node.right != null ? node.right.count : 0;
                int currNodeCount = node.count - leftNodeCount - rightNodeCount;
                if (k > currNodeCount + rightNodeCount ) {
                    // k > 当前结点数加右子树的结点数，则搜索左子树
                    return search(node.left, k - currNodeCount - rightNodeCount);
                } else if (k <= rightNodeCount) {
                    // k <= 右子树的结点数，则搜索右子树
                    return search(node.right, k);
                } else {
                    // k == 当前结点数加右子树的结点数，则找到第k大的结点
                    return node;
                }
            }
        }

        private int k;
        private BST bst = new BST();

        public KthLargest(int k, int[] nums) {
            this.k = k;
            for (int n : nums) {
                bst.add(n);
            }
        }

        public int add(int val) {
            bst.add(val);
            return bst.search(k).val;
        }
    }

    public class KthLargest1 {

        private PriorityQueue<Integer> cache;

        private int capacity = 0;

        public KthLargest1(int k, int[] nums) {
            cache = new PriorityQueue<>(k);
            capacity = k;
            for (int i = 0; i < nums.length; i++) {
                add(nums[i]);
            }
        }

        public int add(int val) {
            if (cache.size() < capacity) {
                cache.add(val);
            } else if (val > cache.peek()) {
                cache.poll(); //移除最小的
                cache.add(val);
            }
            return cache.peek();
        }
    }


    public static TreeNode deleteNode(TreeNode root, int key) {
        if (null == root) {
            return root;
        }
        TreeNode cur = root;
        TreeNode curParent = null;
        while (cur != null) {
            if (cur.val == key) {
                break;
            }
            curParent = cur;
            if (cur.val > key) {
                cur = cur.right;
            } else {
                cur = cur.left;
            }
        }
        if (cur.left == null && cur.right == null) { //目标节点双节点都为空
            if (curParent == null) {//根节点为目标值
                return null;
            } else {
                curParent.left = curParent.left == cur ? null : curParent.left;
                curParent.right = curParent.right == cur ? null : curParent.right;
            }
        } else if (cur.left == null) { //目标节点左节点为空
            if (curParent == null) {//根节点为目标值
                root = root.right;
            } else {
                curParent.left = curParent.left == cur ? cur.right : curParent.left;
                curParent.right = curParent.right == cur ? cur.right : curParent.right;
            }
        } else if (cur.right == null) { //目标节点右节点为空
            if (curParent == null) {//根节点为目标值
                root = root.left;
            } else {
                curParent.left = curParent.left == cur ? cur.left : curParent.left;
                curParent.right = curParent.right == cur ? cur.left : curParent.right;
            }
        } else { //目标双节点都不为空
            TreeNode inOrderSuccessorNode = cur.right; //目标节点中序后继节点
            TreeNode inOrderSuccessorParentNode = cur; //目标节点中序后继节点父节点
            while (inOrderSuccessorNode.left != null) {
                inOrderSuccessorParentNode = inOrderSuccessorNode;
                inOrderSuccessorNode = inOrderSuccessorNode.left;
            }

            if (curParent == null) {//根节点为目标值
                if (inOrderSuccessorParentNode == cur) {
                    inOrderSuccessorNode.left = root.left;
                    inOrderSuccessorNode.right = inOrderSuccessorNode.right.right;
                    root = inOrderSuccessorNode;
                } else {
                    inOrderSuccessorNode.left = root.left;
                }
            }
            if (inOrderSuccessorParentNode == null) {
                inOrderSuccessorParentNode.right = null;
            } else {
                inOrderSuccessorParentNode.left = null;
            }
            cur.val = inOrderSuccessorNode.val;
        }
        return root;
    }

    private TreeNode searchBST2(TreeNode root, int key) {
        if (root == null) {
            return root;
        }

        return null;

    }

    public TreeNode insertIntoBST(TreeNode root, int val) {
        if (null == root) {
            return new TreeNode(val);
        }
        TreeNode cur = root;
        while (cur != null) {
            if (val > cur.val && null != cur.right) {
                cur = cur.right;
            } else if (val < cur.val && null != cur.left) {
                cur = cur.left;
            } else {
                break;
            }
        }
        if (val > cur.val && null == cur.right) {
            cur.right = new TreeNode(val);
        } else if (val < cur.val && null == cur.left) {
            cur.left = new TreeNode(val);
        }
        return root;
    }

    public TreeNode searchBST(TreeNode root, int val) {
        if (null == root) {
            return null;
        }
        TreeNode cur = root;
        while (cur != null) {
            if (val == cur.val) {
                return cur;
            } else if (val < cur.val) {
                cur = cur.left;
            } else if (val > cur.val) {
                cur = cur.right;
            }
        }
        return null;
    }

    private List<Integer> data = new LinkedList<>();

    private int idx = 0;

    public void BSTIterator(TreeNode root) {
        if (null == root) {
            return;
        }
        inOrderTra(root);
    }

    public void inOrderTra(TreeNode root) {
        if (null != root.left) {
            inOrderTra(root.left);
        }
        data.add(root.val);
        if (null != root.right) {
            inOrderTra(root.right);
        }
    }

    /**
     * @return the next smallest number
     */
    public int next() {
        return data.get(idx++);
    }

    /**
     * @return whether we have a next smallest number
     */
    public boolean hasNext() {
        return idx < data.size();
    }

    public boolean isValidBST(TreeNode root) {
        if (null == root) {
            return true;
        }
        List<Integer> bstList = new LinkedList<>();
        inOrderTra(root, bstList);
        for (int i = 0; i < bstList.size() - 1; i++) {
            if (bstList.get(i) > bstList.get(i + 1)) {
                return false;
            }
        }
        return true;
    }

    public void inOrderTra(TreeNode cur, List<Integer> bstList) {
        if (null != cur) {
            inOrderTra(cur.left, bstList);
            bstList.add(cur.val);
            inOrderTra(cur.right, bstList);
        }
    }

    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        if (root == null) {
            return "";
        }
        Queue<TreeNode> treeNodeQueue = new LinkedList<>();
        List<Integer> result = new LinkedList<>();
        treeNodeQueue.offer(root);
        while (!treeNodeQueue.isEmpty()) {
            TreeNode curNode = treeNodeQueue.poll();
            if (null != curNode) {
                result.add(curNode.val);
                treeNodeQueue.offer(curNode.left);
                treeNodeQueue.offer(curNode.right);
            } else {
                result.add(null);
            }
        }
        return result.toString();
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        if (data == null || "".equals(data)) {
            return null;
        }
        String[] splitData = data.substring(1, data.length() - 1).split("\\,"); // [1,2,null,2]
        Queue<TreeNode> treeNodeQueue = new LinkedList<>();
        TreeNode root = new TreeNode(Integer.parseInt(splitData[0]));
        treeNodeQueue.offer(root);
        int curIdx = 1;
        while (!treeNodeQueue.isEmpty()) {
            TreeNode curNode = treeNodeQueue.poll();
            if (!"null".equals(splitData[curIdx].trim())) {
                curNode.left = new TreeNode(Integer.parseInt(splitData[curIdx].trim()));
                treeNodeQueue.offer(curNode.left);
            }
            curIdx++; //指向右节点
            if (!"null".equals(splitData[curIdx].trim())) {
                curNode.right = new TreeNode(Integer.parseInt(splitData[curIdx].trim()));
                treeNodeQueue.offer(curNode.right);
            }
            curIdx++;
        }
        return root;
    }

    public Node connect(Node root) {
        if (null == root) {
            return root;
        }
        Queue<Node> nodeQueue = new LinkedList<>();
        nodeQueue.add(root);
        while (!nodeQueue.isEmpty()) {
            int nodeSize = nodeQueue.size();
            for (int i = 0; i < nodeSize; i++) {
                Node pollNode = nodeQueue.poll();
                if (i < nodeSize - 1) { //最右边节点不设置，所以nodeSize - 1
                    pollNode.next = nodeQueue.peek();
                }
                if (null != pollNode.left) {
                    nodeQueue.add(pollNode.left);
                }
                if (null != pollNode.right) {
                    nodeQueue.add(pollNode.right);
                }
            }
        }
        return root;
    }

    public Node connect2(Node root) {
        Queue<Node> nodeQueue = new LinkedList<>();
        List<List<Node>> levelNodeList = new ArrayList<>();
        nodeQueue.add(root);
        int level = 0;
        while (!nodeQueue.isEmpty()) {
            levelNodeList.add(new ArrayList<>());
            int levelItemNum = nodeQueue.size();
            for (int i = 0; i < levelItemNum; i++) {
                Node curLevelNode = nodeQueue.poll();
                levelNodeList.get(level).add(curLevelNode);
                if (null != curLevelNode.left) {
                    nodeQueue.add(curLevelNode.left);
                }
                if (null != curLevelNode.right) {
                    nodeQueue.add(curLevelNode.right);
                }
            }
            level++;
        }
        for (int i = 0; i < levelNodeList.size(); i++) {
            List<Node> nodes = levelNodeList.get(i);
            for (int j = 0; j < nodes.size(); j++) {
                if (j + 1 < nodes.size()) {
                    nodes.get(j).next = nodes.get(j + 1);
                } else {
                    nodes.get(j).next = null;
                }
            }
        }
        return root;
    }

    private static HashMap<Integer, Integer> inOrderMap = new HashMap<>();
    private static int[] preOrder;

    public static TreeNode buildTree(int[] preorder, int[] inorder) {
        for (int i = 0; i < preorder.length; i++) {
            inOrderMap.put(inorder[i], i);
        }
        preOrder = preorder;
        return buildTreeByIdx(0, inorder.length - 1, 0, preorder.length - 1);
    }

    private static TreeNode buildTreeByIdx(int inOrderLeftIdx, int inOrderRightIdx, int preOrderLeftIdx, int preOrderRightIdx) {
        if (inOrderRightIdx < inOrderLeftIdx || preOrderRightIdx < preOrderLeftIdx) {
            return null;
        }
        int rootNum = preOrder[preOrderLeftIdx];
        int inOrderRootIdx = inOrderMap.get(rootNum);
        TreeNode rootNode = new TreeNode(rootNum);
        rootNode.left = buildTreeByIdx(inOrderLeftIdx, inOrderRootIdx - 1, preOrderLeftIdx + 1, preOrderLeftIdx + inOrderRootIdx);
        rootNode.right = buildTreeByIdx(inOrderRootIdx + 1, inOrderRightIdx, preOrderLeftIdx + inOrderRootIdx - inOrderLeftIdx + 1, preOrderRightIdx);
        return rootNode;
    }

//    private static HashMap<Integer, Integer> inOrderCache = new HashMap<>();
//
//    private static int[] postOrder;
//
//    public static TreeNode buildTree(int[] inorder, int[] postorder) {
//        for (int i = 0; i < inorder.length; i++) {
//            inOrderCache.put(inorder[i], i);
//        }
//        postOrder = postorder;
//        return buildTreeByIndex(0, inorder.length - 1, 0, postorder.length - 1);
//    }
//
//    public static TreeNode buildTreeByIndex(int inOrderLeftIdx, int inOrderRightIdx, int postOrderLeftIdx, int postOrderRightIdx) {
//        if (inOrderRightIdx < inOrderLeftIdx || postOrderRightIdx < postOrderLeftIdx) {
//            return null;
//        }
//        int root = postOrder[postOrderRightIdx];
//        int inOrderRootIdx = inOrderCache.get(root);
//
//        TreeNode rootNode = new TreeNode(root);
//        rootNode.left = buildTreeByIndex(inOrderLeftIdx, inOrderRootIdx - 1, postOrderLeftIdx, postOrderLeftIdx + inOrderRootIdx - 1 - inOrderLeftIdx);
//        rootNode.right = buildTreeByIndex(inOrderRootIdx + 1, inOrderRightIdx, postOrderLeftIdx + inOrderRootIdx - inOrderLeftIdx, postOrderRightIdx - 1);
//        return rootNode;
//    }

    private int targetSum;

    public boolean hasPathSum(TreeNode root, int sum) {
        if (null == root) {
            return false;
        }
        targetSum = sum;
        return hasSubPathSum(root, 0);
    }

    private boolean hasSubPathSum(TreeNode root, int curSum) {
        if (null == root) {
            return false;
        }
        if (null == root.left && null == root.right) {
            return root.val + curSum == targetSum;
        }
        if (null == root.left) {
            return hasSubPathSum(root.right, curSum + root.val);
        }
        if (null == root.right) {
            return hasSubPathSum(root.left, curSum + root.val);
        }
        return false;
    }

    public boolean isSymmetric(TreeNode root) {
        if (null == root) {
            return true;
        }
        return isSymmetricTree2(root);
    }

    private boolean isSymmetricTree2(TreeNode root) {
        Queue<TreeNode> treeNodeQueue = new LinkedList<>();
        treeNodeQueue.add(root);
        treeNodeQueue.add(root);
        while (!treeNodeQueue.isEmpty()) {
            TreeNode leftTreeNode = treeNodeQueue.poll();
            TreeNode rightTreeNode = treeNodeQueue.poll();
            if (leftTreeNode == null && rightTreeNode == null) continue;
            if (leftTreeNode == null || rightTreeNode == null) return false;
            if (leftTreeNode.val != rightTreeNode.val) return false;
            treeNodeQueue.add(leftTreeNode.left);
            treeNodeQueue.add(rightTreeNode.right);
            treeNodeQueue.add(leftTreeNode.right);
            treeNodeQueue.add(rightTreeNode.left);
        }
        return true;
    }

    public boolean isSymmetricTree(TreeNode left, TreeNode right) {
        if (null == left && null == right) {
            return true;
        }
        if (null != left && null != right && Objects.equals(left.val, right.val)) {
            boolean isSymmetricLeftTree = isSymmetricTree(left.left, right.right);
            boolean isSymmetricRightTree = isSymmetricTree(left.right, right.left);
            return isSymmetricLeftTree && isSymmetricRightTree;
        }
        return false;
    }

    private int maxDepth;

    public int maxDepth(TreeNode root) {
        findMaxDepth(root, maxDepth);
        return maxDepth;
    }

    private void findMaxDepth(TreeNode root, int cur) {
        if (root == null) {
            return;
        }
        if (null == root.left && null == root.right) {
            maxDepth = Math.max(cur, maxDepth);
        }
        findMaxDepth(root.left, cur + 1);
        findMaxDepth(root.right, cur + 1);
    }

    private List<List<Integer>> result = new ArrayList<>();

    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (null == root) {
            return result;
        }
        Queue<TreeNode> treeNodeQueue = new LinkedList<TreeNode>();
        treeNodeQueue.add(root);
        int level = 0;
        while (!treeNodeQueue.isEmpty()) {
            result.add(new ArrayList<>());
            int levelItemNum = treeNodeQueue.size();
            for (int i = 0; i < levelItemNum; i++) {
                TreeNode levelItem = treeNodeQueue.poll();
                result.get(level).add(levelItem.val);
                if (null != levelItem.left) {
                    treeNodeQueue.add(levelItem.left);
                }
                if (null != levelItem.right) {
                    treeNodeQueue.add(levelItem.right);
                }
            }
            level++;
        }
        return result;
    }

    private void levelOrderTra(TreeNode root, int level) {
        if (result.size() == level) {
            result.add(new ArrayList<>());
        }
        result.get(level).add(root.val);
        if (null != root.left) {
            levelOrderTra(root.left, level + 1);
        }
        if (null != root.right) {
            levelOrderTra(root.right, level + 1);
        }
    }

    public static int findDuplicate(int[] nums) {
        int low = 0, high = nums.length - 1;
        while (low < high) {
            int mid = low + (high - low + 1) / 2;
            int count = 0;
            for (int num : nums) {
                if (num < mid) {
                    count++;
                }
            }
            if (count >= mid) {
                high = mid - 1;
            } else {
                low = mid;
            }
        }
        return low;
//        int[] numsCopy = Arrays.copyOf(nums, nums.length);
//        Arrays.sort(numsCopy);
//        for (int i = 1; i < numsCopy.length; i++)
//        {
//            if (numsCopy[i - 1] == numsCopy[i]) {
//                return numsCopy[i];
//            }
//        }
//        return -1;
    }

    public static int findMin2(int[] nums) {
        int low = 0, high = nums.length - 1;
        while (low < high) {
            int mid = low + (high - low) / 2;
            if (nums[mid] < nums[high]) {
                high = mid;
            } else if (nums[mid] == nums[high]) {
                high = high - 1;
            } else if (nums[mid] > nums[high]) {
                low = mid + 1;
            }
        }
        return nums[low];
    }

    public static char nextGreatestLetter(char[] letters, char target) {
        int low = 0, high = letters.length - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (letters[mid] < target) {
                low = mid + 1;
            } else if (letters[mid] == target) {
                high = mid + 1;
            } else if (letters[mid] > target) {
                high = mid - 1;
            }
        }
        return letters[low];
    }

    public static boolean isPerfectSquare(int num) {
        if (num == 0 || num == 1) {
            return true;
        }
        long left = 0, right = num - 1;
        while (left <= right) {
            long mid = left + ((right - left) >> 1);
            long midSquare = mid * mid;
            if (midSquare == num) {
                return true;
            } else if (midSquare < num) {
                left = mid + 1;
            } else if (midSquare > num) {
                right = mid - 1;
            }
        }
        return false;
    }

    public static double myPow(double x, int n) {
        long turn = n;
        double positiveX = x;
        if (turn < 0) {
            turn = -turn;
            positiveX = 1 / positiveX;
        }
        return fastPow(positiveX, turn);

//        long turn = n; //暴力循环
//        return forcePow(x, turn);
    }

    private double forcePow(double x, long n) {

        if (x == 0 || x == 1) {
            return x;
        }
        if (n > 0) {
            double result = 1;
            for (long i = 0; i < n; i++) {
                result = result * x;
            }
            return result;
        } else {
            double result = 1;
            for (long i = 0; i < -n; i++) {
                result = result / x;
            }
            return result;
        }
    }

    private static double fastPow(double x, long n) {
        if (n == 0) {
            return 1.0;
        }
        double halfPow = fastPow(x, n >> 1);
        if (n % 2 == 0) {
            return halfPow * halfPow;
        } else {
            return halfPow * halfPow * x;
        }
    }

    public static int findPeakElement2(int[] nums) {
        if (nums.length <= 1) {
            return 0;
        }
        int left = 0, right = nums.length - 1;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] > nums[mid + 1]) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        return left;
    }

    public static int findMin(int[] nums) {
        int low = 0, high = nums.length - 1;
        while (low < high) { // 降序或者旋转数组
            int mid = low + ((high - low) / 2);
            if (nums[low] <= nums[high]) { //升序数组
                return nums[low];
            }
            if (nums[mid] >= nums[low]) { //mid在变化点右边
                low = mid + 1;
            } else { //mid在变化点或者右边
                high = mid;
            }
        }
        return nums[low];
    }

    public static int findPeakElement(int[] nums) {
        if (nums.length == 1) {
            return 0;
        }
        int low = 0, high = nums.length - 1;
        while (low < high) {
            int mid = low + ((high - low) >> 1);
            if (nums[mid] > nums[mid + 1]) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }
        return low;
    }

    public static int firstBadVersion(int n) {
        if (n == 1) {
            return n;
        }
        int low = 1, high = n;
        while (low < high) {
            int mid = low + (high - low + 1) / 2;
            boolean midIsBadVersion = isBadVersion(mid);
            if (midIsBadVersion && !isBadVersion(mid - 1)) {
                return mid;
            }
            if (midIsBadVersion) {
                high = mid;
            } else {
                low = mid;
            }
        }
        return 1;
    }

    private static boolean isBadVersion(int mid) {
        return mid == 2;
    }
}
