package com.example.demo.Controller;

import com.example.demo.Dao.MainDao;
import com.example.demo.model.Main;
import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.client.LineMessagingServiceBuilder;
import com.linecorp.bot.model.PushMessage;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.action.PostbackAction;
import com.linecorp.bot.model.event.*;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.event.source.GroupSource;
import com.linecorp.bot.model.event.source.RoomSource;
import com.linecorp.bot.model.event.source.Source;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.StickerMessage;
import com.linecorp.bot.model.message.TemplateMessage;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.message.template.CarouselColumn;
import com.linecorp.bot.model.message.template.CarouselTemplate;
import com.linecorp.bot.model.profile.MembersIdsResponse;
import com.linecorp.bot.model.response.BotApiResponse;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import sun.plugin.util.UserProfile;

import javax.xml.ws.Response;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutionException;

@LineMessageHandler
public class MainController {
    @Autowired
    private LineMessagingClient lineMessagingClient;

    private static String AccessToken = "u/jyVKXsD5N/OfmNIvEjnI+NffMIhzcFFjIZ3Whm4Gu9/LTL4y7WjWhWehHjYIO+aG6QUKw5991HFzs7i8c1PAZP07r1LIGun6o8X53yZflIk/Th0W8JkY9G/2IpWkL59subrXO5cOQCxJqjemzHvwdB04t89/1O/w1cDnyilFU=";

    @EventMapping
    public void handleJoinNewGroup(JoinEvent joinEvent) {
        List<Message> messageList = new ArrayList<>();
        TextMessage textMessage = null;
        Source source = joinEvent.getSource();
        String id = getId(source);
        MainDao.CreateTableData(id);
        StickerMessage stickerMessage = new StickerMessage("1", "2");
        messageList.add(stickerMessage);
        textMessage = new TextMessage("Nuwun yo aku wes entuk join grup iki\n" +
                "Silakan ketik /woy untuk melihat fitur-fitur yang ada.");
        messageList.add(textMessage);
        KirimPesan(joinEvent.getReplyToken(), messageList);
    }

    @EventMapping
    public void handleNewFollower(FollowEvent followEvent){
        List<Message> messageList = new ArrayList<>();
        TextMessage textMessage = null;
        Source source = followEvent.getSource();
        String id = getId(source);
        MainDao.CreateTableData(id);
        StickerMessage stickerMessage = new StickerMessage("1", "2");
        messageList.add(stickerMessage);
        textMessage = new TextMessage("Nuwun yo aku wes di-add dadi friend\n" +
                "Silakan ketik /woy untuk melihat fitur-fitur yang ada.\n" +
                "Aku di invite ning group lhoooo");
        messageList.add(textMessage);
        KirimPesan(followEvent.getReplyToken(), messageList);
    }

    @EventMapping
    public void handleText(MessageEvent<TextMessageContent> msg) {
        handleContent(msg.getReplyToken(), msg, msg.getMessage());
    }

    public void handleContent(String replyToken, Event event, TextMessageContent content){
        String pesan = content.getText().toUpperCase();
        String apakah = pesan.substring(0,6);
        String siapakah = pesan.substring(0,8);
        System.out.println("siapakah : " + siapakah);
        String command = content.getText().toUpperCase().substring(0,4);
        if(command.equals("/HAP")) {
            if (pesan.substring(7, 12).equals("TUGAS")) {
                command = "/HPT";
            } else if(pesan.substring(7, 12).equals("UJIAN")){
                command = "/HPJ";
            }
        } if(apakah.equals("APAKAH")){
            command = "/APAKAH";
        } if(siapakah.equals("SIAPAKAH")){
            command = "/SIAPAKAH";
        }
        System.out.println("Command : " + command);
        Main main = new Main();
        Source source = event.getSource();
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
            case "/UJI" : {
                String desc = pesan.substring(7);
                main.setId("UJIAN-" + desc.substring(0,7));
                main.setDeskripsi(desc);
                main.setTipe("ujian");
                int status_insert = MainDao.Insert(id, main);
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
            case "/HPJ" : {
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
            case "/APAKAH" : {
                Random random = new Random();
                int randInt = random.nextInt(10) + 1;
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
                String groupId = getId(source);
                String type = getType(source);
                List<String> memberList = GetMembers(type, groupId);
                StringBuilder sb = new StringBuilder();
                for (String members: memberList) {
                    sb.append("Members ID : " + members + "\n");
                }
                System.out.println("Members ID : " + String.valueOf(sb));
                textMessage = new TextMessage(String.valueOf(sb));
                messageList.add(textMessage);
                KirimPesan(replyToken, messageList);
            }
        }
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

    @EventMapping
    public void handlePostback(PostbackEvent event){
        Source source = event.getSource();
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
        KirimPesan(event.getReplyToken(), messageList);
    }

    public String getId(Source source){
        String id=null;
        String type=null;
        if (source instanceof GroupSource) {
            id = ((GroupSource) source).getGroupId();
            type="group";
        } else if (source instanceof RoomSource) {
            id = ((RoomSource) source).getRoomId();
            type="room";
        } else{
            id = source.getUserId();
            type="personal";
        }
        return id;
    }

    public String getType(Source source){
        String type = null;
        if (source instanceof GroupSource)
            type="group";
        else if(source instanceof RoomSource)
            type="room";
        else
            type="personal";
        return type;
    }

    public List<String> GetMembers(String type, String groupId){
        List<String> memberIds = new ArrayList<>();
        try {
            retrofit2.Response<MembersIdsResponse> response = LineMessagingServiceBuilder
                    .create(AccessToken)
                    .build()
                    .getMembersIds(type, groupId, null)
                    .execute();
            MembersIdsResponse idsResponse = response.body();
            memberIds = idsResponse.getMemberIds();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return memberIds;
    }
}
