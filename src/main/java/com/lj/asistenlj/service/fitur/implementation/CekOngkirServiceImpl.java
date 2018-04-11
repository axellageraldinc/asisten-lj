package com.lj.asistenlj.service.fitur.implementation;

import com.linecorp.bot.model.message.TextMessage;
import com.lj.asistenlj.helper.Feature;
import com.lj.asistenlj.helper.RajaOngkirApiUtil;
import com.lj.asistenlj.model.cekongkir.*;
import com.lj.asistenlj.repository.cekongkir.CityRepository;
import com.lj.asistenlj.service.ChatService;
import com.lj.asistenlj.service.FeatureDataService;
import com.lj.asistenlj.service.fitur.CekOngkirService;
import com.lj.asistenlj.service.fitur.RajaOngkirService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

@Service
public class CekOngkirServiceImpl implements CekOngkirService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CekOngkirServiceImpl.class);
    private static final String TAG = CekOngkirServiceImpl.class.getSimpleName();

    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private ChatService chatService;
    @Autowired
    private FeatureDataService featureDataService;

    private static final String API_KEY = "62821e7298a9baad3f24776cb77e1ae7";

    private RajaOngkirService rajaOngkirService;

    @Override
    public void sendResult(String replyToken, String pesan) {
        featureDataService.saveFeatureData(Feature.CEK_ONGKIR);

        rajaOngkirService = RajaOngkirApiUtil.getRajaOngkirService();

        String[] pesanSplit = pesan.split("-");
        String origin = pesanSplit[1];
        String destination = pesanSplit[2];
        String kurir = pesanSplit[3];
        int weight = Integer.parseInt(pesanSplit[4]);

        int cityOriginId = getCityDetailByCityName(origin).getCityId();
        int cityDestinationId = getCityDetailByCityName(destination).getCityId();

        rajaOngkirService.postCekOngkir(API_KEY, cityOriginId, cityDestinationId, kurir.toLowerCase(), weight).enqueue(new Callback<RajaOngkirResponse>() {
            @Override
            public void onResponse(Call<RajaOngkirResponse> call, Response<RajaOngkirResponse> response) {
                if(response.isSuccessful()){
                    StringBuilder messageOngkir = new StringBuilder();
                    List<Result> resultList = response.body().getRajaongkir().getResults();
                    for (Result result:resultList
                            ) {
                        List<Costs> costsList = result.getCosts();
                        messageOngkir.append(cityOriginId + " - " + cityDestinationId + "\n");
                        messageOngkir.append(weight + " gram\n");
                        for (Costs costs:costsList
                                ) {
                            List<Cost> costList = costs.getCost();
                            for (Cost cost:costList
                                    ) {
                                messageOngkir.append(kurir + " " + costs.getService() + " | Rp " + cost.getValue() + "\n");
                            }
                        }
                    }
                    messageOngkir.setLength(messageOngkir.length()-1);
                    chatService.sendResponseMessage(replyToken, new TextMessage(messageOngkir.toString()));
                } else {
                    LOGGER.error(TAG, "Error post cek ongkir : " + response.code() + "," + response.message());
                    LOGGER.error(TAG, response.body().getRajaongkir().getStatus().getDescription());
                }
            }

            @Override
            public void onFailure(Call<RajaOngkirResponse> call, Throwable t) {
                LOGGER.error(TAG, "Failed cek ongkir : " + t.toString());
                chatService.sendResponseMessage(replyToken, new TextMessage("Gagal cek ongkir : " + t.toString()));
            }
        });
    }

    private City getCityDetailByCityName(String cityName){
        return cityRepository.findByCityName(cityName);
    }

}
