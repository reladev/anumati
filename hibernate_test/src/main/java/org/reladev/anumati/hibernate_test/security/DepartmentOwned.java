package org.reladev.anumati.hibernate_test.security;


import org.reladev.anumati.SecuredReferenceObject;

public interface DepartmentOwned {
	void setOwner(SecuredReferenceObject refObj);
}
