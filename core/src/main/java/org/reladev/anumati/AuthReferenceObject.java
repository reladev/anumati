package org.reladev.anumati;

import java.util.Collections;
import java.util.Set;

public interface AuthReferenceObject extends SecuredByRef {
    Object getId();

    AuthReferenceType getSecuredReferenceType();

    default Set<AuthReferenceObject> getIncludedReferenceObjects() {
        return Collections.emptySet();
    }
}
