package com.lj.asistenlj.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FaceDetectBean {

    @Bean
    public String apiKey(){
        return "rv-O10qPNtoC5OF0giY9xigpw1KSzwL9";
    }

    @Bean
    public String apiSecretKey(){
        return "XicM3ba2hQJiJ39oyBJDkOAiFuH7Jdv-";
    }

}
