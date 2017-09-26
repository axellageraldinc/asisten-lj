package com.example.demo.Controller;

import com.linecorp.bot.model.event.FollowEvent;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;

@LineMessageHandler
public class NewFollowerController {
    @EventMapping
    public void testFollow(FollowEvent followEvent){
        String userId = followEvent.getSource().getUserId();
        System.out.println("new follower : " + userId);
    }
}
