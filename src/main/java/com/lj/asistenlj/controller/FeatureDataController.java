package com.lj.asistenlj.controller;

import com.lj.asistenlj.model.FeatureData;
import com.lj.asistenlj.model.FeatureDataResult;
import com.lj.asistenlj.service.FeatureDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/feature")
public class FeatureDataController {

    @Autowired
    private FeatureDataService featureDataService;

    @RequestMapping(
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<FeatureData> getAllFeatureData(){
        return featureDataService.getAllFeatureData();
    }

    @RequestMapping(
            value = "/{featureName}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public FeatureDataResult getFeatureDataCount(@PathVariable("featureName") String featureName){
        return featureDataService.countFeatureDataByName(featureName);
    }

}
