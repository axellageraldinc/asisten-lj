package com.lj.asistenlj.service;

import com.lj.asistenlj.model.FeatureData;
import com.lj.asistenlj.model.FeatureDataResult;
import com.lj.asistenlj.repository.FeatureDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class FeatureDataServiceImpl implements FeatureDataService {

    @Autowired
    private FeatureDataRepository featureDataRepository;

    @Override
    public void saveFeatureData(String featureName) {
        featureDataRepository.save(FeatureData.builder()
                .id(UUID.randomUUID().toString())
                .featureName(featureName)
                .date(new Date())
                .build());
    }

    @Override
    public List<FeatureData> getAllFeatureData() {
        return featureDataRepository.findAll();
    }

    @Override
    public FeatureDataResult countFeatureDataByName(String featureName) {
        long count = featureDataRepository.countByFeatureName(featureName);
        return FeatureDataResult.builder()
                .featureName(featureName)
                .count(count)
                .build();
    }

}
