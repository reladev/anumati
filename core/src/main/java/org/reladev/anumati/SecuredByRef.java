package org.reladev.anumati;

import java.util.Set;

public interface SecuredByRef {
	Object getId();
	SecuredObjectType getSecuredObjectType();


	Set<SecuredReference> getSecuredReferences();
	void addSecuredReference(SecuredReferenceObject refObj, boolean owner, SecuredAction... actions);
	void addSecuredReference(SecuredReference ref);
	void removeSecuredReference(SecuredReferenceObject refObj);

	Set<SecuredParentChild> getChildReferences();

	boolean isCascadeAllNeeded();
	Set<SecuredParentChild> getChildRefsToCascade();
	void clearCascade();


	boolean isCheckRefOnly();
	void setCheckRefOnly(boolean checkRefOnly);
}
