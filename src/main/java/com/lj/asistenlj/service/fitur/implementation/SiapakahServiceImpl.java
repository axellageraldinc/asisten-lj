package com.lj.asistenlj.service.fitur.implementation;

import com.linecorp.bot.model.event.source.Source;
import com.linecorp.bot.model.message.TextMessage;
import com.lj.asistenlj.helper.Helper;
import com.lj.asistenlj.model.GroupMember;
import com.lj.asistenlj.repository.GroupMemberRepository;
import com.lj.asistenlj.service.fitur.SiapakahService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SiapakahServiceImpl implements SiapakahService {

    @Autowired
    private Helper helper;
    @Autowired
    private GroupMemberRepository groupMemberRepository;

    @Override
    public TextMessage getResult(String pesan, Source source) {
        String[] pesanSplit = pesan.split(" ");
        TextMessage textMessage = null;

        String groupId = helper.getId(source);
        List<GroupMember> groupMemberList = getAllMemberOfGroup(groupId);
        int randInt = getRandomInt(groupMemberList);

        if(pesanSplit[1].equals("YANG")){
            GroupMember groupMemberBeruntung = groupMemberList.get(randInt);
            String type = helper.getType(source);
            String senderId = source.getSenderId();
            String usernameBeruntung = helper.getGroupMemberName(type, senderId, groupMemberBeruntung.getMemberId());
            String kalimatYangPaling = generateKalimatYangPaling(pesanSplit, 1);
            String kataTerakhirTanpaTandaTanya = generateKataTerakhirTanpaTandaTanya(pesan, pesanSplit);
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
            String kataTerakhirTanpaTandaTanya = generateKataTerakhirTanpaTandaTanya(pesan, pesanSplit);
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
    private String generateKataTerakhirTanpaTandaTanya(String pesan, String[] pesanSplit){
        char[] kataTerakhir;
        StringBuilder kataTerakhirTanpaTanya = new StringBuilder();
        if(pesan.contains("?")){
            kataTerakhir = String.valueOf(pesanSplit[pesanSplit.length-1]).toCharArray(); //kata terakhir dipecah-pecah jadi perhuruf
            for (int i=0;i<kataTerakhir.length-1; i++){
                kataTerakhirTanpaTanya.append(kataTerakhir[i]); //menggabungkan huruf2 yang dipecah tadi jadi satu tapi minus tanda tanya
            }
        } else{
            kataTerakhirTanpaTanya.append(pesanSplit[pesanSplit.length-1]);
        }
        return String.valueOf(kataTerakhirTanpaTanya);
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
