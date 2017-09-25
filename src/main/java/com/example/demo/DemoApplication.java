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

import java.io.IOException;

@SpringBootApplication
@LineMessageHandler
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	public String GetWebDtetiTitle(){
		String title="";
		try {
			Document doc = Jsoup.connect("http://sarjana.jteti.ugm.ac.id/akademik").get();
			title = doc.title();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return title;
	}

	public String GetCategory(){
		String category="";
		try {
			Document doc = Jsoup.connect("http://sarjana.jteti.ugm.ac.id/akademik").get();
			Elements tableRow = doc.select("table.table-pad > tbody > tr#4470");
			Elements indexBody = tableRow.select("td:eq(2)");
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
			Document doc = Jsoup.connect("http://sarjana.jteti.ugm.ac.id/akademik").get();
			Elements tableRow = doc.select("table.table-pad > tbody > tr#4470");
			Elements indexBody = tableRow.select("td:eq(2)");
			for (Element row:indexBody){
				date = row.select("span.hidden-md.hidden-lg.visible-sm.visible-xs > br").text();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return date;
	}

	@EventMapping
	public TextMessage handleTextMessageEvent(MessageEvent<TextMessageContent> msg){
		TextMessage replyMsg = null;
		if (msg.getMessage().getText().equals("judul")){
			replyMsg = new TextMessage(GetWebDtetiTitle());
		} else if(msg.getMessage().getText().equals("category")){
			replyMsg = new TextMessage(GetCategory());
		} else if(msg.getMessage().getText().equals("date")){
			replyMsg = new TextMessage(GetDate());
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
