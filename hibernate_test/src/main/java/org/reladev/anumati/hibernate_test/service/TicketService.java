package org.reladev.anumati.hibernate_test.service;

import org.reladev.anumati.hibernate_test.dto.TicketDto;
import org.reladev.anumati.hibernate_test.entity.Ticket;
import org.reladev.anumati.hibernate_test.repository.TicketRepository;

public class TicketService extends EntityService<Ticket, TicketDto> {

    public TicketService(TicketRepository repository) {
        super(Ticket.class, repository);
    }

	@Override
    protected Ticket _save(TicketDto dto, Ticket ticket) {
        ticket.setName(dto.getName());

        return ticket;
    }
}
