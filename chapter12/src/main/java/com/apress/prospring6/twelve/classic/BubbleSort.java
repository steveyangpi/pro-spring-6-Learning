package com.apress.prospring6.twelve.classic;

public final class BubbleSort extends AbstractSort{

    public BubbleSort(int[] arr) {
        super(arr);
        name = "BubbleSort";
    }

    @Override
    public void sort(int[] arr) {
        boolean swapped = true;
        while(swapped){
            swapped = false;
            for(int i=0;i<arr.length - 1;i++){
                if(arr[i] > arr[i+1]){
                    int temp = arr[i];
                    arr[i] = arr[i+1];
                    arr[i+1] = temp;
                    swapped = true;
                }
            }
        }
    }
}
