package org.reladev.anumati.hibernate;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.reladev.anumati.PageableList;
import org.reladev.anumati.SecuredAction;
import org.reladev.anumati.SecuredByRef;
import org.reladev.anumati.SecuredObjectType;
import org.reladev.anumati.SecuredParentChild;
import org.reladev.anumati.SecurityContext;
import org.reladev.anumati.UserPermissions;
import org.reladev.anumati.UserReferencePermissions;

public class BaseRepository<Key, T extends SecuredByRef<Key>> {
	protected EntityManager entityManager;

	protected Class<T> entityClass;
	protected SecuredObjectType objectType;

	public BaseRepository(Class<T> entityClass, SecuredObjectType objectType, EntityManager entityManager) {
		this.entityClass = entityClass;
		this.objectType = objectType;
		this.entityManager = entityManager;
	}

	public SecuredByRef<Key> getReferenceObject(Key id, SecuredObjectType type) {
		//noinspection unchecked
		return (SecuredByRef) entityManager.find(type.getTypeClass(), id);
	}

	public List<? extends SecuredParentChild<Key>> getParentReferences(Key childId, SecuredObjectType childType, Class<? extends SecuredParentChild> parentChildClass) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery query = builder.createQuery(parentChildClass);
		Root root = query.from(parentChildClass);

		query.where(
			  builder.equal(root.get("childId"), childId),
			  builder.equal(root.get("childType"), childType));

		List<? extends SecuredParentChild<Key>> resultList = (List<? extends SecuredParentChild<Key>>) entityManager.createQuery(query).getResultList();
		return resultList;
	}

	public Optional<T> findOne(TypedQuery<T> query) {
		query.setMaxResults(1);
		List<T> result = query.getResultList();
		return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
	}

	public T find(Key id) {
		return entityManager.find(entityClass, id);
	}

	public T get(Key id) {
		return entityManager.getReference(entityClass, id);
	}

	public T save(T entity) {
		entityManager.persist(entity);
		entityManager.flush();
		return entity;
	}

	public String getSecurityJoin() {
		return "inner join e.securityReferences ref ";
	}

	public String getSecurityWhere(SecuredAction action) {

		UserPermissions userPermissions = SecurityContext.getUserPermissions();

		StringBuilder refClause = new StringBuilder();
		refClause.append("(");

		boolean first = true;
		for (UserReferencePermissions permissions: userPermissions) {
			if (permissions.getAllowedActions(objectType).contains(action)) {
				if (first) {
					first = false;
				} else {
					refClause.append(" OR ");
				}
				refClause.append("(ref.referenceId=").append(permissions.getReferenceId()).append(" AND ");
				refClause.append("ref.referenceType=").append(permissions.getReferenceType().ordinal()).append(")");
			}
		}

		refClause.append(")");

		return refClause.toString();
	}

	public Predicate getSecurityPredicate(SecuredAction action, CriteriaBuilder builder, Root<T> root) {
		return getSecurityPredicate(action, builder, root, objectType);
	}

	public Predicate getSecurityPredicate(SecuredAction action, CriteriaBuilder builder, Root<T> root, SecuredObjectType type) {
		Predicate securityClause = null;

		UserPermissions userPermissions = SecurityContext.getUserPermissions();

		Join refJoin = root.join("securityReferences");
		for (UserReferencePermissions privileges: userPermissions) {
			if (privileges.getAllowedActions(type).contains(action)) {
				if (securityClause == null) {
					securityClause = builder.and(
						  builder.equal(refJoin.get("referenceId"), privileges.getReferenceId()),
						  builder.equal(refJoin.get("referenceType"), privileges.getReferenceType()));
				} else {
					securityClause = builder.or(securityClause, builder.and(
						  builder.equal(refJoin.get("referenceId"), privileges.getReferenceId()),
						  builder.equal(refJoin.get("referenceType"), privileges.getReferenceType())));
				}
			}
		}

		return securityClause;
	}

	public QueryBuilder buildQuery(SecuredAction action) {
		return new QueryBuilder(action);
	}

	public void flush() {
		entityManager.flush();
	}

	public class QueryBuilder {
		private List<Predicate> predicates = new LinkedList<>();
		private List<Order> orderBys = new LinkedList<>();

		private SecuredAction action;
		private CriteriaBuilder builder;
		private CriteriaQuery<T> query;
		private Root<T> root;

		public QueryBuilder() {
			builder = entityManager.getCriteriaBuilder();
			query = builder.createQuery(entityClass);
			root = query.distinct(true).from(entityClass);

			Predicate security = getSecurityPredicate(action, builder, root);
			predicates.add(security);
		}

		public QueryBuilder(SecuredAction action) {
			//Todo implement
			this.action = action;
		}

		public QueryBuilder addPredicate(PredicateFactory<T> factory) {
			Predicate p = factory.create(builder, query, root);
			predicates.add(p);
			return this;
		}

		public QueryBuilder addOrderBy(OrderByFactory<T> factory) {
			Order o = factory.create(builder, query, root);
			orderBys.add(o);
			return this;
		}

		public List<T> execute() {
			return execute(0, 9999999).getList();
		}

		public PageableList<T> execute(Integer pagePointer, int pageSize) {
			int start = pagePointer == null ? 0 : pagePointer;
			query.where(predicates.toArray(new Predicate[predicates.size()]));
			query.orderBy(orderBys);

			List<T> resultList = entityManager
				  .createQuery(query)
				  .setFirstResult(start)
				  .setMaxResults(pageSize)
				  .getResultList();

			PageableList<T> pageableList = new PageableList<>();
			pageableList.setList(resultList);
			if (resultList.size() < pageSize) {
				pageableList.setNextPagePointer(null);
			} else {
				pageableList.setNextPagePointer(start + pageSize);
			}

			return pageableList;
		}
	}

	public interface PredicateFactory<T extends SecuredByRef> {
		Predicate create(CriteriaBuilder builder, CriteriaQuery<T> query, Root<T> root);
	}

	public interface OrderByFactory<T extends SecuredByRef> {
		Order create(CriteriaBuilder builder, CriteriaQuery<T> query, Root<T> root);
	}

}
