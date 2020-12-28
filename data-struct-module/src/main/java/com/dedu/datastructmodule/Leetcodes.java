package com.dedu.datastructmodule;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

public class Leetcodes {
    public static void main(String[] args) throws InterruptedException {
        Leetcodes l = new Leetcodes();
        l.isValid("{[]}");
    }

    public boolean isValid(String s) {
        char[] chars = s.toCharArray();
        StringBuilder sb = new StringBuilder();
        int idx = -1;
        Deque<Character> queue = new LinkedList<>();
        for (char c : chars) {
            if (c == '(' || c == '{' || c == '[') {
                queue.offer(c);
            } else if (c == ')') {
                if (queue.peekLast() == '(') {
                    queue.poll();
                } else {
                    queue.offer(c);
                }
            } else if (c == '}') {
                if (queue.peekLast() == '{') {
                    queue.poll();
                } else {
                    queue.offer(c);
                }
            } else if (c == ']') {
                if (!queue.isEmpty() && queue.peekLast() == '[') {
                    queue.pollLast();
                } else {
                    queue.offer(c);
                }
            }
        }
        return queue.isEmpty();
    }

    public String convert(String s, int numRows) {
        if (numRows < 2) {
            return s;
        }
        Queue<Character>[] cache = new Queue[numRows];
        for (int i = 0; i < numRows; i++) {
            cache[i] = new LinkedList<>();
        }
        int idx = 0;
        int length = 0;
        boolean forward = true;
        while (length < s.length()) {
            Character c = s.charAt(length);
            if (idx == numRows) {
                idx -= 2;
                forward = false;
            }
            if (idx == -1) {
                idx += 2;
                forward = true;
            }
            cache[idx].offer(c);
            if (forward) {
                idx++;
            } else {
                idx--;
            }
            length++;
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < numRows; i++) {
            while (!cache[i].isEmpty()) {
                sb.append(cache[i].poll());
            }
        }
        return sb.toString();
    }

    public int lengthOfLongestSubstring(String s) {
        if (null == s || s.length() == 0) {
            return 0;
        }
        System.out.println();
        int result = 1;
        Queue<Character> queue = new LinkedList<>();
        for (int i = 0; i < s.length(); i++) {
            while (queue.contains(s.charAt(i))) {
                queue.poll();
            }
            queue.offer(s.charAt(i));
            result = Math.max(result, queue.size());
        }
        return result;
    }
}
