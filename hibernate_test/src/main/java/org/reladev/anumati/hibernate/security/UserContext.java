package org.reladev.anumati.hibernate.security;

import org.reladev.anumati.SecurityContext;
import org.reladev.anumati.hibernate.entity.User;

public class UserContext {

	public static void init() {
		SecurityContext.setSecuredUserContext(UserContext::getUser);

	}

	public static User getUser() {
		return new User();
	}
}
