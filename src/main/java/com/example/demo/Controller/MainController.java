package com.example.demo.Controller;

import com.example.demo.Dao.MainDao;
import com.example.demo.model.Main;
import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.PushMessage;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.action.PostbackAction;
import com.linecorp.bot.model.event.*;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.event.source.GroupSource;
import com.linecorp.bot.model.event.source.RoomSource;
import com.linecorp.bot.model.event.source.Source;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TemplateMessage;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.message.template.CarouselColumn;
import com.linecorp.bot.model.message.template.CarouselTemplate;
import com.linecorp.bot.model.response.BotApiResponse;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

@LineMessageHandler
public class MainController {
    @Autowired
    private LineMessagingClient lineMessagingClient;

    private static String AccessToken = "u/jyVKXsD5N/OfmNIvEjnI+NffMIhzcFFjIZ3Whm4Gu9/LTL4y7WjWhWehHjYIO+aG6QUKw5991HFzs7i8c1PAZP07r1LIGun6o8X53yZflIk/Th0W8JkY9G/2IpWkL59subrXO5cOQCxJqjemzHvwdB04t89/1O/w1cDnyilFU=";

    @EventMapping
    public TextMessage handleJoinNewGroup(JoinEvent joinEvent) {
        TextMessage replyMsg = null;
        Source source = joinEvent.getSource();
        String id = getId(source);
        MainDao.CreateTableData(id);
        replyMsg = new TextMessage("Nuwun yo aku wes entuk join grup iki\n" +
                "Silakan ketik /woy untuk melihat fitur-fitur yang ada.");
        return replyMsg;
    }

    @EventMapping
    public TextMessage handleNewFollower(FollowEvent followEvent){
        TextMessage textMessage = null;
        Source source = followEvent.getSource();
        String id = getId(source);
        MainDao.CreateTableData(id);
        textMessage = new TextMessage("Nuwun yo aku wes di-add dadi friend\n" +
                "Silakan ketik /woy untuk melihat fitur-fitur yang ada.\n" +
                "Aku di invite ning group lhoooo");
        return textMessage;
    }

    @EventMapping
    public void handleTextSlash(MessageEvent<TextMessageContent> msg) {
        handleContent(msg.getReplyToken(), msg, msg.getMessage());
//        if (pesan.equals("/WOY")) {
//
////            TextMessage msgCommand = new TextMessage("Daftar command LJ BOT\n" +
////                    "1. /TUGAS [spasi] [deskripsi TUGAS]\n" +
////                    "2. /UJIAN [spasi] [deskripsi UJIAN]\n");
////            messageList.add(msgCommand);
////        } else if (msg.getMessage().getText().toUpperCase().substring(0,6).equals("/TUGAS")){
////            messageList.clear();
////            String deskripsi = msg.getMessage().getText().substring(7);
//////            StickerMessage stickerSuksesTugas = new StickerMessage("1", "5");
//////            messageList.add(stickerSuksesTugas);
////
//////            //Add TUGAS ke database
////            group.setId("TUGAS" + "-" + deskripsi.substring(0,5));
////            group.setDeskripsi(deskripsi);
////            group.setTipe("tugas");
////            int status_insert = MainDao.Insert(groupId, group);
////            if(status_insert==1)
////                System.out.println("Berhasil masukkan tugas ke database");
////            else
////                System.out.println("Gagal masukkan ke database");
////            List<Main> groupList = MainDao.GetAll(groupId, "tugas");
////            StringBuilder Stringmsg = new StringBuilder();
////            int nomor=1;
////            for (Main groupp:groupList) {
////                Stringmsg.append(nomor + "." + "\nID : " + groupp.getId() + "\nTugas : " + groupp.getDeskripsi());
////                nomor++;
////            }
////            TextMessage msgAllTugas = new TextMessage("LIST TUGAS\n" + String.valueOf(Stringmsg));
////            messageList.add(msgAllTugas);
////        } else if(msg.getMessage().getText().substring(0,6).equals("/UJIAN")){
////            messageList.clear();
////            String deskripsi = msg.getMessage().getText().substring(7);
////            group.setId("UJIAN" + "-" + deskripsi.substring(0,5));
////            group.setDeskripsi(deskripsi);
////            group.setTipe("ujian");
////            int status_insert = MainDao.Insert(groupId, group);
////            if(status_insert==1)
////                System.out.println("Berhasil masukkan tugas ke database");
////            else
////                System.out.println("Gagal masukkan ke database");
////            List<Main> groupList = MainDao.GetAll(groupId, "ujian");
////            StringBuilder Stringmsg = new StringBuilder();
////            int nomor=1;
////            for (Main groupp:groupList) {
////                Stringmsg.append(nomor + "." + "\nID : " + groupp.getId() + "\nUjian : " + groupp.getDeskripsi());
////                nomor++;
////            }
////            TextMessage msgAllTugas = new TextMessage("LIST UJIAN\n" + String.valueOf(Stringmsg));
////            messageList.add(msgAllTugas);
////        }
////            pushMessage = new PushMessage(groupId, messageList);
////            Response<BotApiResponse> response =
////                    null;
////            try {
////                response = LineMessagingServiceBuilder
////                        .create(AccessToken)
////                        .build()
////                        .pushMessage(pushMessage)
////                        .execute();
////            } catch (IOException e) {
////                e.printStackTrace();
////            }
////            System.out.println(response.code() + " " + response.message());
//            try {
//                BotApiResponse apiResponse = lineMessagingClient
//                        .replyMessage(new ReplyMessage(msg.getReplyToken(), templateMessage))
//                        .get();
//            } catch (InterruptedException | ExecutionException e) {
//                throw new RuntimeException(e);
//            }
//        } else if(pesan.substring(0,6).equals("/TUGAS")){
//
//        }
    }

    public void handleContent(String replyToken, Event event, TextMessageContent content){
        String pesan = content.getText().toUpperCase();
        String command = content.getText().toUpperCase().substring(0,4);
        if(command.equals("/HAP")) {
            if (pesan.substring(7, 12).equals("TUGAS")) {
                command = "HPT";
            } else if(pesan.substring(7, 12).equals("UJIAN")){
                command = "HPJ";
            }
        }
        System.out.println("Command : " + command);
        Main main = new Main();
        Source source = event.getSource();
        PushMessage pushMessage;
        List<Message> messageList = new ArrayList<>();
        TextMessage textMessage = null;
        String id = getId(source);
        TemplateMessage templateMessage = null;
        switch (command){
            case "/WOY" : {
                CarouselTemplate carouselTemplate = new CarouselTemplate(
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
                templateMessage = new TemplateMessage("LJ BOT mengirim pesan!", carouselTemplate);
                KirimPesan(replyToken, templateMessage);
                break;
            }
            case "/TUG" : {
                String desc = pesan.substring(7);
                main.setId("TUGAS-" + desc.substring(0,7));
                main.setDeskripsi(desc);
                main.setTipe("tugas");
                int status_insert = MainDao.Insert(id, main);
                if(status_insert==1){
                    textMessage = new TextMessage("Tugas berhasil dicatat.");
                    messageList.add(textMessage);
//                    pushMessage = new PushMessage(id, textMessage);
                } else{
                    textMessage = new TextMessage("Oops! Ada kesalahan sistem, tugas gagal dicatat");
                    messageList.add(textMessage);
//                    pushMessage = new PushMessage(id, textMessage);
                }
                KirimPesan(replyToken, messageList);
                break;
            }
            case "/UJI" : {
                String desc = pesan.substring(7);
                main.setId("UJIAN-" + desc.substring(0,7));
                main.setDeskripsi(desc);
                main.setTipe("ujian");
                int status_insert = MainDao.Insert(id, main);
                if(status_insert==1){
                    textMessage = new TextMessage("Ujian berhasil dicatat.");
                    messageList.add(textMessage);
//                    pushMessage = new PushMessage(id, textMessage);
                } else{
                    textMessage = new TextMessage("Oops! Ada kesalahan sistem, ujian gagal dicatat");
                    messageList.add(textMessage);
//                    pushMessage = new PushMessage(id, textMessage);
                }
                KirimPesan(replyToken, messageList);
                break;
            }
            case "HPT" : {
                String id_delete = pesan.substring(7);
                int status_delete = MainDao.DeleteItem(id, id_delete);
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
            case "HPJ" : {
                String id_delete = pesan.substring(7);
                int status_delete = MainDao.DeleteItem(id, id_delete);
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
        }
    }

    public void KirimPesan(String replyToken, List<Message> messages) {
        try {
            BotApiResponse apiResponse = lineMessagingClient
                    .replyMessage(new ReplyMessage(replyToken, messages))
                    .get();
            System.out.println("Response code : " + apiResponse.getMessage());
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    private void KirimPesan(String replyToken, Message message) {
        KirimPesan(replyToken, Collections.singletonList(message));
    }

    @EventMapping
    public void handlePostback(PostbackEvent event){
        Source source = event.getSource();
        PushMessage pushMessage;
        List<Message> messageList = new ArrayList<>();
        String data = event.getPostbackContent().getData();
        TextMessage textMessage = null;
        String id = getId(source);
        System.out.println("Postback Event : " + data);
        if(data.equals("/ADD-TUGAS")){
            messageList.clear();
            textMessage = new TextMessage("Kirim deskripsi tugas selengkap mungkin (makul, disuruh ngapain, deadline, dikumpul kemana, dll)");
            messageList.add(textMessage);
            textMessage = new TextMessage("Perhatian!\n" +
                    "Kirim deskripsi tugas dengan format /tugas [spasi] [deskripsi]\n" +
                    "Contoh : /tugas progdas bikin kalkulator deadline senin");
            messageList.add(textMessage);
        } else if(data.equals("/SHOW-TUGAS")){
            messageList.clear();
            List<Main> mainList = MainDao.GetAll(id, "tugas");
            StringBuilder sb = new StringBuilder();
            int nomor=1;
            for (Main item: mainList) {
                sb.append("LIST TUGAS\n\n" +
                        nomor + ".\n" +
                        "ID : " + item.getId() + "\n" +
                        item.getDeskripsi() + "\n");
                nomor++;
            }
            System.out.println("Data get all : " + String.valueOf(sb));
            textMessage = new TextMessage(String.valueOf(sb));
            messageList.add(textMessage);
        } else if (data.equals("/ADD-UJIAN")){
            messageList.clear();
            textMessage = new TextMessage("Kirim deskripsi ujian selengkap mungkin (makul, sifat ujian, materi apa aja, dll)");
            messageList.add(textMessage);
            textMessage = new TextMessage("Perhatian!\n" +
                    "Kirim deskripsi ujian dengan format /ujian [spasi] [deskripsi]\n" +
                    "Contoh : /ujian progdas open A4 tinta biru");
            messageList.add(textMessage);
        } else if (data.equals("/SHOW-UJIAN")){
            messageList.clear();
            List<Main> mainList = MainDao.GetAll(id, "ujian");
            StringBuilder sb = new StringBuilder();
            int nomor=1;
            for (Main item: mainList) {
                sb.append("LIST UJIAN\n\n" +
                        nomor + ".\n" +
                        "ID : " + item.getId() + "\n" +
                        item.getDeskripsi() + "\n");
                nomor++;
            }
            System.out.println("Data get all : " + String.valueOf(sb));
            textMessage = new TextMessage(String.valueOf(sb));
            messageList.add(textMessage);
        } else if (data.equals("/HAPUS-TUGAS")){
            messageList.clear();
            List<Main> mainList = MainDao.GetAll(id, "tugas");
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
        } else if(data.equals("/HAPUS-UJIAN")){
            messageList.clear();
            List<Main> mainList = MainDao.GetAll(id, "ujian");
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
        }
        pushMessage = new PushMessage(id, messageList);
        KirimPesan(event.getReplyToken(), messageList);
//        KirimPesan(pushMessage);
    }

//    @EventMapping
//    public void handleContent(MessageEvent<TextMessageContent> msg){
//        Source source = msg.getSource();
//        Main group = new Main();
//        String id = getId(source);
//        String pesan = msg.getMessage().getText();
//        System.out.println("Pesan : " + pesan);
//        System.out.println("Pesan substring : " + pesan.substring(0,6).toUpperCase());
//        PushMessage pushMessage;
//        TextMessage textMessage;
//        if(pesan.substring(0,6).toUpperCase().equals("/TUGAS")){
//            String desc = pesan.substring(7);
//            group.setId("TUGAS-" + desc.substring(0,5));
//            group.setDeskripsi(desc);
//            group.setTipe("tugas");
//            int status_insert = MainDao.Insert(id, group);
//            if(status_insert==1){
//                textMessage = new TextMessage("Tugas berhasil dicatat.");
//                pushMessage = new PushMessage(id, textMessage);
//            } else{
//                textMessage = new TextMessage("Oops! Ada kesalahan sistem, tugas gagal dicatat");
//                pushMessage = new PushMessage(id, textMessage);
//            }
//            KirimPesan(pushMessage);
//        }
//    }

//    public void KirimPesan(PushMessage pushMessage){
//        Response<BotApiResponse> response = null;
//        try {
//            response = LineMessagingServiceBuilder
//                    .create(AccessToken)
//                    .build()
//                    .pushMessage(pushMessage)
//                    .execute();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        System.out.println(response.code() + " " + response.message());
//    }

    public String getId(Source source){
        String id=null;
        if (source instanceof GroupSource) {
            id = ((GroupSource) source).getGroupId();
        } else if (source instanceof RoomSource) {
            id = ((RoomSource) source).getRoomId();
        } else{
            id = source.getUserId();
        }
        return id;
    }
}
