package com.lj.asistenlj.repository;

import com.lj.asistenlj.model.GroupMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupMemberRepository extends JpaRepository<GroupMember, String> {
    @Query("SELECT count(gm.memberId) from GroupMember gm where gm.memberId = :memberId")
    int isGroupMemberExists(@Param("memberId") String memberId);

    List<GroupMember> findAllByGroupId(String groupId);
}
