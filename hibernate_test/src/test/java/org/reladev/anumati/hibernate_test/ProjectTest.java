package org.reladev.anumati.hibernate_test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.reladev.anumati.hibernate_test.dto.TicketDto;
import org.reladev.anumati.hibernate_test.entity.Company;
import org.reladev.anumati.hibernate_test.entity.Department;
import org.reladev.anumati.hibernate_test.entity.Ticket;
import org.reladev.anumati.hibernate_test.factory.CompanyFactory;
import org.reladev.anumati.hibernate_test.factory.DepartmentFactory;
import org.reladev.anumati.hibernate_test.factory.TicketFactory;
import org.reladev.anumati.hibernate_test.repository.TicketRepository;
import org.reladev.anumati.hibernate_test.security.UserContext;
import org.reladev.anumati.hibernate_test.service.TicketService;

public class TicketTest extends JpaBaseRolledBackTestCase {
    TicketService service;

	@Before
	public void init() {
		TestSecurityContext.init();
        service = new TicketService(new TicketRepository(em));
    }

	@Test
	public void testCreate() {
        TicketDto ticketDto = new TicketDto();
        ticketDto.setName("Test1");

        Assertions.assertThatExceptionThrownBy(() -> service.save(ticketDto));

        TestSecurityContext.setCompanyPermissions("DEPARTMENT_V", "TICKET_C");

        service.save(ticketDto);
    }

	@Test
	public void testEdit() {
        Ticket ticket = new TicketFactory().getOrCreatePersist();

        TicketDto ticketDto = new TicketDto();
        ticketDto.setId(ticket.getId());
        ticketDto.setName("Test2");

        Assertions.assertThatExceptionThrownBy(() -> service.save(ticketDto));

        TestSecurityContext.setCompanyPermissions("DEPARTMENT_V", "TICKET_C");

        Ticket actual = service.save(ticketDto);
    }

	@Test
	public void testFilter() {
        Ticket ticketA = new TicketFactory().getOrCreatePersist();
        Department departmentB = new DepartmentFactory().createPersist();
        Ticket ticketB = new TicketFactory().createPersist();

		Company companyZ = new CompanyFactory().createPersist();
		Department departmentZ = new DepartmentFactory().create();
        Ticket ticketZ = new TicketFactory().getOrCreatePersist();

        List<Ticket> all = service.getAll();

		assertThat(all).hasSize(0);

        TestSecurityContext.setCompanyPermissions("TICKET_V");
        all = service.getAll();

		assertThat(all)
			  .hasSize(2).contains(ticketA, ticketB);

		TestSecurityContext.setCompanyPermissions();
        TestSecurityContext.setPermissions(UserContext.getUser().getDefaultDepartment(), "TICKET_V");

		all = service.getAll();
		assertThat(all)
			  .hasSize(1).contains(ticketA);

	}
}
