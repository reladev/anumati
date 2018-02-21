package org.reladev.anumati.hibernate_test.entity;


public class TicketFactory extends BaseFactory<TicketFactory, Ticket> {

    public TicketFactory() {
        super(new Ticket());
    }

    public TicketFactory project(Project project) {
        entity.setOwner(project);
        return this;
    }

    protected void ensureRequired() {
        if (entity.getSecuredReferences().isEmpty()) {
            entity.setOwner(new ProjectFactory().getOrCreatePersist());
        }
    }
}
