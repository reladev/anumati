package org.reladev.anumati.tickets.service;

import javax.inject.Inject;

import org.reladev.anumati.SecurityContext;
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

    public Ticket updateCreate(TicketDto ticketDto) {
        User authUser = (User) SecurityContext.getUser();

        Ticket ticket;
        if (ticketDto.getId() != null) {
            SecurityContext.assertPrivilege(TicketsPrivilege.TicketUpdate);
            ticket = ticketRepository.get(ticketDto.getId());
        } else {
            SecurityContext.assertPrivilege(TicketsPrivilege.TicketCreate);
            ticket = new Ticket();
            ticket.setCompanyId(authUser.getCompanyId());
        }
        ticketDto.copyTo(ticket);

        if (SecurityContext.checkSuperAdmin() || authUser.getCompanyId().equals(ticket.getCompanyId())) {
            SecurityContext.throwPermissionException("Invalid company permissions");
        }

        ticketRepository.save(ticket);

        return ticket;
    }
}
