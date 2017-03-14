package org.reladev.anumati.hibernate.entity;

import java.util.Objects;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Formula;
import org.reladev.anumati.SecuredByRef;
import org.reladev.anumati.SecuredObjectType;
import org.reladev.anumati.SecuredParentChild;
import org.reladev.anumati.hibernate.security.SecurityObjectType;

@Entity
@Table(name = "parent_child")
public class ParentChildReference implements SecuredParentChild<Long> {

	@Id
	@SequenceGenerator(name = "parent_child_id_seq", sequenceName = "parent_child_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "parent_child_id_seq")
	@Access(AccessType.PROPERTY)
	private Long id;

	@Formula("parent_id")
	private Long parentId;
	private SecurityObjectType parentType;

	private Long childId;
	private SecurityObjectType childType;

	protected ParentChildReference() {
	}

	public ParentChildReference(SecuredByRef<Long> parent, SecuredByRef<Long> child) {
		this(parent.getId(), parent.getSecuredObjectType(), child.getId(), child.getSecuredObjectType());
	}

	public ParentChildReference(Long parentId, SecuredObjectType parentType, Long childId, SecuredObjectType childType) {
		this.parentId = parentId;
		this.parentType = (SecurityObjectType) parentType;
		this.childId = childId;
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

	public void setParentId(Long parentId) {
		this.parentId = parentId;
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

	public void setChildId(Long childId) {
		this.childId = childId;
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
