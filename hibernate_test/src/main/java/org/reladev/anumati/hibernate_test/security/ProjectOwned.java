package org.reladev.anumati.hibernate_test.security;


import org.reladev.anumati.SecuredReferenceObject;

public interface ProjectOwned {
    void setOwner(SecuredReferenceObject refObj);
}
