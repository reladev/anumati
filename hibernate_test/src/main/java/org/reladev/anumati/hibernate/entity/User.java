package org.reladev.anumati.hibernate.entity;

import org.reladev.anumati.SecuredUser;
import org.reladev.anumati.UserPermissions;

public class User implements SecuredUser {
	private Company company;
	private Department department;

	@Override
	public UserPermissions getUserPermissions() {
		//Todo implement
		return null;
	}

	public Company getCompany() {
		return company;
	}

	public Department getDepartment() {
		return department;
	}
}
