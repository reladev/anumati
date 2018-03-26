package org.reladev.anumati.tickets.controller;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import org.reladev.anumati.tickets.dto.TicketDto;
import org.reladev.anumati.tickets.entity.Ticket;
import org.reladev.anumati.tickets.service.TicketService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/v1/ticket")
public class TicketController {

    @Inject
    TicketService ticketService;

    public TicketController() {
    }

    @RequestMapping(method = GET)
    public List<TicketDto> get() {
        return Collections.emptyList();
    }

    @RequestMapping(path = "/{id}", method = GET)
    public TicketDto get(@PathVariable String id) {
        Long longId = Long.parseLong(id);
        Ticket ticket = ticketService.get(longId);
        return ticketService.convert(ticket);
    }

    @RequestMapping(method = POST)
    public TicketDto create(@RequestBody TicketDto ticketDto) {
        Ticket ticket = ticketService.updateCreate(ticketDto);
        return ticketService.convert(ticket);
    }
}
