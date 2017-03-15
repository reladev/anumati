package org.reladev.anumati.hibernate_test.entity;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.Formula;
import org.reladev.anumati.SecuredAction;
import org.reladev.anumati.SecuredActionsSet;
import org.reladev.anumati.SecuredByRef;
import org.reladev.anumati.SecuredObjectType;
import org.reladev.anumati.SecuredReference;
import org.reladev.anumati.SecuredReferenceObject;
import org.reladev.anumati.SecuredReferenceType;
import org.reladev.anumati.hibernate_test.security.SecurityObjectType;
import org.reladev.anumati.hibernate_test.security.SecurityReferenceType;

@Entity
public class SecurityReference implements SecuredReference<Long> {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Formula("object_id")
	private Long objectId;
	private SecurityObjectType objectType;
	private Long referenceId;
	private SecurityReferenceType referenceType;

	private boolean owner;
	private boolean fixed;

	private Long actionFlags;

	protected SecurityReference() {
	}

	public SecurityReference(SecuredByRef<Long> object, SecuredReferenceObject<Long> refObj) {
		this(object.getId(), object.getSecuredObjectType(), refObj.getId(), refObj.getSecuredReferenceType());
	}

	public SecurityReference(Long objectId, SecuredObjectType objectType, Long referenceId, SecuredReferenceType referenceType) {
		this.objectId = objectId;
		this.objectType = (SecurityObjectType) objectType;
		this.referenceId = referenceId;
		this.referenceType = (SecurityReferenceType) referenceType;
	}

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

	public SecuredActionsSet getAllowedActions() {
		if (actionFlags == null) {
			return null;

		} else {
			return new SecuredActionsSet(actionFlags);
		}
	}

	public void setActionFlags(Long actionFlags) {
		this.actionFlags = actionFlags;
	}

	@Override
	public void setAllowedActions(SecuredActionsSet actions) {
		actionFlags = actions.getFlags();
	}

	@Override
	public void setAllowedActions(SecuredAction... actions) {
		if (actions.length == 0) {
			actionFlags = null;

		} else {
			SecuredActionsSet actionsSet = new SecuredActionsSet();
			for (SecuredAction action : actions) {
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
	public boolean isFixed() {
		return fixed;
	}

	@Override
	public void setFixed(boolean fixed) {
		this.fixed = fixed;
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
