package com.lj.asistenlj.service.event;

import com.linecorp.bot.model.event.FollowEvent;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.StickerMessage;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;
import com.lj.asistenlj.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@LineMessageHandler
public class NewFollowerEventHandler {

    @Autowired
    private ChatService chatService;
    @Autowired
    private StickerMessage newFollowerStickerMessage;
    @Autowired
    private TextMessage newFollowerMessage;

    @EventMapping
    public void newFollowerEvent(FollowEvent followEvent){
        String replytoken = followEvent.getReplyToken();
        List<Message> messageList = new ArrayList<>();
        messageList.add(newFollowerStickerMessage);
        messageList.add(newFollowerMessage);
        chatService.sendResponseMessage(replytoken, messageList);
    }

}
