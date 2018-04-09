package com.lj.asistenlj.service.fitur.implementation;

import com.linecorp.bot.model.message.TextMessage;
import com.lj.asistenlj.service.fitur.DosaService;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class DosaServiceImpl implements DosaService {

    @Override
    public TextMessage getDosaResult(String pesan) {
        String[] pesanSplit = pesan.split(" ");
        String name = buildName(pesanSplit);
        String dosa = getDosa(name);
        return new TextMessage(dosa);
    }

    private String buildName(String[] pesanSplit){
        StringBuilder name = new StringBuilder();
        for (int i=1; i<pesanSplit.length; i++){
            name.append(pesanSplit[i]);
        }
        return String.valueOf(name);
    }
    private String getDosa(String name){
        int randInt = new Random().nextInt(99);
        String result = "";
        if(randInt>=0 && randInt<40){
            result = "Bagus sekali ya " + String.valueOf(name).toLowerCase() + " dosanya " + randInt + "%" + ", pertahankan!";
        } else if(randInt>=40 && randInt<65){
            result = "Dosanya " + String.valueOf(name).toLowerCase() + " " + randInt + "%" + " masih batas wajar tapi sudah mulai nakal nih...";
        } else if(randInt>=65 && randInt<80){
            result = String.valueOf(name).toLowerCase() + " harus bertobat sesegara mungkin! Ingatlah Tuhan nak!!! Masa iya dosanya " + randInt + "%";
        } else if(randInt>=80 && randInt<=100){
            result = "Sudah tidak bisa dipungkiri bahwa " + String.valueOf(name).toLowerCase() + " adalah sumber kejahatan di dunia ini.\nSegera jauhi dia!\nDosanya " + randInt + "%";
        } else{
            result = String.valueOf(name).toLowerCase() + " makhluk ini tidak terdeteksi sehingga dosanya tidak bisa dihitung";
        }
        return result;
    }

}
