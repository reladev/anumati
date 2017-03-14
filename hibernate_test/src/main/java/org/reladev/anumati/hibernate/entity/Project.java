package org.reladev.anumati.hibernate.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Where;
import org.reladev.anumati.SecuredObjectType;
import org.reladev.anumati.SecuredParentChild;
import org.reladev.anumati.SecuredReference;
import org.reladev.anumati.SecuredReferenceType;
import org.reladev.anumati.hibernate.SecuredByRefEntity;
import org.reladev.anumati.hibernate.security.SecurityObjectType;

public class Project extends SecuredByRefEntity<Long> {
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "object_id", referencedColumnName = "id")
	@Where(clause = "object_type=" + SecurityObjectType.PROJECT_ORDINAL)
	private Set<SecurityReference> securityReferences = new HashSet<>();

	public Project() {
		super(SecurityObjectType.PROJECT);
		this.securityReferences = securityReferences;
	}

	@Override
	protected Set<? extends SecuredReference<Long>> getSecuredReferencesForEdit() {
		//Todo implement
		return null;
	}

	@Override
	protected Set<? extends SecuredParentChild<Long>> getChildReferencesForEdit() {
		//Todo implement
		return null;
	}

	@Override
	protected SecuredReference<Long> createSecuredReference(Long objectId, SecuredObjectType objectType, Long referenceId, SecuredReferenceType referenceType) {
		//Todo implement
		return null;
	}
}
