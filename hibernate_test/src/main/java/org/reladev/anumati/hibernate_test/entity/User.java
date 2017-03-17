package org.reladev.anumati.hibernate_test.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Where;
import org.reladev.anumati.SecuredReference;
import org.reladev.anumati.SecuredReferenceObject;
import org.reladev.anumati.SecuredReferenceType;
import org.reladev.anumati.SecuredUser;
import org.reladev.anumati.UserPermissions;
import org.reladev.anumati.hibernate_test.security.SecurityObjectType;
import org.reladev.anumati.hibernate_test.security.SecurityReferenceType;

@Entity
public class User extends SecuredEntity implements SecuredUser, SecuredReferenceObject {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "object_id",
		  referencedColumnName = "id",
		  foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	@Where(clause = "object_type=" + SecurityObjectType.USER_ORDINAL)
	private Set<SecurityReference> securityReferences = new HashSet<>();

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "company_id", nullable = false)
	private Company company;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "default_department_id", nullable = false)
	private Department defaultDepartment;

	@ManyToMany
	@JoinTable(
			name = "user_department",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "department_id")
	)
	private Set<Department> departments = new HashSet<>();

	private String name;

	public User() {
		super(SecurityObjectType.USER);
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public UserPermissions getUserPermissions() {
		//Todo implement
		return null;
	}

	@Override
	public SecuredReferenceType getSecuredReferenceType() {
		return SecurityReferenceType.USER;
	}

	@Override
	protected Set<? extends SecuredReference> getSecuredReferencesForEdit() {
		return securityReferences;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Set<Department> getDepartments() {
		return departments;
	}

	public void setDepartments(Set<Department> departments) {
		this.departments = departments;
	}

	public Department getDefaultDepartment() {
		return defaultDepartment;
	}

	public void setDefaultDepartment(Department defaultDepartment) {
		this.defaultDepartment = defaultDepartment;
		departments.add(defaultDepartment);
	}
}
