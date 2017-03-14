package org.reladev.anumati;

import java.util.HashMap;

public class UserReferencePermissions {
	private Long referenceId;
	private SecuredReferenceType referenceType;

	private boolean admin;
	private HashMap<SecuredObjectType, SecuredActionsSet> actionsMap = new HashMap<>();

	private UserReferencePermissions() {}

	public UserReferencePermissions(Long referenceId, SecuredReferenceType referenceType) {
		this.referenceId = referenceId;
		this.referenceType = referenceType;
	}

	public Long getReferenceId() {
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


}
