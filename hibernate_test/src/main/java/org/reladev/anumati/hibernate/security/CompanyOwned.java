package org.reladev.anumati.hibernate.security;


import org.reladev.anumati.SecuredReferenceObject;

public interface CompanyOwned {
	void setOwner(SecuredReferenceObject owner);
}
