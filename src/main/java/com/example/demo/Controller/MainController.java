package com.example.demo.Controller;

import com.example.demo.Config.AppConfig;
import com.example.demo.AsyncClass;
import com.example.demo.Dao.MainDao;
import com.example.demo.Service.AsyncServices;
import com.example.demo.model.GroupMember;
import com.example.demo.model.Main;
import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.client.LineMessagingServiceBuilder;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.action.PostbackAction;
import com.linecorp.bot.model.event.*;
import com.linecorp.bot.model.event.message.ImageMessageContent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.event.source.GroupSource;
import com.linecorp.bot.model.event.source.RoomSource;
import com.linecorp.bot.model.event.source.Source;
import com.linecorp.bot.model.message.*;
import com.linecorp.bot.model.message.template.CarouselColumn;
import com.linecorp.bot.model.message.template.CarouselTemplate;
import com.linecorp.bot.model.profile.MembersIdsResponse;
import com.linecorp.bot.model.profile.UserProfileResponse;
import com.linecorp.bot.model.response.BotApiResponse;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import retrofit2.Response;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.net.ssl.SSLException;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@LineMessageHandler
public class MainController {

    int status_waiting_game=0;
    List<String> playerList = new ArrayList<>();
    int detect_img_status=0;

    private static final String api_key = "NlDLxQN3giFSs1AI899WNnZTtCuy-mt7";
    private static final String api_secret = "3W95_8wUGxaiKvcmxDCEciBsTgkTl0E1";

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
        StickerMessage stickerMessage = new StickerMessage("1", "2");
        messageList.add(stickerMessage);
        textMessage = new TextMessage("Nuwun yo aku wes entuk join grup iki\n" +
                "Silakan ketik /fitur untuk melihat fitur-fitur yang ada.");
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
                "Silakan ketik /fitur untuk melihat fitur-fitur yang ada.\n" +
                "Aku di invite ning group lhoooo");
        messageList.add(textMessage);
        KirimPesan(followEvent.getReplyToken(), messageList);
    }

    @EventMapping
    public void handleText(MessageEvent<TextMessageContent> msg) {
        handleContent(msg.getReplyToken(), msg, msg.getMessage());
    }

    @EventMapping
    public void handleImage(MessageEvent<ImageMessageContent> img){
        if(detect_img_status==1){
            ImageMessageContent content = img.getMessage();
            String id = content.getId();
            handleImageContent(img.getReplyToken(), id);
            System.out.println("ID MESSAGE IMAGE : " + id);
        }
    }

    public void handleImageContent(String replyToken, String id){
        TextMessage textMessage;
        File file = new File("downloaded.jpg");
        try {
            URL urlP = new URL("https://api.line.me/v2/bot/message/" + id + "/content");
            URLConnection conn = urlP.openConnection();
            conn.setRequestProperty("Authorization", "Bearer {u/jyVKXsD5N/OfmNIvEjnI+NffMIhzcFFjIZ3Whm4Gu9/LTL4y7WjWhWehHjYIO+aG6QUKw5991HFzs7i8c1PAZP07r1LIGun6o8X53yZflIk/Th0W8JkY9G/2IpWkL59subrXO5cOQCxJqjemzHvwdB04t89/1O/w1cDnyilFU=}");
            conn.setConnectTimeout(5 * 1000); // Tak tambahin sendiri
            BufferedImage img = ImageIO.read(conn.getInputStream());
            ImageIO.write(img, "jpg", file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        byte[] buff = getBytesFromFile(file);
        String url = "https://api-us.faceplusplus.com/facepp/v3/detect";
        HashMap<String, String> map = new HashMap<>();
        HashMap<String, byte[]> byteMap = new HashMap<>();
        map.put("api_key", api_key);
        map.put("api_secret", api_secret);
        map.put("return_attributes", "age,gender,ethnicity,emotion,beauty");
        byteMap.put("image_file", buff);
        String str = null;
        try{
            byte[] bacd = post(url, map, byteMap);
            str = new String(bacd);
            System.out.println(str); //json
        }catch (Exception e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = new JSONObject(str);
        JSONArray jsonArray = jsonObject.getJSONArray("faces");
        int jsonArraySize = jsonArray.length();
        if(jsonArraySize>1){
            textMessage = new TextMessage("tolong kirim foto dengan 1 wajah saja.");
        } else if(jsonArraySize==0)
        {
            textMessage = new TextMessage("Wajah tidak terdeteksi");
        } else{
            JSONObject array_content = jsonArray.getJSONObject(0); //index ke-1 adalah face_attributes
            JSONObject face_attributes = array_content.getJSONObject("attributes");
            JSONObject gender = face_attributes.getJSONObject("gender");
            String gender_value = gender.getString("value");
            System.out.println("GENDER : " + gender_value);
            JSONObject age = face_attributes.getJSONObject("age");
            int age_value = age.getInt("value");
            System.out.println("AGE : " + age_value);
            JSONObject ethnic = face_attributes.getJSONObject("ethnicity");
            String ethnic_value = ethnic.getString("value");
            System.out.println("ETHNIC : " + ethnic_value);
            JSONObject emotion = face_attributes.getJSONObject("emotion");
            double sadness = emotion.getDouble("sadness");
            double neutral = emotion.getDouble("neutral");
            double disgust = emotion.getDouble("disgust");
            double anger = emotion.getDouble("anger");
            double surprise = emotion.getDouble("surprise");
            double fear = emotion.getDouble("fear");
            double happiness = emotion.getDouble("happiness");
            Map<String, Double> emotionMap = new HashMap<>();
            emotionMap.put("Kesedihan", sadness); emotionMap.put("Netral", neutral); emotionMap.put("Jijik", disgust);
            emotionMap.put("Marah", anger); emotionMap.put("Terkejut", surprise); emotionMap.put("Takut", fear); emotionMap.put("Bahagia", happiness);
            System.out.println("Sadness : " + sadness + "\nNeutral : " + neutral + "\nDisgust : " + disgust + "\nAnger : " + anger
                    + "\nSurprise : " + surprise + "\nFear : " + fear + "\nHappiness : " + happiness);
            JSONObject beauty = face_attributes.getJSONObject("beauty");
            double beauty_value=0;
            if (gender_value.toUpperCase().equals("MALE"))
                beauty_value = beauty.getDouble("male_score");
            else
                beauty_value = beauty.getDouble("female_score");
            System.out.println("Beauty Score : " + beauty_value);
            double largestEmotion=0;
            String rautWajah=null;
            for (Map.Entry<String, Double> entry:emotionMap.entrySet()
                    ) {
                if(entry.getValue()>largestEmotion){
                    largestEmotion = entry.getValue();
                    rautWajah = entry.getKey();
                }
            }
            String tampanCantik=null;
            if (gender_value.toUpperCase().equals("MALE"))
                tampanCantik = "ketampanan";
            else
                tampanCantik = "kecantikan";
            textMessage = new TextMessage(
                    "Gender : " + gender_value + "\n" +
                            "Umur : " + age_value + " tahun\n" +
                            "Kebangsaan : " + ethnic_value + "\n" +
                            "Raut wajah yang paling terpancar : " + rautWajah + "\n" +
                            "Tingkat " + tampanCantik + " : " + String.format("%.2f", beauty_value) + "%"
            );
        }
        KirimPesan(replyToken, textMessage);

    }

    private final static int CONNECT_TIME_OUT = 30000;
    private final static int READ_OUT_TIME = 50000;
    private static String boundaryString = getBoundary();
    protected static byte[] post(String url, HashMap<String, String> map, HashMap<String, byte[]> fileMap) throws Exception {
        HttpURLConnection conne;
        URL url1 = new URL(url);
        conne = (HttpURLConnection) url1.openConnection();
        conne.setDoOutput(true);
        conne.setUseCaches(false);
        conne.setRequestMethod("POST");
        conne.setConnectTimeout(CONNECT_TIME_OUT);
        conne.setReadTimeout(READ_OUT_TIME);
        conne.setRequestProperty("accept", "*/*");
        conne.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundaryString);
        conne.setRequestProperty("connection", "Keep-Alive");
        conne.setRequestProperty("user-agent", "Mozilla/4.0 (compatible;MSIE 6.0;Windows NT 5.1;SV1)");
        DataOutputStream obos = new DataOutputStream(conne.getOutputStream());
        Iterator iter = map.entrySet().iterator();
        while(iter.hasNext()){
            Map.Entry<String, String> entry = (Map.Entry) iter.next();
            String key = entry.getKey();
            String value = entry.getValue();
            obos.writeBytes("--" + boundaryString + "\r\n");
            obos.writeBytes("Content-Disposition: form-data; name=\"" + key
                    + "\"\r\n");
            obos.writeBytes("\r\n");
            obos.writeBytes(value + "\r\n");
        }
        if(fileMap != null && fileMap.size() > 0){
            Iterator fileIter = fileMap.entrySet().iterator();
            while(fileIter.hasNext()){
                Map.Entry<String, byte[]> fileEntry = (Map.Entry<String, byte[]>) fileIter.next();
                obos.writeBytes("--" + boundaryString + "\r\n");
                obos.writeBytes("Content-Disposition: form-data; name=\"" + fileEntry.getKey()
                        + "\"; filename=\"" + encode(" ") + "\"\r\n");
                obos.writeBytes("\r\n");
                obos.write(fileEntry.getValue());
                obos.writeBytes("\r\n");
            }
        }
        obos.writeBytes("--" + boundaryString + "--" + "\r\n");
        obos.writeBytes("\r\n");
        obos.flush();
        obos.close();
        InputStream ins = null;
        int code = conne.getResponseCode();
        try{
            if(code == 200){
                ins = conne.getInputStream();
            }else{
                ins = conne.getErrorStream();
            }
        }catch (SSLException e){
            e.printStackTrace();
            return new byte[0];
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buff = new byte[4096];
        int len;
        while((len = ins.read(buff)) != -1){
            baos.write(buff, 0, len);
        }
        byte[] bytes = baos.toByteArray();
        ins.close();
        return bytes;
    }
    private static String getBoundary() {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for(int i = 0; i < 32; ++i) {
            sb.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789_-".charAt(random.nextInt("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789_".length())));
        }
        return sb.toString();
    }
    private static String encode(String value) throws Exception{
        return URLEncoder.encode(value, "UTF-8");
    }
    public static byte[] getBytesFromFile(File f) {
        if (f == null) {
            return null;
        }
        try {
            FileInputStream stream = new FileInputStream(f);
            ByteArrayOutputStream out = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = stream.read(b)) != -1)
                out.write(b, 0, n);
            stream.close();
            out.close();
            return out.toByteArray();
        } catch (IOException e) {
        }
        return null;
    }

    public void handleContent(String replyToken, Event event, TextMessageContent content){
        String pesan = content.getText().toUpperCase();
        Source sourcee = event.getSource();
        String user_id = event.getSource().getUserId();
        String idd = getId(sourcee);
        MainDao.CreateTableData(idd);
        MainDao.CreateTableGroupMember(idd);
        System.out.println("user_id : " + user_id);
        String group_id = getId(sourcee);
        System.out.println("group_id : " + group_id);
        GroupMember groupMember = new GroupMember();
        groupMember.setUserId(user_id);
        List<GroupMember> groupMembers = new ArrayList<>();
        int status_insert_memberId = MainDao.InsertGroupMemberId(group_id, user_id);
        if (status_insert_memberId==1)
            System.out.println("ID user baru berhasil di insert database : " + user_id);
        else
            System.out.println("USER ID : " + user_id + " SUDAH ADA DI DB");
        groupMembers = MainDao.getAllMemberIds(group_id);
        int idKe=1;
        for (GroupMember item:groupMembers
                ) {
            System.out.println("USER ID ke-" + idKe  +" : " + item.getUserId());
            idKe++;
        }

        String command = content.getText().toUpperCase().substring(0,4);
        System.out.println("Command atas : " + command);

        //Coba untuk command ini diganti dengan metode String startsWith()
        if((command + "UR").equals("/FITUR"))
            command = "/FITUR";
        if ((command + "KULIAHAN").equals("/PERKULIAHAN"))
            command = "/PERKULIAHAN";
        else if((command + "URAN").equals("/HIBURAN"))
            command = "/HIBURAN";
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
        else if(pesan.equals("APAKAH LJ BOT TAKUT SAMA DEDY?")){
            command = "/LEAVE-GROUP";
        }
        else if((command + "AH").equals("APAKAH"))
            command = "/APAKAH";
        else if((command + "E-SIAPAKAH").equals("/GAME-SIAPAKAH"))
            command = "/GAME-SIAPAKAH";
        else if((command + "AKAH").equals("SIAPAKAH")){
            command = "/SIAPAKAH";
        } else if((pesan.contains("HAI") ||
                pesan.contains("HEI") ||
                pesan.contains("HEY") ||
                pesan.contains("HI")) && pesan.contains("LJ BOT")){
            command = "/HAI";
        } else if((command + "E-DETECT").equals("/FACE-DETECT")){
            command = "/FACE-DETECT";
        } else if((command + "E-STOP").equals("/FACE-STOP")){
            command = "/FACE-STOP";
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
            case "/FITUR" :{
                textMessage = new TextMessage("FITUR-FITUR LJ BOT\n\n" +
                        "1. /PERKULIAHAN\n" +
                        "2. /HIBURAN");
                KirimPesan(replyToken, textMessage);
                break;
            }
            case "/PERKULIAHAN" : {
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
            case "/HIBURAN" : {
                CarouselTemplate carouselTemplate = new CarouselTemplate(
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
                String[] kata = pesan.split(" ");
                List<GroupMember> groupMemberList = new ArrayList<>();
                if(kata[1].equals("YANG")){
                    int indexDann=0, indexYangg=0;
                    try{
                        groupMemberList = MainDao.getAllMemberIds(group_id);
                    } catch (Exception ex){
                        System.out.println("Gagal get all member id : " + ex.toString());
                    }
                    int banyakMember=0;
                    try{
                        banyakMember = groupMemberList.size();
                    } catch (Exception ex){
                        System.out.println("gagal get banyak member : " + ex.toString());
                    }
//                    Random random = new Random();
//                    int randInt = random.nextInt(banyakMember-1);
                    int randInt = 0;
                    try{
                        randInt = (int) (Math.random() * ((banyakMember-1)-0));
                    } catch (Exception ex){
                        System.out.println("Gagal random : " + ex.toString());
                    }
                    System.out.println("Random int : " + randInt);
//                    for (int i =0; i<kata.length; i++){
//                        if (kata[i].equals("YANG")){
//                            indexYangg=i;
//                        }
//                    }
                    StringBuilder kataYang=new StringBuilder();
                    for (int i=1; i<kata.length-1; i++){
                        kataYang.append(kata[i] + " ");
                    }
                    char[] kataTerakhirr;
                    StringBuilder kataTerakhirTanpaTanyaa = new StringBuilder();
                    if(pesan.contains("?")){
                        kataTerakhirr = String.valueOf(kata[kata.length-1]).toCharArray(); //kata terakhir dipecah-pecah jadi perhuruf
                        for (int i=0;i<kataTerakhirr.length-1; i++){
                            kataTerakhirTanpaTanyaa.append(kataTerakhirr[i]); //menggabungkan huruf2 yang dipecah tadi jadi satu tapi minus tanda tanya
                        }
                    } else{
                        kataTerakhirTanpaTanyaa.append(kata[kata.length-1]);
                    }
                    String senderId = event.getSource().getSenderId();
                    String type  = getType(sourcee);
                    GroupMember user_id_beruntung = groupMemberList.get(randInt);
                    System.out.println("USER ID GroupMemberGetList : " + user_id_beruntung.getUserId());
//                    String user_name_beruntung = getName(user_id_beruntung.getUserId());
                    String user_name_beruntung = getGroupMemberName(type, senderId, user_id_beruntung.getUserId());
                    System.out.println("username beruntung : " + user_name_beruntung);
                    textMessage = new TextMessage(user_name_beruntung + " " + String.valueOf(kataYang).toLowerCase() + String.valueOf(kataTerakhirTanpaTanyaa).toLowerCase());
                    KirimPesan(replyToken, textMessage);
                } else{
                    //kata[0] adalah siapakah
                    //kata[1] adalah diantara
                    //kata[2] adalah nama1
                    //kata[3] adalah dan
                    //kata[4] adalah nama2
                    //kata[5] adalah yang
                    //kata[6] adalah paling
                    //kata[7] adalah ....?
                    //Jika di antara (dipisah antara di dan antara
//                String nama1 = null, nama2 = null;
                    StringBuilder nama1 = new StringBuilder();
                    StringBuilder nama2 = new StringBuilder();
                    int indexDan = 0, indexYang = 0;
                    for(int i=0; i<kata.length; i++){
                        System.out.println(kata[i]);
                    }
                    for(int i=0;i<kata.length; i++){
                        if (kata[i].equals("DAN"))
                            indexDan=i;
                        if(kata[i].equals("YANG"))
                            indexYang=i;
                    }
                    System.out.println("index dan : " + indexDan + " index yang : " + indexYang);
                    //SIAPAKAH ANTARA
                    if(kata[1].equals("ANTARA")){
                        for(int i=2; i<indexDan;i++){
                            nama1.append(kata[i] + " ");
                        }
                        for(int i=indexDan+1; i<indexYang;i++){
                            nama2.append(kata[i] + " ");
                        }
                    }
                    //SIAPAKAH DI ANTARA
                    if (kata[2].equals("ANTARA")){
                        for(int i=3; i<indexDan;i++){
                            nama1.append(kata[i] + " ");
                        }
                        for(int i=indexDan+1; i<indexYang;i++){
                            nama2.append(kata[i] + " ");
                        }
//                    nama1 = kata[3];
//                    nama2 = kata[5];
                    }
                    //SIAPAKAH DIANTARA
                    if(kata[1].equals("DIANTARA")){
                        for(int i=2; i<indexDan;i++){
                            nama1.append(kata[i] + " ");
                        }
                        for(int i=indexDan+1; i<indexYang;i++){
                            nama2.append(kata[i] + " ");
                        }
//                    nama1 = kata[2];
//                    nama2 = kata[4];
                    }
                    char[] kataTerakhir;
                    StringBuilder kataTerakhirTanpaTanya = new StringBuilder();
                    if(pesan.contains("?")){
                        kataTerakhir = String.valueOf(kata[kata.length-1]).toCharArray(); //kata terakhir dipecah-pecah jadi perhuruf
                        for (int i=0;i<kataTerakhir.length-1; i++){
                            kataTerakhirTanpaTanya.append(kataTerakhir[i]); //menggabungkan huruf2 yang dipecah tadi jadi satu tapi minus tanda tanya
                        }
                    } else{
                        kataTerakhirTanpaTanya.append(kata[kata.length-1]);
                    }
                    System.out.println("nama1 : " + nama1);
                    System.out.println("nama2 : " + nama2);
                    StringBuilder yangPaling = new StringBuilder();
                    for (int i=indexYang; i<kata.length-1; i++){
                        yangPaling.append(kata[i] + " "); //menggabungkan kata-kata yang dipisah-pusah tadi (yang ... ... ... dst)
                    }
                    System.out.println("yang Paling : " + yangPaling + kataTerakhirTanpaTanya);
                    Random random = new Random();
                    int randInt = random.nextInt(10) + 1;
                    if(randInt%2==0){
                        textMessage = new TextMessage(String.valueOf(nama1) + yangPaling + kataTerakhirTanpaTanya);
                    } else if (randInt%2!=0){
                        textMessage = new TextMessage(String.valueOf(nama2) + yangPaling + kataTerakhirTanpaTanya);
                    }
                    KirimPesan(replyToken, textMessage);
                }
                break;
            }
            case "/GAME-SIAPAKAH" : {
//                if(status_waiting_game==1){
//                    textMessage = new TextMessage("Game SUDAH dimulai.\nketik /join untuk join");
//                    KirimPesan(replyToken, textMessage);
//                } else{
//                    textMessage = new TextMessage("GAME DIMULAI!\nKetik /join untuk join");
//                    KirimPesan(replyToken, textMessage);
//                    Future<Integer> process=null;
//                    status_waiting_game=1;
//                    try {
//                        process = services.process();
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    try {
//                        if(process.get()==10){
//                            status_waiting_game=0;
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
                break;
            }
            case "/JOIN" : {
                //memang game baru dibuat
//                if(status_waiting_game==1){
//                    String userId = event.getSource().getUserId();
//                    System.out.println("userId : " + userId);
//                    String name = getName(userId);
//                    System.out.println("name : " + name);
//                    boolean status_add_list_player = playerList.add(name);
//                    if(status_add_list_player){
//                        textMessage = new TextMessage(name + " berhasil join!");
//                        messageList.add(textMessage);
//                        KirimPesan(replyToken, messageList);
//                        for (String player:playerList
//                             ) {
//                            System.out.println("LIST PLAYER : " + player);
//                        }
//                    }
//                }
//                //gak ada yang yang dibuat
//                else{
//                    textMessage = new TextMessage("Gak ada game yang jalan.\n" +
//                            "Ketik command /game-siapakah untuk mulai");
//                    messageList.add(textMessage);
//                    KirimPesan(replyToken, messageList);
//                }
                break;
            }
            case "/HAI" : {
                String userId = event.getSource().getUserId();
                System.out.println("user ID : " + userId);
                textMessage = new TextMessage("Hai hai " + getName(userId));
                System.out.println("Name : " + getName(userId));
                KirimPesan(replyToken, textMessage);
                break;
            }
            case "/LEAVE-GROUP" : {
                textMessage = new TextMessage("Wah kabur kalo ada dedy kepok~");
                KirimPesan(replyToken, textMessage);
                String type = getType(source);
                if (type.equals("group")){
                    LeaveGroup(id);
                } else if(type.equals("room")){
                    LeaveRoom(id);
                }
                break;
            }
            case "/FACE-DETECT" : {
                detect_img_status = 1;
                textMessage = new TextMessage("MULAI");
                KirimPesan(replyToken, textMessage);
                break;
            }
            case "/FACE-STOP" : {
                detect_img_status = 0;
                textMessage = new TextMessage("Face detection sudah dihentikan");
                KirimPesan(replyToken, textMessage);
                break;
            }
        }
    }

    public void LeaveGroup(String id){
        try {
            Response<BotApiResponse> response =
                    LineMessagingServiceBuilder
                            .create(AccessToken)
                            .build()
                            .leaveGroup(id)
                            .execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void LeaveRoom(String id){
        try {
            Response<BotApiResponse> response =
                    LineMessagingServiceBuilder
                            .create(AccessToken)
                            .build()
                            .leaveRoom(id)
                            .execute();
        } catch (IOException e) {
            e.printStackTrace();
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
            if(sb.equals(null))
                textMessage = new TextMessage("Belum ada data tugas");
            else
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
            if(sb.equals(null))
                textMessage = new TextMessage("Belum ada data tugas");
            else
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
        } else if(data.equals("/CARA-PAKAI-APAKAH")){
            messageList.clear();
            textMessage = new TextMessage("Cara Pakai LJ Ajaib v1\n\n" +
                    "Ketikkan command dengan format :\n" +
                    "Apakah .......\n" +
                    "Contoh : Apakah dedy tampan?");
            messageList.add(textMessage);
        } else if(data.equals("/CARA-PAKAI-SIAPAKAH")){
            messageList.clear();
            textMessage = new TextMessage("Cara Pakai LJ Ajaib v2\n\n" +
                    "Ketikkan command dengan format :\n" +
                    "Siapakah diantara [nama 1] dan [nama 2] yang ......\n" +
                    "Contoh : Siapakah diantara Dedy dan Kepok yang paling tampan?");
            messageList.add(textMessage);
        } else if(data.equals("/CARA-PAKAI-WAJAH")){
            messageList.clear();
            textMessage = new TextMessage("Cara Pakai LJ Ajaib v3\n\n" +
                    "Ketikkan command /FACE-DETECT lalu tunggu sampai LJ BOT membalas 'MULAI'.\n" +
                    "Setelah itu, kirimlah foto dengan 1 wajah didalamnya untuk dideteksi oleh LJ BOT.\n\n" +
                    "Jika sudah selesai bermain-main, ketikkan command /FACE-STOP");
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

    public String getGroupMemberName(String type, String senderId, String userId){
        String userName = "";
        try{
            Response<UserProfileResponse> response =
                    LineMessagingServiceBuilder
                    .create(AccessToken)
                    .build()
                    .getMemberProfile(type, senderId, userId)
                    .execute();
            if (response.isSuccessful()){
                UserProfileResponse profileResponse = response.body();
                userName = profileResponse.getDisplayName();
            } else{
                System.out.println(response.code() + " " + response.message());
            }
        } catch (Exception ex){
            System.out.println("Gagal get Group Member Name : " + ex.toString());
        }
        return userName;
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

//    public String getName2(String userId){
//        final String[] name = new String[1];
//        lineMessagingClient
//                .getProfile(userId)
//                .whenComplete(((userProfileResponse, throwable) -> {
//                    if(throwable!=null){
//                        name[0] = userProfileResponse.getDisplayName();
//                    }
//                }));
//        return name[0];
//    }
}
