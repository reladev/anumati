package org.reladev.anumati.example;

import org.reladev.anumati.SecurityContext;

public class Service {
	public void save() {
		SecurityContext.assertPermission(null, SecurityAction.CREATE);
	}
}
