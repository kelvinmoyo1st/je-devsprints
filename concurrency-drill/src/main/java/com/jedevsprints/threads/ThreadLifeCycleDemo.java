package com.jedevsprints.threads;

public class ThreadLifeCycleDemo{

    static class WorkerThread extends Thread{
        private final String taskName;

        WorkerThread(String taskName){
            this.taskName = taskName;
        }
        @Override
        public void run(){
            System.out.println(taskName +" started | thread: "+ Thread.currentThread().getName());
            try{
                Thread.sleep(500);
            }catch (InterruptedException e){
                Thread.currentThread().interrupt();
                System.out.println(taskName+" Interrupted");
            }
            System.out.println(taskName+" finished | thread: "+ Thread.currentThread().getName());
        
        }
    
    
    }
    static class RunnableWorker implements Runnable{
        private final String taskName;

        RunnableWorker(String taskName){
            this.taskName = taskName;
        }
        @Override
        public void run(){
            
            System.out.println(taskName+" started | thread: "+Thread.currentThread().getName());
            System.out.println(taskName+" finished | thread: "+Thread.currentThread().getName());

        }
    }

    public static void main(String[] args) throws InterruptedException{
        System.out.println("---Thread sub Class---");
        WorkerThread t1 = new WorkerThread("Task-A");
        WorkerThread t2 = new WorkerThread("Task-B");
        t1.start();
        t2.start();
        t1.join();
        t2.join();


        System.out.println("---Runnable implementation---");
        Thread r1 = new Thread(new RunnableWorker("Task-C"),"runnable-thread-1");
        Thread r2 = new Thread(new RunnableWorker("Task-D"),"runnable-thread-2");
        r1.start();
        r2.start();
        r1.join();
        r2.join();       


        System.out.println("\nMain Thread done"); 

    }
}