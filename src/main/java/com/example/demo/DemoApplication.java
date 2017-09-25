package com.example.demo;

import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.StickerMessageContent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.StickerMessage;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@SpringBootApplication
@LineMessageHandler
public class DemoApplication {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		SpringApplication.run(DemoApplication.class, args);
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
		TestAsyncClass testAsyncClass = context.getBean(TestAsyncClass.class);
		System.out.println("About to run...");
		Future future = testAsyncClass.sendResponse();
		System.out.println("This will run immediately");
		Boolean result = (Boolean) future.get();
		System.out.println("Result is : " + result);

	}

	public Document GetConnection() throws IOException {
		Document doc = Jsoup.connect("http://sarjana.jteti.ugm.ac.id/akademik").get();
		return doc;
	}

	public Elements GetReadableBody() throws IOException {
		Document doc = GetConnection();
		Elements tableRow = doc.select("table.table-pad > tbody > tr#4470");
		Elements indexBody = tableRow.select("td:eq(2)");
		return indexBody;
	}

	public String GetWebDtetiTitle(){
		String title="";
		try {
			Document doc = GetConnection();
			title = doc.title();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return title;
	}

	public String GetCategory(){
		String category="";
		try {
			Elements indexBody = GetReadableBody();
			for (Element row :indexBody){
				category = row.select("span.label").text();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return category;
	}

	public String GetDate(){
		String date="";
		try {
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
		} catch (IOException e) {
			e.printStackTrace();
		}
		return date;
	}

	public String GetDescription(){
		String description="";
		try {
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
		} catch (IOException e) {
			e.printStackTrace();
		}
		return description;
	}

	public String GetTitle() {
		String title="";
		try {
			Elements indexBody = GetReadableBody();
			for (Element row:indexBody) {
				String[] body = row.text().split(" ");
				title = row.select("b").text(); //judul pengumuman
			}
		} catch (IOException e){
			e.printStackTrace();
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
		} else{
			replyMsg = new TextMessage("kowe ngirim : " + msg.getMessage().getText());
		}
		return replyMsg;
	}

	@EventMapping
	public StickerMessage handleStickerMessageEvent(MessageEvent<StickerMessageContent> sticker){
		return new StickerMessage("1", "5");
	}
}
