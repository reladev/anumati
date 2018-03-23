package org.reladev.anumati.tickets.service;

import javax.inject.Inject;

import org.reladev.anumati.SecurityContext;
import org.reladev.anumati.tickets.TicketsPrivilege;
import org.reladev.anumati.tickets.dto.ProjectDto;
import org.reladev.anumati.tickets.entity.Project;
import org.reladev.anumati.tickets.entity.User;
import org.reladev.anumati.tickets.repository.ProjectRepository;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {
    @Inject
    ProjectRepository projectRepository;


    public ProjectDto convert(Project project) {
        ProjectDto dto = new ProjectDto();
        dto.copyTo(project);

        return dto;
    }

    public Project updateCreate(ProjectDto projectDto) {
        User authUser = (User) SecurityContext.getUser();

        Project project;
        if (projectDto.getId() != null) {
            SecurityContext.assertPrivilege(TicketsPrivilege.ProjectUpdate);
            project = projectRepository.get(projectDto.getId());
        } else {
            SecurityContext.assertPrivilege(TicketsPrivilege.ProjectCreate);
            project = new Project();
            project.setCompanyId(authUser.getCompanyId());
        }
        projectDto.copyTo(project);

        if (SecurityContext.checkSuperAdmin() || authUser.getCompanyId().equals(project.getCompanyId())) {
            SecurityContext.throwPermissionException("Invalid company permissions");
        }

        projectRepository.save(project);

        return project;
    }
}
