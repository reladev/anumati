package org.reladev.anumati;

import java.util.Optional;

public interface SecuredReference<Key> {
	Key getObjectId();
	SecuredObjectType getObjectType();
	Key getReferenceId();
	SecuredReferenceType getReferenceType();

	boolean isOwner();
	Optional<SecuredActionsSet> getAllowedActions();
}
