package com.lj.asistenlj.bean;

import com.linecorp.bot.model.message.TextMessage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SourceCodeBean {

    @Bean
    public TextMessage sourceCodeMessage(){
        return new TextMessage("Hai, untuk saat ini source code LJ belum di-publish ya karena masih dalam masa perbaikan" +
                "\nKalau udah di-publish nanti bakal dikasih tau kok :)");
    }

}
