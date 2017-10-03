package com.example.demo.Creator;

import com.linecorp.bot.model.message.TextMessage;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class JadwalSholat {
    public TextMessage jadwalSholat(String lokasi) throws Exception {

        String url = "https://time.siswadi.com/pray/?address=" + lokasi;

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", "Mozilla/5.0");

        int responseCode = con.getResponseCode();

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        System.out.println(response.toString());
        JSONObject jsonObject = new JSONObject(response.toString());
        JSONObject data = jsonObject.getJSONObject("data");
        String Fajr = data.getString("Fajr");
        String Dhuhr = data.getString("Dhuhr");
        String Asr = data.getString("Asr");
        String Maghrib = data.getString("Maghrib");
        String Isha = data.getString("Isha");
        String SepertigaMalam = data.getString("SepertigaMalam");
        String TengahMalam = data.getString("TengahMalam");
        String DuapertigaMalam = data.getString("DuapertigaMalam");
        TextMessage textMessage = new TextMessage("Jadwal sholat wilayah " + lokasi + "\n\n" +
                "Fajr : " + Fajr + "\n" +
                "Dhuhr : " + Dhuhr + "\n" +
                "Asr : " + Asr + "\n" +
                "Maghrib : " + Maghrib + "\n" +
                "Isha : " + Isha + "\n" +
                "Sepertiga Malam : " + SepertigaMalam + "\n" +
                "Tengah Malam : " + TengahMalam + "\n" +
                "Duapertiga Malam : " + DuapertigaMalam);
        return textMessage;
    }
}
