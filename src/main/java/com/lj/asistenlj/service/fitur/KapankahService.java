package com.lj.asistenlj.service.fitur;

import com.linecorp.bot.model.event.source.Source;
import com.linecorp.bot.model.message.TextMessage;

public interface KapankahService {
    TextMessage getResult(Source source, String pesan);
}
