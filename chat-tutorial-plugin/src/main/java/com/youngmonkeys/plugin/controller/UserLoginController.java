package com.youngmonkeys.plugin.controller;

import static com.tvd12.ezyfoxserver.constant.EzyEventNames.USER_LOGIN;

import com.tvd12.ezyfox.sercurity.EzySHA256;
import com.tvd12.ezyfoxserver.constant.EzyLoginError;
import com.tvd12.ezyfoxserver.exception.EzyLoginErrorException;
import com.youngmonkeys.common.entity.ChatUser;
import com.youngmonkeys.common.service.ChatUserService;
import com.youngmonkeys.plugin.service.WelcomeService;
import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyfox.core.annotation.EzyEventHandler;
import com.tvd12.ezyfoxserver.context.EzyPluginContext;
import com.tvd12.ezyfoxserver.controller.EzyAbstractPluginEventController;
import com.tvd12.ezyfoxserver.event.EzyUserLoginEvent;

@EzySingleton
@EzyEventHandler(USER_LOGIN)
public class UserLoginController extends EzyAbstractPluginEventController<EzyUserLoginEvent> {

	@EzyAutoBind
	private WelcomeService welcomeService;

	@EzyAutoBind
	private ChatUserService userService;
	
	@Override
	public void handle(EzyPluginContext ctx, EzyUserLoginEvent event) {
		logger.info("{} login in", welcomeService.welcome(event.getUsername()));

		String username = event.getUsername();
		String password = encodePassword(event.getPassword());

		ChatUser user = userService.getUser(username);

		if (user == null) {
			logger.info("User doesn't exist in db, create a new one!");
			user = userService.createUser(username, password);
			userService.saveUser(user);
		}

		if (!user.getPassword().equals(password)) {
			throw new EzyLoginErrorException(EzyLoginError.INVALID_PASSWORD);
		}

		logger.info("user and password match, accept user: {}", username);

	}

	private String encodePassword(String password) {
		return EzySHA256.cryptUtfToLowercase(password);
	}

}