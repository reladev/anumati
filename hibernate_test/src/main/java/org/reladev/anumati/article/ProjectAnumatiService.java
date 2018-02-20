package org.reladev.anumati.article;

import org.reladev.anumati.hibernate_test.dto.ProjectDto;
import org.reladev.anumati.hibernate_test.entity.Department;
import org.reladev.anumati.hibernate_test.security.SecurityAction;
import org.reladev.anumati.hibernate_test.security.UserContext;

public class ProjectAnumatiService {
    ProjectRepository projectRepository;
    DepartmentRepository departmentRepository;

    public Project save(ProjectDto dto) {
        Long id = dto.getId();

        Project project;
        if (id != null) {
            project = projectRepository.find(id);
            SecurityContext.assertRole(project, "MANAGER", "MEMBER");

        } else {
            project = new Project();
            Department department = getDepartment(dto.getOwnerId());
            project.setOwner(department);
            SecurityContext.assertRole(project, "MANAGER");
        }

        project.setName(dto.getName());

        return project;
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
