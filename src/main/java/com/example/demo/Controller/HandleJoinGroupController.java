package com.example.demo.Controller;

import com.example.demo.Dao.GroupDao;
import com.linecorp.bot.client.LineMessagingServiceBuilder;
import com.linecorp.bot.model.event.JoinEvent;
import com.linecorp.bot.model.event.source.GroupSource;
import com.linecorp.bot.model.event.source.Source;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.profile.UserProfileResponse;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;
import retrofit2.Response;

import java.io.IOException;

@LineMessageHandler
public class HandleJoinGroupController {
    private static String AccessToken = "u/jyVKXsD5N/OfmNIvEjnI+NffMIhzcFFjIZ3Whm4Gu9/LTL4y7WjWhWehHjYIO+aG6QUKw5991HFzs7i8c1PAZP07r1LIGun6o8X53yZflIk/Th0W8JkY9G/2IpWkL59subrXO5cOQCxJqjemzHvwdB04t89/1O/w1cDnyilFU=";

    @EventMapping
    public void handleJoinNewGroup(JoinEvent joinEvent){
        TextMessage replyMsg = null;
        String groupId = joinEvent.getSource().getSenderId();
        System.out.println("Group ID dari join : " + groupId);
        Source source = joinEvent.getSource();
        if (source instanceof GroupSource){
            String group_name = ((GroupSource) source).getGroupId();
            System.out.println("GROUP NAME : " + group_name);
        }
//        System.out.println("Group name : " + getGroupName(groupId));
//        System.out.println("Group ID dari method : " + get_group_name());
//        GroupDao.CreateTableGroup(groupId);
//        replyMsg = new TextMessage("Nuwun yo aku wes entuk join grup " + )
//        return replyMsg;
    }

//    public String getGroupName(String groupId){
//        String group_name="";
//        Response<UserProfileResponse> response =
//                null;
//        try {
//            response = LineMessagingServiceBuilder
//                    .create(AccessToken)
//                    .build()
//                    .getProfile(groupId)
//                    .execute();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        if (response.isSuccessful()) {
//            UserProfileResponse profile = response.body();
//            group_name = profile.getDisplayName();
//            System.out.println(profile.getDisplayName());
//        } else {
//            System.out.println(response.code() + " " + response.message());
//        }
//        return group_name;
//    }

}
