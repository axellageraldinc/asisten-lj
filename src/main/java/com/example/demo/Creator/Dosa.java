package com.example.demo.Creator;

import java.util.List;
import java.util.Random;

public class Dosa {
    public static String[] GenerateDosa(String nama){
        String[] data = new String[3];
        String katakata = null;
        Random random = new Random();
        int randInt = random.nextInt(99);
        if(randInt>=0 && randInt<40){
            katakata = "Bagus sekali ya " + String.valueOf(nama).toLowerCase() + ", pertahankan!";
        } else if(randInt>=40 && randInt<65){
            katakata = "Dosanya " + String.valueOf(nama).toLowerCase() + "masih batas wajar tapi sudah mulai nakal nih...";
        } else if(randInt>=65 && randInt<80){
            katakata = String.valueOf(nama).toLowerCase() + "harus bertobat sesegara mungkin! Ingatlah Tuhan nak!!!";
        } else if(randInt>=80 && randInt<=100){
            katakata = "Sudah tidak bisa dipungkiri bahwa " + String.valueOf(nama).toLowerCase() + "adalah sumber kejahatan di dunia ini.\nSegera jauhi dia!";
        }
        data[0] = katakata;
        data[1] = String.valueOf(randInt);
        return data;
    }
}
