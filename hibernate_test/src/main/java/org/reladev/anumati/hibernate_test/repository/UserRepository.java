package org.reladev.anumati.hibernate_test.repository;

import java.util.List;

import javax.persistence.EntityManager;

import org.reladev.anumati.hibernate_test.entity.User;
import org.reladev.anumati.hibernate_test.security.SecurityObjectType;

public class UserRepository extends EntityRepository<User> {
	public UserRepository(EntityManager entityManager) {
		super(User.class, SecurityObjectType.USER, entityManager);
	}

	@Override
	public List<User> findAll() {
		return buildQuery().execute();
	}
}
