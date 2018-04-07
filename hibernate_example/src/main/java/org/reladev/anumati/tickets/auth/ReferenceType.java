package org.reladev.anumati.tickets.auth;

import org.reladev.anumati.AuthReferenceType;

public enum ReferenceType implements AuthReferenceType {
    COMPANY,
    PROJECT,
    USER(true);

    private boolean checkRefOnly;

    ReferenceType() {
        this(false);
    }

    ReferenceType(boolean checkRefOnly) {
        this.checkRefOnly = checkRefOnly;
    }

    public boolean isCheckRefOnly() {
        return checkRefOnly;
    }

}
