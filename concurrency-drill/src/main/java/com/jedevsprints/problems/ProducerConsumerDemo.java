package com.jedevsprints.problems;

import java.util.concurrent.*;

public class ProducerConsumerDemo {

    static class Producer implements Runnable {
        private final BlockingQueue<Integer> queue;
        private final int itemCount;

        Producer(BlockingQueue<Integer> queue, int itemCount) {
            this.queue = queue;
            this.itemCount = itemCount;
        }

        @Override
        public void run() {
            try {
                for (int i = 1; i <= itemCount; i++) {
                    queue.put(i); // blocks if queue is full
                    System.out.println("Produced: " + i + "  | queue size: " + queue.size());
                    Thread.sleep(100);
                }
                queue.put(-1); // poison pill — signals consumer to stop
                System.out.println("Producer done, sent poison pill.");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    static class Consumer implements Runnable {
        private final BlockingQueue<Integer> queue;

        Consumer(BlockingQueue<Integer> queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    int value = queue.take(); // blocks if queue is empty
                    if (value == -1) {
                        System.out.println("Consumer received poison pill. Stopping.");
                        break;
                    }
                    System.out.println("  Consumed: " + value);
                    Thread.sleep(250); // consumer is slower than producer
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(5); // capacity = 5

        Thread producer = new Thread(new Producer(queue, 10), "producer");
        Thread consumer = new Thread(new Consumer(queue ), "consumer");

        consumer.start();
        producer.start();

        producer.join();
        consumer.join();
        System.out.println("\nAll done.");
    }
}
