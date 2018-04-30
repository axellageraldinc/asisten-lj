package com.lj.asistenlj.service.fitur;

import com.linecorp.bot.model.event.source.Source;
import com.linecorp.bot.model.message.TextMessage;
import com.lj.asistenlj.model.TempatMakan;

import java.util.List;

public interface MakanDimanaYaService {
    TextMessage getResult(Source source);
    TextMessage saveTempatMakan(Source source, String pesan);
    TextMessage getAllTempatMakan(Source source);
    TempatMakan getRandomedTempatMakan(String groupId);
    String getNamaTempatMakanAndLokasiTempatMakan(String pesan, String regex);
}
