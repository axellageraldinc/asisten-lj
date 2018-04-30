package com.lj.asistenlj.bean;

import com.linecorp.bot.model.message.TextMessage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CaraPakaiBean {

    @Bean
    public TextMessage caraPakaiSiapakah(){
        return new TextMessage("Cara Pakai LJ Ajaib Siapakah\n\n" +
                "Ketikkan command dengan format :\n" +
                "Siapakah diantara [nama 1] dan [nama 2] yang ......\n" +
                "Contoh : Siapakah diantara Dedy dan Kepok yang paling tampan?\n" +
                "ATAU\n" +
                "Siapakah yang paling ...." +
                "Contoh : Siapakah yang paling tampan?\n");
    }
    @Bean
    public TextMessage caraPakaiWajah(){
        return new TextMessage("Cara Pakai LJ Ajaib Face Detection\n\n" +
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

    // TODO : Uncomment the commented codes if the feature's already implemented
    @Bean
    public TextMessage caraPakaiInstagram(){
        return new TextMessage("Hallo!\n\n" +
                "Maaf ya fitur stalk instagram masih dalam proses..\n" +
                "Harap maklum ya kami juga gak dibayar untuk mengerjakan ini :(\n" +
                "Cuma mau menghibur kalian aja..");
//        return new TextMessage("Cara Pakai LJ Ajaib v4\n\n" +
//                "Ketikkan command dengan format /stalk [spasi] [username instagram]\n" +
//                "Maka akan dibalas dengan foto yang dimiliki oleh akun instagram tersebut");
    }
    @Bean
    public TextMessage caraPakaiDosa(){
        return new TextMessage("Cara Pakai LJ Ajaib Kalkulator Dosa\n\n" +
                "Ketikkan command dengan format /dosa [spasi] [nama orang]\n" +
                "Contoh : /dosa saya\n" +
                "*ingat, ini cuma bercandaan doang yaaaa");
    }
    @Bean
    public TextMessage caraPakaiKapankah(){
        return new TextMessage("Cara Pakai LJ Ajaib Kapankah\n\n" +
                "Ketikkan command dengan format : \n" +
                "Kapankah [nama] .....\n" +
                "Contoh : Kapankah dedy lulus?");
    }

    // TODO : Uncomment the commented codes if the feature's already implemented
    @Bean
    public TextMessage caraPakaiIslami(){
//        return new TextMessage("Cara Pakai LJ Islami\n\n" +
//                "Ketikkan command dengan format : \n" +
//                "/jadwal-sholat [spasi][nama kota]\n" +
//                "untuk melihat jadwal sholat kotamu");
        return new TextMessage("Hallo!\n\n" +
                "Maaf ya fitur jadwal sholat masih dalam proses..\n" +
                "Harap maklum ya kami juga gak dibayar untuk mengerjakan ini :(\n" +
                "Cuma mau menghibur kalian aja..");
    }
    @Bean
    public TextMessage caraPakaiDimanakah(){
        return new TextMessage("Cara Pakai LJ Ajaib Dimanakah\n\n" +
                "Ketikkan command dengan format : \n" +
                "Dimanakah [nama] berada?\n" +
                "Contoh : Dimanakah dio berada?");
    }

    @Bean
    public TextMessage caraPakaiTambahTempatMakanDimanaYa(){
        return new TextMessage("Cara pakai LJ Ajaib Makan Dimana Ya\n\n" +
                "1. Menambah database tempat makan :\n" +
                "   Ketikkan command dengan format : \n" +
                "   /makan-baru [spasi] [nama tempat makan], [lokasi tempat makan]\n" +
                "   Contoh : /makan-baru pizza hut, Jl. Jend. Sudirman\n" +
                "   *NB : Harap perhatikan penggunaan command, harus sama seperti itu.\n" +
                "         Jika berbeda, maka tempat makan akan gagal disimpan");
    }

    @Bean
    public TextMessage caraPakaiLihatSemuaDatabaseMakanDimanaYa(){
        return new TextMessage("Cara pakai LJ Ajaib Makan Dimana Ya\n\n" +
                "2. Melihat semua daftar tempat makan yang tersimpan :\n" +
                "   Ketikkan command dengan format : \n" +
                "   /list-tempat-makan\n" +
                "   Maka nanti akan keluar list tempat makan yang terdaftar di group/multichat tersebut");
    }

    @Bean
    public TextMessage caraPakaiMakanDimanaYa(){
        return new TextMessage("Cara pakai LJ Ajaib Makan Dimana Ya\n\n" +
                "3. Meminta rekomendasi tempat makan dari Asisten LJ:\n" +
                "   Ketikkan command dengan format : \n" +
                "   makan dimana ya enaknya?\n" +
                "   Maka Asisten LJ akan memberikan rekomendasi tempat makan berdasarkan database yang sudah disimpan");
    }

}
