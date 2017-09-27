package com.example.demo.Controller;

import com.example.demo.GetInfoWebSarjana;
import com.linecorp.bot.model.action.DatetimePickerAction;
import com.linecorp.bot.model.action.MessageAction;
import com.linecorp.bot.model.action.PostbackAction;
import com.linecorp.bot.model.action.URIAction;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.TemplateMessage;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.message.template.CarouselColumn;
import com.linecorp.bot.model.message.template.CarouselTemplate;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;

import java.util.Arrays;

@LineMessageHandler
public class HandleTextMessageController {
//    @EventMapping
//    public TemplateMessage handleCarousel(MessageEvent<TextMessageContent> msg){
//        TemplateMessage templateMessage = null;
//        if (msg.getMessage().getText().equals("carousel")){
//            CarouselTemplate carouselTemplate = new CarouselTemplate(
//                    Arrays.asList(
//                            new CarouselColumn(null, "hoge", "fuga", Arrays.asList(
//                                    new URIAction("Go to line.me",
//                                            "https://line.me"),
//                                    new URIAction("Go to line.me",
//                                            "https://line.me"),
//                                    new PostbackAction("Say hello1",
//                                            "hello こんにちは")
//                            )),
//                            new CarouselColumn(null, "hoge", "fuga", Arrays.asList(
//                                    new PostbackAction("言 hello2",
//                                            "hello こんにちは",
//                                            "hello こんにちは"),
//                                    new PostbackAction("言 hello2",
//                                            "hello こんにちは",
//                                            "hello こんにちは"),
//                                    new MessageAction("Say message",
//                                            "Rice=米")
//                            )),
//                            new CarouselColumn(null, "Datetime Picker", "Please select a date, time or datetime", Arrays.asList(
//                                    new DatetimePickerAction("Datetime",
//                                            "action=sel",
//                                            "datetime",
//                                            "2017-06-18T06:15",
//                                            "2100-12-31T23:59",
//                                            "1900-01-01T00:00"),
//                                    new DatetimePickerAction("Date",
//                                            "action=sel&only=date",
//                                            "date",
//                                            "2017-06-18",
//                                            "2100-12-31",
//                                            "1900-01-01"),
//                                    new DatetimePickerAction("Time",
//                                            "action=sel&only=time",
//                                            "time",
//                                            "06:15",
//                                            "23:59",
//                                            "00:00")
//                            ))
//                    ));
//            templateMessage = new TemplateMessage("Carousel alt text", carouselTemplate);
//        }
//        return templateMessage;
//    }
    //TIDAK TERPAKAI LAGI
//    GetInfoWebSarjana infoWebSarjana = new GetInfoWebSarjana();
//
//    @EventMapping
//    public TextMessage handleTextMessageEvent(MessageEvent<TextMessageContent> msg){
//        TextMessage replyMsg = null;
//        if (msg.getMessage().getText().equals("judul website")){
//            replyMsg = new TextMessage(infoWebSarjana.GetWebDtetiTitle());
//        } else if(msg.getMessage().getText().equals("category")){
//            replyMsg = new TextMessage(infoWebSarjana.GetCategory());
//        } else if(msg.getMessage().getText().equals("date")){
//            replyMsg = new TextMessage(infoWebSarjana.GetDate());
//        } else if(msg.getMessage().getText().equals("deskripsi")){
//            replyMsg = new TextMessage(infoWebSarjana.GetDescription());
//            System.out.println("Deskripsi : " + infoWebSarjana.GetDescription());
//        } else if(msg.getMessage().getText().equals("judul")){
//            replyMsg = new TextMessage(infoWebSarjana.GetTitle());
//        } else if(msg.getMessage().getText().equals("coba")){
//            replyMsg = new TextMessage("tr ID : " + infoWebSarjana.firstId());
//        } else{
//            replyMsg = new TextMessage("kowe ngirim : " + msg.getMessage().getText());
//        }
//        return replyMsg;
//    }
}
