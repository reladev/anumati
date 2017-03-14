package org.reladev.anumati.hibernate;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.MappedSuperclass;

import org.reladev.anumati.SecuredAction;
import org.reladev.anumati.SecuredByRef;
import org.reladev.anumati.SecuredObjectType;
import org.reladev.anumati.SecuredParentChild;
import org.reladev.anumati.SecuredReference;
import org.reladev.anumati.SecuredReferenceObject;
import org.reladev.anumati.SecuredReferenceType;
import org.reladev.anumati.SecurityContext;

@MappedSuperclass
abstract public class SecuredByRefEntity<Key> implements SecuredByRef<Key> {
	private transient boolean cascadeAllNeeded = false;
	private transient Set<SecuredParentChild<Key>> childRefsToCascade = new HashSet<>();
	private transient SecuredObjectType type;

	@Basic
	private boolean checkRefOnly = false;

	public SecuredByRefEntity(SecuredObjectType type) {
		this.type = type;
	}


	@Override
	public Key getId() {
		//Todo implement
		return null;
	}

	abstract protected SecuredReference<Key> createSecuredReference(Key objectId, SecuredObjectType objectType, Key referenceId, SecuredReferenceType referenceType);

	abstract protected Set<? extends SecuredReference<Key>> getSecuredReferencesForEdit();

	protected Set<? extends SecuredParentChild<Key>> getChildReferencesForEdit() {
		return Collections.emptySet();
	}




	@Override
	public SecuredObjectType getSecuredObjectType() {
		return type;
	}

	@Override
	public Set<SecuredReference<Key>> getSecuredReferences() {
		return Collections.unmodifiableSet(getSecuredReferencesForEdit());
	}

	@Override
	public void addSecuredReference(SecuredReferenceObject<Key> refObj, boolean owner, SecuredAction... actions) {
		// Todo clean test to allow this to work
		//UserContext.assertPermissions(getThis(), SecurityAction.PERMISSIONS);
		SecuredReference<Key> ref = createSecuredReference(getId(), getSecuredObjectType(), refObj.getId(), refObj.getSecuredReferenceType());
		ref.setOwner(owner);
		ref.setAllowedActions(actions);
		Set securedReferencesForEdit = getSecuredReferencesForEdit();
		securedReferencesForEdit.remove(ref);
		securedReferencesForEdit.add(ref);

		if (owner) {
			for (SecuredReferenceObject<Key> included : refObj.getIncludedReferenceObjects()) {
				SecuredReference<Key> includedRef = createSecuredReference(getId(), getSecuredObjectType(), included.getId(), included.getSecuredReferenceType());
				includedRef.setOwner(true);
				includedRef.setAllowedActions(actions);
				securedReferencesForEdit.remove(includedRef);
				securedReferencesForEdit.add(includedRef);
			}
		}
		cascadeAllNeeded = true;
	}

	@Override
	public void addSecuredReference(SecuredReference<Key> ref) {
		SecuredReference<Key> newRef = createSecuredReference(this.getId(), this.getSecuredObjectType(), ref.getReferenceId(), ref.getReferenceType());
		Set securedReferencesForEdit = getSecuredReferencesForEdit();
		securedReferencesForEdit.remove(newRef);
		securedReferencesForEdit.add(newRef);
	}



	@Override
	public void removeSecuredReference(SecuredReferenceObject<Key> refObj) {
		SecurityContext.assertPermissions(this, SecurityContext.getPermission());
		SecuredReference ref = createSecuredReference(getId(), getSecuredObjectType(), refObj.getId(), refObj.getSecuredReferenceType());
		Optional<SecuredReference<Key>> existingRef = getSecuredReferences().stream()
			  .filter(r -> r.equals(ref))
			  .findFirst();
		if (existingRef.isPresent() && existingRef.get().isOwner()) {
			SecurityContext.throwPermissionException("Can't remove owner");
		}
		getSecuredReferencesForEdit().remove(ref);
		cascadeAllNeeded = true;
	}

	@Override
	public boolean isCheckRefOnly() {
		return checkRefOnly;
	}

	@Override
	public void setCheckRefOnly(boolean checkRefOnly) {
		SecurityContext.assertPermissions(this, SecurityContext.getPermission());
		this.checkRefOnly = checkRefOnly;
		cascadeAllNeeded = true;
	}


	// Cascade
	public boolean isCascadeAllNeeded() {
		return cascadeAllNeeded;
	}

	public void addChildRefToCascade(SecuredParentChild<Key> ref) {
		childRefsToCascade.add(ref);
	}

	public Set<SecuredParentChild<Key>> getChildRefsToCascade(){
		return childRefsToCascade;
	}

	public void clearCascade() {
		childRefsToCascade.clear();
		cascadeAllNeeded = false;
	}

	//old


	public Set<SecuredParentChild<Key>> getChildReferences() {
		return Collections.unmodifiableSet(getChildReferencesForEdit());
	}


	public void setOwner(SecuredReferenceObject<Key> refObj) {
		addSecuredReference(refObj, true);
	}

	public Set<Key> getReferenceIds(SecuredReferenceType referenceType) {
		HashSet<Key> ids = new HashSet<>();
		for (SecuredReference<Key> ref: getSecuredReferences()) {
			if (ref.getReferenceType() == referenceType) {
				ids.add(ref.getReferenceId());
			}
		}
		return ids;
	}
}
