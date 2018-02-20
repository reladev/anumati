package org.reladev.anumati.hibernate_test.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Where;
import org.reladev.anumati.SecuredParentChild;
import org.reladev.anumati.SecuredReference;
import org.reladev.anumati.hibernate_test.security.ProjectOwned;
import org.reladev.anumati.hibernate_test.security.SecurityObjectType;

@Entity
public class Submission extends SecuredEntity implements ProjectOwned {
    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "object_id",
		  referencedColumnName = "id",
		  foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	@Where(clause = "object_type=" + SecurityObjectType.SUBMISSION_ORDINAL)
	private Set<SecurityReference> securityReferences = new HashSet<>();

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "parent_id", referencedColumnName = "id")
	@Where(clause = "parent_type=" + SecurityObjectType.SUBMISSION_ORDINAL)
	private Set<ParentChildReference> childReferences = new HashSet<>();

	private String name;

	public Submission() {
		super(SecurityObjectType.SUBMISSION);
	}

	@Override
	protected Set<? extends SecuredReference> getSecuredReferencesForEdit() {
		return securityReferences;
	}

	@Override
	protected Set<? extends SecuredParentChild> getChildReferencesForEdit() {
		return childReferences;
	}

	@Override
	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
