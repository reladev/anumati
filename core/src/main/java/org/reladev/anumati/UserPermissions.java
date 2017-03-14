package org.reladev.anumati;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Objects;

public class UserPermissions implements Iterable<UserReferencePermissions> {

	private boolean superAdmin;
	private HashMap<ReferenceKey, UserReferencePermissions> referenceMap = new HashMap<>();

	public boolean isSuperAdmin() {
		return superAdmin;
	}

	public void setSuperAdmin(boolean superAdmin) {
		this.superAdmin = superAdmin;
	}

	public UserReferencePermissions get(Long id, SecuredReferenceType type) {
		return referenceMap.get(new ReferenceKey(id, type));
	}

	public void put(UserReferencePermissions privilegeMap) {
		ReferenceKey referenceKey = new ReferenceKey(privilegeMap.getReferenceId(), privilegeMap.getReferenceType());
		referenceMap.put(referenceKey, privilegeMap);
	}

	public Iterator<UserReferencePermissions> iterator() {
		return referenceMap.values().iterator();
	}

	public void remove(Long id, SecuredReferenceType type) {
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
