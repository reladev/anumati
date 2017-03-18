package org.reladev.anumati.hibernate_test.repository;

import java.util.List;

import javax.persistence.EntityManager;

import org.reladev.anumati.hibernate_test.entity.Department;
import org.reladev.anumati.hibernate_test.security.SecurityObjectType;

public class DepartmentRepository extends EntityRepository<Department> {
	public DepartmentRepository(EntityManager entityManager) {
		super(Department.class, SecurityObjectType.DEPARTMENT, entityManager);
	}

	@Override
	public List<Department> findAll() {
		return buildQuery().execute();
	}
}
