package org.reladev.anumati.hibernate_test.security;


import org.reladev.anumati.AuthReferenceObject;

public interface ProjectOwned {
    void setOwner(AuthReferenceObject refObj);
}
