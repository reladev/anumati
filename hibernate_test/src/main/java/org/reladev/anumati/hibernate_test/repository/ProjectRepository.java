package org.reladev.anumati.hibernate_test.repository;

import javax.persistence.EntityManager;

import org.reladev.anumati.hibernate_test.entity.Project;
import org.reladev.anumati.hibernate_test.security.SecurityObjectType;

public class ProjectRepository extends EntityRepository<Project> {
	public ProjectRepository(EntityManager entityManager) {
		super(Project.class, SecurityObjectType.PROJECT, entityManager);
	}
}
