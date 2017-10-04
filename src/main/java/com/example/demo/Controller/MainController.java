package com.example.demo.Controller;

import com.example.demo.Creator.*;
import com.example.demo.Dao.MainDao;
import com.example.demo.Getter.Getter;
import com.example.demo.Leave.Leave;
import com.example.demo.model.GroupMember;
import com.example.demo.model.Main;
import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.event.*;
import com.linecorp.bot.model.event.message.ImageMessageContent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.event.source.Source;
import com.linecorp.bot.model.message.*;
import com.linecorp.bot.model.response.BotApiResponse;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;
import me.postaddict.instagram.scraper.Instagram;
import me.postaddict.instagram.scraper.domain.Media;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.*;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadLocalRandom;

@LineMessageHandler
public class MainController {

    @Autowired
    private LineMessagingClient lineMessagingClient;

    private static String AccessToken = "u/jyVKXsD5N/OfmNIvEjnI+NffMIhzcFFjIZ3Whm4Gu9/LTL4y7WjWhWehHjYIO+aG6QUKw5991HFzs7i8c1PAZP07r1LIGun6o8X53yZflIk/Th0W8JkY9G/2IpWkL59subrXO5cOQCxJqjemzHvwdB04t89/1O/w1cDnyilFU=";

    Random random = new Random();

    CarouselTemplate carouselTemplate = new CarouselTemplate();
    Getter getter = new Getter(AccessToken);

    @EventMapping
    public void handleJoinNewGroup(JoinEvent joinEvent) {
        List<Message> messageList = new ArrayList<>();
        Source source = joinEvent.getSource();
        StickerMessage stickerMessage = new StickerMessage("1", "2");
        messageList.add(stickerMessage);
        TextMessage textMessage = new TextMessage("Nuwun yo aku wes entuk join grup iki\n" +
                "Silakan ketik /fitur untuk melihat fitur-fitur yang ada.");
        messageList.add(textMessage);
        KirimPesan(joinEvent.getReplyToken(), messageList);
    }

    @EventMapping
    public void handleNewFollower(FollowEvent followEvent){
        List<Message> messageList = new ArrayList<>();
        Source source = followEvent.getSource();
        String id_umum = getter.getId(source);
        MainDao.CreateTableData(id_umum);
        StickerMessage stickerMessage = new StickerMessage("1", "2");
        messageList.add(stickerMessage);
        TextMessage textMessage = new TextMessage("Nuwun yo aku wes di-add dadi friend\n" +
                "Silakan ketik /fitur untuk melihat fitur-fitur yang ada.\n" +
                "Aku di invite ning group yo iso lhoooo");
        messageList.add(textMessage);
        KirimPesan(followEvent.getReplyToken(), messageList);
    }

    @EventMapping
    public void handleText(MessageEvent<TextMessageContent> msg) {
        handleContent(msg.getReplyToken(), msg, msg.getMessage());
    }

    @EventMapping
    public void handleImage(MessageEvent<ImageMessageContent> img){
        String group_id = getter.getId(img.getSource());
        if(MainDao.getStatus(group_id)==1){
            ImageMessageContent imageMessageContent = img.getMessage();
            String id_umum = imageMessageContent.getId();
            FaceDetector faceDetector = new FaceDetector();
            TextMessage textMessage = faceDetector.handleImageContent(id_umum);
            KirimPesan(img.getReplyToken(), textMessage);
            System.out.println("ID MESSAGE IMAGE : " + id_umum);
        }
    }

    @EventMapping
    public void handlePostback(PostbackEvent event){
        List<Message> messageList = new ArrayList<>();
        Source source = event.getSource();
        String postback_response = event.getPostbackContent().getData();
        String id_umum = getter.getId(source);
        switch (postback_response){
            case "/ADD-TUGAS" : {
                messageList.clear();
                TextMessage textMessage = new TextMessage("Kirim deskripsi tugas selengkap mungkin (makul, disuruh ngapain, deadline, dikumpul kemana, dll)");
                messageList.add(textMessage);
                textMessage = new TextMessage("Perhatian!\n" +
                        "Kirim deskripsi tugas dengan format /tugas [spasi] [deskripsi]\n" +
                        "Contoh : /tugas progdas bikin kalkulator deadline senin");
                messageList.add(textMessage);
                break;
            }
            case "/SHOW-TUGAS" : {
                TextMessage textMessage;
                messageList.clear();
                List<Main> mainList = MainDao.GetAll(id_umum, "tugas");
                StringBuilder sb = new StringBuilder();
                int nomor=1;
                sb.append("LIST TUGAS\n\n");
                for (Main item: mainList) {
                    sb.append(nomor + ".\n" +
                            "ID : " + item.getId() + "\n" +
                            item.getDeskripsi() + "\n");
                    nomor++;
                }
                if(sb.equals(null))
                    textMessage = new TextMessage("Belum ada list tugas");
                else
                    textMessage = new TextMessage(String.valueOf(sb));
                messageList.add(textMessage);
                break;
            }
            case "/ADD-UJIAN" : {
                TextMessage textMessage;
                messageList.clear();
                textMessage = new TextMessage("Kirim deskripsi ujian selengkap mungkin (makul, sifat ujian, materi apa aja, dll)");
                messageList.add(textMessage);
                textMessage = new TextMessage("Perhatian!\n" +
                        "Kirim deskripsi ujian dengan format /ujian [spasi] [deskripsi]\n" +
                        "Contoh : /ujian progdas open A4 tinta biru");
                messageList.add(textMessage);
                break;
            }
            case "/SHOW-UJIAN" : {
                TextMessage textMessage;
                messageList.clear();
                List<Main> mainList = MainDao.GetAll(id_umum, "ujian");
                StringBuilder sb = new StringBuilder();
                int nomor=1;
                sb.append("LIST UJIAN\n\n");
                for (Main item: mainList) {
                    sb.append(nomor + ".\n" +
                            "ID : " + item.getId() + "\n" +
                            item.getDeskripsi() + "\n");
                    nomor++;
                }
                if(sb.equals(null))
                    textMessage = new TextMessage("Belum ada postback_response tugas");
                else
                    textMessage = new TextMessage(String.valueOf(sb));
                messageList.add(textMessage);
                break;
            }
            case "/HAPUS-TUGAS" : {
                TextMessage textMessage;
                messageList.clear();
                List<Main> mainList = MainDao.GetAll(id_umum, "tugas");
                StringBuilder sb = new StringBuilder();
                int nomor=1;
                for (Main item: mainList) {
                    sb.append(nomor + ".\n" +
                            "ID : " + item.getId() + "\n" +
                            item.getDeskripsi() + "\n");
                    nomor++;
                }
                textMessage = new TextMessage(String.valueOf(sb));
                messageList.add(textMessage);
                textMessage = new TextMessage("Kirim command dengan format /hapus [spasi] [ID]");
                messageList.add(textMessage);
                textMessage = new TextMessage("List ID tugas bisa dilihat di atas");
                messageList.add(textMessage);
                break;
            }
            case "/HAPUS-UJIAN" : {
                TextMessage textMessage;
                messageList.clear();
                List<Main> mainList = MainDao.GetAll(id_umum, "ujian");
                StringBuilder sb = new StringBuilder();
                int nomor=1;
                for (Main item: mainList) {
                    sb.append(nomor + ".\n" +
                            "ID : " + item.getId() + "\n" +
                            item.getDeskripsi() + "\n");
                    nomor++;
                }
                textMessage = new TextMessage(String.valueOf(sb));
                messageList.add(textMessage);
                textMessage = new TextMessage("Kirim command dengan format /hapus [spasi] [ID]");
                messageList.add(textMessage);
                textMessage = new TextMessage("List ID ujian bisa dilihat di atas");
                messageList.add(textMessage);
                break;
            }
            case "/CARA-PAKAI-APAKAH" : {
                TextMessage textMessage;
                messageList.clear();
                textMessage = new TextMessage("Cara Pakai LJ Ajaib v1\n\n" +
                        "Ketikkan command dengan format :\n" +
                        "Apakah .......\n" +
                        "Contoh : Apakah dedy tampan?");
                messageList.add(textMessage);
                break;
            }
            case "/CARA-PAKAI-SIAPAKAH" : {
                TextMessage textMessage;
                messageList.clear();
                textMessage = new TextMessage("Cara Pakai LJ Ajaib v2\n\n" +
                        "Ketikkan command dengan format :\n" +
                        "Siapakah diantara [nama 1] dan [nama 2] yang ......\n" +
                        "Contoh : Siapakah diantara Dedy dan Kepok yang paling tampan?\n" +
                        "ATAU\n" +
                        "Siapakah yang paling ...." +
                        "Contoh : Siapakah yang paling tampan?");
                messageList.add(textMessage);
                break;
            }
            case "/CARA-PAKAI-WAJAH" : {
                TextMessage textMessage;
                messageList.clear();
                textMessage = new TextMessage("Cara Pakai LJ Ajaib v3\n\n" +
                        "Ketikkan command /FACE-DETECT lalu tunggu sampai LJ BOT membalas 'MULAI'.\n" +
                        "Setelah itu, kirimlah foto dengan 1 wajah didalamnya untuk dideteksi oleh LJ BOT.\n\n" +
                        "Jika sudah selesai bermain-main, ketikkan command /STOP");
                messageList.add(textMessage);
                break;
            }
            case "/CARA-PAKAI-CINTA" : {
                TextMessage textMessage;
                messageList.clear();
                textMessage = new TextMessage("Cara Pakai LJ Ajaib v4\n\n" +
                        "Ketikkan command dengan format /love [spasi] [nama1] [spasi] [nama2]\n" +
                        "untuk menghitung kadar cinta mereka.");
                messageList.add(textMessage);
                break;
            }
            case "/CARA-PAKAI-INSTAGRAM" : {
                TextMessage textMessage;
                messageList.clear();
                textMessage = new TextMessage("CaraPakai LJ Ajaib v5\n\n" +
                        "Ketikkan command dengan format /stalk [spasi] [username instagram]\n" +
                        "Maka akan dibalas dengan foto yang dimiliki oleh akun instagram tersebut");
                messageList.add(textMessage);
                break;
            }
        }
        KirimPesan(event.getReplyToken(), messageList);
    }

    public void handleContent(String replyToken, Event event, TextMessageContent content){
        String pesan = content.getText().toUpperCase();
        String[] pesan_split = pesan.split(" ");
        int pesan_split_length = pesan_split.length;

        Source source = event.getSource();
        String id_umum = getter.getId(source);
        String user_id = event.getSource().getUserId();
        String group_id = getter.getId(source);

        MainDao.CreateTableData(id_umum);
        MainDao.CreateTableGroupMember(id_umum);

        String command = null;
        if(pesan.equals("/FITUR")){
            command = "/FITUR";
        } else if(pesan.equals("/HIBURAN")){
            command = "/HIBURAN";
        } else if(pesan.equals("/PERKULIAHAN")){
            command = "/PERKULIAHAN";
        } else if(pesan_split[0].equals("/TUGAS")){
            command = "/TUGAS";
        } else if(pesan_split[0].equals("/UJIAN")){
            command = "/UJIAN";
        } else if(pesan_split[0].equals("/HAPUS")){
            if (pesan.substring(7, 12).equals("TUGAS")) {
                command = "/HPT";
            } else if(pesan.substring(7, 12).equals("UJIAN")){
                command = "/HPJ";
            }
        } else if(pesan.equals("/FACE-DETECT")){
            command = "/FACE-DETECT";
        } else if(pesan.equals("/STOP")){
            command = "/FACE-STOP";
        } else if(pesan_split[0].equals("SIAPAKAH")){
            command = "/SIAPAKAH";
        } else if(pesan_split[0].equals("APAKAH")){
            command = "/APAKAH";
        } else if(pesan_split[0].equals("/JADWAL-SHOLAT")){
            command = "/JADWAL-SHOLAT";
        } else if(pesan_split[0].equals("/LOVE")){
            command = "/LOVE";
        } else if((pesan.contains("HAI") ||
                pesan.contains("HEI") ||
                pesan.contains("HEY") ||
                pesan.contains("HI")) && pesan.contains("LJ BOT")){
            command = "/HAI";
        } else if(pesan.equals("APAKAH LJ BOT TAKUT SAMA DEDY?")){
            command = "/LEAVE-GROUP";
        } else if((pesan_split[0].equals("/STALK"))) {
            command = "/STALK";
        }

        source = event.getSource();
        List<Message> messageList = new ArrayList<>();
        TextMessage textMessage = null;
        id_umum = getter.getId(source);
        TemplateMessage templateMessage = null;
        TugasUjian tugasUjian = new TugasUjian(id_umum);
        switch (command){
            case "/FITUR" :{
                textMessage = new TextMessage(
                        "FITUR-FITUR LJ BOT\n\n" +
                        "1. /PERKULIAHAN\n" +
                        "2. /HIBURAN");
                KirimPesan(replyToken, textMessage);
                break;
            }
            case "/PERKULIAHAN" : {
                com.linecorp.bot.model.message.template.CarouselTemplate carouselTemplate = this.carouselTemplate.templateKuliah();
                templateMessage = new TemplateMessage("LJ BOT mengirim pesan!", carouselTemplate);
                KirimPesan(replyToken, templateMessage);
                break;
            }
            case "/HIBURAN" : {
                com.linecorp.bot.model.message.template.CarouselTemplate carouselTemplate = this.carouselTemplate.templateHiburan();
                templateMessage = new TemplateMessage("LJ BOT mengirim pesan!", carouselTemplate);
                KirimPesan(replyToken, templateMessage);
                break;
            }
            case "/TUGAS" : {
                StringBuilder sb = new StringBuilder();
                for (int i=1; i<pesan_split_length; i++){
                    sb.append(pesan_split[i] + " ");
                }
                String desc = String.valueOf(sb);
                System.out.println("Deskripsi tugas : " + desc);
                int status_insert = tugasUjian.AddTugas(desc);

                if(status_insert==1){
                    textMessage = new TextMessage("Tugas berhasil dicatat.\n" +
                            "CIYEEEEEE tugasnya nambahhhhhh");
                    messageList.add(textMessage);
                } else{
                    textMessage = new TextMessage("Oops! Ada kesalahan sistem, tugas gagal dicatat");
                    messageList.add(textMessage);
                }

                KirimPesan(replyToken, messageList);
                break;
            }
            case "/UJIAN" : {
                StringBuilder sb = new StringBuilder();
                for (int i=1; i<pesan_split_length; i++){
                    sb.append(pesan_split[i] + " ");
                }
                String desc = String.valueOf(sb);
                System.out.println("Deskripsi ujian : " + desc);
                int status_insert = tugasUjian.AddUjian(desc);
                if(status_insert==1){
                    textMessage = new TextMessage("Ujian berhasil dicatat.");
                    messageList.add(textMessage);
                } else{
                    textMessage = new TextMessage("Oops! Ada kesalahan sistem, ujian gagal dicatat");
                    messageList.add(textMessage);
                }

                KirimPesan(replyToken, messageList);
                break;
            }
            case "/HPT" : {
                String id_delete = pesan_split[1];
                int status_delete = MainDao.DeleteItem(id_umum, id_delete);

                if(status_delete==1){
                    textMessage = new TextMessage("Berhasil delete tugas ID : " + id_delete);
                    messageList.add(textMessage);
                }
                else{
                    textMessage = new TextMessage("Oops! Gagal delete tugas ID : " + id_delete);
                    messageList.add(textMessage);
                }

                KirimPesan(replyToken, messageList);
                break;
            }
            case "/HPJ" : {
                String id_delete = pesan_split[1];
                int status_delete = MainDao.DeleteItem(id_umum, id_delete);

                if(status_delete==1){
                    textMessage = new TextMessage("Berhasil delete ujian ID : " + id_delete);
                    messageList.add(textMessage);
                }
                else{
                    textMessage = new TextMessage("Oops! Gagal delete ujian ID : " + id_delete);
                    messageList.add(textMessage);
                }

                KirimPesan(replyToken, messageList);
                break;
            }
            case "/APAKAH" : {
                int randInt = random.nextInt(10);
                    if(randInt%2==0){
                        textMessage = new TextMessage("Nggak");
                        messageList.add(textMessage);
                    }
                    else if(randInt%2!=0){
                        textMessage = new TextMessage("Ya");
                        messageList.add(textMessage);
                    }
                    KirimPesan(replyToken, messageList);
                break;
            }
            case "/SIAPAKAH" : {
                StringBuilder sb = new StringBuilder();
                List<GroupMember> groupMemberList = new ArrayList<>();
                if(pesan_split[1].equals("YANG")){ //jika teks yang diinputkan pengguna adalah siapakah yang paling ......
                    groupMemberList = MainDao.getAllMemberIds(group_id);
                    int banyakMember=groupMemberList.size();
                    int randInt = (int) (Math.random() * ((banyakMember-1)-0));

                    for (int i=1; i<pesan_split.length-1; i++){
                        sb.append(pesan_split[i] + " "); //Men-generate kalimat yang paling .......
                    }
                    String kataTerakhirTanpaTanya = GenerateKalimatYang(pesan, pesan_split);

                    String senderId = event.getSource().getSenderId();
                    String type  = getter.getType(source);
                    GroupMember user_id_beruntung = groupMemberList.get(randInt);

                    String user_name_beruntung = getter.getGroupMemberName(type, senderId, user_id_beruntung.getUserId());

                    textMessage = new TextMessage(user_name_beruntung + " " + String.valueOf(sb).toLowerCase() + String.valueOf(kataTerakhirTanpaTanya).toLowerCase());
                    KirimPesan(replyToken, textMessage);
                } else{
                    StringBuilder nama1 = new StringBuilder();
                    StringBuilder nama2 = new StringBuilder();

                    int indexDan = 0, indexYang = 0;
                    for(int i=0;i<pesan_split.length; i++){
                        if (pesan_split[i].equals("DAN"))
                            indexDan=i;
                        if(pesan_split[i].equals("YANG"))
                            indexYang=i;
                    }
                    System.out.println("index dan : " + indexDan + " index yang : " + indexYang);
                    //SIAPAKAH ANTARA
                    if(pesan_split[1].equals("ANTARA")){
                        for(int i=2; i<indexDan;i++){
                            nama1.append(pesan_split[i] + " ");
                        }
                        for(int i=indexDan+1; i<indexYang;i++){
                            nama2.append(pesan_split[i] + " ");
                        }
                    }
                    //SIAPAKAH DI ANTARA
                    if (pesan_split[2].equals("ANTARA")){
                        for(int i=3; i<indexDan;i++){
                            nama1.append(pesan_split[i] + " ");
                        }
                        for(int i=indexDan+1; i<indexYang;i++){
                            nama2.append(pesan_split[i] + " ");
                        }
                    }
                    //SIAPAKAH DIANTARA
                    if(pesan_split[1].equals("DIANTARA")){
                        for(int i=2; i<indexDan;i++){
                            nama1.append(pesan_split[i] + " ");
                        }
                        for(int i=indexDan+1; i<indexYang;i++){
                            nama2.append(pesan_split[i] + " ");
                        }
                    }
                    String kataTerakhirTanpaTanya = GenerateKalimatYang(pesan, pesan_split);
                    StringBuilder yangPaling = new StringBuilder();
                    for (int i=indexYang; i<pesan_split.length-1; i++){
                        yangPaling.append(pesan_split[i] + " "); //menggabungkan kata-kata yang dipisah-pusah tadi (yang ... ... ... dst)
                    }
                    int randInt = random.nextInt(10);
                    if(randInt%2==0){
                        textMessage = new TextMessage(String.valueOf(nama1) + yangPaling + kataTerakhirTanpaTanya);
                    } else if (randInt%2!=0){
                        textMessage = new TextMessage(String.valueOf(nama2) + yangPaling + kataTerakhirTanpaTanya);
                    }
                    KirimPesan(replyToken, textMessage);
                }
                break;
            }
            case "/HAI" : {
                textMessage = new TextMessage("Hai hai " + getter.getName(user_id));
                KirimPesan(replyToken, textMessage);
                break;
            }
            case "/LEAVE-GROUP" : {
                textMessage = new TextMessage("Wah kabur kalo ada dedy kepok~");
                KirimPesan(replyToken, textMessage);
                String type = getter.getType(source);
                Leave leave = new Leave(AccessToken);
                if (type.equals("group")){
                    leave.LeaveGroup(id_umum);
                } else if(type.equals("room")){
                    leave.LeaveRoom(id_umum);
                }
                break;
            }
            case "/FACE-DETECT" : {
                MainDao.CreateTableImageDetectStatus(group_id);
                MainDao.UpdateImgStatus(group_id, 1);
                textMessage = new TextMessage("MULAI");
                KirimPesan(replyToken, textMessage);
                break;
            }
            case "/FACE-STOP" : {
                MainDao.UpdateImgStatus(group_id, 0);
                textMessage = new TextMessage("Face detection sudah dihentikan");
                KirimPesan(replyToken, textMessage);
                break;
            }
            case "/JADWAL-SHOLAT" : {
                String lokasi = pesan_split[1];
                System.out.println("Lokasi : " + lokasi);
                try {
                    JadwalSholat jadwalSholat = new JadwalSholat();
                    textMessage = jadwalSholat.jadwalSholat(lokasi);
                    KirimPesan(replyToken, textMessage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }
            case "/LOVE" : {
                if(pesan_split.length<3){
                    textMessage = new TextMessage("Harus ada 2 nama yaaaa");
                    KirimPesan(replyToken, textMessage);
                } else if(pesan_split.length>3){
                    textMessage = new TextMessage("Hayoooo, gak boleh ada orang ketiga atau lebih. Maksimal 2 orang aja ya");
                    KirimPesan(replyToken, textMessage);
                } else{
                    String nama1 = pesan_split[1];
                    String nama2 = pesan_split[2];
                    LoveCalculator loveCalculator = new LoveCalculator();
                    textMessage = loveCalculator.LoveCalculator(nama1, nama2);
                    KirimPesan(replyToken, textMessage);
                }
                break;
            }
            case "/STALK" : {
                String[] kata = pesan.split(" ");
                String username = kata[1];
                System.out.println("Username : " + username);
                try {
                    getInstaPhoto(replyToken, username);
                } catch (Exception e) {
                    e.printStackTrace();
                    KirimPesan(replyToken, new TextMessage("Username tidak tersedia atau di private"));
                }
                break;
            }
        }
    }

    private String GenerateKalimatYang(String pesan, String[] pesan_split){
        char[] kataTerakhir;
        StringBuilder kataTerakhirTanpaTanya = new StringBuilder();
        if(pesan.contains("?")){
            kataTerakhir = String.valueOf(pesan_split[pesan_split.length-1]).toCharArray(); //kata terakhir dipecah-pecah jadi perhuruf
            for (int i=0;i<kataTerakhir.length-1; i++){
                kataTerakhirTanpaTanya.append(kataTerakhir[i]); //menggabungkan huruf2 yang dipecah tadi jadi satu tapi minus tanda tanya
            }
        } else{
            kataTerakhirTanpaTanya.append(pesan_split[pesan_split.length-1]);
        }
        return String.valueOf(kataTerakhirTanpaTanya);
    }

    public void KirimPesan(String replyToken, List<Message> messages) {
        try {
            BotApiResponse apiResponse = lineMessagingClient
                    .replyMessage(new ReplyMessage(replyToken, messages))
                    .get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    private void KirimPesan(String replyToken, Message message) {
        KirimPesan(replyToken, Collections.singletonList(message));
    }

    private void getInstaPhoto(String replyToken, String username) throws IOException {
        Instagram instagram = new Instagram(new OkHttpClient());
        List<Media> media = instagram.getMedias(username, 10);
        int randInt = ThreadLocalRandom.current().nextInt(0, 10+1);
        ImageMessage message = new ImageMessage(media.get(randInt).imageUrls.high,
                media.get(randInt).imageUrls.thumbnail);
        String urlMedia = media.get(randInt).link;
        KirimPesan(replyToken, message);
        KirimPesan(replyToken, new TextMessage(urlMedia));
    }
}
