package org.reladev.anumati;

import java.util.Collections;
import java.util.Set;

public interface SecuredReferenceObject<Key> {
	Key getId();
	SecuredReferenceType getSecuredReferenceType();
	default Set<SecuredReferenceObject> getIncludedReferenceObjects() {
		return Collections.emptySet();
	}
}
