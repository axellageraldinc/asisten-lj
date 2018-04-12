package com.lj.asistenlj.service.fitur.implementation;

import com.linecorp.bot.model.event.source.Source;
import com.linecorp.bot.model.message.TextMessage;
import com.lj.asistenlj.helper.Feature;
import com.lj.asistenlj.helper.Helper;
import com.lj.asistenlj.model.Group;
import com.lj.asistenlj.repository.GroupRepository;
import com.lj.asistenlj.service.FeatureDataService;
import com.lj.asistenlj.service.fitur.FaceDetectService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.imageio.ImageIO;
import javax.net.ssl.SSLException;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

@Service
@Scope(scopeName = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class FaceDetectServiceImpl implements FaceDetectService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FaceDetectServiceImpl.class);

    @Autowired
    private FeatureDataService featureDataService;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private Helper helper;
    @Autowired
    private String apiKey;
    @Autowired
    private String apiSecretKey;
    File file;

    @Override
    public TextMessage getResult(Source source, String id) {
        featureDataService.saveFeatureData(Feature.FACE_DETECT);
        String groupId = helper.getId(source);
        TextMessage textMessage=null;
        String filepath = new File("/opt/tomcat/webapps/ROOT/downloaded.jpg").getAbsolutePath();
        file = new File(filepath);
        try {
            URL urlP = new URL("https://api.line.me/v2/bot/message/" + id + "/content");
            URLConnection conn = urlP.openConnection();
            conn.setRequestProperty("Authorization", "Bearer {ZzEeHlFeiIA/C4TUvl3E/IuYW8TIBEdAr3xzZCgHuURivuycKWOiPGBp5oFqLyHSh/YkUFgm4eGGkVuo4WkOvhUwKdgCbFnO6ltoV/oMU7uJaZAbgM+RqeTo8LAbdjlId0TGTdPe6H0QyfzzoJyppgdB04t89/1O/w1cDnyilFU=}");
            conn.setConnectTimeout(5 * 1000); // Tak tambahin sendiri
            BufferedImage img = ImageIO.read(conn.getInputStream());
            ImageIO.write(img, "jpg", file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        byte[] buff = getBytesFromFile(file);
        String response_faceAPI = doFacePlusAPI(buff);
        textMessage = processJsonFaceApi(response_faceAPI);
        return textMessage;
    }

    @Override
    public TextMessage responseMessage(Source source, String message) {
        TextMessage textMessage = null;
        String groupId = helper.getId(source);
        boolean imageDetectStatus;
        try{
            imageDetectStatus = groupRepository.findImageDetectStatusByGroupId(groupId);
            if(!imageDetectStatus){
                if(message.equals("face-detect")) {
                    updateImageDetectStatus(groupId, true);
                    textMessage = new TextMessage("MULAI");
                } else{
                    textMessage = new TextMessage("/face-detect aja belum dimulai kok mau di stop");
                }
            } else {
                if(message.equals("stop")) {
                    updateImageDetectStatus(groupId, false);
                    textMessage = new TextMessage("Face detection sudah dihentikan");
                } else{
                    textMessage = new TextMessage("face detect sudah dimulai tadi");
                }
            }
        } catch (Exception ex){
            textMessage = new TextMessage("Coba lagi");
            LOGGER.error("Error imageDetectStatus facedetectserviceImpl : " + ex.toString());
        }
        return textMessage;
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
    private String doFacePlusAPI(byte[] buff){
        String url = "https://api-us.faceplusplus.com/facepp/v3/detect";
        HashMap<String, String> map = new HashMap<>();
        HashMap<String, byte[]> byteMap = new HashMap<>();
        map.put("api_key", apiKey);
        map.put("api_secret", apiSecretKey);
        map.put("return_attributes", "age,gender,ethnicity,emotion,beauty");
        byteMap.put("image_file", buff);
        String response = null;
        try{
            byte[] bacd = post(url, map, byteMap);
            response = new String(bacd); //response dalam bentuk json
        }catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }
    private TextMessage processJsonFaceApi(String response_faceAPI){
        TextMessage textMessage = null;
        try{
            JSONObject jsonObject = new JSONObject(response_faceAPI);
            JSONArray jsonArray = jsonObject.getJSONArray("faces");
            int jsonArraySize = jsonArray.length();
            if(jsonArraySize>1){
                textMessage = new TextMessage("tolong kirim foto dengan 1 wajah saja.");
            } else if(jsonArraySize==0) {
                textMessage = new TextMessage("Wajah tidak terdeteksi");
            } else{
                int random = new Random().nextInt(10);
                if(random==5){
                    textMessage = new TextMessage("Ew mukanya jelek, males banget harus detect");
                } else{
                    JSONObject array_content = jsonArray.getJSONObject(0); //index ke-1 adalah face_attributes
                    JSONObject face_attributes = array_content.getJSONObject("attributes");
                    JSONObject gender = face_attributes.getJSONObject("gender");
                    String gender_value = gender.getString("value");
                    JSONObject age = face_attributes.getJSONObject("age");
                    int age_value = age.getInt("value");
                    JSONObject ethnic = face_attributes.getJSONObject("ethnicity");
                    String ethnic_value = ethnic.getString("value");
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
                    JSONObject beauty = face_attributes.getJSONObject("beauty");
                    double beauty_value=0;
                    if (gender_value.toUpperCase().equals("MALE"))
                        beauty_value = beauty.getDouble("male_score");
                    else
                        beauty_value = beauty.getDouble("female_score");
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
            }
        } catch (Exception ex){
            LOGGER.error("Error face detect : " + ex.toString());
        }
        return textMessage;
    }

    public void updateImageDetectStatus(String groupId, boolean imageDetectStatus){
        groupRepository.updateImageDetectStatus(imageDetectStatus, groupId);
    }

}
