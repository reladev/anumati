package org.reladev.anumati.example;

import java.util.Set;

import org.reladev.anumati.SecuredBy;
import org.reladev.anumati.SecuredUser;
import org.reladev.anumati.SecurityActionInterface;

public class User implements SecuredUser<SecuredByType> {

	@Override
	public boolean isSuperAdmin() {
		//Todo implement
		return false;
	}

	@Override
	public boolean isAdmin(SecuredByType type, Object id) {
		switch (type) {
			case COMPANY:
				break;

		}
		//Todo implement
		return false;
	}

	@Override
	public boolean checkPermission(SecuredByType type, Object id, Class<? extends SecuredBy> permissionClass, SecurityActionInterface action) {
		Set<String> permissions = generatePermissionStrings(permissionClass, action);
		//Todo implement
		return false;
	}


}
