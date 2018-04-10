package com.lj.asistenlj.service.event;

import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.event.*;
import com.linecorp.bot.model.event.message.*;
import com.linecorp.bot.model.event.source.Source;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TemplateMessage;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;
import com.lj.asistenlj.model.GroupMember;
import com.lj.asistenlj.service.ChatService;
import com.lj.asistenlj.service.GroupMemberService;
import com.lj.asistenlj.service.GroupService;
import com.lj.asistenlj.service.fitur.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

@LineMessageHandler
public class TextEventHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(TextEventHandler.class);

    @Autowired
    private ChatService chatService;
    @Autowired
    LineMessagingClient lineMessagingClient;
    @Autowired
    private GroupService groupService;
    @Autowired
    private GroupMemberService groupMemberService;
    @Autowired
    private TemplateMessage listFiturTemplateMessage;
    @Autowired
    private TemplateMessage hiburanTemplateMessage1;
    @Autowired
    private TemplateMessage hiburanTemplateMessage2;
    @Autowired
    private TemplateMessage templateAbout1;
    @Autowired
    private TemplateMessage templateAbout2;
    @Autowired
    private TemplateMessage templateAbout3;
    @Autowired
    private TemplateMessage templateAbout4;
    @Autowired
    private TextMessage sourceCodeMessage;
    @Autowired
    private TextMessage sponsorMessage;
    @Autowired
    private ApakahService apakahService;
    @Autowired
    private SiapakahService siapakahService;
    @Autowired
    private LoveService loveService;
    @Autowired
    private DosaService dosaService;
    @Autowired
    private InstagramService instagramService;
    @Autowired
    private FaceDetectService faceDetectService;
    @Autowired
    private DimanakahService dimanakahService;
    @Autowired
    private KapankahService kapankahService;
    @Autowired
    private LeaveService leaveService;
    @Autowired
    private TextMessage leaveMessageFail1;

    @EventMapping
    public void textEvent(MessageEvent<TextMessageContent> messageEvent){
        String pesan = messageEvent.getMessage().getText().toUpperCase();
        String[] pesanSplit = pesan.split(" ");

        List<Message> messageList = new ArrayList<>();

        GroupMember groupMember = groupMemberService.buildGroupMember(messageEvent.getSource());
        groupService.saveGroupToDatabase(groupService.buildGroup(messageEvent.getSource()));
        groupMemberService.saveGroupMemberToDatabase(groupMember);

        String replyToken = messageEvent.getReplyToken();
        Source source = messageEvent.getSource();

        if ("APAKAH".equals(pesanSplit[0])) {
            chatService.sendResponseMessage(replyToken, apakahService.getApakahReplyFromRandomInt());
        } else if("SIAPAKAH".equals(pesanSplit[0])){
            chatService.sendResponseMessage(replyToken, siapakahService.getResult(pesan, source));
        } else if ("/FITUR".equals(pesanSplit[0])) {
            chatService.sendResponseMessage(replyToken, listFiturTemplateMessage);
        } else if ("/HIBURAN".equals(pesanSplit[0])) {
            messageList.add(hiburanTemplateMessage1);
            messageList.add(hiburanTemplateMessage2);
            chatService.sendResponseMessage(replyToken, messageList);
        } else if ("/SOURCE-CODE".equals(pesanSplit[0])) {
            chatService.sendResponseMessage(replyToken, sourceCodeMessage);
        } else if ("/SPONSOR".equals(pesanSplit[0])) {
            chatService.sendResponseMessage(replyToken, sponsorMessage);
        } else if ("/ABOUT".equals(pesanSplit[0])) {
            messageList.add(templateAbout1);
            messageList.add(templateAbout2);
            messageList.add(templateAbout3);
            messageList.add(templateAbout4);
            chatService.sendResponseMessage(replyToken, messageList);
        } else if("/LOVE".equals(pesanSplit[0])){
            chatService.sendResponseMessage(replyToken, loveService.getLoveCalculatorResult(pesan));
        } else if("/DOSA".equals(pesanSplit[0])){
            chatService.sendResponseMessage(replyToken, dosaService.getDosaResult(pesan));
        } else if("/STALK".equals(pesanSplit[0])){
            chatService.sendResponseMessage(replyToken, instagramService.getLinkAndPreview(pesan));
        } else if("/FACE-DETECT".equals(pesanSplit[0])){
            chatService.sendResponseMessage(replyToken, faceDetectService.responseMessage(source, "face-detect"));
        } else if("/STOP".equals(pesanSplit[0])){
            chatService.sendResponseMessage(replyToken, faceDetectService.responseMessage(source, "stop"));
        } else if("DIMANAKAH".equals(pesanSplit[0])){
            chatService.sendResponseMessage(replyToken, dimanakahService.getResult(source));
        } else if("KAPANKAH".equals(pesanSplit[0])) {
            chatService.sendResponseMessage(replyToken, kapankahService.getResult(source, pesan));
        } else if("SAYA PERCAYA BAHWA SAYA ADALAH ORANG YANG JELEK".equals(pesan)){
            messageList = leaveService.getReplyMessages(source);
            chatService.sendResponseMessage(replyToken, messageList);
            if(!messageList.contains(leaveMessageFail1)) {
                leaveService.leave(source);
            }
        }
    }


}
