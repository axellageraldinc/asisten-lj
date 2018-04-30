package com.lj.asistenlj.repository;

import com.lj.asistenlj.model.TempatMakan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MakanDimanaYaRepository extends JpaRepository<TempatMakan, String> {
    List<TempatMakan> findAllByGroupId(String groupId);
}
