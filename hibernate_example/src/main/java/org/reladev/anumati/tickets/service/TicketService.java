package org.reladev.anumati.tickets.service;

import javax.inject.Inject;

import org.reladev.anumati.SecurityContext;
import org.reladev.anumati.tickets.TicketsAction;
import org.reladev.anumati.tickets.TicketsPrivilege;
import org.reladev.anumati.tickets.dto.TicketDto;
import org.reladev.anumati.tickets.entity.Ticket;
import org.reladev.anumati.tickets.entity.User;
import org.reladev.anumati.tickets.repository.TicketRepository;
import org.springframework.stereotype.Service;

@Service
public class TicketService {
    @Inject
    TicketRepository ticketRepository;


    public TicketDto convert(Ticket ticket) {
        TicketDto dto = new TicketDto();
        dto.copyTo(ticket);

        return dto;
    }

    public Ticket get(Long id) {
        Ticket ticket = ticketRepository.get(id);
        SecurityContext.assertPermission(ticket, TicketsAction.READ);
        return ticket;
    }

    public Ticket updateCreate(TicketDto ticketDto) {
        User authUser = (User) SecurityContext.getUser();

        Ticket ticket;
        if (ticketDto.getId() != null) {
            ticket = ticketRepository.get(ticketDto.getId());
            SecurityContext.assertPermission(ticket, TicketsAction.UPDATE);
        } else {
            SecurityContext.assertPermission(TicketsPrivilege.TicketCreate);
            ticket = new Ticket();
            ticket.setCompanyId(authUser.getCompany().getId());
            SecurityContext.assertPermission(ticket, TicketsAction.CREATE);
        }
        ticketDto.copyTo(ticket);

        ticketRepository.save(ticket);

        return ticket;
    }
}
