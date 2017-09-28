package com.example.demo.Service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;

@Service
public class AsyncServices {

    @Async
    public Future<Integer> process() throws InterruptedException {
        int x = 0;
        while (x!=10){
            x++;
            System.out.println("Nilai x : " + x);
            Thread.sleep(1000);
        }
        // Sleep 3s for simulating the processing
//        Thread.sleep(3000);
//        String processInfo = String.format("Processing is Done with Thread id= %d", Thread.currentThread().getId());
        return new AsyncResult<>(x);
    }
}
