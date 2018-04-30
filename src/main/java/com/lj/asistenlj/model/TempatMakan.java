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
@Table(name = "tempat_makan")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TempatMakan {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "tempat_makan")
    private String namaTempatMakan;

    @Column(name = "lokasi_tempat_makan")
    private String lokasiTempatMakan;

    @Column(name = "group_id")
    private String groupId;

}
