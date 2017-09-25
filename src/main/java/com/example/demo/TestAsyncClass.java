package com.example.demo;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

public class TestAsyncClass {

    DemoApplication demo = new DemoApplication();
    Document doc = demo.GetConnection();
    Elements tableRow = demo.getTableRow();
    Element link = tableRow.select("tr").first();
    String trId = link.attr("id");

    @Async
    public Future<Boolean> sendResponse() throws InterruptedException {
        System.out.println("Sending....");
        Thread.sleep(3000);
        System.out.println("Sending response completed!");
        return new AsyncResult<Boolean>(true);
    }

    @Async
    public Future<List<String>> getUpdate(){
        List<String> listArtikel = new ArrayList<>();

        return new AsyncResult<>(listArtikel);
    }
}
