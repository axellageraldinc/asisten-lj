package com.lj.asistenlj.service.event;

import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.ImageMessageContent;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;
import com.lj.asistenlj.helper.Helper;
import com.lj.asistenlj.repository.GroupRepository;
import com.lj.asistenlj.service.ChatService;
import com.lj.asistenlj.service.fitur.FaceDetectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@LineMessageHandler
public class ImageEventHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageEventHandler.class);

    @Autowired
    private ChatService chatService;
    @Autowired
    private Helper helper;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private FaceDetectService faceDetectService;

    @EventMapping
    public void imageEvent(MessageEvent<ImageMessageContent> imageEvent){
        String replyToken = imageEvent.getReplyToken();
        String groupId = helper.getId(imageEvent.getSource());
        try{
            boolean imageDetectStatus = groupRepository.findImageDetectStatusByGroupId(groupId);
            if(imageDetectStatus)
                chatService.sendResponseMessage(replyToken, faceDetectService.getResult(imageEvent.getSource(), imageEvent.getMessage().getId()));
        } catch (Exception ex){
            LOGGER.error("Error imageDetectStatus imageventHandler : " + ex.toString());
        }
    }

}
