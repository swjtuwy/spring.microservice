package com.alg.study;

public class QuickSort2 {

    public static int partition(int[] array, int low, int hi) {
        //固定切分方式
        int key = array[low];
        while (low < hi) {
            while (array[hi] >= key && hi > low) { //从后半部分扫描
                hi--;
            }
            array[low] = array[hi];
            while (array[low] <= key && hi > low) { //前半部分扫描
                low++;
            }
            array[hi] = array[low];
        }
        array[hi] = key;
        return hi;
    }

    public static void sort(int[] array, int low, int hi) {
        if (low >= hi) {
            return;
        }
        int index = partition(array, low, hi);
        sort(array, low, index - 1);
        sort(array, index + 1, hi);
    }
}
