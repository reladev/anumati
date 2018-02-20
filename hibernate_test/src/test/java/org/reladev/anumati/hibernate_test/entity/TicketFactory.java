package org.reladev.anumati.hibernate_test.entity;

public class TicketFactory extends BaseFactory<TicketFactory, Ticket> {

    public TicketFactory() {
        super(new Ticket());
    }

    protected void ensureRequired() {
        if (entity.getSecuredReferences().isEmpty()) {
            entity.setOwner(new CompanyFactory().getOrCreatePersist());
        }
    }
}
