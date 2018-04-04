package org.reladev.anumati.hibernate;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.reladev.anumati.AuthAction;
import org.reladev.anumati.SecuredByRef;
import org.reladev.anumati.SecuredObjectType;
import org.reladev.anumati.SecurityContext;
import org.reladev.anumati.UserPermissions;
import org.reladev.anumati.UserReferencePermissions;

public class SecuredQueryBuilder <T extends SecuredByRef> {
	private LinkedList<Predicate> predicates = new LinkedList<>();
	private List<Order> orderBys = new LinkedList<>();
	private boolean ignoreSecurity = false;

	private SecuredObjectType type;
    private AuthAction action;
    private CriteriaBuilder builder;
    private CriteriaQuery<T> query;
    private Root<T> root;
	private EntityManager entityManager;

    public SecuredQueryBuilder(EntityManager entityManager, Class<T> entityClass, SecuredObjectType type, AuthAction action) {
        this.entityManager = entityManager;
        this.type = type;
        this.action = action;
		builder = entityManager.getCriteriaBuilder();
		query = builder.createQuery(entityClass);
		root = query.distinct(true).from(entityClass);

	}

	public SecuredQueryBuilder ignoreSecurity() {
		ignoreSecurity = true;
		return this;
	}

	public SecuredQueryBuilder addPredicate(PredicateFactory<T> factory) {
		Predicate p = factory.create(builder, query, root);
		predicates.add(p);
		return this;
	}

	public SecuredQueryBuilder addOrderBy(OrderByFactory<T> factory) {
		Order o = factory.create(builder, query, root);
		orderBys.add(o);
		return this;
	}

	public List<T> execute() {
		return execute(0, 9999999);
	}

	public List<T> execute(Integer pagePointer, int pageSize) {
		int start = pagePointer == null ? 0 : pagePointer;

		if (!ignoreSecurity) {
			Predicate security = getSecurityPredicate(action, builder, root, type);
			predicates.addFirst(security);
		}

		query.where(predicates.toArray(new Predicate[predicates.size()]));
		query.orderBy(orderBys);

		List<T> resultList = entityManager
			  .createQuery(query)
			  .setFirstResult(start)
			  .setMaxResults(pageSize)
			  .getResultList();

		return resultList;
	}

    public static Predicate getSecurityPredicate(AuthAction action, CriteriaBuilder builder, Root root, SecuredObjectType type) {
        Predicate securityClause = null;

		UserPermissions userPermissions = SecurityContext.getUserPermissions();

		Join refJoin = root.join("securityReferences");
		for (UserReferencePermissions permissions: userPermissions) {
			if (permissions.getAllowedActions(type).contains(action)) {
				if (securityClause == null) {
					securityClause = builder.and(
						  builder.equal(refJoin.get("referenceId"), permissions.getReferenceId()),
						  builder.equal(refJoin.get("referenceType"), permissions.getReferenceType()));
				} else {
					securityClause = builder.or(securityClause, builder.and(
						  builder.equal(refJoin.get("referenceId"), permissions.getReferenceId()),
						  builder.equal(refJoin.get("referenceType"), permissions.getReferenceType())));
				}
			}
		}

		return securityClause;
	}

	public static String getSecurityJoin() {
		return "inner join e.securityReferences ref ";
	}

    public static String getSecurityWhere(SecuredObjectType objectType, AuthAction action) {

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


	public interface PredicateFactory<T extends SecuredByRef> {
		Predicate create(CriteriaBuilder builder, CriteriaQuery<T> query, Root<T> root);
	}

	public interface OrderByFactory<T extends SecuredByRef> {
		Order create(CriteriaBuilder builder, CriteriaQuery<T> query, Root<T> root);
	}

}


