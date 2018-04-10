package com.lj.asistenlj.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "group_member")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GroupMember {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "group_id")
    private String groupId;

    @Column(name = "member_id")
    private String memberId;

    @Column(name = "member_name")
    private String memberName;

}
