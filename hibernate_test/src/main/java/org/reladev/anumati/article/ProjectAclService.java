package org.reladev.anumati.article;

import org.reladev.anumati.hibernate_test.dto.TicketDto;

public class TicketAclService {
    TicketRepository ticketRepository;

    public Ticket save(TicketDto dto) {
        Long id = dto.getId();

        Ticket ticket;
        if (id != null) {
            ticket = ticketRepository.find(id);
            SecurityContext.assertAcl(ticket, "EDIT");

        } else {
            SecurityContext.assertRole("MANAGER");
            ticket = new Ticket();
            //...Attach ACLs...
        }

        ticket.setName(dto.getName());

        return ticket;
    }
}
