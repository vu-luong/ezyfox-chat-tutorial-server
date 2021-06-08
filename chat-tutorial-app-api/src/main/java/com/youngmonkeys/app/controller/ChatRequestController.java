package com.youngmonkeys.app.controller;

import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.core.annotation.EzyDoHandle;
import com.tvd12.ezyfox.core.annotation.EzyRequestController;
import com.tvd12.ezyfox.util.EzyLoggable;
import com.tvd12.ezyfoxserver.entity.EzyUser;
import com.tvd12.ezyfoxserver.support.factory.EzyResponseFactory;
import com.youngmonkeys.app.constant.Commands;
import com.youngmonkeys.app.data.ChatContact;
import com.youngmonkeys.common.entity.ChatUser;
import com.youngmonkeys.common.service.ChatUserService;

import java.util.ArrayList;
import java.util.List;

@EzyRequestController
public class ChatRequestController extends EzyLoggable {

    @EzyAutoBind
    ChatUserService userService;

    @EzyAutoBind
    EzyResponseFactory responseFactory;

    @EzyDoHandle(Commands.GET_ALL_USERS)
    public void getAllUsers(EzyUser client) {
        logger.info("Receive getAllUsers request");
        List<ChatUser> allUsers = userService.getAllUsers();

        List<ChatContact> contacts = new ArrayList<>();
        for (ChatUser user: allUsers) {
            ChatContact c = new ChatContact(user.getId(), user.getUsername());
            contacts.add(c);
        }

        responseFactory.newArrayResponse()
                .command(Commands.GET_ALL_USERS)
                .data(contacts)
                .user(client)
                .execute();
    }

}
