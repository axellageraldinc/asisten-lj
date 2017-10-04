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
                        ))
                ));
        return carouselTemplate;
    }

    public com.linecorp.bot.model.message.template.CarouselTemplate templateAbout() {
        String imgDefault = "http://www.freeiconspng.com/uploads/profile-icon-9.png";
        String imgAlman = "https://avatars0.githubusercontent.com/u/17737346?v=4&s=460";
        String imgAxell = "https://avatars1.githubusercontent.com/u/19605159?v=4&s=460";
        com.linecorp.bot.model.message.template.CarouselTemplate carouselTemplate = new com.linecorp.bot.model.message.template.CarouselTemplate(
                Arrays.asList(
                        new CarouselColumn(imgAxell, "Axellageraldinc A.", "Creator Asisten LJ", null),
                        new CarouselColumn(imgAlman, "Almantera T. A. F.", "Contributor Asisten LJ", null),
                        new CarouselColumn(imgDefault, "Aditya L. S.", "Member LJ", null),
                        new CarouselColumn(imgDefault, "Aditya N. U.", "Member LJ", null),
                        new CarouselColumn(imgDefault, "Alexander J. H.", "Member LJ", null),
                        new CarouselColumn(imgDefault, "Bayu Adin H", "Sponsor Asisten LJ", null),
                        new CarouselColumn(imgDefault, "Bless Ramadewa", "Member LJ", null),
                        new CarouselColumn(imgDefault, "Dedy K. S.", "Member LJ", null),
                        new CarouselColumn(imgDefault, "Farras Aulia Muhammad", "Member LJ", null),
                        new CarouselColumn(imgDefault, "Faturrahman", "Member LJ", null),
                        new CarouselColumn(imgDefault, "Hera Prasetia", "Member LJ", null),
                        new CarouselColumn(imgDefault, "Mahendra T. S.", "Member LJ", null),
                        new CarouselColumn(imgDefault, "Moh. Azzum J. W.", "Member LJ", null),
                        new CarouselColumn(imgDefault, "Raufi Musaddiq", "Member LJ", null),
                        new CarouselColumn(imgDefault, "Rio Armando M.", "Member LJ", null),
                        new CarouselColumn(imgDefault, "Samuel P.", "Member LJ", null),
                        new CarouselColumn(imgDefault, "Sulistyo D. P.", "Member LJ", null)
                ));
        return carouselTemplate;
    }
}
