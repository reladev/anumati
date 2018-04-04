package org.reladev.anumati;

public interface AuthReference {
    Object getObjectId();

    void setObjectId(Object id);

    SecuredObjectType getObjectType();
	Object getReferenceId();

    AuthReferenceType getReferenceType();

	boolean isOwner();
	void setOwner(boolean owner);

	boolean isFixed();
	void setFixed(boolean fixed);

    AuthActionSet getAllowedActions();

    void setAllowedActions(AuthAction... actions);

    void setAllowedActions(AuthActionSet actions);
}
