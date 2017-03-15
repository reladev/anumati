package org.reladev.anumati.hibernate_test.entity;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Formula;
import org.reladev.anumati.SecuredByRef;
import org.reladev.anumati.SecuredObjectType;
import org.reladev.anumati.SecuredParentChild;
import org.reladev.anumati.hibernate_test.security.SecurityObjectType;

@Entity
@Table(name = "parent_child")
public class ParentChildReference implements SecuredParentChild {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Formula("parent_id")
	private Long parentId;
	private SecurityObjectType parentType;

	private Long childId;
	private SecurityObjectType childType;

	protected ParentChildReference() {
	}

	public ParentChildReference(SecuredByRef parent, SecuredByRef child) {
		this(parent.getId(), parent.getSecuredObjectType(), child.getId(), child.getSecuredObjectType());
	}

	public ParentChildReference(Object parentId, SecuredObjectType parentType, Object childId, SecuredObjectType childType) {
		this.parentId = (Long) parentId;
		this.parentType = (SecurityObjectType) parentType;
		this.childId = (Long) childId;
		this.childType = (SecurityObjectType) childType;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Object parentId) {
		this.parentId = (Long) parentId;
	}

	public SecurityObjectType getParentType() {
		return parentType;
	}

	public void setParentType(SecurityObjectType parentType) {
		this.parentType = parentType;
	}

	public Long getChildId() {
		return childId;
	}

	public void setChildId(Object childId) {
		this.childId = (Long) childId;
	}

	public SecurityObjectType getChildType() {
		return childType;
	}

	public void setChildType(SecurityObjectType childType) {
		this.childType = childType;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || !(o instanceof ParentChildReference)) return false;
		ParentChildReference that = (ParentChildReference) o;
		return Objects.equals(parentId, that.parentId) &&
				parentType == that.parentType &&
				Objects.equals(childId, that.childId) &&
				childType == that.childType;
	}

	@Override
	public int hashCode() {
		return Objects.hash(parentId, parentType, childId, childType);
	}
}
