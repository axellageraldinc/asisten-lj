package com.example.demo.Creator;

import com.linecorp.bot.model.message.TextMessage;

import java.util.Random;

public class Dimanakah {
    Random random = new Random();
    public TextMessage randomTempat(String name2){
        TextMessage textMessage;
        String[] arrayTempat = new String[10];
        arrayTempat[0] = "di rumahnya ";
        arrayTempat[1] = "di kamarnya ";
        arrayTempat[2] = "jalan-jalan sama ";
        arrayTempat[3] = "di rumah, tidur, sendirian, sungguh menyedihkan.";
        arrayTempat[4] = "di kamar mandi, entah ngapain, hanya Tuhan yang tahu.";
        arrayTempat[5] = "di rumah, belajar. Contohlahlah ini, jangan malah mainan Asisten LJ terus ckckck";
        arrayTempat[6] = "di ujung langit, bersama seorang anak yang tangkas dan juga pemberani";
        arrayTempat[7] = "di kegelapan malam, mencari jawaban atas hidup ini.";

        int randTempat = random.nextInt((7 - 0) + 1) + 0;
        if(randTempat==3 || randTempat==4 || randTempat==5 || randTempat==6 || randTempat==7){
            name2 = "";
        }
        textMessage = new TextMessage(arrayTempat[randTempat] + name2);
        return textMessage;
    }
}
