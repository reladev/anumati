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
import org.reladev.anumati.hibernate_test.security.SecurityPrivilege;
import org.reladev.anumati.hibernate_test.security.SecurityRole;
import org.reladev.anumati.hibernate_test.service.TicketService;

import static org.junit.Assert.fail;

public class RolePrivilegeTests extends JpaBaseRolledBackTestCase {
    TicketService service;

    @Before
    public void init() {
        TestSecurityContext.init();
        service = new TicketService(new TicketRepository(em));
    }

    @Test
    public void testUserWithNoRoleFails() {
        try {
            SecurityContext.assertPermission(SecurityRole.MANAGER);
            fail("Excepted AccessDeniedException");
        } catch (AccessDeniedException ignore) {
        }
    }

    @Test
    public void testUserWithRole() {
        Project project = new ProjectFactory().getOrCreatePersist();
        Ticket ticket = new TicketFactory().project(project).createPersist();

        TestSecurityContext.addRoles(SecurityRole.MANAGER);

        SecurityContext.assertPermission(SecurityRole.MANAGER);
        SecurityContext.assertPermission(ticket, SecurityRole.MANAGER);

        try {
            SecurityContext.assertPermission(SecurityRole.MEMBER);
            fail("Excepted AccessDeniedException");
        } catch (AccessDeniedException ignore) {
        }

    }

    @Test
    public void testUserWith2Role() {
        TestSecurityContext.addRoles(SecurityRole.MANAGER);
        TestSecurityContext.addRoles(SecurityRole.MEMBER);

        SecurityContext.assertPermission(SecurityRole.MANAGER);
        SecurityContext.assertPermission(SecurityRole.MEMBER);
    }

    @Test
    public void testUserWithRoleOnReference() {
        Project project = new ProjectFactory().getOrCreatePersist();
        Ticket ticket = new TicketFactory().project(project).createPersist();

        TestSecurityContext.addRoles(project, SecurityRole.MANAGER);

        try {
            SecurityContext.assertPermission(SecurityRole.MANAGER);
            fail("Excepted AccessDeniedException");
        } catch (AccessDeniedException ignore) {
        }

        SecurityContext.assertPermission(ticket, SecurityRole.MANAGER);
    }


    @Test
    public void testUserWithNoPrivilegesFails() {
        try {
            SecurityContext.assertPermission(SecurityPrivilege.SOME_TASK);
            fail("Excepted AccessDeniedException");
        } catch (AccessDeniedException ignore) {
        }
    }

    @Test
    public void testUserWithPrivilege() {
        Project project = new ProjectFactory().getOrCreatePersist();
        Ticket ticket = new TicketFactory().project(project).createPersist();

        TestSecurityContext.addPrivileges(SecurityPrivilege.SOME_TASK);

        SecurityContext.assertPermission(SecurityPrivilege.SOME_TASK);
        SecurityContext.assertPermission(ticket, SecurityPrivilege.SOME_TASK);

        try {
            SecurityContext.assertPermission(SecurityPrivilege.RECOVER);
            fail("Excepted AccessDeniedException");
        } catch (AccessDeniedException ignore) {
        }

    }

    @Test
    public void testUserWith2Privilege() {
        TestSecurityContext.addPrivileges(SecurityPrivilege.SOME_TASK);
        TestSecurityContext.addPrivileges(SecurityPrivilege.RECOVER);

        SecurityContext.assertPermission(SecurityPrivilege.SOME_TASK);
        SecurityContext.assertPermission(SecurityPrivilege.RECOVER);
    }

    @Test
    public void testUserWithPrivilegeOnReference() {
        Project project = new ProjectFactory().getOrCreatePersist();
        Ticket ticket = new TicketFactory().project(project).createPersist();

        TestSecurityContext.addPrivileges(project, SecurityPrivilege.SOME_TASK);

        try {
            SecurityContext.assertPermission(SecurityPrivilege.SOME_TASK);
            fail("Excepted AccessDeniedException");
        } catch (AccessDeniedException ignore) {
        }

        SecurityContext.assertPermission(ticket, SecurityPrivilege.SOME_TASK);
    }


}
