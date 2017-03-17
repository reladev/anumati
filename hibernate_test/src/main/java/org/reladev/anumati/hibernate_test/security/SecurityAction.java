package org.reladev.anumati.hibernate_test.security;

import java.security.InvalidParameterException;

import org.reladev.anumati.SecuredAction;


public enum SecurityAction implements SecuredAction {
	VIEW('V'),
	EDIT('E'),
	CREATE('C'),
	DELETE('D'),
	PERMISSIONS('P');

	private char abbreviation;

	SecurityAction(char abbreviation) {
		this.abbreviation = abbreviation;
	}

	@Override
	public int getFlagPosition() {
		return ordinal();
	}

	public static SecurityAction valueOfAbbreviation(char abbreviation) {
		for (SecurityAction action: values()) {
			if (action.abbreviation == abbreviation) {
				return action;
			}
		}
		throw new InvalidParameterException("Unknow abbreviation:" + abbreviation);
	}
}
