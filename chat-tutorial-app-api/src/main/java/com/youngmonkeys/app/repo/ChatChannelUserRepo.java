package com.youngmonkeys.app.repo;

import com.tvd12.ezydata.mongodb.EzyMongoRepository;
import com.tvd12.ezyfox.database.annotation.EzyQuery;
import com.tvd12.ezyfox.database.annotation.EzyRepository;
import com.youngmonkeys.app.entity.ChatChannelUser;
import com.youngmonkeys.app.entity.ChatChannelUserId;

import java.util.List;

@EzyRepository
public interface ChatChannelUserRepo
        extends EzyMongoRepository<ChatChannelUserId, ChatChannelUser> {

    @EzyQuery("{'_id.user': ?0}")
    List<ChatChannelUser> findByUser(String username);

    @EzyQuery("{'_id.channelId': {$in: ?0}}")
    List<ChatChannelUser> findByChannelIds(List<Long> channelIds);
}
