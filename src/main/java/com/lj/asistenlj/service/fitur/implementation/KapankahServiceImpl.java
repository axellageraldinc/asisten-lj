package com.lj.asistenlj.service.fitur.implementation;

import com.linecorp.bot.model.event.source.Source;
import com.linecorp.bot.model.message.TextMessage;
import com.lj.asistenlj.helper.Feature;
import com.lj.asistenlj.helper.Helper;
import com.lj.asistenlj.service.FeatureDataService;
import com.lj.asistenlj.service.fitur.KapankahService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class KapankahServiceImpl implements KapankahService {

    @Autowired
    private Helper helper;
    @Autowired
    private FeatureDataService featureDataService;

    @Override
    public TextMessage getResult(Source source, String pesan) {
        featureDataService.saveFeatureData(Feature.KAPANKAH);
        int randInt = new Random().nextInt((3 - 1) + 1) + 1;
        String[] pesanSplit = pesan.split(" ");
        String kataTerakhirTanpaTandaTanya = helper.generateKataTerakhirTanpaTandaTanya(pesan, pesanSplit);
        String nama = pesanSplit[1].toLowerCase();
        nama = getReplyNama(nama);
        String predikat = generatePredikat(pesanSplit);
        return new TextMessage(randomKapankah(randInt, nama, predikat, kataTerakhirTanpaTandaTanya));
    }

    private String getReplyNama(String nama){
        if(nama.equals("aku") || nama.equals("saya") || nama.equals("gw") || nama.equals("gue") || nama.equals("gua")){
            nama = "kamu";
        } else if(nama.equals("kami") || nama.equals("kita")){
            nama = "kalian";
        } else if(nama.equals("kamu") || nama.equals("anda")){
            nama = "aku";
        } else if(nama.equals("kalian")){
            nama = "mereka";
        }
        return nama;
    }
    private String generatePredikat(String[] pesanSplit){
        StringBuilder predikat = new StringBuilder();
        for (int i=2; i<pesanSplit.length-1; i++){
            predikat.append(pesanSplit[i] + " ");
        }
        return String.valueOf(predikat);
    }
    private String randomKapankah(int randInt,
                                  String nama,
                                  String predikat,
                                  String kataTerakhirTanpaTandaTanya){
        Random random = new Random();
        int randHariBulanTahun;
        int randAngka;
        String result="";
        switch (randInt){
            case 1 : {
                randAngka = random.nextInt((30 - 2) + 1) + 2;
                randHariBulanTahun = random.nextInt(3);
                String hariBulanTahun = null;
                if(randAngka%2==0 && randHariBulanTahun%2==0)
                    hariBulanTahun = "hari";
                else if(randHariBulanTahun%2==0)
                    hariBulanTahun = "bulan";
                else if(randHariBulanTahun%2!=0)
                    hariBulanTahun = "tahun";
                result = nama + " " + String.valueOf(predikat).toLowerCase() + kataTerakhirTanpaTandaTanya.toLowerCase() + " " + randAngka + " " + hariBulanTahun + " lagi.";
                break;
            }
            case 2 : {
                String kata = null;
                randAngka = random.nextInt(4);
                if(randAngka==1)
                    kata = "besok";
                else if(randAngka==2)
                    kata = "tidak akan pernah";
                else if(randAngka%2==0)
                    kata = "hari ini";
                else
                    kata = "sudah dari kemarin";

                if(kata.equals("besok") || kata.equals("hari ini") || kata.equals("sudah dari kemarin"))
                    result = nama + " " + String.valueOf(predikat).toLowerCase() + kataTerakhirTanpaTandaTanya.toLowerCase() + " " + kata;
                else
                    result = nama + " " + kata + " " + String.valueOf(predikat).toLowerCase() + kataTerakhirTanpaTandaTanya.toLowerCase();
                break;
            }
            case 3 : {
                result = "Ruang dan waktu bukanlah batasan bagi " + nama + ", " + String.valueOf(predikat).toLowerCase() + kataTerakhirTanpaTandaTanya.toLowerCase() + " bisa kapan saja";
                break;
            }
        }
        return result;
    }

}
