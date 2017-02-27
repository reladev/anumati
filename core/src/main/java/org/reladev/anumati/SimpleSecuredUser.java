package org.reladev.anumati;

public interface SimpleSecuredUser extends SecuredUser<SimpleType> {

	boolean isAdmin(Object id);

	boolean checkPermission(Object id, SecurityActionInterface action);

	default boolean isAdmin(SimpleType type, Object id) {
		return isAdmin(id);
	}

	default boolean checkPermission(SimpleType type, Object id, SecurityActionInterface action) {
		return checkPermission(id, action);
	}

}
