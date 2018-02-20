package org.reladev.anumati.hibernate_test.repository;

import java.util.List;

import javax.persistence.EntityManager;

import org.reladev.anumati.hibernate_test.entity.Company;
import org.reladev.anumati.hibernate_test.security.SecurityObjectType;

public class CompanyRepository extends EntityRepository<Company> {
	public CompanyRepository(EntityManager entityManager) {
		super(Company.class, SecurityObjectType.COMPANY, entityManager);
	}

	@Override
	public List<Company> findAll() {
        return buildQuery().ignoreSecurity().execute();
    }
}
