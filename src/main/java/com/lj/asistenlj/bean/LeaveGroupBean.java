package com.lj.asistenlj.bean;

import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class LeaveGroupBean {

    @Bean
    public TextMessage leaveMessageFail1(){
        return new TextMessage("Terima kasih sudah mengakui dan menerima dirimu apa adanya");
    }
    @Bean
    public TextMessage leaveMessageFail2(){
        return new TextMessage("Tapi maaf kamu belum beruntung, coba lagi untuk kick aku :p");
    }

}
