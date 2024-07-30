package com.apress.prospring6.twelve.classic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ClassicDemo {
//    private static final Logger LOGGER = LoggerFactory.getLogger(ClassicDemo.class);

    public static void main(String[] args) {
        int[] arr = new Random().ints(100_000, 0, 500_000).toArray();
        //LOGGER.info("Starting Array:{} "

        var algsMonitor = new ThreadPoolMonitor();
        var monitor = new Thread(algsMonitor);

        var executor = new ThreadPoolExecutor(2, 4, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());
        algsMonitor.setExecutor(executor);

        List.of(new BubbleSort(arr),
                        new InsertionSort(arr),
                        new MergeSort(arr),
                        new QuickSort(arr),
                        new ShellSort(arr))
                .forEach(executor::execute);
        monitor.start();
        executor.shutdown();
        try {
            executor.awaitTermination(30,TimeUnit.MINUTES);
        }catch (InterruptedException e){
            throw new RuntimeException(e);
        }
    }
}
