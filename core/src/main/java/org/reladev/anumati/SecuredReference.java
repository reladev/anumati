package org.reladev.anumati;

public interface SecuredReference<Key> {
	Key getObjectId();
	void setObjectId(Key id);
	SecuredObjectType getObjectType();
	Key getReferenceId();
	SecuredReferenceType getReferenceType();

	boolean isOwner();
	void setOwner(boolean owner);

	boolean isFixed();
	void setFixed(boolean fixed);

	SecuredActionsSet getAllowedActions();
	void setAllowedActions(SecuredAction... actions);
	void setAllowedActions(SecuredActionsSet actions);
}
