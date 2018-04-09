package com.lj.asistenlj.service;

import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

@LineMessageHandler
public class ChatService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChatService.class);

    @Autowired
    private LineMessagingClient lineMessagingClient;

    public void sendResponseMessage(String replyToken, List<Message> messageList){
        try {
            lineMessagingClient
                    .replyMessage(new ReplyMessage(replyToken, messageList))
                    .get();
        } catch (InterruptedException | ExecutionException e) {
            LOGGER.error("Error Send Message : " + e.getMessage());
        }
    }
    public void sendResponseMessage(String replyToken, Message message){
        sendResponseMessage(replyToken, Collections.singletonList(message));
    }

}
