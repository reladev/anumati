package org.reladev.anumati;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Objects;

public class AllReferencePermissions implements Iterable<ReferencePermissions> {

	private boolean superAdmin;
	private HashMap<ReferenceKey, ReferencePermissions> referenceMap = new HashMap<>();

	public boolean isSuperAdmin() {
		return superAdmin;
	}

	public void setSuperAdmin(boolean superAdmin) {
		this.superAdmin = superAdmin;
	}

	public ReferencePermissions get(Long id, SecuredReferenceType type) {
		return referenceMap.get(new ReferenceKey(id, type));
	}

	public void put(ReferencePermissions privilegeMap) {
		ReferenceKey referenceKey = new ReferenceKey(privilegeMap.getReferenceId(), privilegeMap.getReferenceType());
		referenceMap.put(referenceKey, privilegeMap);
	}

	public Iterator<ReferencePermissions> iterator() {
		return referenceMap.values().iterator();
	}

	public void remove(Long id, SecuredReferenceType type) {
		referenceMap.remove(new ReferenceKey(id, type));
	}

	public SecuredActionsSet getAllowedActions(SecuredReference ref) {
		ReferenceKey key = new ReferenceKey(ref.getReferenceId(), ref.getReferenceType());
		ReferencePermissions ReferencePermissions = referenceMap.get(key);
		if (ReferencePermissions != null) {
			return ReferencePermissions.getPrivileges(ref.getObjectType());
		} else {
			return new SecuredActionsSet();
		}
	}

	public boolean isAdmin(SecuredReference ref) {
		ReferenceKey key = new ReferenceKey(ref.getReferenceId(), ref.getReferenceType());
		ReferencePermissions ReferencePermissions = referenceMap.get(key);
		return ReferencePermissions != null && ReferencePermissions.isAdmin();
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
