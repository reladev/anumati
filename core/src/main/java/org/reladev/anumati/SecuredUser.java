package org.reladev.anumati;

import java.util.Set;
import java.util.stream.Collectors;

public interface SecuredUser<T extends SecuredByTypeInterface> {

	boolean isSuperAdmin();

	boolean isAdmin(T type, Object id);

	boolean checkPermission(T type, Object id, Class<? extends SecuredBy> permissionClass, SecurityActionInterface action);

	default Set<String> generatePermissionStrings(Class<? extends SecuredBy> permissionClass, SecurityActionInterface action) {
		return action.getAllActionsThatInclude().stream()
			  .map(a -> permissionClass.getSimpleName() + "_" + a)
			  .collect(Collectors.toSet());
	}
}
