package com.example.demo.Controller;

import com.example.demo.Dao.GroupDao;
import com.linecorp.bot.model.event.JoinEvent;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.event.source.GroupSource;
import com.linecorp.bot.model.event.source.RoomSource;
import com.linecorp.bot.model.event.source.Source;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;
import sun.plugin2.message.TextEventMessage;

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
        GroupDao.CreateTableGroup(groupId);
        GroupDao.CreateTableData(groupId);
//        System.out.println("Group name : " + getGroupName(groupId));
//        System.out.println("Group ID dari method : " + get_group_name());
//        GroupDao.CreateTableGroup(groupId);
        replyMsg = new TextMessage("Nuwun yo aku wes entuk join grup iki\n" +
                "Silakan ketik /help untuk melihat command-command yang ada.");
        return replyMsg;
    }

    @EventMapping
    public TextMessage handleTextSlash(MessageEvent<TextMessageContent> msg){
        TextMessage replyMsg = null;
        if(msg.getMessage().getText().equals("/help")){
            replyMsg = new TextMessage("command help");
        }
        return replyMsg;
    }

}
