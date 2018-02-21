package org.reladev.anumati.hibernate_test;

import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collection;

import org.mockito.Mockito;
import org.reladev.anumati.SecuredAction;
import org.reladev.anumati.SecuredActionsSet;
import org.reladev.anumati.SecuredByRef;
import org.reladev.anumati.SecuredObjectType;
import org.reladev.anumati.SecuredReferenceObject;
import org.reladev.anumati.SecuredReferenceType;
import org.reladev.anumati.SecuredRole;
import org.reladev.anumati.SecurityContext;
import org.reladev.anumati.UserPermissions;
import org.reladev.anumati.UserReferencePermissions;
import org.reladev.anumati.hibernate_test.entity.User;
import org.reladev.anumati.hibernate_test.entity.UserFactory;
import org.reladev.anumati.hibernate_test.security.SecurityAction;
import org.reladev.anumati.hibernate_test.security.SecurityObjectType;
import org.reladev.anumati.hibernate_test.security.UserContext;

public class TestSecurityContext {
	private static UserPermissions userPermissions;

	public static void init() {
		init(new UserFactory().getOrCreatePersist());
	}

	@SuppressWarnings("SuspiciousMethodCalls")
	public static void init(User user) {
		userPermissions = new UserPermissions();

		if (user != null) {
			User spy = Mockito.spy(user);

			setAdminPermissions(user);
			when(spy.getUserPermissions()).thenReturn(userPermissions);

			SecurityContext.setBaseActionEnums(SecurityAction.VIEW, SecurityAction.EDIT, SecurityAction.CREATE, SecurityAction.DELETE, SecurityAction.PERMISSIONS);
			UserContext.init(spy);
		}

	}

    ////////////////////////////////////////////////////////////////
    //  Admin Methods
    ////////////////////////////////////////////////////////////////

	public static void setSuperAdmin(boolean superAdmin) {
		userPermissions.setSuperAdmin(superAdmin);
	}

	public static void setAdminPermissions(SecuredReferenceObject refObj) {
		UserReferencePermissions permissions = new UserReferencePermissions(refObj.getId(), refObj.getSecuredReferenceType());
		permissions.setAdmin(true);
		userPermissions.put(permissions);
	}

    ////////////////////////////////////////////////////////////////
    //  Role Methods
    ////////////////////////////////////////////////////////////////

    public static void addRoles(SecuredRole... roles) {
        for (SecuredRole role : roles) {
            userPermissions.addRole(role);
        }

    }

    public static void addRoles(SecuredReferenceObject refObj, SecuredRole... roles) {
        UserReferencePermissions refPermissions = userPermissions.getOrCreate(refObj.getId(), refObj.getSecuredReferenceType());
        for (SecuredRole role : roles) {
            refPermissions.addRole(role);
        }
    }

    ////////////////////////////////////////////////////////////////
    //  Role Methods
    ////////////////////////////////////////////////////////////////

	public static void setPermissions(SecuredReferenceObject refObj, SecuredObjectType type, SecuredAction... actions) {
		UserReferencePermissions permissions = new UserReferencePermissions(refObj.getId(), refObj.getSecuredReferenceType());
		permissions.setAllowedActions(type, new SecuredActionsSet(actions));
		userPermissions.put(permissions);
	}

	public static void setPermissions(SecuredReferenceObject refObj, String... permissions) {
		userPermissions.put(createReferencePermissions(refObj.getId(), refObj.getSecuredReferenceType(), Arrays.asList(permissions)));
	}

	public static void removePermissions(SecuredReferenceObject refObj) {
		userPermissions.remove(refObj.getId(), refObj.getSecuredReferenceType());
	}

	public static void setCompanyPermissions(String... permissions) {
		setPermissions(UserContext.getUser().getCompany(), permissions);
	}

	public static void addRef(SecuredByRef entity, SecuredReferenceObject refObj, SecuredAction... actions) {
		setSuperAdmin(true);
		entity.addSecuredReference(refObj, false, actions);
		setSuperAdmin(false);
	}

	public static void removeRef(SecuredByRef entity, SecuredReferenceObject refObj) {
		setSuperAdmin(true);
		entity.removeSecuredReference(refObj);
		setSuperAdmin(false);
	}

	public static void setCheckRefOnly(SecuredByRef entity, boolean refOnly) {
		setSuperAdmin(true);
		entity.setCheckRefOnly(refOnly);
		setSuperAdmin(false);
	}

	public static UserReferencePermissions createReferencePermissions(Object referenceId, SecuredReferenceType referenceType, Collection<String> permissionsList) {
		UserReferencePermissions referencePrivileges = new UserReferencePermissions(referenceId, referenceType);
		for (String permissions: permissionsList) {
			try {
				String[] parts = permissions.split("_");
				SecuredObjectType objectType = SecurityObjectType.valueOf(parts[0]);
				SecuredActionsSet actionsSet = new SecuredActionsSet();
				for (char actionChar : parts[1].toCharArray()) {
					SecuredAction action = SecurityAction.valueOfAbbreviation(actionChar);
					actionsSet.add(action);
				}
				referencePrivileges.mergePermissions(objectType, actionsSet);
			} catch(Exception e) {
				throw new IllegalStateException("Bad privilege(should be in the format of ENTITY_VECP) - probably old security model :" + permissions
														+ " " + e.getMessage());
			}
		}

		return referencePrivileges;
	}


}
