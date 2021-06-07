package com.youngmonkeys.common.service.impl;

import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.youngmonkeys.common.entity.ChatUser;
import com.youngmonkeys.common.repo.ChatUserRepo;
import com.youngmonkeys.common.service.ChatMaxIdService;
import com.youngmonkeys.common.service.ChatUserService;
import lombok.Setter;

@Setter
@EzySingleton("userService")
public class ChatUserServiceImpl implements ChatUserService {

    @EzyAutoBind
    private ChatUserRepo chatUserRepo;

    @EzyAutoBind
    private ChatMaxIdService maxIdService;

    @Override
    public void saveUser(ChatUser user) {
        chatUserRepo.save(user);
    }

    @Override
    public ChatUser createUser(String username, String password) {
        ChatUser user = new ChatUser();
        user.setId(maxIdService.incrementAndGet("chat_user"));
        user.setUsername(username);
        user.setPassword(password);
        chatUserRepo.save(user);
        return user;
    }

    @Override
    public ChatUser getUser(String username) {
        return chatUserRepo.findByField("username", username);
    }
}
