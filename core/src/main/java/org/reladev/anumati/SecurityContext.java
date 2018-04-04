package org.reladev.anumati;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class SecurityContext {
	private static SecuredUserContext securedUserContext;
	private static PermissionExceptionThrower thrower = (message) -> {throw new AccessDeniedException(message);};
    private static AuthAction view;
    private static AuthAction edit;
    private static AuthAction create;
    private static AuthAction delete;
    private static AuthAction permission;


    public static void setBaseActionEnums(AuthAction view, AuthAction edit, AuthAction create, AuthAction delete, AuthAction permission) {
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

    public static AuthAction getView() {
        return view;
    }

    public static AuthAction getEdit() {
        return edit;
    }

    public static AuthAction getCreate() {
        return create;
    }

    public static AuthAction getDelete() {
        return delete;
    }

    public static AuthAction getPermission() {
        return permission;
    }

	public static UserPermissions getUserPermissions() {
		return securedUserContext.getSecuredUser().getUserPermissions();
	}

    public static AuthUser getUser() {
        AuthUser authUser = securedUserContext.getSecuredUser();
        if (authUser == null) {
            thrower.throwPermissionFailed("User not defined.");
        }
        return authUser;
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


    public static void assertPermission(AuthRole role) {
        if (!checkPermission(role)) {
            thrower.throwPermissionFailed("Need " + role);
        }
    }

    public static boolean checkPermission(AuthRole role) {
        UserPermissions allReferencePermissions = getUser().getUserPermissions();

        if (allReferencePermissions.isSuperAdmin()) {
            return true;
        }

        return allReferencePermissions.hasRole(role);
    }

    public static void assertPermission(SecuredByRef entity, AuthRole role) {
        if (!checkPermission(entity, role)) {
            thrower.throwPermissionFailed("Need " + role);
        }
    }

    public static boolean checkPermission(SecuredByRef entity, AuthRole role) {
        UserPermissions allReferencePermissions = getUser().getUserPermissions();

        if (allReferencePermissions.isSuperAdmin()) {
            return true;
        }

        if (allReferencePermissions.hasRole(role)) {
            return true;
        }

        Set<AuthReference> references = entity.getSecuredReferences();
        for (AuthReference ref : references) {
            if (allReferencePermissions.isAdmin(ref)) {
                return true;
            }
            if (allReferencePermissions.hasRole(ref, role)) {
                return true;
            }
        }

        return false;
    }

    public static void assertPermission(AuthPrivilege privilege) {
        if (!checkPermission(privilege)) {
            thrower.throwPermissionFailed("Need " + privilege);
        }
    }

    public static boolean checkPermission(AuthPrivilege privilege) {
        UserPermissions allReferencePermissions = securedUserContext.getSecuredUser().getUserPermissions();

        if (allReferencePermissions.isSuperAdmin()) {
            return true;
        }

        return allReferencePermissions.hasPrivilege(privilege);
    }

    public static void assertPermission(SecuredByRef entity, AuthPrivilege privilege) {
        if (!checkPermission(entity, privilege)) {
            thrower.throwPermissionFailed("Need " + privilege);
        }
    }

    public static boolean checkPermission(SecuredByRef entity, AuthPrivilege privilege) {
        UserPermissions allReferencePermissions = getUser().getUserPermissions();

        if (allReferencePermissions.isSuperAdmin()) {
            return true;
        }

        if (allReferencePermissions.hasPrivilege(privilege)) {
            return true;
        }

        Set<AuthReference> references = entity.getSecuredReferences();
        for (AuthReference ref : references) {
            if (allReferencePermissions.isAdmin(ref)) {
                return true;
            }
            if (allReferencePermissions.hasPrivilege(ref, privilege)) {
                return true;
            }
        }

        return false;
    }

    public static void assertPermission(SecuredByRef entity, AuthAction action) {
        if (!checkPermission(entity, action)) {
            thrower.throwPermissionFailed("Need " + action + " access for " + entity);
		}
	}

    public static boolean checkPermission(SecuredByRef entity, AuthAction action) {

        UserPermissions allReferencePermissions = getUser().getUserPermissions();

		if (allReferencePermissions.isSuperAdmin()) {
			return true;
		}

        Set<AuthReference> references = entity.getSecuredReferences();
        boolean refActionsOnly = entity.isCheckRefOnly();

		boolean check;
        for (AuthReference ref : references) {
            if (allReferencePermissions.isAdmin(ref)) {
                return true;
            }
            AuthActionSet refActions = ref.getAllowedActions();
            if (refActions != null) {
                check = refActions.contains(action);
            } else {
				check = !refActionsOnly && !ref.getReferenceType().isCheckRefOnly();
			}

			if (check) {
				if (ref.getReferenceType().isCheckRefOnly()) {
					return true;
				}
                AuthActionSet allowedActions = allReferencePermissions.getAllowedActions(ref);
                if (allowedActions.contains(action)) {
                    return true;
                }
			}
		}

		return false;
	}

    public static <T extends SecuredByRef> List<T> filter(List<T> list, AuthAction action) {
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
        AuthUser getSecuredUser();
    }

	public interface PermissionExceptionThrower {
		void throwPermissionFailed(String message);
	}
}
