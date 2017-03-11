package org.reladev.anumati.hibernate;

import org.reladev.anumati.SecuredContext;

public class Service {
	public void save() {
		SecuredContext.assertPermissions(null, SecurityAction.CREATE);
	}
}
