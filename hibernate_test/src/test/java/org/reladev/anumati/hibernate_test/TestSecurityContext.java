package org.reladev.anumati.hibernate_test;

import java.util.Arrays;
import java.util.Collection;

import org.mockito.Mockito;
import org.reladev.anumati.AuthAction;
import org.reladev.anumati.AuthActionSet;
import org.reladev.anumati.AuthPrivilege;
import org.reladev.anumati.AuthReferenceObject;
import org.reladev.anumati.AuthReferenceType;
import org.reladev.anumati.AuthRole;
import org.reladev.anumati.ParsedPermissions;
import org.reladev.anumati.SecuredByRef;
import org.reladev.anumati.SecuredObjectType;
import org.reladev.anumati.SecurityContext;
import org.reladev.anumati.UserPermissions;
import org.reladev.anumati.hibernate_test.entity.User;
import org.reladev.anumati.hibernate_test.entity.UserFactory;
import org.reladev.anumati.hibernate_test.security.SecurityAction;
import org.reladev.anumati.hibernate_test.security.SecurityObjectType;
import org.reladev.anumati.hibernate_test.security.UserContext;

import static org.mockito.Mockito.when;

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

            setAdminPermissions(user, true);
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

    public static void setAdminPermissions(AuthReferenceObject refObj, boolean admin) {
        ParsedPermissions permissions = userPermissions.getOrCreate(refObj.getId(), refObj.getSecuredReferenceType());
        permissions.setAdmin(admin);
    }

    ////////////////////////////////////////////////////////////////
    //  Role Methods
    ////////////////////////////////////////////////////////////////

    public static void addRoles(AuthRole... roles) {
        for (AuthRole role : roles) {
            userPermissions.addRoles(role);
        }

    }

    public static void addRoles(AuthReferenceObject refObj, AuthRole... roles) {
        ParsedPermissions refPermissions = userPermissions.getOrCreate(refObj.getId(), refObj.getSecuredReferenceType());
        for (AuthRole role : roles) {
            refPermissions.addRole(role);
        }
    }

    ////////////////////////////////////////////////////////////////
    //  Privilege Methods
    ////////////////////////////////////////////////////////////////

    public static void addPrivileges(AuthPrivilege... privileges) {
        for (AuthPrivilege privilege : privileges) {
            userPermissions.addPrivileges(privilege);
        }

    }

    public static void addPrivileges(AuthReferenceObject refObj, AuthPrivilege... privileges) {
        ParsedPermissions refPermissions = userPermissions.getOrCreate(refObj.getId(), refObj.getSecuredReferenceType());
        for (AuthPrivilege privilege : privileges) {
            refPermissions.addPrivilege(privilege);
        }
    }

    ////////////////////////////////////////////////////////////////
    //  Role Methods
    ////////////////////////////////////////////////////////////////

    public static void setPermissions(AuthReferenceObject refObj, SecuredObjectType type, AuthAction... actions) {
        ParsedPermissions permissions = new ParsedPermissions(refObj.getId(), refObj.getSecuredReferenceType());
        permissions.setAllowedActions(type, new AuthActionSet(actions));
        userPermissions.put(permissions);
    }

    public static void setPermissions(AuthReferenceObject refObj, String... permissions) {
        userPermissions.put(createReferencePermissions(refObj.getId(), refObj.getSecuredReferenceType(), Arrays.asList(permissions)));
    }

    public static void removePermissions(AuthReferenceObject refObj) {
        userPermissions.remove(refObj.getId(), refObj.getSecuredReferenceType());
    }

	public static void setCompanyPermissions(String... permissions) {
		setPermissions(UserContext.getUser().getCompany(), permissions);
	}

    public static void addRef(SecuredByRef entity, AuthReferenceObject refObj, AuthAction... actions) {
        setSuperAdmin(true);
        entity.addSecuredReference(refObj, false, actions);
        setSuperAdmin(false);
	}

    public static void removeRef(SecuredByRef entity, AuthReferenceObject refObj) {
        setSuperAdmin(true);
        entity.removeSecuredReference(refObj);
        setSuperAdmin(false);
	}

	public static void setCheckRefOnly(SecuredByRef entity, boolean refOnly) {
		setSuperAdmin(true);
		entity.setCheckRefOnly(refOnly);
		setSuperAdmin(false);
	}

    public static ParsedPermissions createReferencePermissions(Object referenceId, AuthReferenceType referenceType, Collection<String> permissionsList) {
        ParsedPermissions referencePrivileges = new ParsedPermissions(referenceId, referenceType);
        for (String permissions : permissionsList) {
            try {
				String[] parts = permissions.split("_");
				SecuredObjectType objectType = SecurityObjectType.valueOf(parts[0]);
                AuthActionSet actionsSet = new AuthActionSet();
                for (char actionChar : parts[1].toCharArray()) {
                    AuthAction action = SecurityAction.valueOfAbbreviation(actionChar);
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
