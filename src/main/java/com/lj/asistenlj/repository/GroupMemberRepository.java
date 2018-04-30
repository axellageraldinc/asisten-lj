package com.lj.asistenlj.repository;

import com.lj.asistenlj.model.GroupMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupMemberRepository extends JpaRepository<GroupMember, String> {
//    @Query("SELECT count(gm.memberId) from GroupMember gm where gm.groupId= :groupId AND gm.memberId = :memberId")
//    int isGroupMemberExists(String groupId, String memberId);

    GroupMember findByMemberNameContaining(String memberName);

    GroupMember findByGroupIdAndMemberId(String groupId, String memberId);

    List<GroupMember> findAllByGroupId(String groupId);
}
