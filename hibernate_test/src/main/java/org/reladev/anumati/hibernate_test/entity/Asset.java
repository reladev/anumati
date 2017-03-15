package org.reladev.anumati.hibernate_test.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Where;
import org.reladev.anumati.SecuredObjectType;
import org.reladev.anumati.SecuredReference;
import org.reladev.anumati.SecuredReferenceType;
import org.reladev.anumati.hibernate.SecuredByRefEntity;
import org.reladev.anumati.hibernate_test.security.SecurityObjectType;

public class Asset extends SecuredByRefEntity {
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "object_id", referencedColumnName = "id")
	@Where(clause = "object_type=" + SecurityObjectType.ASSET_ORDINAL)
	private Set<SecurityReference> securityReferences = new HashSet<>();

	public Asset() {
		super(SecurityObjectType.ASSET);
	}

	@Override
	protected Set<? extends SecuredReference> getSecuredReferencesForEdit() {
		return securityReferences;
	}

	@Override
	protected SecuredReference createSecuredReference(Object objectId, SecuredObjectType objectType, Object referenceId, SecuredReferenceType referenceType) {
		//Todo implement
		return null;
	}
}
