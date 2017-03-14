package org.reladev.anumati;

import java.util.Set;

public interface SecuredByRef<Key> {
	Key getId();
	SecuredObjectType getSecuredObjectType();


	Set<SecuredReference<Key>> getSecuredReferences();
	void addSecuredReference(SecuredReferenceObject<Key> refObj, boolean owner, SecuredAction... actions);
	void addSecuredReference(SecuredReference<Key> ref);
	void removeSecuredReference(SecuredReferenceObject<Key> refObj);

	Set<SecuredParentChild<Key>> getChildReferences();

	boolean isCascadeAllNeeded();
	Set<SecuredParentChild<Key>> getChildRefsToCascade();
	void clearCascade();


	boolean isCheckRefOnly();
	void setCheckRefOnly(boolean checkRefOnly);
}
