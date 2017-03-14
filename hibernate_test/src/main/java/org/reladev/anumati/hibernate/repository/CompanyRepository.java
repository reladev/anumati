package org.reladev.anumati.hibernate.repository;

import javax.persistence.EntityManager;

import org.reladev.anumati.hibernate.entity.Company;
import org.reladev.anumati.hibernate.security.SecurityObjectType;

public class CompanyRepository extends EntityRepository<Company> {
	public CompanyRepository(EntityManager entityManager) {
		super(Company.class, SecurityObjectType.COMPANY, entityManager);
	}
}
