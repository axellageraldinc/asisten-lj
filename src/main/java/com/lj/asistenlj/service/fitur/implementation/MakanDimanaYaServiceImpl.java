package com.lj.asistenlj.service.fitur.implementation;

import com.linecorp.bot.model.event.source.Source;
import com.linecorp.bot.model.message.TextMessage;
import com.lj.asistenlj.helper.Feature;
import com.lj.asistenlj.helper.Helper;
import com.lj.asistenlj.model.TempatMakan;
import com.lj.asistenlj.repository.MakanDimanaYaRepository;
import com.lj.asistenlj.service.FeatureDataService;
import com.lj.asistenlj.service.fitur.MakanDimanaYaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class MakanDimanaYaServiceImpl implements MakanDimanaYaService {

    @Autowired
    private MakanDimanaYaRepository makanDimanaYaRepository;
    @Autowired
    private FeatureDataService featureDataService;
    @Autowired
    private Helper helper;

    @Override
    public TextMessage getResult(Source source) {
        featureDataService.saveFeatureData(Feature.MAKAN_DIMANA_YA);
        TempatMakan tempatMakan = getRandomedTempatMakan(helper.getId(source));
        TextMessage textMessage;
        if(tempatMakan == null){
            textMessage = new TextMessage("Hmmmm kamu belum ada database tempat makan nih.. Add dulu deh, caranya :\n" +
                    "Ketik /cara-pakai-makan-dimana-ya\n" +
                    "untuk lihat tutorialnya");
        } else{
            textMessage = new TextMessage("Menurut Asisten LJ, hari ini kamu seharusnya makan di :\n" +
                    tempatMakan.getNamaTempatMakan() + "\n" +
                    tempatMakan.getLokasiTempatMakan());
        }
        return textMessage;
    }

    @Override
    public TextMessage saveTempatMakan(Source source, String pesan) {
        pesan = getNamaTempatMakanAndLokasiTempatMakan(pesan, " ");
        String[] pesanSplit = pesan.split(", ");
        TempatMakan tempatMakan;
        TextMessage textMessage;
        if(pesanSplit.length == 2) {
            tempatMakan = makanDimanaYaRepository.save(TempatMakan.builder()
                    .id(UUID.randomUUID().toString())
                    .namaTempatMakan(pesanSplit[0])
                    .lokasiTempatMakan(pesanSplit[1])
                    .groupId(helper.getId(source))
                    .build());
            textMessage = new TextMessage("Berhasil menyimpan database tempat makan :\n" +
                    "Nama tempat makan : " + tempatMakan.getNamaTempatMakan() + "\n" +
                    "Lokasi tempat makan : " + tempatMakan.getLokasiTempatMakan());
        } else{
            textMessage = new TextMessage("Gagal menyimpan database tempat makan.\n" +
                    "Harap periksa lagi command yang digunakan. Untuk melihat command yang benar, ketik :\n" +
                    "/CARA-PAKAI-MAKAN-DIMANA-YA\n" +
                    "untuk melihat cara penggunaan fitur Makan Dimana Ya");
        }
        return textMessage;
    }

    @Override
    public TextMessage getAllTempatMakan(Source source) {
        List<TempatMakan> tempatMakanList = makanDimanaYaRepository.findAllByGroupId(helper.getId(source));
        StringBuilder stringTempatMakan = new StringBuilder();
        stringTempatMakan.append("DAFTAR TEMPAT MAKANMU\n");
        int count=1;
        for (TempatMakan tempatMakan:tempatMakanList
             ) {
            stringTempatMakan.append(count + ". " + tempatMakan.getNamaTempatMakan() + " " + tempatMakan.getLokasiTempatMakan() + "\n");
            count++;
        }
        stringTempatMakan.append("\n" +
                "Total tempat makan terdaftarkan : " + tempatMakanList.size());
        return new TextMessage(String.valueOf(stringTempatMakan));
    }

    @Override
    public TempatMakan getRandomedTempatMakan(String groupId) {
        List<TempatMakan> tempatMakanList = makanDimanaYaRepository.findAllByGroupId(groupId);
        int randomedIndexOfTempatMakanList;
        TempatMakan tempatMakan;
        if(tempatMakanList.size()==0){
            tempatMakan = null;
        } else {
            randomedIndexOfTempatMakanList = (int) (Math.random() * ((tempatMakanList.size() - 1) - 0));
            tempatMakan = tempatMakanList.get(randomedIndexOfTempatMakanList);
        }
        return tempatMakan;
    }

    @Override
    public String getNamaTempatMakanAndLokasiTempatMakan(String pesan, String regex) {
        // Returns a substring containing all characters after a string.
        int posA = pesan.indexOf(regex);
        if (posA == -1) {
            return "";
        }
        int adjustedPosA = posA + regex.length();
        if (adjustedPosA >= pesan.length()) {
            return "";
        }
        return pesan.substring(adjustedPosA);
    }
}
