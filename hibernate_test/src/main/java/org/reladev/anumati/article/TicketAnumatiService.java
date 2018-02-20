package org.reladev.anumati.article;

import org.reladev.anumati.hibernate_test.dto.TicketDto;
import org.reladev.anumati.hibernate_test.entity.Department;
import org.reladev.anumati.hibernate_test.security.SecurityAction;
import org.reladev.anumati.hibernate_test.security.UserContext;

public class TicketAnumatiService {
    TicketRepository ticketRepository;
    DepartmentRepository departmentRepository;

    public Ticket save(TicketDto dto) {
        Long id = dto.getId();

        Ticket ticket;
        if (id != null) {
            ticket = ticketRepository.find(id);
            SecurityContext.assertRole(ticket, "MANAGER", "MEMBER");

        } else {
            ticket = new Ticket();
            Department department = getDepartment(dto.getOwnerId());
            ticket.setOwner(department);
            SecurityContext.assertRole(ticket, "MANAGER");
        }

        ticket.setName(dto.getName());

        return ticket;
    }

    private Department getDepartment(Long departmentId) {
        Department department;
        if (departmentId != null) {
            department = departmentRepository.find(departmentId);
            SecurityContext.assertPermissions(department, SecurityAction.VIEW);

        } else {
            department = UserContext.getUser().getDefaultDepartment();
        }

        return department;
    }
}
