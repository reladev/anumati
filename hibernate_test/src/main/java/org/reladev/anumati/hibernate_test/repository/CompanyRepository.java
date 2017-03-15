package org.reladev.anumati.hibernate_test.repository;

import javax.persistence.EntityManager;

import org.reladev.anumati.hibernate_test.entity.Company;
import org.reladev.anumati.hibernate_test.security.SecurityObjectType;

public class CompanyRepository extends EntityRepository<Company> {
	public CompanyRepository(EntityManager entityManager) {
		super(Company.class, SecurityObjectType.COMPANY, entityManager);
	}
}
