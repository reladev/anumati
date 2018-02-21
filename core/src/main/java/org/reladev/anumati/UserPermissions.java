package org.reladev.anumati;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;

public class UserPermissions implements Iterable<UserReferencePermissions> {

	private boolean superAdmin;
    private HashSet<SecuredRole> roles = new HashSet<>();
    private HashSet<SecuredPrivilege> privileges = new HashSet<>();
    private HashMap<ReferenceKey, UserReferencePermissions> referenceMap = new HashMap<>();

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

    public boolean hasRole(SecuredRole role) {
        return roles.contains(role);
    }

    public boolean hasRole(SecuredReference ref, SecuredRole role) {
        ReferenceKey key = new ReferenceKey(ref.getReferenceId(), ref.getReferenceType());
        UserReferencePermissions refPermissions = referenceMap.get(key);
        if (refPermissions != null) {
            return refPermissions.hasRole(role);
        }
        return false;
    }

    public void addRole(SecuredRole role) {
        roles.add(role);

    }

    public void removeRole(SecuredRole role) {
        roles.remove(role);
    }

    ////////////////////////////////////////////////////////////////
    //  Privilege Methods
    ////////////////////////////////////////////////////////////////

    public boolean hasPrivilege(SecuredPrivilege privilege) {
        return privileges.contains(privilege);
    }

    public boolean hasPrivilege(SecuredReference ref, SecuredPrivilege privilege) {
        ReferenceKey key = new ReferenceKey(ref.getReferenceId(), ref.getReferenceType());
        UserReferencePermissions refPermissions = referenceMap.get(key);
        if (refPermissions != null) {
            return refPermissions.hasPrivilege(privilege);
        }
        return false;
    }

    public void addPrivilege(SecuredPrivilege privilege) {
        privileges.add(privilege);

    }

    public void removePrivilege(SecuredPrivilege privilege) {
        privileges.remove(privilege);
    }

    ////////////////////////////////////////////////////////////////
    //  Reference Methods
    ////////////////////////////////////////////////////////////////


	public UserReferencePermissions get(Object id, SecuredReferenceType type) {
		return referenceMap.get(new ReferenceKey(id, type));
	}

    public UserReferencePermissions getOrCreate(Object id, SecuredReferenceType type) {
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

	public Iterator<UserReferencePermissions> iterator() {
		return referenceMap.values().iterator();
	}

	public void remove(Object id, SecuredReferenceType type) {
		referenceMap.remove(new ReferenceKey(id, type));
	}

	public SecuredActionsSet getAllowedActions(SecuredReference ref) {
		ReferenceKey key = new ReferenceKey(ref.getReferenceId(), ref.getReferenceType());
		UserReferencePermissions UserReferencePermissions = referenceMap.get(key);
		if (UserReferencePermissions != null) {
			return UserReferencePermissions.getAllowedActions(ref.getObjectType());
		} else {
			return new SecuredActionsSet();
		}
	}

	public boolean isAdmin(SecuredReference ref) {
		ReferenceKey key = new ReferenceKey(ref.getReferenceId(), ref.getReferenceType());
		UserReferencePermissions UserReferencePermissions = referenceMap.get(key);
		return UserReferencePermissions != null && UserReferencePermissions.isAdmin();
	}

	private class ReferenceKey {
		Object id;
		SecuredReferenceType type;

		public ReferenceKey(Object id, SecuredReferenceType type) {
			this.id = id;
			this.type = type;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;
			ReferenceKey that = (ReferenceKey) o;
			return type == that.type &&
					Objects.equals(id, that.id);
		}

		@Override
		public int hashCode() {
			return Objects.hash(type, id);
		}
	}
}
