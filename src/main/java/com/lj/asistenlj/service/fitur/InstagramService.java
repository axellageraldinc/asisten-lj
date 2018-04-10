package com.lj.asistenlj.service.fitur;

import com.linecorp.bot.model.message.Message;

import java.util.List;

public interface InstagramService {
    String INSTAGRAM_URL = "https://www.instagram.com/";
    List<Message> getLinkAndPreview(String pesan);
}
