package com.lj.asistenlj.service.event;

import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.AudioMessageContent;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;
import com.lj.asistenlj.service.ChatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@LineMessageHandler
public class AudioEventHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(AudioEventHandler.class);

    @Autowired
    private ChatService chatService;

    @EventMapping
    public void audioEvent(MessageEvent<AudioMessageContent> audioEvent){
        LOGGER.info("Ada audio message");
    }

}
