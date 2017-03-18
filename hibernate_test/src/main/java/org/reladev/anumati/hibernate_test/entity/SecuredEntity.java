package org.reladev.anumati.hibernate_test.entity;

import java.util.Objects;

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

	abstract public Long getId();

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (getId() == null) {
			return false;
		}
		if (!getClass().isAssignableFrom(o.getClass()) && !o.getClass().isAssignableFrom(getClass())) {
			return false;
		}
		SecuredEntity that = (SecuredEntity) o;
		return Objects.equals(getId(), that.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId());
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + ":" + getId();
	}
}
