package org.reladev.anumati;

import java.util.HashMap;

public class ReferencePermissions {
	private Long referenceId;
	private SecuredReferenceType referenceType;

	private boolean admin;
	private HashMap<SecuredObjectType, SecuredActionsSet> privilegeMap = new HashMap<>();

	private ReferencePermissions() {}

	public ReferencePermissions(Long referenceId, SecuredReferenceType referenceType) {
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

	public SecuredActionsSet getPrivileges(SecuredObjectType objectType) {
		if (admin) {
			return new SecuredActionsSet(~0);
		} else {
			SecuredActionsSet actionsSet = privilegeMap.get(objectType);
			if (actionsSet == null) {
				return new SecuredActionsSet();
			} else {
				return actionsSet;
			}
		}
	}

	public void setPermissions(SecuredObjectType objectType, SecuredActionsSet actions) {
		privilegeMap.put(objectType, actions);
	}


}
