package org.reladev.anumati;

public class SecuredActionsSet {
	public long flags;

	public SecuredActionsSet() {
	}

	public SecuredActionsSet(long flags) {
		this.flags = flags;
	}

	public SecuredActionsSet(SecuredAction[] actions) {
		for (SecuredAction action: actions) {
			add(action);
		}
	}

	public void add(SecuredAction action) {
		long flag = 1 << action.getFlagPosition();
		flags |= flag;
	}

	public void remove(SecuredAction action) {
		long flag = 1 << action.getFlagPosition();
		flags &= ~flag;
	}

	public boolean contains(SecuredAction action) {
		long flag = 1 << action.getFlagPosition();
		return (flags & flag) > 0;
	}

	public boolean containsAny(SecuredActionsSet actions) {
		return (flags & actions.flags) > 0;
	}

	public long getFlags() {
		return flags;
	}

	public void merge(SecuredActionsSet actions) {
		flags |= actions.flags;
	}
}