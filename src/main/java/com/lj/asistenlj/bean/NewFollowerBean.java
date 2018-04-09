package com.lj.asistenlj.bean;

import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.StickerMessage;
import com.linecorp.bot.model.message.TextMessage;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class NewFollowerBean {

    @Bean
    public TextMessage newFollowerMessage(){
        return new TextMessage("Nuwun yo aku wes di-add dadi friend\n" +
                "Silakan ketik /fitur untuk melihat fitur-fitur yang ada.\n" +
                "Aku di invite ning group yo iso lhoooo");
    }

    @Bean
    public StickerMessage newFollowerStickerMessage(){
        return new StickerMessage("1", "2");
    }

}
