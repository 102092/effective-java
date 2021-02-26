package com.effective.java.ch11.item81;

import org.springframework.scheduling.concurrent.ConcurrentTaskExecutor;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;

/**
 * @author han
 */
public class Foo {

    public static void main(String[] args) throws InterruptedException {
        time(new ConcurrentTaskExecutor(), 3, () -> {
            try {
                Thread.sleep(1000);
                System.out.println("hello!");
            } catch (InterruptedException e) {
            }
        });
    }

    public static long time(Executor executor, int concurrency, Runnable action) throws InterruptedException {
        CountDownLatch ready = new CountDownLatch(concurrency);
        CountDownLatch start = new CountDownLatch(1);
        CountDownLatch done = new CountDownLatch(concurrency);

        for (int i = 0; i < concurrency; i++) {
            executor.execute(() -> {
                ready.countDown();
                try {
                    start.await();
                    action.run();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    done.countDown();
                }
            });
        }
        ready.await();
        long startNanos = System.nanoTime();
        start.countDown();
        done.await();
        return System.nanoTime() - startNanos;
    }
}
