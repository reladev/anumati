package org.reladev.anumati;

public interface SecuredReference {
	Object getObjectId();
	void setObjectId(Object id);
	SecuredObjectType getObjectType();
	Object getReferenceId();
	SecuredReferenceType getReferenceType();

	boolean isOwner();
	void setOwner(boolean owner);

	boolean isFixed();
	void setFixed(boolean fixed);

	SecuredActionsSet getAllowedActions();
	void setAllowedActions(SecuredAction... actions);
	void setAllowedActions(SecuredActionsSet actions);
}
