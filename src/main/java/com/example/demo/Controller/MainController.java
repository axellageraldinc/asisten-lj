package com.example.demo.Controller;

import com.example.demo.Config.AppConfig;
import com.example.demo.AsyncClass;
import com.example.demo.Dao.MainDao;
import com.example.demo.Service.AsyncServices;
import com.example.demo.model.Main;
import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.client.LineMessagingServiceBuilder;
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
import com.linecorp.bot.model.profile.UserProfileResponse;
import com.linecorp.bot.model.response.BotApiResponse;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import retrofit2.Response;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@LineMessageHandler
public class MainController {

    int status_waiting_game=0;
    List<String> playerList = new ArrayList<>();

    @Resource
    AsyncServices services;

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
        MainDao.CreateTableGroupMember(id);
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
//        String apakah = pesan.substring(0,6);
//        String game_siapakah = pesan.substring(0,14);
//        String join = pesan.substring(0,5);
        String command = content.getText().toUpperCase().substring(0,4);
        System.out.println("Command atas : " + command);

        //Coba untuk command ini diganti dengan metode String startsWith()
        if (command.equals("/WOY"))
            command = "/WOY";
        else if((command + "AS").equals("/TUGAS"))
            command = "/TUGAS";
        else if((command + "AN").equals("/UJIAN"))
            command = "/UJIAN";
        else if((command + "US").equals("/HAPUS")){
            if (pesan.substring(7, 12).equals("TUGAS")) {
                command = "/HPT";
            } else if(pesan.substring(7, 12).equals("UJIAN")){
                command = "/HPJ";
            }
        }
        else if((command + "N").equals("/JOIN"))
            command = "/JOIN";
        else if((command + "AH").equals("APAKAH"))
            command = "/APAKAH";
        else if((command + "E-SIAPAKAH").equals("/GAME-SIAPAKAH"))
            command = "/GAME-SIAPAKAH";
        else if((command + "AKAH").equals("SIAPAKAH")){
            command = "/SIAPAKAH";
        }
//        if(command.equals("/HAP")) {
//            if (pesan.substring(7, 12).equals("TUGAS")) {
//                command = "/HPT";
//            } else if(pesan.substring(7, 12).equals("UJIAN")){
//                command = "/HPJ";
//            }
//        } else if(join.equals("/JOIN")){
//            command = "/JOIN";
//        } else if(apakah.equals("APAKAH")){
//            command = "/APAKAH";
//        } else if(game_siapakah.equals("/GAME-SIAPAKAH")){
//            command = "/GAME-SIAPAKAH";
//        }
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
            case "/TUGAS" : {
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
            case "/UJIAN" : {
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
//                String minusSiapakahDiantara = pesan.substring(18);
//                System.out.println("String minus siapakah diantara : " + minusSiapakahDiantara);
//                char[] chars = minusSiapakahDiantara.toCharArray();
//                char[] nama1 = new char[30];
//                char[] nama2 = new char[30];
//                StringBuilder name1 = new StringBuilder();
//                StringBuilder name2 = new StringBuilder();
//                int x=0, y=0;
//                for(int i=0;i<minusSiapakahDiantara.length();i++){
//                    //Ketika loop character tadi belum menemukan kata "DAN",
//                    //Berarti itu adalah NAMA, maka masukkan ke list Nama
//                    while (chars[i+1]!="D".charAt(0) &&
//                            chars[i+2]!="A".charAt(0)&&
//                            chars[i+3]!="N".charAt(0)){
//                        nama1[x] = chars[i];
//                        System.out.println("char nama1 ke-" + x + " : " + nama1[x]);
//                        i++;
//                        x++;
//                    }
//                    i+=5;
//                    while(chars[i+1]!="Y".charAt(0) &&
//                            chars[i+2]!="A".charAt(0) &&
//                            chars[i+3]!="N".charAt(0) &&
//                            chars[i+4]!="G".charAt(0)){
//                        nama2[y] = chars[i];
//                        System.out.println("char nama2 ke-" + y + " : " + nama2[y]);
//                        i++;
//                        y++;
//                    }
//                    i=minusSiapakahDiantara.length();
////                    if(chars[i]!=" ".charAt(0) && chars[i+1]!="D".charAt(0) && chars[i+2]!="A".charAt(0) && chars[i+3]!="N".charAt(0)){
////                        nama1[x] = chars[i];
////                        System.out.println("char nama1 ke-" + x + " : " + chars[i]);
////                        x++;
////                    } else{
////                        i+=4;
////                        if(chars[i]!=" ".charAt(0)){
////                            nama2[y] = chars[i];
////                            System.out.println("char nama2 ke-" + x + " : " + chars[i]);
////                            y++;
////                        }
////                    }
//                }
//                for (int i=0; i<nama1.length; i++){
//                    name1.append(nama1[i]);
//                }
//                for (int i=0; i<nama2.length; i++){
//                    name2.append(nama2[i]);
//                }
//                System.out.println("nama 1 : " + name1);
//                System.out.println("nama 2 : " + name2);
                break;
            }
            case "/GAME-SIAPAKAH" : {
//                String groupId = getId(source);
//                String type = getType(source);
                textMessage = new TextMessage("GAME DIMULAI!\nKetik /join untuk join");
                KirimPesan(replyToken, textMessage);
                Future<Integer> process=null;
                status_waiting_game=1;
                    try {
                        process = services.process();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    try {
                        if(process.get()==10){
                            textMessage = new TextMessage("Waktu habis!\nGame dimulai!");
                            KirimPesan(replyToken, textMessage);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
//                    AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
//                    AsyncClass asyncClass = context.getBean(AsyncClass.class);
//                    Future future = asyncClass.gameMulai();
//                    status_waiting_game=1;
//                    int pengumuman=0;
//                    try {
//                        pengumuman = (int) future.get();
//                    } catch (Exception e) {
//                        System.out.println("Gagal asyncClass : " + e.toString());
//                        e.printStackTrace();
//                    }
//                    if (pengumuman==15){
//                        status_waiting_game=0;
//                        textMessage = new TextMessage("GAME DIMULAI!");
//                        KirimPesan(replyToken, textMessage);
//                    }
                if(status_waiting_game==1){
                    textMessage = new TextMessage("Game SUDAH dimulai.\nketik /join untuk join");
                    KirimPesan(replyToken, textMessage);
                }
//                StartGame(replyToken);
//                List<String> memberList = GetMembers(type, groupId);
//                StringBuilder sb = new StringBuilder();
//                for (String members: memberList) {
//                    sb.append("Members ID : " + members + "\n");
//                }
//                System.out.println("Members ID : " + String.valueOf(sb));
//                textMessage = new TextMessage(String.valueOf(sb));
//                messageList.add(textMessage);
//                KirimPesan(replyToken, messageList);
                break;
            }
            case "/JOIN" : {
                //memang game baru dibuat
                if(status_waiting_game==1){
//                    String userId = source.getSenderId();
                    String userId = event.getSource().getUserId();
                    System.out.println("userId : " + userId);
                    String name = getName(userId);
                    System.out.println("name : " + name);
                    boolean status_add_list_player = playerList.add(name);
                    if(status_add_list_player){
                        textMessage = new TextMessage(name + " berhasil join!");
                        messageList.add(textMessage);
                        KirimPesan(replyToken, messageList);
                        for (String player:playerList
                             ) {
                            System.out.println("LIST PLAYER : " + player);
                        }
                    }
                }
                //gak ada yang yang dibuat
                else{
                    textMessage = new TextMessage("Gak ada game yang jalan.\n" +
                            "Ketik command /game-siapakah untuk mulai");
                    messageList.add(textMessage);
                    KirimPesan(replyToken, messageList);
                }
                break;
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

    public void StartGame(String replyToken){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        AsyncClass asyncClass = context.getBean(AsyncClass.class);
        Future future = asyncClass.gameMulai();
        status_waiting_game=1;
        String pengumuman = null;
        try {
            pengumuman = future.get().toString();
        } catch (Exception e) {
            System.out.println("Gagal asyncClass : " + e.toString());
            e.printStackTrace();
        }
        System.out.println("UPDATE : " + pengumuman);
        TextMessage textMessage = new TextMessage("GAME DIMULAI!");
        KirimPesan(replyToken, textMessage);
    }

    public String getName(String userId){
        String name = "";
        try {
            Response<UserProfileResponse> response =
                    LineMessagingServiceBuilder
                    .create(AccessToken)
                    .build()
                    .getProfile(userId)
                    .execute();
            if (response.isSuccessful()){
                UserProfileResponse profileResponse = response.body();
                name = profileResponse.getDisplayName();
            } else{
                System.out.println(response.code() + " " + response.message());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return name;
    }

    public String getName2(String userId){
        final String[] name = new String[1];
        lineMessagingClient
                .getProfile(userId)
                .whenComplete(((userProfileResponse, throwable) -> {
                    if(throwable!=null){
                        name[0] = userProfileResponse.getDisplayName();
                    }
                }));
        return name[0];
    }
}
