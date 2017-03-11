package org.reladev.anumati.hibernate;

import java.util.Objects;
import java.util.Optional;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Formula;
import org.reladev.anumati.SecuredActionsSet;
import org.reladev.anumati.SecuredByRef;
import org.reladev.anumati.SecuredObjectType;
import org.reladev.anumati.SecuredReference;
import org.reladev.anumati.SecuredReferenceObject;
import org.reladev.anumati.SecuredReferenceType;

@Entity
abstract public class SecurityReference implements SecuredReference {

	@Id
	Long id;

	@Formula("object_id")
	private Long objectId;
	private SecurityObjectType objectType;
	private Long referenceId;
	private SecurityReferenceType referenceType;

	private boolean owner;

	private Long actionFlags;

	protected SecurityReference() {
	}

	public SecurityReference(SecuredByRef<Long> object, SecuredReferenceObject<Long> refObj) {
		this(object.getId(), object.getSecuredObjectType(), refObj.getId(), refObj.getSecuredReferenceType());
	}

	public SecurityReference(Long objectId, SecuredObjectType objectType, Long referenceId, SecuredReferenceType referenceType) {
		setId(objectId);
		this.objectType = (SecurityObjectType) objectType;
		this.referenceId = referenceId;
		this.referenceType = (SecurityReferenceType) referenceType;
	}

	abstract public void setId(Object id);

	public Long getObjectId() {
		return objectId;
	}

	public void setObjectId(Long objectId) {
		this.objectId = objectId;
	}

	public SecuredObjectType getObjectType() {
		return objectType;
	}

	public void setObjectType(SecurityObjectType objectType) {
		this.objectType = objectType;
	}

	public Long getReferenceId() {
		return referenceId;
	}

	public void setReferenceId(Long referenceId) {
		this.referenceId = referenceId;
	}

	public SecurityReferenceType getReferenceType() {
		return referenceType;
	}

	public void setReferenceType(SecurityReferenceType referenceType) {
		this.referenceType = referenceType;
	}

	public Optional<SecuredActionsSet> getAllowedActions() {
		if (actionFlags == null) {
			return Optional.empty();

		} else {
			return Optional.of(new SecuredActionsSet(actionFlags));
		}
	}

	public void setActionFlags(Long actionFlags) {
		this.actionFlags = actionFlags;
	}

	public void setActions(SecurityAction... actions) {
		if (actions.length == 0) {
			actionFlags = null;

		} else {
			SecuredActionsSet actionsSet = new SecuredActionsSet();
			for (SecurityAction action : actions) {
				actionsSet.add(action);
			}
			actionFlags = actionsSet.getFlags();
		}
	}

	public boolean isOwner() {
		return owner;
	}

	public void setOwner(boolean owner) {
		this.owner = owner;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || !(o instanceof SecurityReference)) return false;
		SecurityReference that = (SecurityReference) o;
		return Objects.equals(objectId, that.objectId) &&
				objectType == that.objectType &&
				Objects.equals(referenceId, that.referenceId) &&
				referenceType == that.referenceType;
	}

	@Override
	public int hashCode() {
		return Objects.hash(objectId, objectType, referenceId, referenceType);
	}
}
