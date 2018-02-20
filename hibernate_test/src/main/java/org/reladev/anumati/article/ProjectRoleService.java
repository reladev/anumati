package org.reladev.anumati.article;

import org.reladev.anumati.hibernate_test.dto.ProjectDto;

public class ProjectRoleService {
    ProjectRepository projectRepository;

    public Project save(ProjectDto dto) {
        Long id = dto.getId();

        Project project;
        if (id != null) {
            SecurityContext.assertRole("MANAGER", "MEMBER");
            project = projectRepository.find(id);

        } else {
            SecurityContext.assertRole("MANAGER");
            project = new Project();
        }

        project.setName(dto.getName());

        return project;
    }
}
