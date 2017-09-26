package com.example.demo;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class GetInfoWebSarjana {

    public Document GetConnection() {
        Document doc = null;
        try {
            doc = Jsoup.connect("http://sarjana.jteti.ugm.ac.id/akademik").get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return doc;
    }

    public Elements getTableRow(){
        Document doc = GetConnection();
        Elements tableRow = doc.select("table.table-pad > tbody");
        return tableRow;
    }

    public String firstId(){
        Element link = getTableRow().select("tr").first();
        String trId = link.attr("id");
        return trId;
    }

    public Elements GetReadableBody() {
        Document doc = GetConnection();
        Elements tableRow = doc.select("table.table-pad > tbody > tr#" + firstId());
        Elements indexBody = tableRow.select("td:eq(2)");
        return indexBody;
    }

    public String GetWebDtetiTitle(){
        String title="";
        Document doc = GetConnection();
        title = doc.title();
        return title;
    }

    public String GetCategory(){
        String category="";
        Elements indexBody = GetReadableBody();
        for (Element row :indexBody){
            category = row.select("span.label").text();
        }
        return category;
    }

    public String GetDate(){
        String date="";
        Elements indexBody = GetReadableBody();
        for (Element row:indexBody){
            String[] body = row.text().split(" ");
            String title = row.select("b").text(); //judul pengumuman
            int titleSize = title.split(" ").length;
            int CategoryDanTanggal = titleSize + 4; //4 adalah kategori tambah tanggal, 4 adalah jumlah kata
            String description="";
            while (CategoryDanTanggal < body.length){
                description += body[CategoryDanTanggal] + " ";
                CategoryDanTanggal++;
            }
            //CategoryDanTanggal dimulai dari index ke-1 (yaitu suku kata tanggal)
            CategoryDanTanggal=1;
            //Kurang dari 4 karena indeks ke-4 sudah masuk description
            while (CategoryDanTanggal < 4){
                //suku kata ke-1 adalah tanggal, suku kata ke-2 adalah bulan, suku kata ke-3 adalah tahun, lalu stop looping
                date += body[CategoryDanTanggal] + " ";
                CategoryDanTanggal++;
            }
        }
        return date;
    }

    public String GetDescription(){
        String description="";
        Elements indexBody = GetReadableBody();
        for (Element row:indexBody){
            String[] body = row.text().split(" ");
            String title = row.select("b").text(); //judul pengumuman
            int titleSize = title.split(" ").length;
            int CategoryDanTanggal = titleSize + 4; //4 adalah kategori tambah tanggal, 4 adalah jumlah kata
            while (CategoryDanTanggal < body.length){
                description += body[CategoryDanTanggal] + " ";
                CategoryDanTanggal++;
            }
        }
        return description;
    }

    public String GetTitle() {
        String title="";
        Elements indexBody = GetReadableBody();
        for (Element row:indexBody) {
            String[] body = row.text().split(" ");
            title = row.select("b").text(); //judul pengumuman
        }
        return title;
    }

}
