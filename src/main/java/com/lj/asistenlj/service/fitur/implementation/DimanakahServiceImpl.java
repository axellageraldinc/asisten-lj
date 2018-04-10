package com.lj.asistenlj.service.fitur.implementation;

import com.linecorp.bot.model.event.source.Source;
import com.linecorp.bot.model.message.TextMessage;
import com.lj.asistenlj.helper.Feature;
import com.lj.asistenlj.helper.Helper;
import com.lj.asistenlj.model.GroupMember;
import com.lj.asistenlj.repository.GroupMemberRepository;
import com.lj.asistenlj.service.FeatureDataService;
import com.lj.asistenlj.service.fitur.DimanakahService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class DimanakahServiceImpl implements DimanakahService {

    @Autowired
    private Helper helper;
    @Autowired
    private GroupMemberRepository groupMemberRepository;
    @Autowired
    private FeatureDataService featureDataService;

    @Override
    public TextMessage getResult(Source source) {
        featureDataService.saveFeatureData(Feature.DIMANAKAH);
        String groupId = helper.getId(source);
        String senderId = source.getSenderId();
        String type  = helper.getType(source);

        List<GroupMember> groupMemberList = groupMemberRepository.findAllByGroupId(groupId);
        int randInt = (int) (Math.random() * ((groupMemberList.size()-1)-0));
        GroupMember groupMemberBeruntung = groupMemberList.get(randInt);

        String usernameBeruntung = helper.getGroupMemberName(type, senderId, groupMemberBeruntung.getMemberId());
        return new TextMessage(randomTempat(usernameBeruntung));
    }

    private String randomTempat(String usernameBeruntung){
        String[] arrayTempat = new String[10];
        arrayTempat[0] = "di rumahnya ";
        arrayTempat[1] = "di kamarnya ";
        arrayTempat[2] = "jalan-jalan sama ";
        arrayTempat[3] = "di rumah, tidur, sendirian, sungguh menyedihkan.";
        arrayTempat[4] = "di kamar mandi, entah ngapain, hanya Tuhan yang tahu.";
        arrayTempat[5] = "di rumah, belajar. Contohlahlah ini, jangan malah mainan Asisten LJ terus ckckck";
        arrayTempat[6] = "di ujung langit, bersama seorang anak yang tangkas dan juga pemberani";
        arrayTempat[7] = "di kegelapan malam, mencari jawaban atas hidup ini.";

        int randTempat = new Random().nextInt((7 - 0) + 1) + 0;
        if(randTempat==3 || randTempat==4 || randTempat==5 || randTempat==6 || randTempat==7){
            usernameBeruntung = "";
        }
        return arrayTempat[randTempat] + usernameBeruntung;
    }

}
