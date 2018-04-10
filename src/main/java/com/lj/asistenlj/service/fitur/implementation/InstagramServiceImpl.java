package com.lj.asistenlj.service.fitur.implementation;

import com.linecorp.bot.model.message.ImageMessage;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.message.VideoMessage;
import com.lj.asistenlj.service.fitur.InstagramService;
import me.postaddict.instagram.scraper.Instagram;
import me.postaddict.instagram.scraper.cookie.CookieHashSet;
import me.postaddict.instagram.scraper.cookie.DefaultCookieJar;
import me.postaddict.instagram.scraper.interceptor.ErrorInterceptor;
import me.postaddict.instagram.scraper.model.Account;
import me.postaddict.instagram.scraper.model.Media;
import me.postaddict.instagram.scraper.model.MediaType;
import me.postaddict.instagram.scraper.model.PageObject;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class InstagramServiceImpl implements InstagramService {

    private static final String INSTAGRAM_URL = "https://www.instagram.com/";


    @Override
    public List<Message> getLinkAndPreview(String pesan) {
        String[] pesanSplit = pesan.split(" ");
        String username = getUsername(pesanSplit);
        Instagram instagram = createInstagram();
        Media mediaInstagram = getMediaInstagram(instagram, username);
        return getMessage(mediaInstagram);
    }

    private String getUsername(String[] pesanSplit) {
        return pesanSplit[1];
    }

    private Instagram createInstagram() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addNetworkInterceptor(loggingInterceptor)
                .addInterceptor(new ErrorInterceptor())
                .cookieJar(new DefaultCookieJar(new CookieHashSet()))
                .build();
        return new Instagram(httpClient);
    }

    private Media getMediaInstagram(Instagram instagram, String username) {
        Media media;
        try {
            Account account = instagram.getAccountByUsername(username);
            int randInt = new Random().nextInt(account.getMedia().getCount());
            media = account.getMedia().getNodes().get(randInt);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return media;
    }

    private List<Message> getMessage(Media media) {
        List<Message> messages = null;
        switch (media.getMediaType()) {
            case GraphImage:
                messages = createImageOrSidecarMessage(media);
                break;
            case GraphSidecar:
                messages = createImageOrSidecarMessage(media);
                break;
            case GraphVideo:
                messages = createVideoMessage(media);
                break;
            default:
                messages = null;
        }
        return messages;
    }


    private List<Message> createImageOrSidecarMessage(Media media) {
        ImageMessage imageMessage = new ImageMessage(media.getDisplayUrl(), media.getDisplayUrl());
        TextMessage textMessage = null;
        if (media.getMediaType() == MediaType.GraphImage) {
            textMessage = new TextMessage("Tipe: [GAMBAR]\nLink: " + INSTAGRAM_URL + "/p/" + media.getShortcode());
        } else {
            textMessage = new TextMessage("Tipe: [CAROUSEL]\nLink: " + INSTAGRAM_URL + "/p/" + media.getShortcode());
        }
        List<Message> messages = new ArrayList<>();
        messages.add(imageMessage);
        messages.add(textMessage);
        return messages;
    }

    private List<Message> createVideoMessage(Media media) {
        VideoMessage videoMessage = new VideoMessage(media.getVideoUrl(), media.getDisplayUrl());
        TextMessage textMessage = new TextMessage("Tipe: [VIDEO]\nLink: " + INSTAGRAM_URL + "/p/" + media.getShortcode());
        List<Message> messages = new ArrayList<>();
        messages.add(videoMessage);
        messages.add(textMessage);
        return messages;
    }

}
