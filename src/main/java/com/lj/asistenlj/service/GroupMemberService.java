package com.lj.asistenlj.service;

import com.linecorp.bot.model.event.source.Source;
import com.lj.asistenlj.model.GroupMember;

import java.util.List;

public interface GroupMemberService {
    GroupMember buildGroupMember(Source source);
    void saveGroupMemberToDatabase(GroupMember groupMember);
    List<GroupMember> findAllGroupMembersByGroupId(String groupId);
    GroupMember findByMemberNameLike(String memberName);
}
