package org.reladev.anumati.example;

import org.reladev.anumati.SecurityContext;

public class UserContext {

	public static void init() {
		SecurityContext.setSecuredUserContext(UserContext::getUser);

	}

	public static User getUser() {
		return null;
	}
}
