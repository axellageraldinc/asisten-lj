package com.lj.asistenlj.service.event;

import com.linecorp.bot.model.event.JoinEvent;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;
import com.lj.asistenlj.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;

@LineMessageHandler
public class JoinEventHandler {

    @Autowired
    private ChatService chatService;
    @Autowired
    private TextMessage joinGroupMessage;

    @EventMapping
    public void joinGroupEvent(JoinEvent joinEvent){
        chatService.sendResponseMessage(joinEvent.getReplyToken(), joinGroupMessage);
    }

}
