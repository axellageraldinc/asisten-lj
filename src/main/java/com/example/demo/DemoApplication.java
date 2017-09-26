package com.example.demo;

import com.linecorp.bot.client.ChannelTokenSupplier;
import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.client.LineMessagingServiceBuilder;
import com.linecorp.bot.model.Multicast;
import com.linecorp.bot.model.PushMessage;
import com.linecorp.bot.model.event.FollowEvent;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.StickerMessageContent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.event.source.Source;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.StickerMessage;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.response.BotApiResponse;
import com.linecorp.bot.spring.boot.LineBotAutoConfiguration;
import com.linecorp.bot.spring.boot.LineBotProperties;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineBotMessages;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;
import retrofit2.Response;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@SpringBootApplication
@LineMessageHandler
@EnableAsync
public class DemoApplication implements ChannelTokenSupplier {

	@Autowired
	private LineMessagingClient lineMessagingClient;

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		SpringApplication.run(DemoApplication.class, args);
		System.out.println("Hello from method main...");
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
		TestAsyncClass testAsyncClass = context.getBean(TestAsyncClass.class);
		Future future = testAsyncClass.getUpdate();
		String update = future.get().toString();
		System.out.println("UPDATE : " + update);
//		System.out.println("About to run...");
//		Future future = testAsyncClass.sendResponse();
//		System.out.println("This will run immediately");
//		String result = (String) future.get();
//		System.out.println("Result is : " + result);
	}

	@EventMapping
	public void testFollow(FollowEvent followEvent){
		String userId = followEvent.getSource().getUserId();
		System.out.println("new follower : " + userId);
	}

	public Document GetConnection() {
		Document doc = null;
		try {
			doc = Jsoup.connect("http://sarjana.jteti.ugm.ac.id/akademik").get();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return doc;
	}

	public Elements getTableRow(){
		Document doc = GetConnection();
		Elements tableRow = doc.select("table.table-pad > tbody");
		return tableRow;
	}

	public String firstId(){
		Element link = getTableRow().select("tr").first();
		String trId = link.attr("id");
		return trId;
	}

	public Elements GetReadableBody() {
		Document doc = GetConnection();
		Elements tableRow = doc.select("table.table-pad > tbody > tr#" + firstId());
		Elements indexBody = tableRow.select("td:eq(2)");
		return indexBody;
	}

	public String GetWebDtetiTitle(){
		String title="";
		Document doc = GetConnection();
		title = doc.title();
		return title;
	}

	public String GetCategory(){
		String category="";
		Elements indexBody = GetReadableBody();
		for (Element row :indexBody){
            category = row.select("span.label").text();
        }
		return category;
	}

	public String GetDate(){
		String date="";
		Elements indexBody = GetReadableBody();
		for (Element row:indexBody){
            String[] body = row.text().split(" ");
            String title = row.select("b").text(); //judul pengumuman
            int titleSize = title.split(" ").length;
            int CategoryDanTanggal = titleSize + 4; //4 adalah kategori tambah tanggal, 4 adalah jumlah kata
            String description="";
            while (CategoryDanTanggal < body.length){
                description += body[CategoryDanTanggal] + " ";
                CategoryDanTanggal++;
            }
            //CategoryDanTanggal dimulai dari index ke-1 (yaitu suku kata tanggal)
            CategoryDanTanggal=1;
            //Kurang dari 4 karena indeks ke-4 sudah masuk description
            while (CategoryDanTanggal < 4){
                //suku kata ke-1 adalah tanggal, suku kata ke-2 adalah bulan, suku kata ke-3 adalah tahun, lalu stop looping
                date += body[CategoryDanTanggal] + " ";
                CategoryDanTanggal++;
            }
        }
		return date;
	}

	public String GetDescription(){
		String description="";
		Elements indexBody = GetReadableBody();
		for (Element row:indexBody){
            String[] body = row.text().split(" ");
            String title = row.select("b").text(); //judul pengumuman
            int titleSize = title.split(" ").length;
            int CategoryDanTanggal = titleSize + 4; //4 adalah kategori tambah tanggal, 4 adalah jumlah kata
            while (CategoryDanTanggal < body.length){
                description += body[CategoryDanTanggal] + " ";
                CategoryDanTanggal++;
            }
        }
		return description;
	}

	public String GetTitle() {
		String title="";
		Elements indexBody = GetReadableBody();
		for (Element row:indexBody) {
            String[] body = row.text().split(" ");
            title = row.select("b").text(); //judul pengumuman
        }
		return title;
	}

	@EventMapping
	public TextMessage handleTextMessageEvent(MessageEvent<TextMessageContent> msg){
		TextMessage replyMsg = null;
		if (msg.getMessage().getText().equals("judul website")){
			replyMsg = new TextMessage(GetWebDtetiTitle());
		} else if(msg.getMessage().getText().equals("category")){
			replyMsg = new TextMessage(GetCategory());
		} else if(msg.getMessage().getText().equals("date")){
			replyMsg = new TextMessage(GetDate());
		} else if(msg.getMessage().getText().equals("deskripsi")){
			replyMsg = new TextMessage(GetDescription());
			System.out.println("Deskripsi : " + GetDescription());
		} else if(msg.getMessage().getText().equals("judul")){
			replyMsg = new TextMessage(GetTitle());
		} else if(msg.getMessage().getText().equals("coba")){
			replyMsg = new TextMessage("tr ID : " + firstId());
		} else{
			replyMsg = new TextMessage("kowe ngirim : " + msg.getMessage().getText());
		}
		return replyMsg;
	}

	@EventMapping
	public StickerMessage handleStickerMessageEvent(MessageEvent<StickerMessageContent> sticker){
		return new StickerMessage("1", "5");
	}

	@Override
	public String get() {
		return null;
	}
}
