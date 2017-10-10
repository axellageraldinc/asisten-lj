package com.example.demo.Creator;

import com.linecorp.bot.model.message.TextMessage;

import java.util.Random;

public class Dimanakah {
    Random random = new Random();
    public TextMessage randomTempat(String name1, String name2){
        TextMessage textMessage;
        String[] arrayTempat = new String[10];
        arrayTempat[0] = "di rumahnya ";
        arrayTempat[1] = "di kamarnya ";
        arrayTempat[2] = "jalan-jalan sama ";
        arrayTempat[3] = "di rumah, tidur, sendirian, sungguh menyedihkan.";
        arrayTempat[4] = "di kamar mandi, hanya dia dan Tuhan yang tau apa yang dia lakukan disitu.";
        arrayTempat[5] = "di rumah, belajar. Contohlah orang seperti ini, jangan malah mainan Asisten LJ terus ckckck";

        int randTempat = random.nextInt((5 - 0) + 1) + 0;
        if(randTempat==3 || randTempat==4 || randTempat==5){
            name2 = "";
        }
        textMessage = new TextMessage(name1 + "sedang " + arrayTempat[randTempat] + name2);
        return textMessage;
    }
}
