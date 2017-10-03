package com.example.demo.Creator;

import com.linecorp.bot.model.message.TextMessage;

public class LoveCalculator {

    public TextMessage LoveCalculator(String name1, String name2){
        String concat = String.valueOf(name1).concat(String.valueOf(name2)).toUpperCase();
        int sum = 0;
        for (int i = 0; i < concat.length(); i++) {
            char character = concat.charAt(i);
            int ascii = (int) character;
            sum += ascii;
        }
        double loveRate = sum%100;
        String kataKata=null;
        if (loveRate>=0 && loveRate <=10)
            kataKata = name1 + " dan " + name2 + " sepertinya kurang cocok ya, mending cari yang lain aja..";
        else if(loveRate>10 && loveRate<=20)
            kataKata = name1 + " dan " + name2 + ", cinta kalian kurang kuat, butuh perbaikan lagi untuk dapat menjadi cinta yang sejati..";
        else if(loveRate>20 && loveRate<=30)
            kataKata = name1 + " dan " + name2 + " memiliki potensi untuk menjadi pasangan sejati, namun masih butuh usaha lebih untuk mencapai itu..";
        else if (loveRate>30 && loveRate<=40)
            kataKata = name1 + " dan " + name2 + " jangan menyerah, ada banyak cara untuk menumbuhkan kembali rasa cinta kalian..";
        else if(loveRate>40 && loveRate<=50)
            kataKata = name1 + " dan " + name2 + ", cinta kalian ada di batas antara cinta dan tidak cinta. Kalian perlu melakukan hal yang dulu sering kalian lakukan untuk menumbuhkan cinta kalian kembali..";
        else if(loveRate>50 && loveRate<=60)
            kataKata = name1 + " dan " + name2 + ", hubungan kalian masih bisa dibilang aman, namun hati-hati, bisa jadi ada orang ketiga yang dapat merusak semuanya..";
        else if(loveRate>60 && loveRate<=70)
            kataKata = name1 + " dan " + name2 + " memiliki kadar cinta yang terbilang besar, tetap pertahankan hal itu, maka kalian dapat menjadi pasangan sejati kelak..";
        else if(loveRate>70 && loveRate<=80)
            kataKata = name1 + " dan " + name2 + " memiliki hubungan yang teramat sangat mesra, membuat pasangan-pasangan lain iri pada kalian..";
        else if(loveRate>80 && loveRate<=90)
            kataKata = name1 + " dan " + name2 + " mencintai satu sama lain sepenuh hati, hampir tidak mungkin untuk mengganggu hubungan mereka berdua..";
        else if(loveRate>90 && loveRate<=100)
            kataKata = name1 + " dan " + name2 + ", kalian adalah arti sesungguhnya dari CINTA SEJATI. Tidak ada hal di dunia ini yang mampu memisahkan kalian.";
        TextMessage textMessage = new TextMessage("Kadar cinta : " + loveRate + "%\n" +
                kataKata);
        return textMessage;
    }
}
