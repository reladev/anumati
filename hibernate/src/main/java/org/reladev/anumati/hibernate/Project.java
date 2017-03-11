package org.reladev.anumati.hibernate;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Where;
import org.reladev.anumati.SecuredAction;
import org.reladev.anumati.SecuredByRef;
import org.reladev.anumati.SecuredObjectType;
import org.reladev.anumati.SecuredReference;
import org.reladev.anumati.SecuredReferenceObject;

public class Project implements SecuredByRef {
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "object_id", referencedColumnName = "id")
	@Where(clause = "object_type=" + SecurityObjectType.PROJECT_ORDINAL)
	private Set<SecurityReference> securityReferences = new HashSet<>();

	@Override
	public Long getId() {
		//Todo implement
		return null;
	}

	@Override
	public SecuredObjectType getSecuredObjectType() {
		//Todo implement
		return null;
	}

	@Override
	public Set<SecuredReference> getSecuredReferences() {
		//Todo implement
		return null;
	}

	@Override
	public void addSecuredReference(SecuredReferenceObject refObj, boolean owner, SecuredAction... actions) {
		//Todo implement
	}

	@Override
	public void removeSecurityReference(SecuredReferenceObject refObj) {
		//Todo implement
	}

	@Override
	public boolean isRefActionsOnly() {
		//Todo implement
		return false;
	}

	@Override
	public void setRefActionsOnly(boolean refActionsOnly) {
		//Todo implement
	}
}
