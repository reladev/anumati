package org.reladev.anumati.hibernate_test;

import org.junit.Before;
import org.junit.Test;
import org.reladev.anumati.AccessDeniedException;
import org.reladev.anumati.SecurityContext;
import org.reladev.anumati.hibernate_test.entity.Project;
import org.reladev.anumati.hibernate_test.entity.ProjectFactory;
import org.reladev.anumati.hibernate_test.entity.Ticket;
import org.reladev.anumati.hibernate_test.entity.TicketFactory;
import org.reladev.anumati.hibernate_test.repository.TicketRepository;
import org.reladev.anumati.hibernate_test.security.SecurityAction;
import org.reladev.anumati.hibernate_test.security.SecurityPrivilege;
import org.reladev.anumati.hibernate_test.security.SecurityRole;
import org.reladev.anumati.hibernate_test.service.TicketService;

import static org.junit.Assert.fail;

public class AdminTests extends JpaBaseRolledBackTestCase {
    TicketService service;

    @Before
    public void init() {
        TestSecurityContext.init();
        service = new TicketService(new TicketRepository(em));
    }

    @Test
    public void testSuperAdmin() {
        Project project = new ProjectFactory().getOrCreatePersist();
        Ticket ticket = new TicketFactory().project(project).createPersist();

        TestSecurityContext.setSuperAdmin(true);

        SecurityContext.assertPermission(SecurityRole.MANAGER);
        SecurityContext.assertPermission(SecurityPrivilege.SOME_TASK);

        SecurityContext.assertPermission(ticket, SecurityRole.MANAGER);
        SecurityContext.assertPermission(ticket, SecurityPrivilege.SOME_TASK);
        SecurityContext.assertPermission(ticket, SecurityAction.PERMISSIONS);
    }

    @Test
    public void testProjectAdmin() {
        Project project = new ProjectFactory().getOrCreatePersist();
        Ticket ticket = new TicketFactory().project(project).createPersist();

        TestSecurityContext.setAdminPermissions(project, true);


        try {
            SecurityContext.assertPermission(SecurityRole.MANAGER);
            fail("Excepted AccessDeniedException");
        } catch (AccessDeniedException ignore) {
        }
        try {
            SecurityContext.assertPermission(SecurityPrivilege.SOME_TASK);
            fail("Excepted AccessDeniedException");
        } catch (AccessDeniedException ignore) {
        }

        SecurityContext.assertPermission(ticket, SecurityRole.MANAGER);
        SecurityContext.assertPermission(ticket, SecurityPrivilege.SOME_TASK);
        SecurityContext.assertPermission(ticket, SecurityAction.PERMISSIONS);
    }
}
