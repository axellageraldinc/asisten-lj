package com.lj.asistenlj.service;

import com.linecorp.bot.model.event.source.Source;
import com.lj.asistenlj.helper.Helper;
import com.lj.asistenlj.model.Group;
import com.lj.asistenlj.repository.GroupRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GroupServiceImpl implements GroupService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GroupServiceImpl.class);

    @Autowired
    private Helper helper;
    @Autowired
    private GroupRepository groupRepository;

    @Override
    public Group buildGroup(Source source) {
        return Group.builder()
                .id(UUID.randomUUID().toString())
                .groupId(helper.getId(source))
                .imageDetectStatus(false)
                .build();
    }

    @Override
    public void saveGroupToDatabase(Group group) {
        if(groupRepository.findByGroupId(group.getGroupId()) == null){
            groupRepository.save(group);
        } else{
//            LOGGER.info("Group " + group.getGroupId() + " sudah ada");
        }
//        int isExists = groupRepository.isGroupExists(group.getGroupId());
//        if(isExists>0){
//            LOGGER.info("Group " + group.getGroupId() + " sudah ada");
//        } else {
//            groupRepository.save(group);
//        }
    }
}
