package com.lj.asistenlj.service.event;

import com.linecorp.bot.model.event.PostbackEvent;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;
import com.lj.asistenlj.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

@LineMessageHandler
public class PostbackEventHandler {

    @Autowired
    private ChatService chatService;
    @Autowired
    private TextMessage caraPakaiSiapakah;
    @Autowired
    private TextMessage caraPakaiWajah;
    @Autowired
    private TextMessage caraPakaiCinta;
    @Autowired
    private TextMessage caraPakaiInstagram;
    @Autowired
    private TextMessage caraPakaiDosa;
    @Autowired
    private TextMessage caraPakaiKapankah;
    @Autowired
    private TextMessage caraPakaiIslami;
    @Autowired
    private TextMessage caraPakaiDimanakah;
    @Autowired
    private TextMessage caraPakaiTambahTempatMakanDimanaYa;
    @Autowired
    private TextMessage caraPakaiMakanDimanaYa;
    @Autowired
    private TextMessage caraPakaiLihatSemuaDatabaseMakanDimanaYa;

    @EventMapping
    public void postbackEvent(PostbackEvent postbackEvent){
        String replyToken = postbackEvent.getReplyToken();
        String postbackMessage = postbackEvent.getPostbackContent().getData();
        switch (postbackMessage){
            case "/CARA-PAKAI-SIAPAKAH":
                chatService.sendResponseMessage(replyToken, caraPakaiSiapakah);
                break;
            case "/CARA-PAKAI-WAJAH":
                chatService.sendResponseMessage(replyToken, caraPakaiWajah);
                break;
            case "/CARA-PAKAI-CINTA":
                chatService.sendResponseMessage(replyToken, caraPakaiCinta);
                break;
            case "/CARA-PAKAI-INSTAGRAM":
                chatService.sendResponseMessage(replyToken, caraPakaiInstagram);
                break;
            case "/CARA-PAKAI-DOSA":
                chatService.sendResponseMessage(replyToken, caraPakaiDosa);
                break;
            case "/CARA-PAKAI-KAPANKAH":
                chatService.sendResponseMessage(replyToken, caraPakaiKapankah);
                break;
            case "/CARA-PAKAI-ISLAMI":
                chatService.sendResponseMessage(replyToken, caraPakaiIslami);
                break;
            case "/CARA-PAKAI-DIMANAKAH":
                chatService.sendResponseMessage(replyToken, caraPakaiDimanakah);
                break;
            case "/CARA-PAKAI-MAKAN-DIMANA-YA":
                chatService.sendResponseMessage(replyToken, new ArrayList<Message>(){{
                    add(caraPakaiTambahTempatMakanDimanaYa);
                    add(caraPakaiLihatSemuaDatabaseMakanDimanaYa);
                    add(caraPakaiMakanDimanaYa);
                }});
                break;
        }
    }

}
