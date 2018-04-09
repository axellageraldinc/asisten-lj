package com.lj.asistenlj.bean;

import com.linecorp.bot.model.action.PostbackAction;
import com.linecorp.bot.model.message.TemplateMessage;
import com.linecorp.bot.model.message.template.CarouselColumn;
import com.linecorp.bot.model.message.template.CarouselTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class ListFiturBean {

    @Bean
    public CarouselTemplate listFiturCarouselTemplate(){
        return new CarouselTemplate(
                Arrays.asList(
                        new CarouselColumn(null, "HIBURAN", "Serba serbi hiburan", Arrays.asList(
                                new PostbackAction("List Command",
                                        "/HIBURAN",
                                        "/HIBURAN")
                        )),
                        new CarouselColumn(null, "SOURCE CODE", "Source Code Asisten LJ", Arrays.asList(
                                new PostbackAction("Lihat SourceCode",
                                        "/SOURCE-CODE",
                                        "/SOURCE-CODE")
                        )),
                        new CarouselColumn(null, "ABOUT", "About Asisten LJ", Arrays.asList(
                                new PostbackAction("LIHAT",
                                        "/ABOUT",
                                        "/ABOUT")
                        )),
                        new CarouselColumn(null, "SPONSOR", "Sponsor Asisten LJ", Arrays.asList(
                                new PostbackAction("Lihat Sponsor",
                                        "/SPONSOR",
                                        "/SPONSOR")
                        ))
                ));
    }

    @Bean
    public TemplateMessage listFiturTemplateMessage(){
        return new TemplateMessage("Asisten LJ mengirim pesan!", listFiturCarouselTemplate());
    }

}
