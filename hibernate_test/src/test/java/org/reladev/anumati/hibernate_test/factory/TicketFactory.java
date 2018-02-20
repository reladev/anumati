package org.reladev.anumati.hibernate_test.factory;


import org.reladev.anumati.hibernate_test.entity.BaseFactory;
import org.reladev.anumati.hibernate_test.entity.Project;
import org.reladev.anumati.hibernate_test.entity.ProjectFactory;
import org.reladev.anumati.hibernate_test.entity.Ticket;

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
