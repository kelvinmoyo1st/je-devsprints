package com.jedevsprints.executor;

import java.util.concurrent.*;
import java.util.List;
import java.util.ArrayList;

public class ExecutorDemo{

    static String simulateWork(int taskId) throws InterruptedException{
        Thread.sleep(200);
        return "Task-"+taskId+"done by"+Thread.currentThread().getName();
    }

    public static void main(String[] args) throws InterruptedException,ExecutionException{

        ExecutorService pool = Executors.newFixedThreadPool(3);
        List<Future<String>> futures = new ArrayList<>();
        for(int i=1;i<=8;i++){
            final int id=i;
            futures.add(pool.submit(()->simulateWork(id)));
        }
        System.out.println("=== Results ===");
        for(Future<String> f:futures){
            System.out.println(f.get());
        }
        pool.shutdown();
        boolean finished = pool.awaitTermination(5, TimeUnit.SECONDS);
        System.out.println("Pool terminated cleanly"+finished);
        System.out.println("\n=== Schewduled Task ===");
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.schedule(()->System.out.println("DelaYEd task fired on:"+Thread.currentThread().getName()),1,TimeUnit.SECONDS);
        scheduler.shutdown();
        scheduler.awaitTermination(3,TimeUnit.SECONDS);


    }
}
