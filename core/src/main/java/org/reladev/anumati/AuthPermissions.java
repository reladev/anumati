package org.reladev.anumati;

import java.util.Set;

public interface AuthPermissions {

    Long getObject_id();

    SecuredObjectType getObjectType();

    Set<String> getPrivileges();

    Set<SecuredRole> getRoles();
}
