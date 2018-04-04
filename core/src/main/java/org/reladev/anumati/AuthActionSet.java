package org.reladev.anumati;

public class AuthActionSet {
    public long flags;

    public AuthActionSet() {
    }

    public AuthActionSet(long flags) {
        this.flags = flags;
    }

    public AuthActionSet(AuthAction[] actions) {
        for (AuthAction action : actions) {
            add(action);
        }
    }

    public void add(AuthAction action) {
        long flag = 1 << action.getFlagPosition();
        flags |= flag;
    }

    public void remove(AuthAction action) {
        long flag = 1 << action.getFlagPosition();
        flags &= ~flag;
    }

    public boolean contains(AuthAction action) {
        long flag = 1 << action.getFlagPosition();
        return (flags & flag) > 0;
    }

    public boolean containsAny(AuthActionSet actions) {
        return (flags & actions.flags) > 0;
    }

	public long getFlags() {
		return flags;
	}

    public void merge(AuthActionSet actions) {
        flags |= actions.flags;
    }
}
