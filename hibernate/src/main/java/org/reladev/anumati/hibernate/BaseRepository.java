package org.reladev.anumati.hibernate;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.reladev.anumati.SecuredAction;
import org.reladev.anumati.SecuredByRef;
import org.reladev.anumati.SecuredObjectType;
import org.reladev.anumati.SecuredParentChild;

abstract public class BaseRepository<T extends SecuredByRef> {
	protected EntityManager entityManager;

	protected Class<T> entityClass;
	protected SecuredObjectType objectType;

	public BaseRepository(Class<T> entityClass, SecuredObjectType objectType, EntityManager entityManager) {
		this.entityClass = entityClass;
		this.objectType = objectType;
		this.entityManager = entityManager;
	}

	public SecuredByRef getReferenceObject(Object id, SecuredObjectType type) {
		return entityManager.find(type.getTypeClass(), id);
	}

	public List<? extends SecuredParentChild> getParentReferences(Object childId, SecuredObjectType childType, Class<? extends SecuredParentChild> parentChildClass) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery query = builder.createQuery(parentChildClass);
		Root root = query.from(parentChildClass);

		query.where(
			  builder.equal(root.get("childId"), childId),
			  builder.equal(root.get("childType"), childType));

		@SuppressWarnings("unchecked")
		List<? extends SecuredParentChild> resultList = (List<? extends SecuredParentChild>) entityManager.createQuery(query).getResultList();
		return resultList;
	}

	public Optional<T> findOne(TypedQuery<T> query) {
		query.setMaxResults(1);
		List<T> result = query.getResultList();
		return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
	}

	public T find(Object id) {
		return entityManager.find(entityClass, id);
	}

	abstract public List<T> findAll();

	public T get(Object id) {
		return entityManager.getReference(entityClass, id);
	}

	public T save(T entity) {
		entityManager.persist(entity);
		entityManager.flush();
		return entity;
	}

	public SecuredQueryBuilder buildQuery(SecuredAction action) {
		return new SecuredQueryBuilder<>(entityManager, entityClass, objectType, action);
	}

	public void flush() {
		entityManager.flush();
	}
}
