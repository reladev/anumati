package org.reladev.anumati.hibernate_test.repository;

import java.util.List;

import javax.persistence.EntityManager;

import org.reladev.anumati.hibernate_test.entity.Ticket;
import org.reladev.anumati.hibernate_test.security.SecurityObjectType;

public class TicketRepository extends EntityRepository<Ticket> {
    public TicketRepository(EntityManager entityManager) {
        super(Ticket.class, SecurityObjectType.TICKET, entityManager);
    }

    public List<Ticket> findAll() {
        return buildQuery().ignoreSecurity()
			  .addOrderBy((builder, query, root) ->
				    builder.asc(root.get("name")))
			  .execute();
	}
}
