package com.example.demo.Leave;

import com.linecorp.bot.client.LineMessagingServiceBuilder;
import com.linecorp.bot.model.response.BotApiResponse;
import retrofit2.Response;

import java.io.IOException;

public class Leave {
    String AccessToken;

    public Leave(String accessToken){
        this.AccessToken = accessToken;
    }

    public void LeaveGroup(String id){
        try {
            Response<BotApiResponse> response =
                    LineMessagingServiceBuilder
                            .create(AccessToken)
                            .build()
                            .leaveGroup(id)
                            .execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void LeaveRoom(String id){
        try {
            Response<BotApiResponse> response =
                    LineMessagingServiceBuilder
                            .create(AccessToken)
                            .build()
                            .leaveRoom(id)
                            .execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
