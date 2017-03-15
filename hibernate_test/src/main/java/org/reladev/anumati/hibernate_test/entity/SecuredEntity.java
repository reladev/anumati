package org.reladev.anumati.hibernate_test.entity;

import org.reladev.anumati.SecuredObjectType;
import org.reladev.anumati.SecuredReference;
import org.reladev.anumati.SecuredReferenceType;
import org.reladev.anumati.hibernate.SecuredByRefEntity;

abstract public class SecuredEntity extends SecuredByRefEntity {
	public SecuredEntity(SecuredObjectType type) {
		super(type);
	}

	@Override
	protected SecuredReference createSecuredReference(Object objectId, SecuredObjectType objectType, Object referenceId, SecuredReferenceType referenceType) {
		return new SecurityReference(objectId, objectType, referenceId, referenceType);
	}
}
