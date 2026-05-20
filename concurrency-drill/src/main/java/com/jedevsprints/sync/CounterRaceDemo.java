package com.jedevsprints.sync;
public class CounterRaceDemo{

    static class UnSafeCounter{
        private int count=0;
        public void increment(){count++;}
        public int get(){return count;}
    }
     static class SynchronizedCounter{
        private int count=0;
        public synchronized void increment(){count++;}
        public int get(){return count;}
    }
     static class VolatileCounter{
        private volatile int count=0;
        public  void increment(){count++;}
        public int get(){return count;}
    }
     static void runWith(String label,Runnable incrementFn,java.util.function.IntSupplier getFn) throws InterruptedException{
        int THREADS =10,OPS_PER_THREAD=100000;
        Thread[] threads = new Thread[THREADS];
        for(int i=0;i<THREADS;i++){
            threads[i]=new Thread(()->{
                for(int j=0;j<OPS_PER_THREAD;j++)incrementFn.run();
            });
        }
        for(Thread t:threads)t.start();
        for(Thread t:threads)t.join();
        System.out.printf("%-25s expected = %d actual = %d %s%n",label,THREADS*OPS_PER_THREAD,getFn.getAsInt(),getFn.getAsInt()==THREADS*OPS_PER_THREAD ?  "✓" : "✗ RACE!");

    }
        public static void main(String[] args) throws InterruptedException {
        UnSafeCounter unsafe = new UnSafeCounter();
        SynchronizedCounter safe = new SynchronizedCounter();
        VolatileCounter vol = new VolatileCounter();

        runWith("UnsafeCounter",       unsafe::increment, unsafe::get);
        runWith("SynchronizedCounter", safe::increment,   safe::get);
        runWith("VolatileCounter",     vol::increment,    vol::get);
    }
}

    
