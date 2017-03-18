package org.reladev.anumati.hibernate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;

import org.reladev.anumati.PageableList;
import org.reladev.anumati.SecuredActionsSet;
import org.reladev.anumati.SecuredByRef;
import org.reladev.anumati.SecuredObjectType;
import org.reladev.anumati.SecuredParentChild;
import org.reladev.anumati.SecuredReference;
import org.reladev.anumati.SecuredReferenceObject;
import org.reladev.anumati.SecuredReferenceType;
import org.reladev.anumati.SecurityContext;


abstract public class BaseService<T extends SecuredByRef, D extends IdDto> {
	private Class<T> entityClass;
	protected BaseRepository<T> repository;

	@SuppressWarnings("unchecked")
	public BaseService(Class<T> entityClass, BaseRepository<T> repository) {
		this.entityClass = entityClass;
		this.repository = repository;
	}

	abstract public T createNewInstance(D dto, Class<T> entityClass);
	abstract public SecuredReference createSecuredReference(Object objectId, SecuredObjectType objectType, Object referenceId, SecuredReferenceType referenceType);
	abstract public Class<? extends SecuredParentChild> getSecuredParentChildClass();

	public T get(Object id) {
		T entity = repository.find(id);
		SecurityContext.assertPermissions(entity, SecurityContext.getView());
		return entity;
	}

	public List<T> getAll() {
		List<T> result = repository.findAll();
		SecurityContext.filter(result, SecurityContext.getView());
		return result;
	}

	public T save(D dto) {
		T entity;
		if (dto.getId() != null) {
			entity = repository.find(dto.getId());
			SecurityContext.assertPermissions(entity, SecurityContext.getEdit());

		} else {
			entity = createNewInstance(dto, entityClass);
			SecurityContext.assertPermissions(entity, SecurityContext.getCreate());
		}
		T savedEntity = _save(dto, entity);
		repository.flush();
		updateReferences(savedEntity);
		cascadeToChildren(savedEntity);
		return savedEntity;
	}

	private void updateReferences(SecuredByRef savedEntity) {
		for (SecuredReference ref: savedEntity.getSecuredReferences()) {
			if (ref.getObjectId() == null) {
				ref.setObjectId(savedEntity.getId());
			}
		}
		for (SecuredParentChild ref: savedEntity.getChildReferences()) {
			if (ref.getParentId() == null) {
				ref.setParentId(savedEntity.getId());
			}
		}
	}

	abstract protected T _save(D dto, T entity);

	public void mergeReference(SecuredByRef entity, Collection ids, SecuredReferenceType referenceType, BaseRepository<? extends SecuredReferenceObject> repository) {
		SecurityContext.assertPermissions(entity, SecurityContext.getPermission());

		Set<SecuredReference> refs = entity.getSecuredReferences();
		SecuredObjectType objectType = entity.getSecuredObjectType();
		List<SecuredReference> toRemove = new ArrayList<>(refs);
		for (Object id: ids) {
			SecuredReferenceObject referenceObject = repository.find(id);
			SecuredReference ref = createSecuredReference(entity.getId(), objectType, referenceObject.getId(), referenceObject.getSecuredReferenceType());

			if (refs.contains(ref)) {
				toRemove.remove(ref);

			} else {
				SecurityContext.assertPermissions(referenceObject, SecurityContext.getView());
				refs.add(ref);
			}
		}
		for (SecuredReference ref: toRemove) {
			if (!ref.isOwner()) {
				refs.remove(ref);
			}
		}

	}

	/**
	 * Cascades permissions to child objects.
	 * All new persistent methods need to call this to propagate permissions
	 * @param entity
	 */
	public void cascadeToChildren(SecuredByRef entity) {
		if (entity.isCascadeAllNeeded()) {
			for (SecuredParentChild childRef : entity.getChildReferences()) {
				cascadeToChild(childRef.getChildId(), childRef.getChildType());
			}
		} else {
			for (SecuredParentChild reference : entity.getChildRefsToCascade()) {
				cascadeToChild(reference.getChildId(), reference.getChildType());
			}
		}
		entity.clearCascade();
	}

	public void cascadeToChild(Object childId, SecuredObjectType childType) {
		Set<SecuredReference> mergedSecurityRefs = new HashSet<>();

		SecuredByRef child = repository.getReferenceObject(childId, childType);
		for (SecuredReference ref : child.getSecuredReferences()) {
			if (ref.isFixed()) {
				mergedSecurityRefs.add(ref);
			}
		}


		List<? extends SecuredParentChild> parentRefs = repository.getParentReferences(childId, childType, getSecuredParentChildClass());
		for (SecuredParentChild parentRef : parentRefs) {
			SecuredByRef parent = repository.getReferenceObject(parentRef.getParentId(), parentRef.getParentType());
			Set<SecuredReference> parentSecurityRefs = parent.getSecuredReferences();
			mergeSecurityReferences(mergedSecurityRefs, parentSecurityRefs);
		}
		for (SecuredReference ref : mergedSecurityRefs) {
			child.addSecuredReference(ref);
		}
	}

	private void mergeSecurityReferences(Set<SecuredReference> dest, Set<SecuredReference> source) {
		for (SecuredReference ref: source) {
			boolean merged = false;
			for (SecuredReference mergeRef: dest) {
				if (mergeRef.equals(ref)) {
					if (!mergeRef.isFixed()) {
						merge(mergeRef, ref);
						merged = true;
					}
					break;
				}
			}
			if (!merged) {
				dest.add(createSecuredReference(ref.getObjectId(), ref.getObjectType(), ref.getReferenceId(), ref.getReferenceType()));
			}
		}
	}

	public void merge(SecuredReference into, SecuredReference ref) {
		into.setOwner(into.isOwner() || ref.isOwner());

		if (into.getAllowedActions() == null || ref.getAllowedActions() == null) {
			into.setAllowedActions((SecuredActionsSet)null);

		} else {
			SecuredActionsSet allowedActions = into.getAllowedActions();
			allowedActions.merge(ref.getAllowedActions());
		}
	}


	public PageableList<T> filter(Integer pagePointer, int pageSize, BiFunction<Integer, Integer, PageableList<T>> fetchMethod) {
		Integer runPointer = pagePointer;
		int runSize = pageSize;

		int missing = 0;
		PageableList<T> complete = new PageableList<>();
		List<T> completeList = new ArrayList<>(pageSize);
		complete.setList(completeList);
		do {
			PageableList<T> result = fetchMethod.apply(runPointer, runSize);
			int processedCount = 0;
			for (T entity : result.getList()) {
				if (SecurityContext.checkPermissions(entity, SecurityContext.getView())) {
					completeList.add(entity);
				}
				processedCount++;
				if (completeList.size() == pageSize) {
					break;
				}
			}

			Integer nextPagePointer = result.getNextPagePointer();
			if (nextPagePointer != null) {
				nextPagePointer = nextPagePointer - (runSize - processedCount);
			}
			complete.setNextPagePointer(nextPagePointer);

			if (nextPagePointer != null) {
				missing = pageSize - completeList.size();
				runPointer = result.getNextPagePointer();
				//TODO improve research algorithm
				runSize = missing * 2;
			}
		} while (missing > 0);

		return complete;
	}
}

