package org.reladev.anumati.hibernate.security;

import org.reladev.anumati.SecuredAction;
import org.reladev.anumati.SecuredActionsSet;


public enum SecurityAction implements SecuredAction {
	VIEW,
	EDIT,
	CREATE,
	DELETE,
	PERMISSIONS;

	private SecuredActionsSet allActionsThatInclude = new SecuredActionsSet();

	SecurityAction(SecurityAction... includes) {
		allActionsThatInclude.add(this);
		for (SecurityAction action: includes) {
			action.allActionsThatInclude.add(this);
		}
	}



	@Override
	public SecuredActionsSet getAllActionsThatInclude() {
		return allActionsThatInclude;
	}

	@Override
	public int getFlagPosition() {
		return ordinal();
	}
}
