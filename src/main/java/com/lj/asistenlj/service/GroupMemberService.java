package com.lj.asistenlj.service;

import com.linecorp.bot.model.event.source.Source;
import com.lj.asistenlj.model.GroupMember;

public interface GroupMemberService {
    GroupMember buildGroupMember(Source source);
    void saveGroupMemberToDatabase(GroupMember groupMember);
}
