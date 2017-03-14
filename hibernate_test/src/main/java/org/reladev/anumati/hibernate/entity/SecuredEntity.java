package org.reladev.anumati.hibernate.entity;

import org.reladev.anumati.SecuredObjectType;
import org.reladev.anumati.SecuredReference;
import org.reladev.anumati.SecuredReferenceType;
import org.reladev.anumati.hibernate.SecuredByRefEntity;

abstract public class SecuredEntity extends SecuredByRefEntity<Long> {
	public SecuredEntity(SecuredObjectType type) {
		super(type);
	}

	@Override
	protected SecuredReference<Long> createSecuredReference(Long objectId, SecuredObjectType objectType, Long referenceId, SecuredReferenceType referenceType) {
		return new SecurityReference(objectId, objectType, referenceId, referenceType);
	}


}
