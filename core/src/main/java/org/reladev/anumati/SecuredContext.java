package org.reladev.anumati;

import java.util.Optional;
import java.util.Set;

public class SecuredContext {
	private static SecuredUserContext securedUserContext;
	private static PermissionExceptionThrower thrower = (message) -> {throw new AccessDeniedException(message);};

	public static void setSecuredUserContext(SecuredUserContext securedUserContext) {
		SecuredContext.securedUserContext = securedUserContext;
	}

	public static void setThrowPermissionFailed(PermissionExceptionThrower thrower) {
		SecuredContext.thrower = thrower;
	}

	public static <T> void assertPermissions(SecuredByRef<T> entity, SecuredAction action) {
		if (!checkPermissions(entity, action)) {
			thrower.throwPermissionFailed("Need " + action + " access for " + entity);
		}
	}

	public static <T> boolean checkPermissions(SecuredByRef<T> entity, SecuredAction action) {

		AllReferencePermissions allReferencePermissions = securedUserContext.getSecuredUser().getAllReferencePermissions();

		if (allReferencePermissions.isSuperAdmin()) {
			return true;
		}

		Set<SecuredReference<T>> references = entity.getSecuredReferences();
		boolean refActionsOnly = entity.isRefActionsOnly();

		boolean check;
		for (SecuredReference<T> ref : references) {
			if (allReferencePermissions.isAdmin(ref)) {
				return true;
			}
			Optional<SecuredActionsSet> refActions = ref.getAllowedActions();
			check = refActions.map(allowedActions -> allowedActions.containsAny(action.getAllActionsThatInclude()))
				  .orElseGet(() -> !refActionsOnly && !ref.getReferenceType().isCheckRefOnly());

			if (check) {
				if (ref.getReferenceType().isCheckRefOnly()) {
					return true;
				}
				SecuredActionsSet allowedActions = allReferencePermissions.getAllowedActions(ref);
				if (allowedActions.containsAny(action.getAllActionsThatInclude())) {
					return true;
				}
			}
		}

		return false;
	}

	public interface SecuredUserContext {
		SecuredUser getSecuredUser();
	}

	public interface PermissionExceptionThrower {
		void throwPermissionFailed(String message);
	}
}
