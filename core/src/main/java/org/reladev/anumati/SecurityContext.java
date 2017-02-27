package org.reladev.anumati;

import java.util.Set;

import org.apache.commons.lang3.StringUtils;

public class SecurityContext {

	public interface SecuredUserContext {
		SecuredUser<? extends SecuredByTypeInterface> getSecuredUser();
	}

	private static SecuredUserContext securedUserContext;

	public static void setSecuredUserContext(SecuredUserContext securedUserContext) {
		SecurityContext.securedUserContext = securedUserContext;
	}

	public static void assertPermission(SecuredBy<? extends SecuredByTypeInterface> object, SecurityActionInterface action) {
		if (!checkPermission(object, action)) {
			throw new SecurityException();
		}
	}

	@SuppressWarnings("unchecked")
	public static boolean checkPermission(SecuredBy<? extends SecuredByTypeInterface> object, SecurityActionInterface action) {

		SecuredUser securedUser = securedUserContext.getSecuredUser();

		Set<? extends SecurityReferenceInterface> references = object.getSecurityReferences();

		for (SecurityReferenceInterface ref: references) {
			String actions = ref.getActions();
			for (SecurityActionInterface check: action.getAllActionsThatInclude()) {
				if (securedUser.isAdmin(ref.getSecureByType(), ref.getId())) {
					return true;
				}
				if (StringUtils.isEmpty(actions) || actions.contains(check + ",")) {
					if (securedUser.checkPermission(ref.getSecureByType(), ref.getId(), object.getClass(), action)) {
						return true;
					}
				}
			}
		}

		return false;
	}

}
