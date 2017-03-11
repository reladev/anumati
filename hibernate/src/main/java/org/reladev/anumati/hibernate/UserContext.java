package org.reladev.anumati.hibernate;

import org.reladev.anumati.SecuredContext;

public class UserContext {

	public static void init() {
		SecuredContext.setSecuredUserContext(UserContext::getUser);

	}

	public static User getUser() {
		return null;
	}
}
