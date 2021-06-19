package com.youngmonkeys.app.controller;

import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.core.annotation.EzyDoHandle;
import com.tvd12.ezyfox.core.annotation.EzyRequestController;
import com.tvd12.ezyfox.util.EzyLoggable;
import com.tvd12.ezyfoxserver.entity.EzyUser;
import com.tvd12.ezyfoxserver.support.factory.EzyResponseFactory;
import com.youngmonkeys.app.constant.Commands;
import com.youngmonkeys.app.data.ChatChannelUsers;
import com.youngmonkeys.app.data.ChatContact;
import com.youngmonkeys.app.entity.ChatChannel;
import com.youngmonkeys.app.entity.ChatChannelUser;
import com.youngmonkeys.app.entity.ChatChannelUserId;
import com.youngmonkeys.app.request.GetChannelRequest;
import com.youngmonkeys.app.service.ChatChannelService;
import com.youngmonkeys.app.service.ChatChannelUserService;
import com.youngmonkeys.common.entity.ChatUser;
import com.youngmonkeys.common.service.ChatUserService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@EzyRequestController
public class ChatRequestController extends EzyLoggable {

    @EzyAutoBind
    ChatUserService userService;

    @EzyAutoBind
    ChatChannelUserService channelUserService;

    @EzyAutoBind
    ChatChannelService channelService;

    @EzyAutoBind
    EzyResponseFactory responseFactory;

    @EzyDoHandle(Commands.GET_ALL_USERS)
    public void getAllUsers(EzyUser client) {
        logger.info("Receive getAllUsers request");
        List<ChatUser> allUsers = userService.getAllUsers();

        List<ChatContact> contacts = new ArrayList<>();
        for (ChatUser user : allUsers) {
            ChatContact c = new ChatContact(user.getId(), user.getUsername());
            contacts.add(c);
        }

        responseFactory.newArrayResponse()
                .command(Commands.GET_ALL_USERS)
                .data(contacts)
                .user(client)
                .execute();
    }

    @EzyDoHandle(Commands.GET_CHANNEL)
    public void getChannel(EzyUser client, GetChannelRequest request) {
        logger.info("Receive getChannel request!");

        List<ChatChannelUsers> channels = channelUserService.getChannelsOfUser(client.getName());

        List<String> users = request.getUsers();
        Set<String> usersSet = new HashSet<>(users);
        usersSet.add(client.getName());

        // Check if channel already existed
        boolean channelExisted = false;
        long channelId = -1;
        for (ChatChannelUsers item: channels) {
            Set<String> channelUsers = item.getUsernames();
            if (channelUsers.equals(usersSet)) {
                channelExisted = true;
                channelId = item.getChannelId();
                break;
            }
        }

        // If channel doesn't exist, create a new one
        if (!channelExisted) {
            channelId = channelService.newChannelId();
            ChatChannel channel = new ChatChannel();
            channel.setId(channelId);

            List<ChatChannelUser> newChannelUsers = new ArrayList<>();
            for (String username: usersSet) {
                ChatChannelUser channelUser = new ChatChannelUser();
                channelUser.setId(new ChatChannelUserId(channelId, username));
                newChannelUsers.add(channelUser);
            }

            channelService.saveChannel(channel);
            channelUserService.saveChannelUsers(newChannelUsers);
        }

        responseFactory.newObjectResponse()
                .command(Commands.GET_CHANNEL)
                .param("channelId", channelId)
                .user(client)
                .execute();

    }

}
