package com.lj.asistenlj.bean;

import com.linecorp.bot.model.action.PostbackAction;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TemplateMessage;
import com.linecorp.bot.model.message.template.CarouselColumn;
import com.linecorp.bot.model.message.template.CarouselTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
public class HiburanBean {

    @Bean
    public CarouselTemplate carouselTemplateHiburan1(){
        return new CarouselTemplate(
                Arrays.asList(
                        new CarouselColumn(null, "LJ AJAIB v1", "LJ Ajaib Apakah Siapakah", Arrays.asList(
                                new PostbackAction("How to LJ Ajaib v1",
                                        "/CARA-PAKAI-SIAPAKAH")
                        )),
                        new CarouselColumn(null, "LJ AJAIB v2", "LJ Ajaib Wajah", Arrays.asList(
                                new PostbackAction("How to LJ Ajaib v2",
                                        "/CARA-PAKAI-WAJAH")
                        )),
                        new CarouselColumn(null, "LJ AJAIB v3", "LJ Ajaib Cinta", Arrays.asList(
                                new PostbackAction("How to LJ Ajaib v3",
                                        "/CARA-PAKAI-CINTA")
                        )),
                        new CarouselColumn(null, "LJ AJAIB v4", "LJ Ajaib InstagramService", Arrays.asList(
                                new PostbackAction("How to LJ Ajaib v4",
                                        "/CARA-PAKAI-INSTAGRAM")
                        ))
                ));
    }

    @Bean
    public CarouselTemplate carouselTemplateHiburan2(){
        return new CarouselTemplate(
                Arrays.asList(
                        new CarouselColumn(null, "LJ AJAIB v5", "LJ Ajaib Dosa", Arrays.asList(
                                new PostbackAction("How to LJ Ajaib v5",
                                        "/CARA-PAKAI-DOSA")
                        )),
                        new CarouselColumn(null, "LJ AJAIB v6", "LJ Ajaib Kapankah", Arrays.asList(
                                new PostbackAction("How to LJ Ajaib v6",
                                        "/CARA-PAKAI-KAPANKAH")
                        )),
                        new CarouselColumn(null, "LJ Ajaib v7", "LJ Ajaib Dimanakah", Arrays.asList(
                                new PostbackAction("How to LJ Ajaib v7",
                                        "/CARA-PAKAI-DIMANAKAH")
                        )),
                        new CarouselColumn(null, "LJ ISLAMI", "LJ ISLAMI", Arrays.asList(
                                new PostbackAction("How to LJ ISLAMI",
                                        "/CARA-PAKAI-ISLAMI")
                        ))
                ));
    }

    @Bean
    public TemplateMessage hiburanTemplateMessage1(@Qualifier("carouselTemplateHiburan1") CarouselTemplate carouselTemplateHiburan1){
        return new TemplateMessage("Asisten LJ mengirim pesan!", carouselTemplateHiburan1);
    }

    @Bean
    public TemplateMessage hiburanTemplateMessage2(@Qualifier("carouselTemplateHiburan2") CarouselTemplate carouselTemplateHiburan2){
        return new TemplateMessage("Asisten LJ mengirim pesan!", carouselTemplateHiburan2);
    }

}
