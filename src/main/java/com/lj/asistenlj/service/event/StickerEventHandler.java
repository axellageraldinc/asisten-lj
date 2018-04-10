package com.lj.asistenlj.service.event;

import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.StickerMessageContent;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;
import com.lj.asistenlj.service.ChatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Random;

@LineMessageHandler
public class StickerEventHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(StickerEventHandler.class);

    private static final String PACKAGE_ID_STICKER_DEDY_KAMU_YAKIN = "3200101";
    private static final String STICKER_ID_STICKER_DEDY_KAMU_YAKIN = "34638418";

    private static final String PACKAGE_ID_STICKER_DAMAS_PIKIR = "3237943";
    private static final String STICKER_ID_STICKER_DAMAS_PIKIR = "35638478";

    private static final String PACKAGE_ID_STICKER_HERA_GUE_LIATIN = "3200101";
    private static final String STICKER_ID_STICKER_HERA_GUE_LIATIN = "34638417";

    private static final String PACKAGE_ID_STICKER_DEDY_SINI_KALO_BERANI = "3237943";
    private static final String STICKER_ID_STICKER_DEDY_SINI_KALO_BERANI = "35638482";

    @Autowired
    private ChatService chatService;

    @EventMapping
    public void stickerEvent(MessageEvent<StickerMessageContent> messageEvent) {
        String packageId = messageEvent.getMessage().getPackageId();
        String stickerId = messageEvent.getMessage().getStickerId();
        LOGGER.info("Ada message sticker\n" +
                "packageId : " + packageId + "\n" +
                "stickerId : " + stickerId);
        int randInt = new Random().nextInt(10);
        String replyToken = messageEvent.getReplyToken();
        TextMessage textMessage;
        if (randInt == 7) {
            if (packageId.equals(PACKAGE_ID_STICKER_DEDY_KAMU_YAKIN) && stickerId.equals(STICKER_ID_STICKER_DEDY_KAMU_YAKIN)) {
                textMessage = new TextMessage("Iya yakinnnnnn");
                chatService.sendResponseMessage(replyToken, textMessage);
            } else if (packageId.equals(PACKAGE_ID_STICKER_DAMAS_PIKIR) && stickerId.equals(STICKER_ID_STICKER_DAMAS_PIKIR)) {
                textMessage = new TextMessage("Jangan lama-lama mikirnya");
                chatService.sendResponseMessage(replyToken, textMessage);
            } else if (packageId.equals(PACKAGE_ID_STICKER_HERA_GUE_LIATIN) && stickerId.equals(STICKER_ID_STICKER_HERA_GUE_LIATIN)) {
                textMessage = new TextMessage("Jangan lupa kedip");
                chatService.sendResponseMessage(replyToken, textMessage);
            } else if (packageId.equals(PACKAGE_ID_STICKER_DEDY_SINI_KALO_BERANI) && stickerId.equals(STICKER_ID_STICKER_DEDY_SINI_KALO_BERANI)) {
                textMessage = new TextMessage("Ampun bos");
                chatService.sendResponseMessage(replyToken, textMessage);
            }
        }
    }
}
