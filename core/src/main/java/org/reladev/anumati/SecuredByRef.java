package org.reladev.anumati;

import java.util.Set;

public interface SecuredByRef {
	Object getId();
	SecuredObjectType getSecuredObjectType();


    Set<AuthReference> getSecuredReferences();

    void addSecuredReference(AuthReferenceObject refObj, boolean owner, AuthAction... actions);

    void addSecuredReference(AuthReference ref);

    void removeSecuredReference(AuthReferenceObject refObj);

	Set<SecuredParentChild> getChildReferences();

	boolean isCascadeAllNeeded();
	Set<SecuredParentChild> getChildRefsToCascade();
	void clearCascade();


	boolean isCheckRefOnly();
	void setCheckRefOnly(boolean checkRefOnly);
}
