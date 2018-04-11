package com.lj.asistenlj.repository.cekongkir;

import com.lj.asistenlj.model.cekongkir.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City, Integer> {
    City findByCityName(String cityName);
}
