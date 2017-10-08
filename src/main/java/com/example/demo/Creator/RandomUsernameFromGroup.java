package com.example.demo.Creator;

import com.example.demo.Dao.MainDao;
import com.example.demo.Getter.Getter;
import com.example.demo.model.GroupMember;
import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.source.Source;

import java.util.ArrayList;
import java.util.List;

public class RandomUsernameFromGroup {
    private String AccessToken;
    public RandomUsernameFromGroup(String AccessToken){
        this.AccessToken = AccessToken;
    }
    Getter getter = new Getter(AccessToken);

    public String randomName(Event event, Source source, String group_id){
        List<GroupMember> groupMemberList = new ArrayList<>();
        groupMemberList = MainDao.getAllMemberIds(group_id);
        int banyakMember=groupMemberList.size();
        int randInt = (int) (Math.random() * ((banyakMember-1)-0));

        String senderId = event.getSource().getSenderId();
        String type  = getter.getType(source);
        GroupMember user_id_beruntung = groupMemberList.get(randInt);

        String user_name_beruntung = getter.getGroupMemberName(type, senderId, user_id_beruntung.getUserId());

        System.out.println("ID BERUNTUNG : " + user_id_beruntung);
        System.out.println("USERNAME BERUNTUNG " + user_name_beruntung);
        return user_name_beruntung;
    }
}
