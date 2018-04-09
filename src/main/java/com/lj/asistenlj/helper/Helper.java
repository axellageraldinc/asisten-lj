package com.lj.asistenlj.helper;

import com.linecorp.bot.client.LineMessagingServiceBuilder;
import com.linecorp.bot.model.event.source.GroupSource;
import com.linecorp.bot.model.event.source.RoomSource;
import com.linecorp.bot.model.event.source.Source;
import com.linecorp.bot.model.profile.UserProfileResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import retrofit2.Response;

import java.io.IOException;

@Component
public class Helper {

    private static final Logger LOGGER = LoggerFactory.getLogger(Helper.class);

    @Autowired
    private String accessToken;

    public String getId(Source source){
        String id;
        if (source instanceof GroupSource) {
            id = ((GroupSource) source).getGroupId();
        } else if (source instanceof RoomSource) {
            id = ((RoomSource) source).getRoomId();
        } else{
            id = source.getUserId();
        }
        return id;
    }

    public String getType(Source source){
        String type;
        if (source instanceof GroupSource)
            type="group";
        else if(source instanceof RoomSource)
            type="room";
        else
            type="personal";
        return type;
    }

    public String getName(String userId){
        String name = "";
        try {
            Response<UserProfileResponse> response =
                    LineMessagingServiceBuilder
                            .create(accessToken)
                            .build()
                            .getProfile(userId)
                            .execute();
            if (response.isSuccessful()){
                UserProfileResponse profileResponse = response.body();
                name = profileResponse.getDisplayName();
            } else{
                LOGGER.error("Response getName not successful \n" + response.code() + " " + response.message());
            }
        } catch (IOException e) {
            LOGGER.error("Gagal get name : " + e.toString());
        }
        return name;
    }

    public String getGroupMemberName(String type, String senderId, String userId){
        String userName = "";
        try{
            Response<UserProfileResponse> response =
                    LineMessagingServiceBuilder
                            .create(accessToken)
                            .build()
                            .getMemberProfile(type, senderId, userId)
                            .execute();
            if (response.isSuccessful()){
                UserProfileResponse profileResponse = response.body();
                userName = profileResponse.getDisplayName();
            } else{
                LOGGER.error("Response getGroupMemberName not successful \n" + response.code() + " " + response.message());
            }
        } catch (Exception ex){
            LOGGER.error("Gagal get Group Member Name : " + ex.toString());
        }
        return userName;
    }

}
