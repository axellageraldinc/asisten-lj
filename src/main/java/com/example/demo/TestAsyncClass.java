package com.example.demo;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;

@Service
public class TestAsyncClass {

    @Async
    public Future<Boolean> sendResponse() throws InterruptedException {
        System.out.println("Sending....");
        Thread.sleep(3000);
        System.out.println("Sending response completed!");
        return new AsyncResult<Boolean>(true);
    }
}
