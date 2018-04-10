package com.lj.asistenlj.service;

import com.linecorp.bot.model.event.source.Source;
import com.lj.asistenlj.helper.Helper;
import com.lj.asistenlj.model.GroupMember;
import com.lj.asistenlj.repository.GroupMemberRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
        String type = helper.getType(source);
        String senderId = source.getSenderId();
        String userId = source.getUserId();
        return GroupMember.builder()
                .id(UUID.randomUUID().toString())
                .groupId(helper.getId(source))
                .memberId(userId)
                .memberName(helper.getGroupMemberName(type, senderId, userId))
                .build();
    }

    @Override
    public void saveGroupMemberToDatabase(GroupMember groupMember) {
        if(groupMemberRepository.findByGroupIdAndMemberId(groupMember.getGroupId(), groupMember.getMemberId()) == null){
            groupMemberRepository.save(groupMember);
        } else{
//            LOGGER.info("Member " + groupMember.getMemberId() + " sudah ada");
        }
//        int isExists = groupMemberRepository.isGroupMemberExists(groupMember.getGroupId(), groupMember.getMemberId());
//        if(isExists>0){
//            LOGGER.info("Member " + groupMember.getMemberId() + " sudah ada");
//        } else {
//            groupMemberRepository.save(groupMember);
//        }
    }

    @Override
    public List<GroupMember> findAllGroupMembersByGroupId(String groupId) {
        return groupMemberRepository.findAllByGroupId(groupId);
    }

    @Override
    public GroupMember findByMemberNameLike(String memberName) {
        return groupMemberRepository.findByMemberNameLike(memberName);
    }
}
