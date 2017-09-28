package com.example.demo.Config;

import com.example.demo.AsyncClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@Configuration
public class AppConfig {
    @Bean
    public AsyncClass testAsyncClass(){
        return new AsyncClass();
    }
}
