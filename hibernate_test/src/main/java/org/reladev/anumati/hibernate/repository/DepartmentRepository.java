package org.reladev.anumati.hibernate.repository;

import javax.persistence.EntityManager;

import org.reladev.anumati.hibernate.entity.Department;
import org.reladev.anumati.hibernate.security.SecurityObjectType;

public class DepartmentRepository extends EntityRepository<Department> {
	public DepartmentRepository(EntityManager entityManager) {
		super(Department.class, SecurityObjectType.DEPARTMENT, entityManager);
	}
}
