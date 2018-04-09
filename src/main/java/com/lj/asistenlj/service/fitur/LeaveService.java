package com.lj.asistenlj.service.fitur;

import com.linecorp.bot.model.event.source.Source;
import com.linecorp.bot.model.message.Message;

import java.util.List;

public interface LeaveService {
    void leave(Source source);
    List<Message> getReplyMessages(Source source);
}
