package org.reladev.anumati.hibernate_test.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.reladev.anumati.hibernate_test.JpaBaseRolledBackTestCase;
import org.reladev.anumati.hibernate_test.TestSecurityContext;
import org.reladev.anumati.hibernate_test.dto.TicketDto;
import org.reladev.anumati.hibernate_test.repository.TicketRepository;
import org.reladev.anumati.hibernate_test.security.UserContext;
import org.reladev.anumati.hibernate_test.service.TicketService;

public class TicketTests extends JpaBaseRolledBackTestCase {
    private TicketRepository repository;
    private TicketService service;

    @Before
    public void init() {
        TestSecurityContext.init();
        repository = new TicketRepository(em);
        service = new TicketService(repository);

    }

    @Test
    public void testFactoryCreate() {
        TestSecurityContext.setSuperAdmin(true);

        Ticket ticket = new TicketFactory().getOrCreatePersist();

        assertNotNull(ticket.getId());

        Ticket ticket1 = repository.get(ticket.getId());
        assertEquals(ticket, ticket1);

        List<Ticket> all = repository.findAll();
        assertThat(all).hasSize(1).contains(ticket);
    }

    @Test
    public void testCreate() {
        TicketDto ticketDto = new TicketDto();
        ticketDto.setName("Test1");

        Assertions.assertThatExceptionThrownBy(() -> service.save(ticketDto));

        TestSecurityContext.setCompanyPermissions("PROJECT_V", "TICKET_C");

        service.save(ticketDto);
    }

    @Test
    @Ignore
    public void testEdit() {
        Ticket ticket = new org.reladev.anumati.hibernate_test.factory.TicketFactory().getOrCreatePersist();

        TicketDto ticketDto = new TicketDto();
        ticketDto.setId(ticket.getId());
        ticketDto.setName("Test2");

        Assertions.assertThatExceptionThrownBy(() -> service.save(ticketDto));

        TestSecurityContext.setCompanyPermissions("PROJECT_V", "TICKET_C");

        Ticket actual = service.save(ticketDto);
    }

    @Test
    @Ignore
    public void testFilter() {
        Ticket ticketA = new org.reladev.anumati.hibernate_test.factory.TicketFactory().getOrCreatePersist();
        Project projectB = new ProjectFactory().createPersist();
        Ticket ticketB = new org.reladev.anumati.hibernate_test.factory.TicketFactory().createPersist();

        Company companyZ = new CompanyFactory().createPersist();
        Project projectZ = new ProjectFactory().create();
        Ticket ticketZ = new org.reladev.anumati.hibernate_test.factory.TicketFactory().getOrCreatePersist();

        List<Ticket> all = service.getAll();

        assertThat(all).hasSize(0);

        TestSecurityContext.setCompanyPermissions("TICKET_V");
        all = service.getAll();

        assertThat(all).hasSize(2).contains(ticketA, ticketB);

        TestSecurityContext.setCompanyPermissions();
        TestSecurityContext.setPermissions(UserContext.getUser().getDefaultProject(), "TICKET_V");

        all = service.getAll();
        assertThat(all).hasSize(1).contains(ticketA);

    }

}
