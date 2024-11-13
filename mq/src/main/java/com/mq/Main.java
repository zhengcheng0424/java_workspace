package com.mq;

/**
 * @author zc at 2024.11.07 16:07
 */

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

@Slf4j
public class Main {
    public static void main(String[] args) throws InterruptedException {
        // 创建一个 CompletableFuture 并异步执行一个任务
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            try {
                System.out.println("Start: " + LocalDateTime.now());
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                log.error("main stream aborted: {}", e.getMessage(), e);
            }
            System.out.println("End: " + LocalDateTime.now());
            return "Async result";
        });


        System.out.println("End program");
        // 为了让主线程不立即退出，等待异步任务完成
        Thread.sleep(3000);
    }
}
