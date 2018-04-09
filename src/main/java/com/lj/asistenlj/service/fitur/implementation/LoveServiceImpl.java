package com.lj.asistenlj.service.fitur.implementation;

import com.linecorp.bot.model.message.TextMessage;
import com.lj.asistenlj.service.fitur.LoveService;
import org.springframework.stereotype.Service;

@Service
public class LoveServiceImpl implements LoveService {

    @Override
    public TextMessage getLoveCalculatorResult(String pesan) {
        String[] pesanSplit = pesan.split(" ");
        TextMessage textMessage = null;
        if(pesanSplit.length<3){
            textMessage = new TextMessage("Harus ada 2 nama yaaaa");
        } else if(pesanSplit.length>3){
            textMessage = new TextMessage("Hayoooo, gak boleh ada orang ketiga atau lebih. Maksimal 2 orang aja ya");
        } else {
            String name1 = pesanSplit[1];
            String name2 = pesanSplit[2];
            String mergedNames = getMergedNames(name1, name2);
            int sumOfMergedNames = getSumOfMergedNames(mergedNames);
            textMessage = new TextMessage(getLoveResult(sumOfMergedNames, name1, name2));
        }
        return textMessage;
    }


    private String getMergedNames(String name1, String name2){
        return String.valueOf(name1).concat(String.valueOf(name2)).toUpperCase();
    }
    private int getSumOfMergedNames(String mergedNames){
        int sum = 0;
        for (int i = 0; i < mergedNames.length(); i++) {
            char character = mergedNames.charAt(i);
            int ascii = (int) character;
            sum += ascii;
        }
        return sum;
    }
    private String getLoveResult(int sumOfMergedNames, String name1, String name2){
        double loveRate = sumOfMergedNames%100;
        String loveResult = "";
        if (loveRate>=0 && loveRate <=10)
            loveResult = name1 + " dan " + name2 + " sepertinya kurang cocok ya, mending cari yang lain aja..";
        else if(loveRate>10 && loveRate<=20)
            loveResult = name1 + " dan " + name2 + ", cinta kalian kurang kuat, butuh perbaikan lagi untuk dapat menjadi cinta yang sejati..";
        else if(loveRate>20 && loveRate<=30)
            loveResult = name1 + " dan " + name2 + " memiliki potensi untuk menjadi pasangan sejati, namun masih butuh usaha lebih untuk mencapai itu..";
        else if (loveRate>30 && loveRate<=40)
            loveResult = name1 + " dan " + name2 + " jangan menyerah, ada banyak cara untuk menumbuhkan kembali rasa cinta kalian..";
        else if(loveRate>40 && loveRate<=50)
            loveResult = name1 + " dan " + name2 + ", cinta kalian ada di batas antara cinta dan tidak cinta. Kalian perlu melakukan hal yang dulu sering kalian lakukan untuk menumbuhkan cinta kalian kembali..";
        else if(loveRate>50 && loveRate<=60)
            loveResult = name1 + " dan " + name2 + ", hubungan kalian masih bisa dibilang aman, namun hati-hati, bisa jadi ada orang ketiga yang dapat merusak semuanya..";
        else if(loveRate>60 && loveRate<=70)
            loveResult = name1 + " dan " + name2 + " memiliki kadar cinta yang terbilang besar, tetap pertahankan hal itu, maka kalian dapat menjadi pasangan sejati kelak..";
        else if(loveRate>70 && loveRate<=80)
            loveResult = name1 + " dan " + name2 + " memiliki hubungan yang teramat sangat mesra, membuat pasangan-pasangan lain iri pada kalian..";
        else if(loveRate>80 && loveRate<=90)
            loveResult = name1 + " dan " + name2 + " mencintai satu sama lain sepenuh hati, hampir tidak mungkin untuk mengganggu hubungan mereka berdua..";
        else if(loveRate>90 && loveRate<=100)
            loveResult = name1 + " dan " + name2 + ", kalian adalah arti sesungguhnya dari CINTA SEJATI. Tidak ada hal di dunia ini yang mampu memisahkan kalian.";
        return loveResult;
    }
}
