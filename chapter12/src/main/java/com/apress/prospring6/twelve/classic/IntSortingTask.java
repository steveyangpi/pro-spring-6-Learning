package com.apress.prospring6.twelve.classic;

public sealed interface IntSortingTask extends Runnable permits AbstractSort {
    void sort(int[] arr);
}
