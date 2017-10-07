package com.example.demo.Creator;

import com.linecorp.bot.model.action.PostbackAction;
import com.linecorp.bot.model.action.URIAction;
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
                        new CarouselColumn(null, "LJ AJAIB v1", "LJ Ajaib Apakah Siapakah", Arrays.asList(
                                new PostbackAction("How to LJ Ajaib v1",
                                        "/CARA-PAKAI-SIAPAKAH")
                        )),
                        new CarouselColumn(null, "LJ AJAIB v2", "LJ Ajaib Wajah", Arrays.asList(
                                new PostbackAction("How to LJ Ajaib v2",
                                        "/CARA-PAKAI-WAJAH")
                        )),
                        new CarouselColumn(null, "LJ AJAIB v3", "LJ Ajaib Cinta", Arrays.asList(
                                new PostbackAction("How to LJ Ajaib v3",
                                        "/CARA-PAKAI-CINTA")
                        )),
                        new CarouselColumn(null, "LJ AJAIB v4", "LJ Ajaib Instagram", Arrays.asList(
                                new PostbackAction("How to LJ Ajaib v4",
                                        "/CARA-PAKAI-INSTAGRAM")
                        )),
                        new CarouselColumn(null, "LJ AJAIB v5", "LJ Ajaib Dosa", Arrays.asList(
                                new PostbackAction("How to LJ Ajaib v5",
                                        "/CARA-PAKAI-DOSA")
                        ))
                ));
        return carouselTemplate;
    }
    public com.linecorp.bot.model.message.template.CarouselTemplate templateHiburan2(){
        com.linecorp.bot.model.message.template.CarouselTemplate carouselTemplate = new com.linecorp.bot.model.message.template.CarouselTemplate(
                Arrays.asList(
                        new CarouselColumn(null, "LJ AJAIB v6", "LJ Ajaib Kapankah", Arrays.asList(
                                new PostbackAction("How to LJ Ajaib v6",
                                        "/CARA-PAKAI-KAPANKAH")
                        ))
                ));
        return carouselTemplate;
    }

    public com.linecorp.bot.model.message.template.CarouselTemplate templateAbout1(){
        com.linecorp.bot.model.message.template.CarouselTemplate carouselTemplate = new com.linecorp.bot.model.message.template.CarouselTemplate(
                Arrays.asList(
                        new CarouselColumn("https://s1.postimg.org/9nr1wc2xkv/Raufi.png", "Raufi Musaddiq", "Kenapa harus satu kalau bisa banyak?", Arrays.asList(
                                new URIAction("INSTAGRAM",
                                        "https://www.instagram.com/raufimusaddiq/")
                        )),
                        new CarouselColumn("https://s1.postimg.org/701llzelzz/Farras.png", "Farras Aulia M", "If you love cats, then we should be friends :3", Arrays.asList(
                                new URIAction("INSTAGRAM",
                                        "https://www.instagram.com/simplyfarras/")
                        )),
                        new CarouselColumn("https://s1.postimg.org/1ag9ae9inz/Adin.png", "Bayu Adin H", "Sedikit berbeda lebih baik drpd sedikit lebih baik", Arrays.asList(
                                new URIAction("INSTAGRAM",
                                        "https://www.instagram.com/bayuadinh/")
                        )),
                        new CarouselColumn("https://s1.postimg.org/5tiaddq4u7/Rio.png", "Rio Armando M", "Pecandu senyuman, a beauty hunter", Arrays.asList(
                                new URIAction("INSTAGRAM",
                                        "https://www.instagram.com/rioarmandom/")
                        ))

                ));
        return carouselTemplate;
    }
    public com.linecorp.bot.model.message.template.CarouselTemplate templateAbout2(){
        com.linecorp.bot.model.message.template.CarouselTemplate carouselTemplate = new com.linecorp.bot.model.message.template.CarouselTemplate(
                Arrays.asList(
                        new CarouselColumn("https://s1.postimg.org/4qil2hknqn/Damas.png", "Sulistyo Damas P", "Rapper yang gak jago bahasa inggris", Arrays.asList(
                                new URIAction("INSTAGRAM",
                                        "https://www.instagram.com/sulistyodamas/")
                        )),
                        new CarouselColumn("https://s1.postimg.org/1z9iuf01pb/Adot.png", "Aditya Laksana S", "Fans nya bob dylan", Arrays.asList(
                                new URIAction("INSTAGRAM",
                                        "https://www.instagram.com/aditya.suwandi/")
                        )),
                        new CarouselColumn("https://s1.postimg.org/7lb98a2uj3/Putos.png", "Mahendra Tirta S", "Love music, sport, and computer stuff", Arrays.asList(
                                new URIAction("INSTAGRAM",
                                        "https://www.instagram.com/saputer/")
                        )),
                        new CarouselColumn("https://s1.postimg.org/5pyofnr9sf/Bless.png", "Bless Ramadewa", "Asumsi itu membunuhmu", Arrays.asList(
                                new URIAction("INSTAGRAM",
                                        "https://www.instagram.com/blesswastika/")
                        ))
                ));
        return carouselTemplate;
    }
    public com.linecorp.bot.model.message.template.CarouselTemplate templateAbout3(){
        com.linecorp.bot.model.message.template.CarouselTemplate carouselTemplate = new com.linecorp.bot.model.message.template.CarouselTemplate(
                Arrays.asList(
                        new CarouselColumn("https://s1.postimg.org/7hq4mj4xf3/Axel.png", "Axellageraldinc A", "Hai sayang..", Arrays.asList(
                                new URIAction("INSTAGRAM",
                                        "https://www.instagram.com/axellageraldinc/")
                        )),
                        new CarouselColumn("https://s1.postimg.org/2ypm7l7im7/Alman.png", "Almantera T A F", "Gak jelas anjas!", Arrays.asList(
                                new URIAction("INSTAGRAM",
                                        "https://www.instagram.com/almanalfaruq/")
                        )),
                        new CarouselColumn("https://s1.postimg.org/54p0tcuw1r/Azzum.png", "Moh Azzum Jordhan W", "Susah gak ada artinya kl bersama keluarga", Arrays.asList(
                                new URIAction("INSTAGRAM",
                                        "https://www.instagram.com/azzumjordhan/")
                        )),
                        new CarouselColumn("https://s1.postimg.org/6sydqjolof/Sam.png", "Samuel Tulus P", "Pekerjaan berat akan terasa ringan kl gak dikerjakan", Arrays.asList(
                                new URIAction("INSTAGRAM",
                                        "https://www.instagram.com/samuel_tulus/")
                        ))
                ));
        return carouselTemplate;
    }
}
