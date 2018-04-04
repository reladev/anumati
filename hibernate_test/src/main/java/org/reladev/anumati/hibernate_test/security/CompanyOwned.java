package org.reladev.anumati.hibernate_test.security;


import org.reladev.anumati.AuthReferenceObject;

public interface CompanyOwned {
    void setOwner(AuthReferenceObject owner);
}
