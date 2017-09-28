package com.example.demo;

import com.example.demo.Dao.MainDao;
import com.example.demo.Service.AsyncServices;
import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

import javax.annotation.Resource;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@SpringBootApplication
@LineMessageHandler
@EnableAsync
public class DemoApplication {

	@Resource
	static AsyncServices services;

	@Autowired
	private LineMessagingClient lineMessagingClient;

	GetInfoWebSarjana infoWebSarjana = new GetInfoWebSarjana();

	private static String AccessToken = "u/jyVKXsD5N/OfmNIvEjnI+NffMIhzcFFjIZ3Whm4Gu9/LTL4y7WjWhWehHjYIO+aG6QUKw5991HFzs7i8c1PAZP07r1LIGun6o8X53yZflIk/Th0W8JkY9G/2IpWkL59subrXO5cOQCxJqjemzHvwdB04t89/1O/w1cDnyilFU=";

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		SpringApplication.run(DemoApplication.class, args);
		MainDao.CreateType();
//		getUserId();
//		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
//		AsyncClass testAsyncClass = context.getBean(AsyncClass.class);
//		Future future = testAsyncClass.getUpdate();
//		String update = future.get().toString();
//		System.out.println("UPDATE : " + update);
//		pushMessageKeAxell(update);

//		System.out.println("About to run...");
//		Future future = testAsyncClass.sendResponse();
//		System.out.println("This will run immediately");
//		String result = (String) future.get();
//		System.out.println("Result is : " + result);
	}

//	@EventMapping
//	public StickerMessage handleStickerMessageEvent(MessageEvent<StickerMessageContent> sticker){
//		return new StickerMessage("1", "5");
//	}
}
