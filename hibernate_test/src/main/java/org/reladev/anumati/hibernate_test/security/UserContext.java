package org.reladev.anumati.hibernate_test.security;

import org.reladev.anumati.SecurityContext;
import org.reladev.anumati.hibernate_test.entity.User;

public class UserContext {

	private static ThreadLocal<User> user = new ThreadLocal<>();

	public static void init(User user) {
		UserContext.user.set(user);
		SecurityContext.setSecuredUserContext(UserContext::getUser);
	}

	public static User getUser() {
		return user.get();
	}
}
