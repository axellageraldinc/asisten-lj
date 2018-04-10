package com.lj.asistenlj.controller;

import com.lj.asistenlj.model.GroupMember;
import com.lj.asistenlj.service.GroupMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/member")
public class GroupMemberDataController {

    @Autowired
    private GroupMemberService groupMemberService;

    @RequestMapping(
            value = "/{groupId}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<GroupMember> findAllGroupMemberOfAGroup(@PathVariable("groupId") String groupId){
        return groupMemberService.findAllGroupMembersByGroupId(groupId);
    }

    @RequestMapping(
            value = "/{memberName}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public GroupMember findByMemberNameLike(@PathVariable("memberName") String memberName){
        return groupMemberService.findByMemberNameLike(memberName);
    }

}
