package com.lj.asistenlj.service;

import com.linecorp.bot.model.event.source.Source;
import com.lj.asistenlj.helper.Helper;
import com.lj.asistenlj.model.GroupMember;
import com.lj.asistenlj.repository.GroupMemberRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GroupMemberServiceImpl implements GroupMemberService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GroupMemberServiceImpl.class);

    @Autowired
    private Helper helper;
    @Autowired
    private GroupMemberRepository groupMemberRepository;

    @Override
    public GroupMember buildGroupMember(Source source) {
        return GroupMember.builder()
                .id(UUID.randomUUID().toString())
                .groupId(helper.getId(source))
                .memberId(source.getUserId())
                .build();
    }

    @Override
    public void saveGroupMemberToDatabase(GroupMember groupMember) {
        int isExists = groupMemberRepository.isGroupMemberExists(groupMember.getGroupId());
        if(isExists>0){

        } else {
            groupMemberRepository.save(groupMember);
        }
    }
}
