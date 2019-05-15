package com.koalacute.micro.koala.utils.util;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadUtils {
    /**
     * 线程池
     */
    private static final ExecutorService es = new ThreadPoolExecutor(10, 100, 1000, TimeUnit.SECONDS,
                                                new ArrayBlockingQueue<Runnable>(1000),
                                                new ThreadPoolExecutor.CallerRunsPolicy());

    /**
     * 异步执行
     * 
     * @param runnable
     */
    public static void async(Runnable runnable) {
        es.execute(runnable);
    }


    public static void sleepSilently(long millis){
        try {
            Thread.sleep(millis);
        }catch (Exception e){
            //do nothing
        }
    }
}
