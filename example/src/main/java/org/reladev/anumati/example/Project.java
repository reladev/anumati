package org.reladev.anumati.example;

import java.util.Set;

import org.reladev.anumati.SecuredBy;

public class Project implements SecuredBy<SecurityReference> {
	@Override
	public Set<SecurityReference> getSecurityReferences() {
		//Todo implement
		return null;
	}
}
