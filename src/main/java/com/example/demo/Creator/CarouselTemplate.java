package com.example.demo.Creator;

import com.linecorp.bot.model.action.PostbackAction;
import com.linecorp.bot.model.message.template.CarouselColumn;

import java.util.Arrays;

public class CarouselTemplate {

    public com.linecorp.bot.model.message.template.CarouselTemplate templateKuliah(){
        com.linecorp.bot.model.message.template.CarouselTemplate carouselTemplate = new com.linecorp.bot.model.message.template.CarouselTemplate(
                Arrays.asList(
                        new CarouselColumn(null, "TUGAS", "Tambah/Lihat seputar Tugas", Arrays.asList(
                                new PostbackAction("Tambah Tugas",
                                        "/ADD-TUGAS"),
                                new PostbackAction("Lihat Tugas",
                                        "/SHOW-TUGAS"),
                                new PostbackAction("Hapus Tugas",
                                        "/HAPUS-TUGAS")
                        )),
                        new CarouselColumn(null, "UJIAN", "Tambah/Lihat seputar Ujian", Arrays.asList(
                                new PostbackAction("Tambah Ujian",
                                        "/ADD-UJIAN"),
                                new PostbackAction("Lihat Ujian",
                                        "/SHOW-UJIAN"),
                                new PostbackAction("Hapus Ujian",
                                        "/HAPUS-UJIAN")
                        ))
                ));
        return carouselTemplate;
    }

    public com.linecorp.bot.model.message.template.CarouselTemplate templateHiburan(){
        com.linecorp.bot.model.message.template.CarouselTemplate carouselTemplate = new com.linecorp.bot.model.message.template.CarouselTemplate(
                Arrays.asList(
                        new CarouselColumn(null, "LJ AJAIB v1", "LJ Ajaib Apakah", Arrays.asList(
                                new PostbackAction("How to LJ Ajaib v1",
                                        "/CARA-PAKAI-APAKAH")
                        )),
                        new CarouselColumn(null, "LJ AJAIB v2", "LJ Ajaib Siapakah", Arrays.asList(
                                new PostbackAction("How to LJ Ajaib v2",
                                        "/CARA-PAKAI-SIAPAKAH")
                        )),
                        new CarouselColumn(null, "LJ AJAIB v3", "LJ Ajaib Wajah", Arrays.asList(
                                new PostbackAction("How to LJ Ajaib v3",
                                        "/CARA-PAKAI-WAJAH")
                        )),
                        new CarouselColumn(null, "LJ AJAIB v4", "LJ Ajaib Cinta", Arrays.asList(
                                new PostbackAction("How to LJ Ajaib v4",
                                        "/CARA-PAKAI-CINTA")
                        )),
                        new CarouselColumn(null, "LJ AJAIB v5", "LJ Ajaib Instagram", Arrays.asList(
                                new PostbackAction("How to LJ Ajaib v5",
                                        "/CARA-PAKAI-INSTAGRAM")
                        )),
                        new CarouselColumn(null, "LJ AJAIB v6", "LJ Ajaib Dosa", Arrays.asList(
                                new PostbackAction("How to LJ Ajaib v6",
                                        "/CARA-PAKAI-DOSA")
                        ))
                ));
        return carouselTemplate;
    }

    public com.linecorp.bot.model.message.template.CarouselTemplate templateAbout(){
        com.linecorp.bot.model.message.template.CarouselTemplate carouselTemplate = new com.linecorp.bot.model.message.template.CarouselTemplate(
                Arrays.asList(
                        new CarouselColumn("https://www.dropbox.com/s/vwafgm1sq1iqeof/Axel.png?dl=0", "Axellageraldinc A", "Hai sayang..", Arrays.asList(
                                new PostbackAction("INSTAGRAM",
                                        "https://www.instagram.com/axellageraldinc/")
                        )),
                        new CarouselColumn("https://www.dropbox.com/s/z21033te9gy3qyd/Alman.png?dl=0", "Almantera T A F", "Gak jelas anjas!", Arrays.asList(
                                new PostbackAction("INSTAGRAM",
                                        "https://www.instagram.com/almanalfaruq/")
                        )),
                        new CarouselColumn("https://www.dropbox.com/s/17rb0gyaw5imhzj/Raufi.png?dl=0", "Raufi Musaddiq", "Kenapa harus satu kalau bisa banyak?", Arrays.asList(
                                new PostbackAction("INSTAGRAM",
                                        "https://www.instagram.com/raufimusaddiq/")
                        )),
                        new CarouselColumn("https://www.dropbox.com/s/s0hrzzmqvfg8gs5/Farras.png?dl=0", "Farras Aulia M", "If you love cats, then we should be friends :3", Arrays.asList(
                                new PostbackAction("INSTAGRAM",
                                        "https://www.instagram.com/simplyfarras/")
                        )),
                        new CarouselColumn("https://www.dropbox.com/s/xb1em05yv3a1o0z/Adin.png?dl=0", "Bayu Adin H", "Sedikit berbeda lebih baik drpd sedikit lebih baik", Arrays.asList(
                                new PostbackAction("INSTAGRAM",
                                        "https://www.instagram.com/bayuadinh/")
                        )),
                        new CarouselColumn("https://www.dropbox.com/s/l8pefq1tmlbo4rk/Damas.png?dl=0", "Sulistyo Damas P", "Rapper yang gak jago bahasa inggris", Arrays.asList(
                                new PostbackAction("INSTAGRAM",
                                        "https://www.instagram.com/sulistyodamas/")
                        )),
                        new CarouselColumn("https://www.dropbox.com/s/1r5dlm8pgggsugd/Adot.png?dl=0", "Aditya Laksana S", "Fans nya bob dylan", Arrays.asList(
                                new PostbackAction("INSTAGRAM",
                                        "https://www.instagram.com/aditya.suwandi/")
                        )),
                        new CarouselColumn("https://www.dropbox.com/s/ayu81drc6rtkvtg/Putos.png?dl=0", "Mahendra Tirta S", "Love music, sport, and computer stuff", Arrays.asList(
                                new PostbackAction("INSTAGRAM",
                                        "https://www.instagram.com/saputer/")
                        )),
                        new CarouselColumn("https://www.dropbox.com/s/91w4zhu82ymwfc7/Bless.png?dl=0", "Bless Ramadewa", "Saya suka nasi goreng", Arrays.asList(
                                new PostbackAction("INSTAGRAM",
                                        "https://www.instagram.com/blesswastika/")
                        )),
                        new CarouselColumn("https://www.dropbox.com/s/8l2iz8kkyebmn1i/Rio.png?dl=0", "Rio Armando M", "Pecandu senyuman, a beauty hunter", Arrays.asList(
                                new PostbackAction("INSTAGRAM",
                                        "https://www.instagram.com/rioarmandom/")
                        )),
                        new CarouselColumn("https://www.dropbox.com/s/hj3rw5ai4a9sdci/Azzum.png?dl=0", "Moh Azzum Jordhan W", "Aku paling ganteng", Arrays.asList(
                                new PostbackAction("INSTAGRAM",
                                        "https://www.instagram.com/azzumjordhan/")
                        )),
                        new CarouselColumn("https://www.dropbox.com/s/b1rout864gpbb38/Sam.png?dl=0", "Samuel Tulus P", "Terlahir penjahat", Arrays.asList(
                                new PostbackAction("INSTAGRAM",
                                        "https://www.instagram.com/samuel_tulus/")
                        ))

                ));
        return carouselTemplate;
    }
}
