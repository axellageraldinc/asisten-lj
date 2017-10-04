package com.example.demo.Creator;

import com.example.demo.Dao.MainDao;
import com.example.demo.model.Main;

import java.util.Random;

public class TugasUjian {
    String id;

    public TugasUjian(String id){
        this.id = id;
    }

    Main main = new Main();
    Random random = new Random();
    StringBuilder sb = new StringBuilder();

    public int AddTugas(String desc){
        main.setId("TUGAS-" + desc.substring(0,3) + "-" + random.nextInt(999));
        main.setDeskripsi(desc);
        main.setTipe("tugas");

        int status_insert = MainDao.Insert(id, main);
        return status_insert;
    }

    public int AddUjian(String desc){
        main.setId("UJIAN-" + desc.substring(0,3) + "-" + random.nextInt(999));
        main.setDeskripsi(desc);
        main.setTipe("ujian");

        int status_insert = MainDao.Insert(id, main);
        return status_insert;
    }
}
