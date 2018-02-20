package org.reladev.anumati.article;

import org.reladev.anumati.hibernate_test.dto.TicketDto;

public class TicketRoleService {
    TicketRepository ticketRepository;

    public Ticket save(TicketDto dto) {
        Long id = dto.getId();

        Ticket ticket;
        if (id != null) {
            SecurityContext.assertRole("MANAGER", "MEMBER");
            ticket = ticketRepository.find(id);

        } else {
            SecurityContext.assertRole("MANAGER");
            ticket = new Ticket();
        }

        ticket.setName(dto.getName());

        return ticket;
    }
}
