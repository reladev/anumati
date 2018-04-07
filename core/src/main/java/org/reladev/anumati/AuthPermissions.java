package org.reladev.anumati;

import java.util.Set;

public interface AuthPermissions {

    Object getReferenceId();

    AuthReferenceType getReferenceType();

    Set<String> getPrivileges();

    Set<? extends AuthRole> getRoles();
}
