package com.lj.asistenlj.model.cekongkir;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "city")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class City {

    @Id
    @Column(name = "city_id")
    private int cityId;

    @Column(name = "province_id")
    private int provinceId;

    @Column(name = "province")
    private String province;

    @Column(name = "type")
    private String type;

    @Column(name = "city_name")
    private String cityName;

    @Column(name = "postal_code")
    private int postalCode;

}
