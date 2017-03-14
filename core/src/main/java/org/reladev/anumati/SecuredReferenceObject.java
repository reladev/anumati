package org.reladev.anumati;

import java.util.Collections;
import java.util.Set;

public interface SecuredReferenceObject<Key> extends SecuredByRef<Key> {
	Key getId();
	SecuredReferenceType getSecuredReferenceType();
	default Set<SecuredReferenceObject<Key>> getIncludedReferenceObjects() {
		return Collections.emptySet();
	}
}
