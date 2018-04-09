package com.lj.asistenlj.bean;

import com.linecorp.bot.model.message.TextMessage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JoinGroupBean {

    @Bean
    public TextMessage joinGroupMessage(){
        return new TextMessage("Nuwun yo aku wes entuk join grup iki\n" +
                "Silakan ketik /fitur untuk melihat fitur-fitur yang ada.");
    }

}
