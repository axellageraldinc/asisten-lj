package com.lj.asistenlj.bean;

import com.linecorp.bot.model.message.TextMessage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SponsorBean {

    @Bean
    public TextMessage sponsorMessage(){
        return new TextMessage("SPONSOR TUNGGAL ASISTEN LJ\n\n" +
                "www.daiserver.com\n" +
                "Daiserver adalah alasan utama kenapa Asisten LJ tidak pernah down lagi meskipun kalian masih suka ngirim foto wajah tidak bermutu dan tidak enak dipandang itu.");
    }

}
