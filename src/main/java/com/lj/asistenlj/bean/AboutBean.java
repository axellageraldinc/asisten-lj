package com.lj.asistenlj.bean;

import com.linecorp.bot.model.action.URIAction;
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
public class AboutBean {

    @Bean
    public CarouselTemplate carouselTemplate1(){
        return new CarouselTemplate(
                Arrays.asList(
                        new CarouselColumn("https://s1.postimg.org/2oftwfysgb/Dedy.png", "Dedy Kurniawan S", "Backfiring Underdog", Arrays.asList(
                                new URIAction("INSTAGRAM",
                                        "https://www.instagram.com/dedykur.s/")
                        )),
                        new CarouselColumn("https://s1.postimg.org/9nr1wc2xkv/Raufi.png", "Raufi Musaddiq", "Kenapa harus satu kalau bisa banyak?", Arrays.asList(
                                new URIAction("INSTAGRAM",
                                        "https://www.instagram.com/raufimusaddiq/")
                        )),
                        new CarouselColumn("https://s1.postimg.org/701llzelzz/Farras.png", "Farras Aulia M", "If you love cats, then we should be friends :3", Arrays.asList(
                                new URIAction("INSTAGRAM",
                                        "https://www.instagram.com/simplyfarras/")
                        )),
                        new CarouselColumn("https://s1.postimg.org/1ag9ae9inz/Adin.png", "Bayu Adin H", "Sedikit berbeda lebih baik drpd sedikit lebih baik", Arrays.asList(
                                new URIAction("INSTAGRAM",
                                        "https://www.instagram.com/bayuadinh/")
                        )),
                        new CarouselColumn("https://s1.postimg.org/5tiaddq4u7/Rio.png", "Rio Armando M", "Pecandu senyuman, a beauty hunter", Arrays.asList(
                                new URIAction("INSTAGRAM",
                                        "https://www.instagram.com/rioarmandom/")
                        ))

                ));
    }
    @Bean
    public CarouselTemplate carouselTemplate2(){
        return new CarouselTemplate(
                Arrays.asList(
                        new CarouselColumn("https://s1.postimg.org/4qil2hknqn/Damas.png", "Sulistyo Damas P", "Rapper yang gak jago bahasa inggris", Arrays.asList(
                                new URIAction("INSTAGRAM",
                                        "https://www.instagram.com/sulistyodamas/")
                        )),
                        new CarouselColumn("https://s1.postimg.org/9kkhgmlxgr/Fatur.png", "Ramadhan Faturrahman", "Ryuu ga waga teki wo kurau!!!", Arrays.asList(
                                new URIAction("INSTAGRAM",
                                        "https://www.instagram.com/rmdhnfatur/")
                        )),
                        new CarouselColumn("https://s1.postimg.org/1z9iuf01pb/Adot.png", "Aditya Laksana S", "Fans nya bob dylan", Arrays.asList(
                                new URIAction("INSTAGRAM",
                                        "https://www.instagram.com/aditya.suwandi/")
                        )),
                        new CarouselColumn("https://s1.postimg.org/7lb98a2uj3/Putos.png", "Mahendra Tirta S", "Love music, sport, and computer stuff", Arrays.asList(
                                new URIAction("INSTAGRAM",
                                        "https://www.instagram.com/saputer/")
                        )),
                        new CarouselColumn("https://s1.postimg.org/5pyofnr9sf/Bless.png", "Bless Ramadewa", "Asumsi itu membunuhmu", Arrays.asList(
                                new URIAction("INSTAGRAM",
                                        "https://www.instagram.com/blesswastika/")
                        ))
                ));
    }
    @Bean
    public CarouselTemplate carouselTemplate3(){
        return new CarouselTemplate(
                Arrays.asList(
                        new CarouselColumn("https://s1.postimg.org/4cp6tmtknv/Hera.png", "Hera Prasetia", "Kehidupan yang menyenangkan di Mozambique", Arrays.asList(
                                new URIAction("INSTAGRAM",
                                        "https://www.instagram.com/heraprasetia")
                        )),
                        new CarouselColumn("https://s1.postimg.org/6sydqjolof/Sam.png", "Samuel Tulus P", "Pekerjaan berat akan terasa ringan kl gak dikerjakan", Arrays.asList(
                                new URIAction("INSTAGRAM",
                                        "https://www.instagram.com/samuel_tulus/")
                        )),
                        new CarouselColumn("https://s1.postimg.org/1jyj58ph1b/Pakdhe.png", "Alexander Jordan H", " Be Still", Arrays.asList(
                                new URIAction("INSTAGRAM",
                                        "https://www.instagram.com/jordanhimawan/")
                        ))
                ));
    }
    @Bean
    public CarouselTemplate carouselTemplate4(){
        return new CarouselTemplate(
                Arrays.asList(
                        new CarouselColumn("https://s1.postimg.org/7hq4mj4xf3/Axel.png", "Axellageraldinc A", "Hai sayang..", Arrays.asList(
                                new URIAction("INSTAGRAM",
                                        "https://www.instagram.com/axellageraldinc/")
                        )),
                        new CarouselColumn("https://s1.postimg.org/2ypm7l7im7/Alman.png", "Almantera T A F", "Gak jelas anjas!", Arrays.asList(
                                new URIAction("INSTAGRAM",
                                        "https://www.instagram.com/almanalfaruq/")
                        )),
                        new CarouselColumn("https://s1.postimg.org/54p0tcuw1r/Azzum.png", "Moh Azzum Jordhan W", "Susah gak ada artinya kl bersama keluarga", Arrays.asList(
                                new URIAction("INSTAGRAM",
                                        "https://www.instagram.com/azzumjordhan/")
                        ))
                ));
    }

    @Bean
    public TemplateMessage templateAbout1(@Qualifier("carouselTemplate1") CarouselTemplate carouselTemplate1){
        return new TemplateMessage("Asisten LJ mengirim pesan!", carouselTemplate1);
    }
    @Bean
    public TemplateMessage templateAbout2(@Qualifier("carouselTemplate2") CarouselTemplate carouselTemplate2){
        return new TemplateMessage("Asisten LJ mengirim pesan!", carouselTemplate2);
    }
    @Bean
    public TemplateMessage templateAbout3(@Qualifier("carouselTemplate3") CarouselTemplate carouselTemplate3){
        return new TemplateMessage("Asisten LJ mengirim pesan!", carouselTemplate3);
    }
    @Bean
    public TemplateMessage templateAbout4(@Qualifier("carouselTemplate4") CarouselTemplate carouselTemplate4){
        return new TemplateMessage("Asisten LJ mengirim pesan!", carouselTemplate4);
    }

}
