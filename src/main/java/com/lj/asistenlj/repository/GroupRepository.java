package com.lj.asistenlj.repository;

import com.lj.asistenlj.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface GroupRepository extends JpaRepository<Group, String> {
//    @Query("SELECT count(g.groupId) from Group g where g.groupId = :groupId")
//    int isGroupExists(String groupId);

    @Query("SELECT g.imageDetectStatus from Group g where g.groupId= :groupId")
    boolean findImageDetectStatusByGroupId(String groupId);

    Group findByGroupId(String groupId);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("UPDATE Group g SET g.imageDetectStatus= :imageDetectStatus where g.groupId= :groupId")
    void updateImageDetectStatus(boolean imageDetectStatus, String groupId);
}
