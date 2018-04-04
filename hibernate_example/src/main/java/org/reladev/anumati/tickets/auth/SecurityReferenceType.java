package org.reladev.anumati.tickets.auth;

import org.reladev.anumati.AuthReferenceType;

public enum SecurityReferenceType implements AuthReferenceType {
    COMPANY,
    PROJECT,
    USER(true);

    private boolean checkRefOnly;

    SecurityReferenceType() {
        this(false);
    }

    SecurityReferenceType(boolean checkRefOnly) {
        this.checkRefOnly = checkRefOnly;
    }

    public boolean isCheckRefOnly() {
        return checkRefOnly;
    }

}
