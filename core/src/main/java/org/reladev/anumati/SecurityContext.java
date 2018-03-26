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

    public static SecuredUser getUser() {
        SecuredUser securedUser = securedUserContext.getSecuredUser();
        if (securedUser == null) {
            thrower.throwPermissionFailed("User not defined.");
        }
        return securedUser;
    }

    public static void assertSuperAdmin() {
        if (!checkSuperAdmin()) {
            thrower.throwPermissionFailed("Need to be SuperAdmin");
        }
    }

    public static boolean checkSuperAdmin() {
        UserPermissions allReferencePermissions = getUser().getUserPermissions();
        return allReferencePermissions.isSuperAdmin();
    }

    public static void assertAdmin(SecuredByRef entity) {
        if (!checkAdmin(entity)) {
            thrower.throwPermissionFailed("Need to be Admin");
        }
    }

    public static boolean checkAdmin(SecuredByRef entity) {
        UserPermissions allReferencePermissions = getUser().getUserPermissions();
        return allReferencePermissions.isSuperAdmin();
    }


    public static void assertPermission(SecuredRole role) {
        if (!checkPermission(role)) {
            thrower.throwPermissionFailed("Need " + role);
        }
    }

    public static boolean checkPermission(SecuredRole role) {
        UserPermissions allReferencePermissions = getUser().getUserPermissions();

        if (allReferencePermissions.isSuperAdmin()) {
            return true;
        }

        return allReferencePermissions.hasRole(role);
    }

    public static void assertPermission(SecuredByRef entity, SecuredRole role) {
        if (!checkPermission(entity, role)) {
            thrower.throwPermissionFailed("Need " + role);
        }
    }

    public static boolean checkPermission(SecuredByRef entity, SecuredRole role) {
        UserPermissions allReferencePermissions = getUser().getUserPermissions();

        if (allReferencePermissions.isSuperAdmin()) {
            return true;
        }

        if (allReferencePermissions.hasRole(role)) {
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

    public static void assertPermission(SecuredPrivilege privilege) {
        if (!checkPermission(privilege)) {
            thrower.throwPermissionFailed("Need " + privilege);
        }
    }

    public static boolean checkPermission(SecuredPrivilege privilege) {
        UserPermissions allReferencePermissions = securedUserContext.getSecuredUser().getUserPermissions();

        if (allReferencePermissions.isSuperAdmin()) {
            return true;
        }

        return allReferencePermissions.hasPrivilege(privilege);
    }

    public static void assertPermission(SecuredByRef entity, SecuredPrivilege privilege) {
        if (!checkPermission(entity, privilege)) {
            thrower.throwPermissionFailed("Need " + privilege);
        }
    }

    public static boolean checkPermission(SecuredByRef entity, SecuredPrivilege privilege) {
        UserPermissions allReferencePermissions = getUser().getUserPermissions();

        if (allReferencePermissions.isSuperAdmin()) {
            return true;
        }

        if (allReferencePermissions.hasPrivilege(privilege)) {
            return true;
        }

        Set<SecuredReference> references = entity.getSecuredReferences();
        for (SecuredReference ref : references) {
            if (allReferencePermissions.isAdmin(ref)) {
                return true;
            }
            if (allReferencePermissions.hasPrivilege(ref, privilege)) {
                return true;
            }
        }

        return false;
    }

    public static void assertPermission(SecuredByRef entity, SecuredAction action) {
        if (!checkPermission(entity, action)) {
            thrower.throwPermissionFailed("Need " + action + " access for " + entity);
		}
	}

    public static boolean checkPermission(SecuredByRef entity, SecuredAction action) {

        UserPermissions allReferencePermissions = getUser().getUserPermissions();

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
            if (SecurityContext.checkPermission(entity, action)) {
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
