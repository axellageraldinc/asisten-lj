package com.example.demo.Controller;

import com.example.demo.Dao.FollowersDao;
import com.example.demo.DemoApplication;
import com.example.demo.model.Followers;
import com.linecorp.bot.client.LineMessagingServiceBuilder;
import com.linecorp.bot.model.event.FollowEvent;
import com.linecorp.bot.model.profile.UserProfileResponse;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;
import retrofit2.Response;

import java.io.IOException;

@LineMessageHandler
public class NewFollowerController {
    private static String AccessToken = "u/jyVKXsD5N/OfmNIvEjnI+NffMIhzcFFjIZ3Whm4Gu9/LTL4y7WjWhWehHjYIO+aG6QUKw5991HFzs7i8c1PAZP07r1LIGun6o8X53yZflIk/Th0W8JkY9G/2IpWkL59subrXO5cOQCxJqjemzHvwdB04t89/1O/w1cDnyilFU=";

    @EventMapping
    public void testFollow(FollowEvent followEvent){
        String userId = followEvent.getSource().getUserId();
        Followers followers = new Followers();
        followers.setName(getName(userId));
        followers.setUser_id(userId);
        int statusInsert = FollowersDao.Insert(followers);
        if(statusInsert==1){
            System.out.println("Insert success!");
            System.out.println("User ID : " + userId);
            System.out.println("Name : " + followers.getName());
            DemoApplication.getUserId();
        } else{
            System.out.println("Insert FAILED!");
        }
        System.out.println("new follower : " + userId);
    }

    public String getName(String userId){
        String name="";
        Response<UserProfileResponse> response =
                null;
        try {
            response = LineMessagingServiceBuilder
                    .create(AccessToken)
                    .build()
                    .getProfile(userId)
                    .execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (response.isSuccessful()) {
            UserProfileResponse profile = response.body();
            name = profile.getDisplayName();
            System.out.println(profile.getDisplayName());
        } else {
            System.out.println(response.code() + " " + response.message());
        }
        return name;
    }
}
