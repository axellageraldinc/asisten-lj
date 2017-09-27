package com.example.demo.Controller;

import com.example.demo.GetInfoWebSarjana;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;

@LineMessageHandler
public class HandleTextMessageController {
    //TIDAK TERPAKAI LAGI
//    GetInfoWebSarjana infoWebSarjana = new GetInfoWebSarjana();
//
//    @EventMapping
//    public TextMessage handleTextMessageEvent(MessageEvent<TextMessageContent> msg){
//        TextMessage replyMsg = null;
//        if (msg.getMessage().getText().equals("judul website")){
//            replyMsg = new TextMessage(infoWebSarjana.GetWebDtetiTitle());
//        } else if(msg.getMessage().getText().equals("category")){
//            replyMsg = new TextMessage(infoWebSarjana.GetCategory());
//        } else if(msg.getMessage().getText().equals("date")){
//            replyMsg = new TextMessage(infoWebSarjana.GetDate());
//        } else if(msg.getMessage().getText().equals("deskripsi")){
//            replyMsg = new TextMessage(infoWebSarjana.GetDescription());
//            System.out.println("Deskripsi : " + infoWebSarjana.GetDescription());
//        } else if(msg.getMessage().getText().equals("judul")){
//            replyMsg = new TextMessage(infoWebSarjana.GetTitle());
//        } else if(msg.getMessage().getText().equals("coba")){
//            replyMsg = new TextMessage("tr ID : " + infoWebSarjana.firstId());
//        } else{
//            replyMsg = new TextMessage("kowe ngirim : " + msg.getMessage().getText());
//        }
//        return replyMsg;
//    }
}
