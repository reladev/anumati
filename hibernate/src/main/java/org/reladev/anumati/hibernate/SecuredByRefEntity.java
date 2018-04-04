package org.reladev.anumati.hibernate;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.MappedSuperclass;

import org.reladev.anumati.AuthAction;
import org.reladev.anumati.AuthReference;
import org.reladev.anumati.AuthReferenceObject;
import org.reladev.anumati.AuthReferenceType;
import org.reladev.anumati.SecuredByRef;
import org.reladev.anumati.SecuredObjectType;
import org.reladev.anumati.SecuredParentChild;
import org.reladev.anumati.SecurityContext;

@MappedSuperclass
abstract public class SecuredByRefEntity implements SecuredByRef {
	private transient boolean cascadeAllNeeded = false;
	private transient Set<SecuredParentChild> childRefsToCascade = new HashSet<>();
	private transient SecuredObjectType type;

	@Basic
	private boolean checkRefOnly = false;

	public SecuredByRefEntity(SecuredObjectType type) {
		this.type = type;
	}

    abstract protected AuthReference createSecuredReference(Object objectId, SecuredObjectType objectType, Object referenceId, AuthReferenceType referenceType);

    abstract protected Set<? extends AuthReference> getSecuredReferencesForEdit();

	protected Set<? extends SecuredParentChild> getChildReferencesForEdit() {
		return Collections.emptySet();
	}




	@Override
	public SecuredObjectType getSecuredObjectType() {
		return type;
	}

	@Override
    public Set<AuthReference> getSecuredReferences() {
        return Collections.unmodifiableSet(getSecuredReferencesForEdit());
    }

	@Override
    public void addSecuredReference(AuthReferenceObject refObj, boolean owner, AuthAction... actions) {
        // Todo clean test to allow this to work
        //UserContext.assertPermissions(getThis(), SecurityAction.PERMISSIONS);
        AuthReference ref = createSecuredReference(getId(), getSecuredObjectType(), refObj.getId(), refObj.getSecuredReferenceType());
        ref.setOwner(owner);
        ref.setAllowedActions(actions);
        Set securedReferencesForEdit = getSecuredReferencesForEdit();
		securedReferencesForEdit.remove(ref);
		securedReferencesForEdit.add(ref);

		if (owner) {
            for (AuthReferenceObject included : refObj.getIncludedReferenceObjects()) {
                AuthReference includedRef = createSecuredReference(getId(), getSecuredObjectType(), included.getId(), included.getSecuredReferenceType());
                includedRef.setOwner(true);
                includedRef.setAllowedActions(actions);
                securedReferencesForEdit.remove(includedRef);
				securedReferencesForEdit.add(includedRef);
			}
		}
		cascadeAllNeeded = true;
	}

	@Override
    public void addSecuredReference(AuthReference ref) {
        AuthReference newRef = createSecuredReference(this.getId(), this.getSecuredObjectType(), ref.getReferenceId(), ref.getReferenceType());
        Set securedReferencesForEdit = getSecuredReferencesForEdit();
        securedReferencesForEdit.remove(newRef);
        securedReferencesForEdit.add(newRef);
	}



	@Override
    public void removeSecuredReference(AuthReferenceObject refObj) {
        SecurityContext.assertPermission(this, SecurityContext.getPermission());
        AuthReference ref = createSecuredReference(getId(), getSecuredObjectType(), refObj.getId(), refObj.getSecuredReferenceType());
        Optional<AuthReference> existingRef = getSecuredReferences().stream().filter(r -> r.equals(ref)).findFirst();
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
        SecurityContext.assertPermission(this, SecurityContext.getPermission());
        this.checkRefOnly = checkRefOnly;
		cascadeAllNeeded = true;
	}


	// Cascade
	public boolean isCascadeAllNeeded() {
		return cascadeAllNeeded;
	}

	public void addChildRefToCascade(SecuredParentChild ref) {
		childRefsToCascade.add(ref);
	}

	public Set<SecuredParentChild> getChildRefsToCascade(){
		return childRefsToCascade;
	}

	public void clearCascade() {
		childRefsToCascade.clear();
		cascadeAllNeeded = false;
	}

	//old


	public Set<SecuredParentChild> getChildReferences() {
		return Collections.unmodifiableSet(getChildReferencesForEdit());
	}


    public void setOwner(AuthReferenceObject refObj) {
        addSecuredReference(refObj, true);
    }

    public Set getReferenceIds(AuthReferenceType referenceType) {
        HashSet<Object> ids = new HashSet<>();
        for (AuthReference ref : getSecuredReferences()) {
            if (ref.getReferenceType() == referenceType) {
                ids.add(ref.getReferenceId());
            }
		}
		return ids;
	}
}
