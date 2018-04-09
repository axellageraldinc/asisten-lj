package com.lj.asistenlj.service.event;

import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.FileMessageContent;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;
import com.lj.asistenlj.service.ChatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@LineMessageHandler
public class FileEventHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileEventHandler.class);

    @Autowired
    private ChatService chatService;

    @EventMapping
    public void fileEvent(MessageEvent<FileMessageContent> fileEvent){
        LOGGER.info("Ada file message");
    }

}
