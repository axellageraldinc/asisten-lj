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
                        ))
                ));
        return carouselTemplate;
    }
}
