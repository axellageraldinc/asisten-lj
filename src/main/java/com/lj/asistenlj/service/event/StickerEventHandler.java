package com.lj.asistenlj.service.event;

import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.StickerMessageContent;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;
import com.lj.asistenlj.service.ChatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@LineMessageHandler
public class StickerEventHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(StickerEventHandler.class);

    @Autowired
    private ChatService chatService;

    @EventMapping
    public void stickerEvent(MessageEvent<StickerMessageContent> messageEvent){
        LOGGER.info("Ada message sticker\n" +
                "packageId : " + messageEvent.getMessage().getPackageId() + "\n" +
                "stickerId : " + messageEvent.getMessage().getStickerId());
    }

}
