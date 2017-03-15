package org.reladev.anumati;

import java.util.Collections;
import java.util.Set;

public interface SecuredReferenceObject extends SecuredByRef {
	Object getId();
	SecuredReferenceType getSecuredReferenceType();
	default Set<SecuredReferenceObject> getIncludedReferenceObjects() {
		return Collections.emptySet();
	}
}
