package com.youngmonkeys.common.entity;

import com.tvd12.ezyfox.annotation.EzyId;
import com.tvd12.ezyfox.database.annotation.EzyCollection;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
@EzyCollection
public class ChatUser {

    @EzyId
    Long id;

    String username;
    String password;

}
