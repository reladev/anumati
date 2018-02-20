package org.reladev.anumati.hibernate_test.entity;


public class UserFactory extends BaseFactory<UserFactory, User> {

	public UserFactory() {
		super(new User());
	}

	protected void ensureRequired() {
		if (entity.getCompany() == null) {
			entity.setCompany(new CompanyFactory().getOrCreatePersist());
		}

        if (entity.getProjects().isEmpty()) {
            entity.setDefaultProject(new ProjectFactory().getOrCreatePersist());
        }
	}
}
