package com.lj.asistenlj.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "feature_data")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FeatureData {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "feature_name")
    private String featureName;

    @Column(name = "date")
    private Date date;

}
