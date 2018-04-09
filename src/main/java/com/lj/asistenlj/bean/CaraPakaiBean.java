package com.lj.asistenlj.bean;

import com.linecorp.bot.model.message.TextMessage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CaraPakaiBean {

    @Bean
    public TextMessage caraPakaiSiapakah(){
        return new TextMessage("Cara Pakai LJ Ajaib v1\n\n" +
                "Ketikkan command dengan format :\n" +
                "Siapakah diantara [nama 1] dan [nama 2] yang ......\n" +
                "Contoh : Siapakah diantara Dedy dan Kepok yang paling tampan?\n" +
                "ATAU\n" +
                "Siapakah yang paling ...." +
                "Contoh : Siapakah yang paling tampan?\n");
    }
    @Bean
    public TextMessage caraPakaiWajah(){
        return new TextMessage("Cara Pakai LJ Ajaib v2\n\n" +
                "Ketikkan command /FACE-DETECT lalu tunggu sampai Asisten LJ membalas 'MULAI'.\n" +
                "Setelah itu, kirimlah foto dengan 1 wajah didalamnya untuk dideteksi oleh Asisten LJ.\n\n" +
                "Jika sudah selesai bermain-main, ketikkan command /STOP");
    }
    @Bean
    public TextMessage caraPakaiCinta(){
        return new TextMessage("Cara Pakai LJ Ajaib v3\n\n" +
                "Ketikkan command dengan format /love [spasi] [nama1] [spasi] [nama2]\n" +
                "untuk menghitung kadar cinta mereka.");
    }
    @Bean
    public TextMessage caraPakaiInstagram(){
        return new TextMessage("Cara Pakai LJ Ajaib v4\n\n" +
                "Ketikkan command dengan format /stalk [spasi] [username instagram]\n" +
                "Maka akan dibalas dengan foto yang dimiliki oleh akun instagram tersebut");
    }
    @Bean
    public TextMessage caraPakaiDosa(){
        return new TextMessage("Cara Pakai LJ Ajaib v5\n\n" +
                "Ketikkan command dengan format /dosa [spasi] [nama orang]\n" +
                "Contoh : /dosa saya\n" +
                "*ingat, ini cuma bercandaan doang yaaaa");
    }
    @Bean
    public TextMessage caraPakaiKapankah(){
        return new TextMessage("Cara Pakai LJ Ajaib v6\n\n" +
                "Ketikkan command dengan format : \n" +
                "Kapankah [nama] .....\n" +
                "Contoh : Kapankah dedy lulus?");
    }
    @Bean
    public TextMessage caraPakaiIslami(){
        return new TextMessage("Cara Pakai LJ Islami\n\n" +
                "Ketikkan command dengan format : \n" +
                "/jadwal-sholat [spasi][nama kota]\n" +
                "untuk melihat jadwal sholat kotamu");
    }
    @Bean
    public TextMessage caraPakaiDimanakah(){
        return new TextMessage("Cara Pakai LJ Ajaib v7\n\n" +
                "Ketikkan command dengan format : \n" +
                "Dimanakah [nama] berada?\n" +
                "Contoh : Dimanakah dio berada?");
    }

}
