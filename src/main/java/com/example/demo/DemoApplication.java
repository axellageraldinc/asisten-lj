package com.example.demo;

import com.example.demo.Dao.FollowersDao;
import com.example.demo.Dao.GroupDao;
import com.example.demo.model.Followers;
import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.client.LineMessagingServiceBuilder;
import com.linecorp.bot.model.Multicast;
import com.linecorp.bot.model.PushMessage;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.response.BotApiResponse;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import retrofit2.Response;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;

@SpringBootApplication
@LineMessageHandler
@EnableAsync
public class DemoApplication {

	@Autowired
	private LineMessagingClient lineMessagingClient;

	GetInfoWebSarjana infoWebSarjana = new GetInfoWebSarjana();

	private static String AccessToken = "u/jyVKXsD5N/OfmNIvEjnI+NffMIhzcFFjIZ3Whm4Gu9/LTL4y7WjWhWehHjYIO+aG6QUKw5991HFzs7i8c1PAZP07r1LIGun6o8X53yZflIk/Th0W8JkY9G/2IpWkL59subrXO5cOQCxJqjemzHvwdB04t89/1O/w1cDnyilFU=";

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		SpringApplication.run(DemoApplication.class, args);
		FollowersDao.CreateTable();
		GroupDao.CreateType();
//		getUserId();
//		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
//		TestAsyncClass testAsyncClass = context.getBean(TestAsyncClass.class);
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

	public static void getUserId(){
		List<Followers> followersList = FollowersDao.getFollowers();
		for (Followers followers:followersList) {
			System.out.println("user id : " + followers.getUser_id());
		}
	}

	public static void MulticastEveryone(String message){
		TextMessage textMessage = new TextMessage(message);
		List<Followers> followersList = FollowersDao.getFollowers();
		Set<String> setPenerima = new HashSet<>();
		for (Followers followers:followersList) {
			String user_id = followers.getUser_id();
			setPenerima.add(user_id);
		}
		Multicast multicast = new Multicast(setPenerima, textMessage);
		try {
			Response<BotApiResponse> botApiResponse = LineMessagingServiceBuilder
					.create(AccessToken)
					.build()
					.multicast(multicast)
					.execute();
			System.out.println("Response.code() " + " " + botApiResponse.message());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void pushMessageKeAxell(String message){
		String userId = "Ue16daaf9316ecaa47ecbc4ead0a1685b";
		TextMessage textMessage = new TextMessage(message);
		PushMessage pushMessage = new PushMessage(userId, textMessage);
		try {
			Response<BotApiResponse> botApiResponse = LineMessagingServiceBuilder
                    .create(AccessToken)
                    .build()
                    .pushMessage(pushMessage)
                    .execute();
			System.out.println("Response.code() " + " " + botApiResponse.message());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

//	@EventMapping
//	public StickerMessage handleStickerMessageEvent(MessageEvent<StickerMessageContent> sticker){
//		return new StickerMessage("1", "5");
//	}
}
