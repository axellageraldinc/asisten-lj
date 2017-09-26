package com.example.demo;

import com.linecorp.bot.model.event.FollowEvent;
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
    String firstTrId = demo.firstId();

    @Async
    public Future<String> sendResponse() throws InterruptedException {
        String newArticle="";
        newArticle = "PENGUMUMAN BARU!!!\n\n" + demo.GetTitle() + "\n" + demo.GetDate() + "\n" +
                        demo.GetCategory() + "\n" + demo.GetDescription();
            Thread.sleep(5000);
        return new AsyncResult<>(newArticle);
    }

    @Async
    public Future<String> followEvent() throws InterruptedException{
        String userId="";
        int x=0;
        while(x!=1){
            FollowEvent followEvent = null;
            userId = followEvent.getSource().getUserId();
        }
        return new AsyncResult<>(userId);
    }

    @Async
    public Future<String> getUpdate() throws InterruptedException {
        String newArticle="";
        String firstTrIdAsync;
        int x=0;
        while (x!=1){
            firstTrIdAsync = demo.firstId();
            //Berarti ada update
            if(firstTrIdAsync!=firstTrId){
                newArticle = "PENGUMUMAN BARU!!!\n\n" + demo.GetTitle() + "\n" + demo.GetDate() + "\n" +
                        demo.GetCategory() + "\n" + demo.GetDescription();
                firstTrId = demo.firstId();
            }
            Thread.sleep(5000);
        }
        return new AsyncResult<>(newArticle);
    }
}
