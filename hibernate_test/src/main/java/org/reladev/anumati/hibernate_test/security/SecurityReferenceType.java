package org.reladev.anumati.hibernate_test.security;

import org.reladev.anumati.SecuredReferenceType;

public enum SecurityReferenceType implements SecuredReferenceType {
	COMPANY,
    PROJECT,
    USER(true);

	private boolean checkRefOnly;

	SecurityReferenceType() {
		this(false);
	}

	SecurityReferenceType(boolean checkRefOnly) {
		this.checkRefOnly = checkRefOnly;
	}

	public boolean isCheckRefOnly() {
		return checkRefOnly;
	}

}
