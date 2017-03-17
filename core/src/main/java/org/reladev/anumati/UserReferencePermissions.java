package org.reladev.anumati;

import java.util.HashMap;

public class UserReferencePermissions {
	private Object referenceId;
	private SecuredReferenceType referenceType;

	private boolean admin;
	private HashMap<SecuredObjectType, SecuredActionsSet> actionsMap = new HashMap<>();

	private UserReferencePermissions() {}

	public UserReferencePermissions(Object referenceId, SecuredReferenceType referenceType) {
		this.referenceId = referenceId;
		this.referenceType = referenceType;
	}

	public Object getReferenceId() {
		return referenceId;
	}

	public SecuredReferenceType getReferenceType() {
		return referenceType;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public SecuredActionsSet getAllowedActions(SecuredObjectType objectType) {
		if (admin) {
			return new SecuredActionsSet(~0);
		} else {
			SecuredActionsSet actionsSet = actionsMap.get(objectType);
			if (actionsSet == null) {
				return new SecuredActionsSet();
			} else {
				return actionsSet;
			}
		}
	}

	public void setAllowedActions(SecuredObjectType objectType, SecuredActionsSet actions) {
		actionsMap.put(objectType, actions);
	}

	public void mergePermissions(SecuredObjectType objectType, SecuredActionsSet actions) {
		SecuredActionsSet existing = actionsMap.get(objectType);
		if (existing != null) {
			existing.merge(actions);

		}else {
			actionsMap.put(objectType, actions);
		}
	}

}
