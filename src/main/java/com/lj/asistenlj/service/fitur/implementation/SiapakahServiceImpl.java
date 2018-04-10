package com.lj.asistenlj.service.fitur.implementation;

import com.linecorp.bot.model.event.source.Source;
import com.linecorp.bot.model.message.TextMessage;
import com.lj.asistenlj.helper.Feature;
import com.lj.asistenlj.helper.Helper;
import com.lj.asistenlj.model.GroupMember;
import com.lj.asistenlj.repository.GroupMemberRepository;
import com.lj.asistenlj.service.FeatureDataService;
import com.lj.asistenlj.service.fitur.SiapakahService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SiapakahServiceImpl implements SiapakahService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SiapakahServiceImpl.class);

    @Autowired
    private FeatureDataService featureDataService;
    @Autowired
    private Helper helper;
    @Autowired
    private GroupMemberRepository groupMemberRepository;

    @Override
    public TextMessage getResult(String pesan, Source source) {
        featureDataService.saveFeatureData(Feature.SIAPAKAH);
        String[] pesanSplit = pesan.split(" ");
        TextMessage textMessage = null;

        String groupId = helper.getId(source);
        List<GroupMember> groupMemberList = getAllMemberOfGroup(groupId);
//        for (GroupMember item:groupMemberList){
//            LOGGER.info("Siapakah fitur get all member list\n" + item.getGroupId() + "\n" +
//                    item.getMemberId());
//        }
        int randInt = getRandomInt(groupMemberList);

        if(!pesanSplit[1].equals("YANG") &&
                !pesanSplit[1].equals("ANTARA") &&
                !pesanSplit[2].equals("ANTARA") &&
                !pesanSplit[1].equals("DIANTARA")){
            textMessage = new TextMessage("Salah format woy\n" +
                    "Yang bener itu siapakah yang... atau siapakah diantara ...dan...yang...");
        } else if(pesanSplit[1].equals("YANG")){
            GroupMember groupMemberBeruntung = groupMemberList.get(randInt);
            String type = helper.getType(source);
            String senderId = source.getSenderId();
            String memberId = groupMemberBeruntung.getMemberId();
            String usernameBeruntung;
            if(memberId == null){
                usernameBeruntung = "Tidak ada";
            } else {
                usernameBeruntung = helper.getGroupMemberName(type, senderId, memberId);
            }
            String kalimatYangPaling = generateKalimatYangPaling(pesanSplit, 1);
            String kataTerakhirTanpaTandaTanya = helper.generateKataTerakhirTanpaTandaTanya(pesan, pesanSplit);
            textMessage = new TextMessage(usernameBeruntung + " " + String.valueOf(kalimatYangPaling).toLowerCase() + String.valueOf(kataTerakhirTanpaTandaTanya).toLowerCase());
        } else{
            int indexDan = getIndexDan(pesanSplit);
            int indexYang = getIndexYang(pesanSplit);
            String nama1="";
            if(pesanSplit[1].equals("ANTARA")){
                nama1 = getFirstName(pesanSplit, 2, indexDan);
            } else if(pesanSplit[2].equals("ANTARA")){
                nama1 = getFirstName(pesanSplit, 3, indexDan);
            } else if(pesanSplit[1].equals("DIANTARA")){
                nama1 = getFirstName(pesanSplit, 2, indexDan);
            }
            String nama2 = getSecondName(pesanSplit, indexDan, indexYang);
            String kalimatYangPaling = generateKalimatYangPaling(pesanSplit, indexYang);
            String kataTerakhirTanpaTandaTanya = helper.generateKataTerakhirTanpaTandaTanya(pesan, pesanSplit);
            if(randInt%2==0){
                textMessage = new TextMessage(nama1 + kalimatYangPaling + kataTerakhirTanpaTandaTanya);
            } else{
                textMessage = new TextMessage(nama2 + kalimatYangPaling + kataTerakhirTanpaTandaTanya);
            }
        }
        return textMessage;
    }

    private List<GroupMember> getAllMemberOfGroup(String groupId){
        return groupMemberRepository.findAllByGroupId(groupId);
    }
    private int getRandomInt(List<GroupMember> memberList){
        return (int) (Math.random() * ((memberList.size()-1)-0));
    }

    private String generateKalimatYangPaling(String[] pesanSplit, int startIndex){
        StringBuilder kalimatYangPaling = new StringBuilder();
        for (int i=startIndex; i<pesanSplit.length-1; i++){
            kalimatYangPaling.append(pesanSplit[i] + " "); //Men-generate kalimat yang paling .......
        }
        return String.valueOf(kalimatYangPaling);
    }

    private int getIndexDan(String[] pesanSplit){
        int indexDan = 0;
        for(int i=0;i<pesanSplit.length; i++){
            if (pesanSplit[i].equals("DAN"))
                indexDan=i;
        }
        return indexDan;
    }
    private int getIndexYang(String[] pesanSplit){
        int indexYang = 0;
        for(int i=0;i<pesanSplit.length; i++){
            if (pesanSplit[i].equals("YANG"))
                indexYang=i;
        }
        return indexYang;
    }
    private String getFirstName(String[] pesanSplit, int startIndex, int indexDan){
        StringBuilder nama1 = new StringBuilder();
        for(int i=startIndex; i<indexDan;i++){
            nama1.append(pesanSplit[i] + " ");
        }
        return String.valueOf(nama1);
    }
    private String getSecondName(String[] pesanSplit, int indexDan, int indexYang){
        StringBuilder nama2 = new StringBuilder();
        for(int i=indexDan+1; i<indexYang;i++){
            nama2.append(pesanSplit[i] + " ");
        }
        return String.valueOf(nama2);
    }

}
