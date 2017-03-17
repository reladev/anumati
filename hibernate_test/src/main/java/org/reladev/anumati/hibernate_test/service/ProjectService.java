package org.reladev.anumati.hibernate_test.service;

import org.reladev.anumati.hibernate_test.dto.ProjectDto;
import org.reladev.anumati.hibernate_test.entity.Project;
import org.reladev.anumati.hibernate_test.repository.ProjectRepository;

public class ProjectService extends EntityService<Project, ProjectDto> {

	public ProjectService(ProjectRepository repository) {
		super(Project.class, repository);
	}

	@Override
	protected Project _save(ProjectDto dto, Project project) {
		project.setName(dto.getName());

		return project;
	}
}
