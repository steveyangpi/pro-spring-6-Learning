package com.apress.prospring6.twelve.classic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract sealed class AbstractSort implements IntSortingTask permits BubbleSort, HeapSort, InsertionSort, MergeSort, QuickSort, ShellSort {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractSort.class);

    protected String name;

    protected final int[] arr;

    public AbstractSort(int[] arr) {
        this.arr = arr;
        System.arraycopy(arr, 0, this.arr, 0, arr.length);
    }

    public abstract void sort(int[] arr);

    @Override
    public void run() {
        long startTime = System.currentTimeMillis();
        sort(arr);
        long endTime = System.currentTimeMillis();
        float seconds = ((float) (endTime - startTime)) / 1000;
        LOGGER.info("{} Sort Time: {} seconds ", name, seconds);
    }
}
