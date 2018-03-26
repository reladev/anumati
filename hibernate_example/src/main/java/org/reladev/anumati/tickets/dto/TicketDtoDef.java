package org.reladev.anumati.tickets.dto;

import org.reladev.anumati.tickets.entity.Ticket;
import org.reladev.quickdto.shared.CopyFromOnly;
import org.reladev.quickdto.shared.QuickDto;

@QuickDto(source = Ticket.class)
public class TicketDtoDef {
    @CopyFromOnly
    Long id;

    String title;
    String description;
}
