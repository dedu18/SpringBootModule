package com.dedu.datastructmodule;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class Leetcodes {
    public static void main(String[] args) throws InterruptedException {
        Leetcodes l = new Leetcodes();
        int[] arr = {2,1,2};
        System.out.println(l.findDuplicate(arr));
    }

    public int findDuplicate(int[] nums) {
        // length = n+1
        int length = nums.length;
        // nums为1至n，
        for (int i = 0; i < length; ) {
            // 如果num[i]不在位置上，则交换位置
            if (nums[i] != (i+1)) {
                // 如果位置nums[nums[i]]上的数等于nums[i]，则说明原位置有人了
                if (nums[nums[i] - 1] == nums[i]) {
                    return nums[i];
                }
                if (nums[nums[i]] == nums[i]) {
                    return nums[i];
                }
                int tmp = nums[nums[i]];
                nums[nums[i]] = nums[i];
                nums[i] = tmp;
            } else {
                i++;
            }
        }
        return -1;
    }

    public int findKthLargest(int[] nums, int k) {
        int len = nums.length;
        int left = 0;
        int right = len - 1;

        // 转换一下，第 k 大元素的索引是 len - k
        int target = len - k;

        while (true) {
            int index = partition(nums, left, right);
            if (index == target) {
                return nums[index];
            } else if (index < target) {
                left = index + 1;
            } else {
                right = index - 1;
            }
        }
    }

    /**
     * 在数组 nums 的子区间 [left, right] 执行 partition 操作，返回 nums[left] 排序以后应该在的位置
     * 在遍历过程中保持循环不变量的语义
     * 1、[left + 1, j] < nums[left]
     * 2、(j, i] >= nums[left]
     *
     * @param nums
     * @param left
     * @param right
     * @return
     */
    public int partition(int[] nums, int left, int right) {
        int pivot = nums[left];
        int j = left;
        for (int i = left + 1; i <= right; i++) {
            if (nums[i] < pivot) {
                // 小于 pivot 的元素都被交换到前面
                j++;
                swap(nums, j, i);
            }
        }
        // 在之前遍历的过程中，满足 [left + 1, j] < pivot，并且 (j, i] >= pivot
        swap(nums, j, left);
        // 交换以后 [left, j - 1] < pivot, nums[j] = pivot, [j + 1, right] >= pivot
        return j;
    }

    private void swap(int[] nums, int index1, int index2) {
        int temp = nums[index1];
        nums[index1] = nums[index2];
        nums[index2] = temp;
    }

    public boolean canFinish(int numCourses, int[][] prerequisites) {
        // 如果不需要选修，则肯定能完成学习
        if (numCourses <= 0) {
            return true;
        }
        // 如果选修课列表为空，则肯定能完成学习
        if (null == prerequisites || prerequisites.length == 0) {
            return true;
        }
        // 当前课程入度
        int[] inEdge = new int[numCourses];
        // 当前课程的出边
        HashSet<Integer>[] edge = new HashSet[numCourses];
        for (int i = 0; i < numCourses; i++) {
            edge[i] = new HashSet<>();
        }
        for (int[] tmp : prerequisites) {
            // 当前课程
            int course = tmp[0];
            // 前置课程
            int preCourse = tmp[1];
            // 当前课程的前置课程数+1
            inEdge[course]++;
            // 添加到前置课程的列表中
            edge[preCourse].add(course);
        }
        Queue<Integer> queue = new LinkedList<>();
        for (int j = 0; j < numCourses; j++) {
            // 如果当前课程没有前置课程，则直接修读
            if (inEdge[j] == 0) {
                queue.add(j);
            }
        }
        int result = 0;
        // 处理所有可直接修读的课程，修读完后并将以该课程为前置课程的放入队列
        while (!queue.isEmpty()) {
            Integer course = queue.poll();
            //修读课程数+1
            result++;
            HashSet<Integer> preCourses = edge[course];
            // 处理该课程的所有前置课程
            for (int pre : preCourses) {
                inEdge[pre]--;
                if (inEdge[pre] == 0) {
                    queue.add(pre);
                }
            }
        }
        System.out.println(result);
        return result == numCourses;
    }
}
