package org.reladev.anumati.hibernate_test.factory;


import org.reladev.anumati.hibernate_test.entity.Department;

public class DepartmentFactory extends BaseFactory<DepartmentFactory, Department> {

	public DepartmentFactory() {
		super(new Department());
	}

	protected void ensureRequired() {
		if (entity.getSecuredReferences().isEmpty()) {
			entity.setOwner(new CompanyFactory().getOrCreatePersist());
		}
	}
}
