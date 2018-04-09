package com.lj.asistenlj.service;

import com.linecorp.bot.model.event.source.Source;
import com.lj.asistenlj.model.Group;

public interface GroupService {
    Group buildGroup(Source source);
    void saveGroupToDatabase(Group group);
}
