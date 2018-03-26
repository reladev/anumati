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
            SecurityContext.assertPermission(TicketsPrivilege.ProjectUpdate);
            project = projectRepository.get(projectDto.getId());
        } else {
            SecurityContext.assertPermission(TicketsPrivilege.ProjectCreate);
            project = new Project();
            project.setCompany(authUser.getCompany());
        }
        projectDto.copyTo(project);

        projectRepository.save(project);

        return project;
    }
}
