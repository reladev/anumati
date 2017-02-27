package org.reladev.anumati.example;

import java.util.HashSet;
import java.util.Set;

import org.reladev.anumati.SecurityActionInterface;


public enum SecurityAction implements SecurityActionInterface {
	VIEW,
	EDIT,
	CREATE,
	DELETE;

	private HashSet<SecurityActionInterface> allActionsThatInclude = new HashSet<>();

	SecurityAction(SecurityAction... includes) {
		allActionsThatInclude.add(this);
		for (SecurityAction action: includes) {
			action.allActionsThatInclude.add(this);
		}
	}

	public Set<SecurityActionInterface> getAllActionsThatInclude() {
		return allActionsThatInclude;
	}

}
