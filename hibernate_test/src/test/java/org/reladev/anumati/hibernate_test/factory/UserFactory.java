package org.reladev.anumati.hibernate_test.factory;


import org.reladev.anumati.hibernate_test.entity.User;

public class UserFactory extends BaseFactory<UserFactory, User> {

	public UserFactory() {
		super(new User());
	}

	protected void ensureRequired() {
		if (entity.getCompany() == null) {
			entity.setCompany(new CompanyFactory().getOrCreatePersist());
		}

		if (entity.getDepartments().isEmpty()) {
			entity.setDefaultDepartment(new DepartmentFactory().getOrCreatePersist());
		}
	}
}
