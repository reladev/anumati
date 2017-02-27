package org.reladev.anumati;

import java.util.Set;

public interface SecuredBy<T extends SecurityReferenceInterface> {
	Set<T> getSecurityReferences();
}
