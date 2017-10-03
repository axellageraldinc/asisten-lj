package com.example.demo;

import com.example.demo.Dao.MainDao;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.RestController;
import java.util.concurrent.ExecutionException;

@SpringBootApplication
@LineMessageHandler
@EnableAsync
@RestController
public class DemoApplication {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		SpringApplication.run(DemoApplication.class, args);
		MainDao.CreateType();
	}
}
