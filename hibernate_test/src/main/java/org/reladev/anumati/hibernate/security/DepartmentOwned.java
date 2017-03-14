package org.reladev.anumati.hibernate.security;


import org.reladev.anumati.SecuredReferenceObject;

public interface DepartmentOwned {
	void setOwner(SecuredReferenceObject refObj);
}
