package org.reladev.anumati.article;

import org.reladev.anumati.hibernate_test.dto.TicketDto;
import org.reladev.anumati.hibernate_test.entity.Project;
import org.reladev.anumati.hibernate_test.security.SecurityAction;
import org.reladev.anumati.hibernate_test.security.UserContext;

public class TicketAnumatiService {
    TicketRepository ticketRepository;
    ProjectRepository projectRepository;

    public Ticket save(TicketDto dto) {
        Long id = dto.getId();

        Ticket ticket;
        if (id != null) {
            ticket = ticketRepository.find(id);
            SecurityContext.assertRole(ticket, "MANAGER", "MEMBER");

        } else {
            ticket = new Ticket();
            Project project = getProject(dto.getOwnerId());
            ticket.setOwner(project);
            SecurityContext.assertRole(ticket, "MANAGER");
        }

        ticket.setName(dto.getName());

        return ticket;
    }

    private Project getProject(Long projectId) {
        Project project;
        if (projectId != null) {
            project = projectRepository.find(projectId);
            SecurityContext.assertPermissions(project, SecurityAction.VIEW);

        } else {
            project = UserContext.getUser().getDefaultProject();
        }

        return project;
    }
}
