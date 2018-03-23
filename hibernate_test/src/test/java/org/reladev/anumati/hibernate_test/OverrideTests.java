package org.reladev.anumati.hibernate_test;

import org.junit.Before;
import org.junit.Test;
import org.reladev.anumati.SecurityContext;
import org.reladev.anumati.hibernate_test.entity.Project;
import org.reladev.anumati.hibernate_test.entity.ProjectFactory;
import org.reladev.anumati.hibernate_test.entity.Ticket;
import org.reladev.anumati.hibernate_test.entity.TicketFactory;
import org.reladev.anumati.hibernate_test.repository.TicketRepository;
import org.reladev.anumati.hibernate_test.security.SecurityAction;
import org.reladev.anumati.hibernate_test.security.SecurityObjectType;
import org.reladev.anumati.hibernate_test.service.TicketService;

public class OverrideTests extends JpaBaseRolledBackTestCase {
    TicketService service;

    @Before
    public void init() {
        TestSecurityContext.init();
        service = new TicketService(new TicketRepository(em));
    }

    @Test
    public void testUserWithPrivilegeOnReference() {
        Project project = new ProjectFactory().getOrCreatePersist();
        Ticket ticket = new TicketFactory().project(project).createPersist();

        TestSecurityContext.setPermissions(project, SecurityObjectType.TICKET, SecurityAction.VIEW);

        SecurityContext.assertPermissions(ticket, SecurityAction.VIEW);
    }

    @Test
    public void testUserWithPrivilegeOnReference2() {
        Project project = new ProjectFactory().getOrCreatePersist();
        Ticket ticket = new TicketFactory().project(project).createPersist();


        TestSecurityContext.setPermissions(project, SecurityObjectType.TICKET, SecurityAction.VIEW);

        SecurityContext.assertPermissions(ticket, SecurityAction.VIEW);
    }

}
