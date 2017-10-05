package com.example.demo.Controller;

import com.example.demo.Creator.*;
import com.example.demo.Dao.MainDao;
import com.example.demo.Getter.Getter;
import com.example.demo.model.GroupMember;
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
        TextMessage textMessage = new TextMessage("LJ BOT ini udah kadaluwarsa :(\n" +
                "Kick LJ BOT ini dulu, terus add Asisten LJ di @doy7181p\n" +
                "Setelah itu invite aja Asisten LJ ke grup ini lagi..\n" +
                "Fitur2nya sama kok :)");
        messageList.add(textMessage);
        KirimPesan(joinEvent.getReplyToken(), messageList);
    }

    @EventMapping
    public void handleNewFollower(FollowEvent followEvent){
        List<Message> messageList = new ArrayList<>();
        Source source = followEvent.getSource();
        String id_umum = getter.getId(source);
        MainDao.CreateTableData(id_umum);
        TextMessage textMessage = new TextMessage("LJ BOT ini udah kadaluwarsa :(\n" +
                "Kick LJ BOT ini dulu, terus add Asisten LJ di @doy7181p\n" +
                "Setelah itu invite aja Asisten LJ ke grup ini lagi..\n" +
                "Fitur2nya sama kok :)");
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
                TextMessage textMessage = new TextMessage("LJ BOT ini udah kadaluwarsa :(\n" +
                        "Kick LJ BOT ini dulu, terus add Asisten LJ di @doy7181p\n" +
                        "Setelah itu invite aja Asisten LJ ke grup ini lagi..\n" +
                        "Fitur2nya sama kok :)");
                messageList.add(textMessage);
                break;
            }
            case "/SHOW-TUGAS" : {
                TextMessage textMessage = new TextMessage("LJ BOT ini udah kadaluwarsa :(\n" +
                        "Kick LJ BOT ini dulu, terus add Asisten LJ di @doy7181p\n" +
                        "Setelah itu invite aja Asisten LJ ke grup ini lagi..\n" +
                        "Fitur2nya sama kok :)");
                messageList.add(textMessage);
                break;
            }
            case "/ADD-UJIAN" : {
                TextMessage textMessage = new TextMessage("LJ BOT ini udah kadaluwarsa :(\n" +
                        "Kick LJ BOT ini dulu, terus add Asisten LJ di @doy7181p\n" +
                        "Setelah itu invite aja Asisten LJ ke grup ini lagi..\n" +
                        "Fitur2nya sama kok :)");
                messageList.add(textMessage);
                break;
            }
            case "/SHOW-UJIAN" : {
                TextMessage textMessage = new TextMessage("LJ BOT ini udah kadaluwarsa :(\n" +
                        "Kick LJ BOT ini dulu, terus add Asisten LJ di @doy7181p\n" +
                        "Setelah itu invite aja Asisten LJ ke grup ini lagi..\n" +
                        "Fitur2nya sama kok :)");
                messageList.add(textMessage);
                break;
            }
            case "/HAPUS-TUGAS" : {
                TextMessage textMessage = new TextMessage("LJ BOT ini udah kadaluwarsa :(\n" +
                        "Kick LJ BOT ini dulu, terus add Asisten LJ di @doy7181p\n" +
                        "Setelah itu invite aja Asisten LJ ke grup ini lagi..\n" +
                        "Fitur2nya sama kok :)");
                messageList.add(textMessage);
                break;
            }
            case "/HAPUS-UJIAN" : {
                TextMessage textMessage = new TextMessage("LJ BOT ini udah kadaluwarsa :(\n" +
                        "Kick LJ BOT ini dulu, terus add Asisten LJ di @doy7181p\n" +
                        "Setelah itu invite aja Asisten LJ ke grup ini lagi..\n" +
                        "Fitur2nya sama kok :)");
                messageList.add(textMessage);
                break;
            }
            case "/CARA-PAKAI-APAKAH" : {
                TextMessage textMessage = new TextMessage("LJ BOT ini udah kadaluwarsa :(\n" +
                        "Kick LJ BOT ini dulu, terus add Asisten LJ di @doy7181p\n" +
                        "Setelah itu invite aja Asisten LJ ke grup ini lagi..\n" +
                        "Fitur2nya sama kok :)");
                messageList.add(textMessage);
                break;
            }
            case "/CARA-PAKAI-SIAPAKAH" : {
                TextMessage textMessage = new TextMessage("LJ BOT ini udah kadaluwarsa :(\n" +
                        "Kick LJ BOT ini dulu, terus add Asisten LJ di @doy7181p\n" +
                        "Setelah itu invite aja Asisten LJ ke grup ini lagi..\n" +
                        "Fitur2nya sama kok :)");
                messageList.add(textMessage);
                break;
            }
            case "/CARA-PAKAI-WAJAH" : {
                TextMessage textMessage = new TextMessage("LJ BOT ini udah kadaluwarsa :(\n" +
                        "Kick LJ BOT ini dulu, terus add Asisten LJ di @doy7181p\n" +
                        "Setelah itu invite aja Asisten LJ ke grup ini lagi..\n" +
                        "Fitur2nya sama kok :)");
                messageList.add(textMessage);
                break;
            }
            case "/CARA-PAKAI-CINTA" : {
                TextMessage textMessage = new TextMessage("LJ BOT ini udah kadaluwarsa :(\n" +
                        "Kick LJ BOT ini dulu, terus add Asisten LJ di @doy7181p\n" +
                        "Setelah itu invite aja Asisten LJ ke grup ini lagi..\n" +
                        "Fitur2nya sama kok :)");
                messageList.add(textMessage);
                break;
            }
            case "/CARA-PAKAI-INSTAGRAM" : {
                TextMessage textMessage = new TextMessage("LJ BOT ini udah kadaluwarsa :(\n" +
                        "Kick LJ BOT ini dulu, terus add Asisten LJ di @doy7181p\n" +
                        "Setelah itu invite aja Asisten LJ ke grup ini lagi..\n" +
                        "Fitur2nya sama kok :)");
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
        } else if(pesan_split[0].equals("SIAPAKAH") || pesan_split[0].equals("MANAKAH")){
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
        } else if(pesan.equals("APAKAH LJ BOT TAKUT SAMA DEDY?")){
            command = "/LEAVE-GROUP";
        } else if(pesan.equals("/SOURCE-CODE")){
            command = "/SOURCE-CODE";
        } else if(pesan.equals("/DBANYAR")){
            command = "/DBANYAR";
        }

        source = event.getSource();
        List<Message> messageList = new ArrayList<>();
        TextMessage textMessage = null;
        id_umum = getter.getId(source);
        TemplateMessage templateMessage = null;
        TugasUjian tugasUjian = new TugasUjian(id_umum);
        switch (command){
            case "/FITUR" :{
                textMessage = new TextMessage("LJ BOT ini udah kadaluwarsa :(\n" +
                        "Kick LJ BOT ini dulu, terus add Asisten LJ di @doy7181p\n" +
                        "Setelah itu invite aja Asisten LJ ke grup ini lagi..\n" +
                        "Fitur2nya sama kok :)");
                KirimPesan(replyToken, textMessage);
                break;
            }
            case "/DBANYAR" : {
                List<GroupMember> groupMemberList = MainDao.getAllMemberIds(group_id);
                for (GroupMember item: groupMemberList
                     ) {
                    System.out.println("ID USER : " + item.getUserId());
                }
                break;
            }
            case "/PERKULIAHAN" : {
                textMessage = new TextMessage("LJ BOT ini udah kadaluwarsa :(\n" +
                        "Kick LJ BOT ini dulu, terus add Asisten LJ di @doy7181p\n" +
                        "Setelah itu invite aja Asisten LJ ke grup ini lagi..\n" +
                        "Fitur2nya sama kok :)");
                KirimPesan(replyToken, textMessage);
                break;
            }
            case "/HIBURAN" : {
                textMessage = new TextMessage("LJ BOT ini udah kadaluwarsa :(\n" +
                        "Kick LJ BOT ini dulu, terus add Asisten LJ di @doy7181p\n" +
                        "Setelah itu invite aja Asisten LJ ke grup ini lagi..\n" +
                        "Fitur2nya sama kok :)");
                KirimPesan(replyToken, textMessage);
                break;
            }
            case "/SOURCE-CODE" : {
                textMessage = new TextMessage("LJ BOT ini udah kadaluwarsa :(\n" +
                        "Kick LJ BOT ini dulu, terus add Asisten LJ di @doy7181p\n" +
                        "Setelah itu invite aja Asisten LJ ke grup ini lagi..\n" +
                        "Fitur2nya sama kok :)");
                KirimPesan(replyToken, textMessage);
                break;
            }
            case "/TUGAS" : {
                textMessage = new TextMessage("LJ BOT ini udah kadaluwarsa :(\n" +
                        "Kick LJ BOT ini dulu, terus add Asisten LJ di @doy7181p\n" +
                        "Setelah itu invite aja Asisten LJ ke grup ini lagi..\n" +
                        "Fitur2nya sama kok :)");
                KirimPesan(replyToken, textMessage);
                break;
            }
            case "/UJIAN" : {
                textMessage = new TextMessage("LJ BOT ini udah kadaluwarsa :(\n" +
                        "Kick LJ BOT ini dulu, terus add Asisten LJ di @doy7181p\n" +
                        "Setelah itu invite aja Asisten LJ ke grup ini lagi..\n" +
                        "Fitur2nya sama kok :)");
                KirimPesan(replyToken, textMessage);
                break;
            }
            case "/HPT" : {
                textMessage = new TextMessage("LJ BOT ini udah kadaluwarsa :(\n" +
                        "Kick LJ BOT ini dulu, terus add Asisten LJ di @doy7181p\n" +
                        "Setelah itu invite aja Asisten LJ ke grup ini lagi..\n" +
                        "Fitur2nya sama kok :)");
                KirimPesan(replyToken, textMessage);
                break;
            }
            case "/HPJ" : {
                textMessage = new TextMessage("LJ BOT ini udah kadaluwarsa :(\n" +
                        "Kick LJ BOT ini dulu, terus add Asisten LJ di @doy7181p\n" +
                        "Setelah itu invite aja Asisten LJ ke grup ini lagi..\n" +
                        "Fitur2nya sama kok :)");
                KirimPesan(replyToken, textMessage);
                break;
            }
            case "/APAKAH" : {
                textMessage = new TextMessage("LJ BOT ini udah kadaluwarsa :(\n" +
                        "Kick LJ BOT ini dulu, terus add Asisten LJ di @doy7181p\n" +
                        "Setelah itu invite aja Asisten LJ ke grup ini lagi..\n" +
                        "Fitur2nya sama kok :)");
                KirimPesan(replyToken, textMessage);
                break;
            }
            case "/SIAPAKAH" : {
                textMessage = new TextMessage("LJ BOT ini udah kadaluwarsa :(\n" +
                        "Kick LJ BOT ini dulu, terus add Asisten LJ di @doy7181p\n" +
                        "Setelah itu invite aja Asisten LJ ke grup ini lagi..\n" +
                        "Fitur2nya sama kok :)");
                KirimPesan(replyToken, textMessage);
                break;
            }
            case "/HAI" : {
                textMessage = new TextMessage("LJ BOT ini udah kadaluwarsa :(\n" +
                        "Kick LJ BOT ini dulu, terus add Asisten LJ di @doy7181p\n" +
                        "Setelah itu invite aja Asisten LJ ke grup ini lagi..\n" +
                        "Fitur2nya sama kok :)");
                KirimPesan(replyToken, textMessage);
                break;
            }
            case "/LEAVE-GROUP" : {
                textMessage = new TextMessage("LJ BOT ini udah kadaluwarsa :(\n" +
                        "Kick LJ BOT ini dulu, terus add Asisten LJ di @doy7181p\n" +
                        "Setelah itu invite aja Asisten LJ ke grup ini lagi..\n" +
                        "Fitur2nya sama kok :)");
                KirimPesan(replyToken, textMessage);
                break;
            }
            case "/FACE-DETECT" : {
                textMessage = new TextMessage("LJ BOT ini udah kadaluwarsa :(\n" +
                        "Kick LJ BOT ini dulu, terus add Asisten LJ di @doy7181p\n" +
                        "Setelah itu invite aja Asisten LJ ke grup ini lagi..\n" +
                        "Fitur2nya sama kok :)");
                KirimPesan(replyToken, textMessage);
                break;
            }
            case "/FACE-STOP" : {
                textMessage = new TextMessage("LJ BOT ini udah kadaluwarsa :(\n" +
                        "Kick LJ BOT ini dulu, terus add Asisten LJ di @doy7181p\n" +
                        "Setelah itu invite aja Asisten LJ ke grup ini lagi..\n" +
                        "Fitur2nya sama kok :)");
                KirimPesan(replyToken, textMessage);
                break;
            }
            case "/JADWAL-SHOLAT" : {
                textMessage = new TextMessage("LJ BOT ini udah kadaluwarsa :(\n" +
                        "Kick LJ BOT ini dulu, terus add Asisten LJ di @doy7181p\n" +
                        "Setelah itu invite aja Asisten LJ ke grup ini lagi..\n" +
                        "Fitur2nya sama kok :)");
                KirimPesan(replyToken, textMessage);
                break;
            }
            case "/LOVE" : {
                textMessage = new TextMessage("LJ BOT ini udah kadaluwarsa :(\n" +
                        "Kick LJ BOT ini dulu, terus add Asisten LJ di @doy7181p\n" +
                        "Setelah itu invite aja Asisten LJ ke grup ini lagi..\n" +
                        "Fitur2nya sama kok :)");
                KirimPesan(replyToken, textMessage);
                break;
            }
            case "/STALK" : {
                textMessage = new TextMessage("LJ BOT ini udah kadaluwarsa :(\n" +
                        "Kick LJ BOT ini dulu, terus add Asisten LJ di @doy7181p\n" +
                        "Setelah itu invite aja Asisten LJ ke grup ini lagi..\n" +
                        "Fitur2nya sama kok :)");
                KirimPesan(replyToken, textMessage);
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
