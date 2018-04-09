package com.lj.asistenlj.service.fitur.implementation;

import com.linecorp.bot.client.LineMessagingServiceBuilder;
import com.linecorp.bot.model.event.source.Source;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.response.BotApiResponse;
import com.lj.asistenlj.helper.Helper;
import com.lj.asistenlj.service.fitur.LeaveService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class LeaveServiceImpl implements LeaveService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LeaveServiceImpl.class);

    @Autowired
    private Helper helper;
    @Autowired
    private String accessToken;
    @Autowired
    private TextMessage leaveMessageFail1;
    @Autowired
    private TextMessage leaveMessageFail2;

    @Override
    public void leave(Source source){
        String type = helper.getType(source);
        String id = helper.getId(source);
        if(type.equals("group")){
            leaveGroup(id);
        } else if(type.equals("room")){
            leaveRoom(id);
        }
    }

    @Override
    public List<Message> getReplyMessages(Source source) {
        List<Message> messageList = new ArrayList<>();
        TextMessage textMessage;
        if(getRandomInt()%2==0){
            messageList.add(leaveMessageFail1);
            messageList.add(leaveMessageFail2);
        } else {
            textMessage = new TextMessage("Bye " + helper.getName(source.getUserId()) + " yang jelek");
            messageList.add(textMessage);
        }
        return messageList;
    }

    private int getRandomInt(){
        return new Random().nextInt();
    }

    private void leaveGroup(String id){
        try {
            Response<BotApiResponse> response =
                    LineMessagingServiceBuilder
                            .create(accessToken)
                            .build()
                            .leaveGroup(id)
                            .execute();
        } catch (IOException e) {
           LOGGER.error("Error leave group : " + e.toString());
        }
    }

    private void leaveRoom(String id){
        try {
            Response<BotApiResponse> response =
                    LineMessagingServiceBuilder
                            .create(accessToken)
                            .build()
                            .leaveRoom(id)
                            .execute();
        } catch (IOException e) {
            LOGGER.error("Error leave room : " + e.toString());
        }
    }

}
