package org.reladev.anumati;

import java.util.Set;

public interface SecuredByRef<Key> {
	Key getId();
	SecuredObjectType getSecuredObjectType();
	Set<SecuredReference<Key>> getSecuredReferences();
	void addSecuredReference(SecuredReferenceObject<Key> refObj, boolean owner, SecuredAction... actions);
	void removeSecurityReference(SecuredReferenceObject<Key> refObj);
	boolean isRefActionsOnly();
	void setRefActionsOnly(boolean refActionsOnly);
}
