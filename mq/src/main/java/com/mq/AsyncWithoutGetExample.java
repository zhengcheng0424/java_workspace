package com.mq;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

/**
 * @author zc at 2024.11.07 16:20
 */
public class AsyncWithoutGetExample {
    public static void main(String[] args) {
        CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> {
            // 异步任务逻辑
            System.out.println("1.Async task is running.");
            try {
                System.out.println("2.Start: " + LocalDateTime.now());
                Thread.sleep(2000);
                System.out.println("3.End .Async task .");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }).thenRun(() -> {
            // 后续操作，在异步任务完成后执行，但不依赖其结果
            System.out.println("4.Subsequent task after async task.");
        });

        // 这里没有调用 get，主线程继续执行其他任务
        System.out.println("5.Main thread continues.");
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
