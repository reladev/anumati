package org.reladev.anumati;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

public class UserPermissions implements Iterable<UserReferencePermissions> {
    private boolean superAdmin;
    private HashSet<AuthRole> roles = new HashSet<>();
    private HashSet<AuthPrivilege> privileges = new HashSet<>();
    private HashMap<ReferenceKey, UserReferencePermissions> referenceMap = new HashMap<>();

    public UserPermissions() {}

    public UserPermissions(Iterable<AuthPermissions> permissions) {


    }

    ////////////////////////////////////////////////////////////////
    //  Admin Methods
    ////////////////////////////////////////////////////////////////


	public boolean isSuperAdmin() {
		return superAdmin;
	}

	public void setSuperAdmin(boolean superAdmin) {
		this.superAdmin = superAdmin;
	}

    ////////////////////////////////////////////////////////////////
    //  Role Methods
    ////////////////////////////////////////////////////////////////

    public boolean hasRole(AuthRole role) {
        return roles.contains(role);
    }

    public boolean hasRole(AuthReference ref, AuthRole role) {
        ReferenceKey key = new ReferenceKey(ref.getReferenceId(), ref.getReferenceType());
        UserReferencePermissions refPermissions = referenceMap.get(key);
        if (refPermissions != null) {
            return refPermissions.hasRole(role);
        }
        return false;
    }

    public void addRoles(AuthRole... newRoles) {
        this.roles.addAll(Arrays.asList(newRoles));
    }

    public void removeRoles(AuthRole... removeRoles) {
        this.roles.removeAll(Arrays.asList(removeRoles));
    }

    ////////////////////////////////////////////////////////////////
    //  Privilege Methods
    ////////////////////////////////////////////////////////////////

    public boolean hasPrivilege(AuthPrivilege privilege) {
        return privileges.contains(privilege);
    }

    public boolean hasPrivilege(AuthReference ref, AuthPrivilege privilege) {
        ReferenceKey key = new ReferenceKey(ref.getReferenceId(), ref.getReferenceType());
        UserReferencePermissions refPermissions = referenceMap.get(key);
        if (refPermissions != null) {
            return refPermissions.hasPrivilege(privilege);
        }
        return false;
    }

    public void addPrivileges(AuthPrivilege... newPrivilege) {
        privileges.addAll(Arrays.asList(newPrivilege));

    }

    public void removePrivileges(AuthPrivilege... removePrivileges) {
        privileges.removeAll(Arrays.asList(removePrivileges));
    }

    ////////////////////////////////////////////////////////////////
    //  Reference Methods
    ////////////////////////////////////////////////////////////////


    public UserReferencePermissions get(Object id, AuthReferenceType type) {
        return referenceMap.get(new ReferenceKey(id, type));
    }

    public UserReferencePermissions getOrCreate(Object id, AuthReferenceType type) {
        UserReferencePermissions refPermissions = referenceMap.get(new ReferenceKey(id, type));
        if (refPermissions == null) {
            refPermissions = new UserReferencePermissions(id, type);
            referenceMap.put(new ReferenceKey(id, type), refPermissions);
        }
        return refPermissions;
    }

	public void put(UserReferencePermissions privilegeMap) {
		ReferenceKey referenceKey = new ReferenceKey(privilegeMap.getReferenceId(), privilegeMap.getReferenceType());
		referenceMap.put(referenceKey, privilegeMap);
	}

    @Override
    public Iterator<UserReferencePermissions> iterator() {
        return referenceMap.values().iterator();
    }

    public void remove(Object id, AuthReferenceType type) {
        referenceMap.remove(new ReferenceKey(id, type));
    }

    public AuthActionSet getAllowedActions(AuthReference ref) {
        ReferenceKey key = new ReferenceKey(ref.getReferenceId(), ref.getReferenceType());
        UserReferencePermissions UserReferencePermissions = referenceMap.get(key);
        if (UserReferencePermissions != null) {
			return UserReferencePermissions.getAllowedActions(ref.getObjectType());
		} else {
            return new AuthActionSet();
        }
    }

    public boolean isAdmin(AuthReference ref) {
        ReferenceKey key = new ReferenceKey(ref.getReferenceId(), ref.getReferenceType());
        UserReferencePermissions UserReferencePermissions = referenceMap.get(key);
        return UserReferencePermissions != null && UserReferencePermissions.isAdmin();
	}
}
