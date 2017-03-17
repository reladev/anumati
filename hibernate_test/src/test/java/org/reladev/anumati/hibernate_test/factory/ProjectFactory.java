package org.reladev.anumati.hibernate_test.factory;


import org.reladev.anumati.hibernate_test.entity.Project;

public class ProjectFactory extends BaseFactory<ProjectFactory, Project> {

	public ProjectFactory() {
		super(new Project());
	}

	protected void ensureRequired() {
		if (entity.getSecuredReferences().isEmpty()) {
			entity.setOwner(new DepartmentFactory().getOrCreatePersist());
		}
	}
}
