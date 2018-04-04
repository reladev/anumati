package org.reladev.anumati.tickets.entity;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.Formula;
import org.reladev.anumati.AuthAction;
import org.reladev.anumati.AuthActionSet;
import org.reladev.anumati.AuthReference;
import org.reladev.anumati.AuthReferenceObject;
import org.reladev.anumati.AuthReferenceType;
import org.reladev.anumati.SecuredByRef;
import org.reladev.anumati.SecuredObjectType;
import org.reladev.anumati.tickets.auth.SecurityObjectType;
import org.reladev.anumati.tickets.auth.SecurityReferenceType;

@Entity
public class SecurityReference implements AuthReference {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Formula("object_id")
    private Long objectId;
    private SecurityObjectType objectType;
    private Long referenceId;
    private SecurityReferenceType referenceType;

    private boolean owner;
    private boolean fixed;

    private Long actionFlags;

    protected SecurityReference() {
    }

    public SecurityReference(SecuredByRef object, AuthReferenceObject refObj) {
        this(object.getId(), object.getSecuredObjectType(), refObj.getId(), refObj.getSecuredReferenceType());
    }

    public SecurityReference(Object objectId, SecuredObjectType objectType, Object referenceId, AuthReferenceType referenceType) {
        if (referenceId == null) {
            throw new NullPointerException("referenceId");
        }
        this.objectId = (Long) objectId;
        this.objectType = (SecurityObjectType) objectType;
        this.referenceId = (Long) referenceId;
        this.referenceType = (SecurityReferenceType) referenceType;
    }

    public Long getId() {
        return id;
    }

    public Long getObjectId() {
        return objectId;
    }

    public void setObjectId(Object objectId) {
        this.objectId = (Long) objectId;
    }

    public SecuredObjectType getObjectType() {
        return objectType;
    }

    public void setObjectType(SecurityObjectType objectType) {
        this.objectType = objectType;
    }

    public Long getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(Object referenceId) {
        this.referenceId = (Long) referenceId;
    }

    public SecurityReferenceType getReferenceType() {
        return referenceType;
    }

    public void setReferenceType(SecurityReferenceType referenceType) {
        this.referenceType = referenceType;
    }

    public AuthActionSet getAllowedActions() {
        if (actionFlags == null) {
            return null;

        } else {
            return new AuthActionSet(actionFlags);
        }
    }

    public void setActionFlags(Long actionFlags) {
        this.actionFlags = actionFlags;
    }

    @Override
    public void setAllowedActions(AuthActionSet actions) {
        actionFlags = actions.getFlags();
    }

    @Override
    public void setAllowedActions(AuthAction... actions) {
        if (actions.length == 0) {
            actionFlags = null;

        } else {
            AuthActionSet actionsSet = new AuthActionSet();
            for (AuthAction action : actions) {
                actionsSet.add(action);
            }
            actionFlags = actionsSet.getFlags();
        }
    }

    public boolean isOwner() {
        return owner;
    }

    public void setOwner(boolean owner) {
        this.owner = owner;
    }

    @Override
    public boolean isFixed() {
        return fixed;
    }

    @Override
    public void setFixed(boolean fixed) {
        this.fixed = fixed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || !(o instanceof SecurityReference)) {
            return false;
        }
        SecurityReference that = (SecurityReference) o;
        return Objects.equals(objectId, that.objectId) && objectType == that.objectType && Objects.equals(referenceId, that.referenceId) && referenceType == that.referenceType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(objectId, objectType, referenceId, referenceType);
    }
}
