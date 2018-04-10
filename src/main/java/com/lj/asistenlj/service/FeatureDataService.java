package com.lj.asistenlj.service;

import com.lj.asistenlj.model.FeatureData;
import com.lj.asistenlj.model.FeatureDataResult;

import java.util.List;

public interface FeatureDataService {
    void saveFeatureData(String featureName);
    List<FeatureData> getAllFeatureData();
    FeatureDataResult countFeatureDataByName(String featureName);
}
