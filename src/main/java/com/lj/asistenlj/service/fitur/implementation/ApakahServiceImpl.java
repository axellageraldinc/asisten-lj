package com.lj.asistenlj.service.fitur.implementation;

import com.linecorp.bot.model.message.TextMessage;
import com.lj.asistenlj.helper.Feature;
import com.lj.asistenlj.service.FeatureDataService;
import com.lj.asistenlj.service.fitur.ApakahService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class ApakahServiceImpl implements ApakahService {

    @Autowired
    private FeatureDataService featureDataService;

    @Override
    public TextMessage getApakahReplyFromRandomInt(){
        featureDataService.saveFeatureData(Feature.APAKAH);
        TextMessage textMessage = null;
        int randInt = getRandomInt();
        if(randInt==5){
            textMessage = new TextMessage("Sudah pasti Ya kalau itu");
        }
        else if(randInt==6){
            textMessage = new TextMessage("Pikir sendiri aja, tanya terus kayak Dora");
        }
        else if(randInt%2==0){
            textMessage = new TextMessage("Nggak");
        }
        else if(randInt%2!=0){
            textMessage = new TextMessage("Ya");
        }
        return textMessage;
    }

    private int getRandomInt(){
        return new Random().nextInt(10);
    }

}
