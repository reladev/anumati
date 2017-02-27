package org.reladev.anumati;

public interface SecurityReferenceInterface<T extends SecuredByTypeInterface> {

	T getSecureByType();
	Object getId();
	String getActions();
}
