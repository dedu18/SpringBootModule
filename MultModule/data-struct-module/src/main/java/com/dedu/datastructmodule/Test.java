package com.dedu.datastructmodule;

import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

public class Test {
    public static int pivotIndex(int[] nums) {
        if (null == nums || nums.length == 0) {
            return -1;
        }
        int sum = 0;
        for (int i : nums) {
            sum += i;
        }
        int suml = 0;
        for (int j=0; j<nums.length; j++) {
            if (j == 0) {
                continue;
            }
            suml += nums[j-1];
            int sumr = sum - suml - nums[j];
            if (sumr == suml) {
                return j;
            }
        }
        return -1;
    }

    public static int[] plusOne(int[] digits) {
        digits[digits.length-1] += 1;
        for (int i=digits.length-1; i>=0; i--) {
            if (i != 0 && digits[i]/10 == 1) {
                digits[i] = 0;
                digits[i - 1] += 1;
            } else if (i == 0 && digits[i]/10 == 1) {
                int[] newDigits = new int[digits.length+1];
                newDigits[0] = 1;
                return newDigits;
            } else {
                break;
            }
        }
        return digits;
    }

    public static void main(String[] args) {
        Executors.newFixedThreadPool(2);
        ReentrantLock r;
        Executors.newWorkStealingPool();
        new Thread().start();
        int[] a = {1,7,3,6,5,9};
        plusOne(a);
    }
}
