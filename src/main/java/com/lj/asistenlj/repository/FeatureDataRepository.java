package com.lj.asistenlj.repository;

import com.lj.asistenlj.model.FeatureData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeatureDataRepository extends JpaRepository<FeatureData, String> {
    long countByFeatureName(String featureName);
}
