package org.reladev.anumati.hibernate_test;

import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.reladev.anumati.AccessDeniedException;
import org.reladev.anumati.SecurityContext;
import org.reladev.anumati.hibernate_test.entity.Project;
import org.reladev.anumati.hibernate_test.entity.ProjectFactory;
import org.reladev.anumati.hibernate_test.entity.Ticket;
import org.reladev.anumati.hibernate_test.factory.TicketFactory;
import org.reladev.anumati.hibernate_test.repository.TicketRepository;
import org.reladev.anumati.hibernate_test.security.SecurityRole;
import org.reladev.anumati.hibernate_test.service.TicketService;

public class PureRoleTests extends JpaBaseRolledBackTestCase {
    TicketService service;

    @Before
    public void init() {
        TestSecurityContext.init();
        service = new TicketService(new TicketRepository(em));
    }

    @Test
    public void testUserWithNoRoleFails() {
        try {
            SecurityContext.assertRole(SecurityRole.MANAGER);
            fail("Excepted AccessDeniedException");
        } catch (AccessDeniedException ignore) {
        }
    }

    @Test
    public void testUserWithRole() {
        TestSecurityContext.addRoles(SecurityRole.MANAGER);

        SecurityContext.assertRole(SecurityRole.MANAGER);
        try {
            SecurityContext.assertRole(SecurityRole.MEMBER);
            fail("Excepted AccessDeniedException");
        } catch (AccessDeniedException ignore) {
        }

    }

    @Test
    public void testUserWith2Role() {
        TestSecurityContext.addRoles(SecurityRole.MANAGER);
        TestSecurityContext.addRoles(SecurityRole.MEMBER);

        SecurityContext.assertRole(SecurityRole.MANAGER);
        SecurityContext.assertRole(SecurityRole.MEMBER);
    }

    @Test
    @Ignore
    public void testUserWithRoleOnReference() {
        Project project = new ProjectFactory().getOrCreatePersist();
        Ticket ticket = new TicketFactory().project(project).createPersist();


        TestSecurityContext.addRoles(project, SecurityRole.MANAGER);

        try {
            SecurityContext.assertRole(SecurityRole.MANAGER);
            fail("Excepted AccessDeniedException");
        } catch (AccessDeniedException ignore) {
        }

        SecurityContext.assertRole(ticket, SecurityRole.MANAGER);
    }


}
