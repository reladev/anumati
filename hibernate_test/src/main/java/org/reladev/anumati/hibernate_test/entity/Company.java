package org.reladev.anumati.hibernate_test.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Where;
import org.reladev.anumati.SecuredObjectType;
import org.reladev.anumati.SecuredReference;
import org.reladev.anumati.SecuredReferenceObject;
import org.reladev.anumati.SecuredReferenceType;
import org.reladev.anumati.hibernate.SecuredByRefEntity;
import org.reladev.anumati.hibernate_test.security.SecurityObjectType;
import org.reladev.anumati.hibernate_test.security.SecurityReferenceType;

@Entity
public class Company extends SecuredByRefEntity<Long> implements SecuredReferenceObject<Long> {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "object_id", referencedColumnName = "id")
	@Where(clause = "object_type=" + SecurityObjectType.PROJECT_ORDINAL)
	private Set<SecurityReference> securityReferences = new HashSet<>();

	public Company() {
		super(SecurityObjectType.PROJECT);
		this.securityReferences = securityReferences;
	}

	@Override
	public SecuredReferenceType getSecuredReferenceType() {
		return SecurityReferenceType.COMPANY;
	}

	@Override
	protected Set<? extends SecuredReference<Long>> getSecuredReferencesForEdit() {
		return securityReferences;
	}


	@Override
	protected SecuredReference<Long> createSecuredReference(Long objectId, SecuredObjectType objectType, Long referenceId, SecuredReferenceType referenceType) {
		//Todo implement
		return null;
	}
}
