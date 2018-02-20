package org.reladev.anumati;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class SecurityContext {
	private static SecuredUserContext securedUserContext;
	private static PermissionExceptionThrower thrower = (message) -> {throw new AccessDeniedException(message);};
	private static SecuredAction view;
	private static SecuredAction edit;
	private static SecuredAction create;
	private static SecuredAction delete;
	private static SecuredAction permission;


	public static void setBaseActionEnums(SecuredAction view, SecuredAction edit, SecuredAction create, SecuredAction delete, SecuredAction permission) {
		SecurityContext.view = view;
		SecurityContext.edit = edit;
		SecurityContext.create = create;
		SecurityContext.delete = delete;
		SecurityContext.permission = permission;
	}

	public static void setSecuredUserContext(SecuredUserContext securedUserContext) {
		SecurityContext.securedUserContext = securedUserContext;
	}

	public static void setThrowPermissionFailed(PermissionExceptionThrower thrower) {
		SecurityContext.thrower = thrower;
	}

	public static SecuredAction getView() {
		return view;
	}

	public static SecuredAction getEdit() {
		return edit;
	}

	public static SecuredAction getCreate() {
		return create;
	}

	public static SecuredAction getDelete() {
		return delete;
	}

	public static SecuredAction getPermission() {
		return permission;
	}

	public static UserPermissions getUserPermissions() {
		return securedUserContext.getSecuredUser().getUserPermissions();
	}

    public static void assertRole(SecuredRole role) {
        if (!checkRole(role)) {
            thrower.throwPermissionFailed("Need " + role);
        }
    }

    public static boolean checkRole(SecuredRole role) {
        UserPermissions allReferencePermissions = securedUserContext.getSecuredUser().getUserPermissions();
        return allReferencePermissions.hasRole(role);
    }

    public static void assertRole(SecuredByRef entity, SecuredRole role) {
        if (!checkRole(entity, role)) {
            thrower.throwPermissionFailed("Need " + role);
        }
    }

    public static boolean checkRole(SecuredByRef entity, SecuredRole role) {
        UserPermissions allReferencePermissions = securedUserContext.getSecuredUser().getUserPermissions();

        if (allReferencePermissions.isSuperAdmin()) {
            return true;
        }

        Set<SecuredReference> references = entity.getSecuredReferences();
        for (SecuredReference ref : references) {
            if (allReferencePermissions.isAdmin(ref)) {
                return true;
            }
            if (allReferencePermissions.hasRole(ref, role)) {
                return true;
            }
        }

        return false;
    }

    public static void assertPrivelege(SecuredPrivilege privilege) {
        if (!checkPrivelege(privilege)) {
            thrower.throwPermissionFailed("Need " + privilege);
        }
    }

    public static boolean checkPrivelege(SecuredPrivilege privilege) {
        UserPermissions allReferencePermissions = securedUserContext.getSecuredUser().getUserPermissions();
        return allReferencePermissions.hasPrivilege(privilege);
    }

	public static void assertPermissions(SecuredByRef entity, SecuredAction action) {
		if (!checkPermissions(entity, action)) {
			thrower.throwPermissionFailed("Need " + action + " access for " + entity);
		}
	}

	public static boolean checkPermissions(SecuredByRef entity, SecuredAction action) {

		UserPermissions allReferencePermissions = securedUserContext.getSecuredUser().getUserPermissions();

		if (allReferencePermissions.isSuperAdmin()) {
			return true;
		}

		Set<SecuredReference> references = entity.getSecuredReferences();
		boolean refActionsOnly = entity.isCheckRefOnly();

		boolean check;
		for (SecuredReference ref : references) {
			if (allReferencePermissions.isAdmin(ref)) {
				return true;
			}
			SecuredActionsSet refActions = ref.getAllowedActions();
			if (refActions != null) {
				check = refActions.contains(action);
			} else {
				check = !refActionsOnly && !ref.getReferenceType().isCheckRefOnly();
			}

			if (check) {
				if (ref.getReferenceType().isCheckRefOnly()) {
					return true;
				}
				SecuredActionsSet allowedActions = allReferencePermissions.getAllowedActions(ref);
				if (allowedActions.contains(action)) {
					return true;
				}
			}
		}

		return false;
	}

	public static <T extends SecuredByRef> List<T> filter(List<T> list, SecuredAction action) {
		List<T> filteredList = new LinkedList<>();
		for (T entity : list) {
			if (SecurityContext.checkPermissions(entity, action)) {
				filteredList.add(entity);
			}
		}

		return filteredList;
	}


	public static void throwPermissionException(String message) {
		thrower.throwPermissionFailed(message);
	}

	public interface SecuredUserContext {
		SecuredUser getSecuredUser();
	}

	public interface PermissionExceptionThrower {
		void throwPermissionFailed(String message);
	}
}
