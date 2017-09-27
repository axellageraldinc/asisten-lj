package com.example.demo.Controller;

import com.example.demo.Dao.GroupDao;
import com.linecorp.bot.model.PushMessage;
import com.linecorp.bot.model.event.JoinEvent;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.event.source.GroupSource;
import com.linecorp.bot.model.event.source.RoomSource;
import com.linecorp.bot.model.event.source.Source;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.StickerMessage;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;

import java.util.ArrayList;
import java.util.List;

@LineMessageHandler
public class HandleJoinGroupController {

    @EventMapping
    public TextMessage handleJoinNewGroup(JoinEvent joinEvent){
        TextMessage replyMsg = null;
        String groupId = joinEvent.getSource().getSenderId();
        System.out.println("Group ID dari join : " + groupId);
        Source source = joinEvent.getSource();
        String group_id;
        if (source instanceof GroupSource){
            group_id = ((GroupSource) source).getGroupId();
            System.out.println("GROUP ID : " + group_id);
        } else if (source instanceof RoomSource){
            group_id = ((RoomSource) source).getRoomId();
            System.out.println("ROOM ID : " + group_id);
        }
//        GroupDao.CreateTableData(groupId);
        replyMsg = new TextMessage("Nuwun yo aku wes entuk join grup iki\n" +
                "Silakan ketik /help untuk melihat command-command yang ada.");
        return replyMsg;
    }

    @EventMapping
    public PushMessage handleTextSlash(MessageEvent<TextMessageContent> msg){
        String groupId = msg.getSource().getSenderId();
        PushMessage pushMessage = null;
        List<Message> messageList = new ArrayList<>();
        if(msg.getMessage().getText().toUpperCase().equals("/HELP")){
            TextMessage msgCommand = new TextMessage("Daftar command LJ BOT\n" +
                    "1. /PR [spasi] [deskripsi PR]\n" +
                    "2. /UJIAN [spasi] [deskripsi UJIAN]\n");
            messageList.add(msgCommand);
        } else if (msg.getMessage().getText().toUpperCase().substring(0,3).equals("/PR")){
            String deskripsi = msg.getMessage().getText().substring(4);
            System.out.println("deskripsi PR : " + deskripsi);
            TextMessage msgDeskripsi = new TextMessage("deskripsi PR : " + deskripsi + "\nSudah berhasil masuk ke database");
            messageList.add(msgDeskripsi);
            StickerMessage stickerSuksesPR = new StickerMessage("1", "5");
            messageList.add(stickerSuksesPR);
        }
        pushMessage = new PushMessage(groupId, messageList);
        return pushMessage;
    }

}
