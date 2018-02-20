package org.reladev.anumati.article;

import org.reladev.anumati.hibernate_test.dto.ProjectDto;

public class ProjectAclService {
    ProjectRepository projectRepository;

    public Project save(ProjectDto dto) {
        Long id = dto.getId();

        Project project;
        if (id != null) {
            project = projectRepository.find(id);
            SecurityContext.assertAcl(project, "EDIT");

        } else {
            SecurityContext.assertRole("MANAGER");
            project = new Project();
            //...Attach ACLs...
        }

        project.setName(dto.getName());

        return project;
    }
}
