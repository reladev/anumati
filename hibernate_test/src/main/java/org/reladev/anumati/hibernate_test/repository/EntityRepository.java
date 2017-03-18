package org.reladev.anumati.hibernate_test.repository;

import javax.persistence.EntityManager;

import org.reladev.anumati.SecuredByRef;
import org.reladev.anumati.SecuredObjectType;
import org.reladev.anumati.hibernate.BaseRepository;
import org.reladev.anumati.hibernate.SecuredQueryBuilder;
import org.reladev.anumati.hibernate_test.security.SecurityAction;

abstract public class EntityRepository<T extends SecuredByRef> extends BaseRepository<T> {
	public EntityRepository(Class<T> entityClass, SecuredObjectType objectType, EntityManager entityManager) {
		super(entityClass, objectType, entityManager);
	}

	public <E> E find(Class<E> entityClass, Object id) {
		return entityManager.find(entityClass, id);
	}

	public T find(Long id) {
		return entityManager.find(entityClass, id);
	}

	public SecuredQueryBuilder buildQuery() {
		return super.buildQuery(SecurityAction.VIEW);
	}
}
