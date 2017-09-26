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

    GetInfoWebSarjana infoWebSarjana = new GetInfoWebSarjana();
    String firstTrId = infoWebSarjana.firstId();

//    @Async
//    public Future<String> sendResponse() throws InterruptedException {
//        String newArticle="";
//        newArticle = "PENGUMUMAN BARU!!!\n\n" + infoWebSarjana.GetTitle() + "\n" + infoWebSarjana.GetDate() + "\n" +
//                        infoWebSarjana.GetCategory() + "\n" + infoWebSarjana.GetDescription();
//            Thread.sleep(5000);
//        return new AsyncResult<>(newArticle);
//    }

//    @Async
//    public Future<String> followEvent() throws InterruptedException{
//        String userId="";
//        int x=0;
//        while(x!=1){
//            FollowEvent followEvent = null;
//            userId = followEvent.getSource().getUserId();
//        }
//        return new AsyncResult<>(userId);
//    }

    @Async
    public Future<String> getUpdate() throws InterruptedException {
        String newArticle="";
        String firstTrIdAsync;
        int x=0;
        while (x!=1){
            firstTrIdAsync = infoWebSarjana.firstId();
            //Berarti ada update
            if(!firstTrIdAsync.equals(firstTrId)){
                newArticle = "PENGUMUMAN BARU SU!!!\n" + infoWebSarjana.GetDate() + "\n" +
                        infoWebSarjana.GetCategory() + "\n" + infoWebSarjana.GetTitle() + "\n" + "\n" + infoWebSarjana.GetDescription();
                firstTrId = infoWebSarjana.firstId();
                DemoApplication.pushMessageKeAxell(newArticle);
            }
            System.out.println("firstTrIdAsync : " + firstTrIdAsync);
            System.out.println("firstTrId : " + firstTrId);
            Thread.sleep(5000);
        }
        return new AsyncResult<>(newArticle);
    }
}
