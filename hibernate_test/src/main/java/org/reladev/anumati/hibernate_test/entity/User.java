package org.reladev.anumati.hibernate_test.entity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
import org.reladev.anumati.SecuredUser;
import org.reladev.anumati.UserPermissions;
import org.reladev.anumati.hibernate_test.security.SecurityObjectType;

@Entity
public class User extends SecuredEntity implements SecuredUser {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "object_id", referencedColumnName = "id")
	@Where(clause = "object_type=" + SecurityObjectType.PROJECT_ORDINAL)
	private Set<SecurityReference> securityReferences = new HashSet<>();

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "company_id", nullable = false)
	private Company company;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "company_id", nullable = false)
	private Department defaultDepartment;

	@ManyToMany
	@JoinTable(
			name = "user_department",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "department_id")
	)
	private List<Department> departments;

	private String name;

	public User() {
		super(SecurityObjectType.USER);
	}

	@Override
	public UserPermissions getUserPermissions() {
		//Todo implement
		return null;
	}

	@Override
	protected Set<? extends SecuredReference<Long>> getSecuredReferencesForEdit() {
		return securityReferences;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public List<Department> getDepartments() {
		return departments;
	}

	public void setDepartments(List<Department> departments) {
		this.departments = departments;
	}

	public Department getDefaultDepartment() {
		return defaultDepartment;
	}

	public void setDefaultDepartment(Department defaultDepartment) {
		this.defaultDepartment = defaultDepartment;
	}
}
