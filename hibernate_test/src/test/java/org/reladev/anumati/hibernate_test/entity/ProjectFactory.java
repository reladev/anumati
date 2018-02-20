package org.reladev.anumati.hibernate_test.entity;

public class ProjectFactory extends BaseFactory<ProjectFactory, Project> {

    public static Project rawProject(Long id) {
        return new Project(id);
    }

    public ProjectFactory() {
        super(new Project());
    }

    protected void ensureRequired() {
        if (entity.getSecuredReferences().isEmpty()) {
            entity.setOwner(new CompanyFactory().getOrCreatePersist());
        }
    }
}
