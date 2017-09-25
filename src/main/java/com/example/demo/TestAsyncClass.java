package com.example.demo;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.Future;

@Component
public class TestAsyncClass {

    @Async
    public Future<Boolean> sendResponse() throws InterruptedException {
        System.out.println("Sending....");
        Thread.sleep(3000);
        System.out.println("Sending response completed!");
        return new AsyncResult<Boolean>(true);
    }
}
