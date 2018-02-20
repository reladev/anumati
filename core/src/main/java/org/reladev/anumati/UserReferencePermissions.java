package org.reladev.anumati;

import java.util.HashMap;
import java.util.HashSet;

public class UserReferencePermissions {
    private Object referenceId;
    private SecuredReferenceType referenceType;

    private boolean admin;
    private HashSet<SecuredRole> roles = new HashSet<>();
    private HashSet<SecuredPrivilege> privileges = new HashSet<>();
    private HashMap<SecuredObjectType, SecuredActionsSet> actionsMap = new HashMap<>();

    private UserReferencePermissions() {}

    public UserReferencePermissions(Object referenceId, SecuredReferenceType referenceType) {
        this.referenceId = referenceId;
        this.referenceType = referenceType;
    }

    public Object getReferenceId() {
        return referenceId;
    }

    public SecuredReferenceType getReferenceType() {
        return referenceType;
    }

    ////////////////////////////////////////////////////////////////
    //  Admin Methods
    ////////////////////////////////////////////////////////////////

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    ////////////////////////////////////////////////////////////////
    //  Role Methods
    ////////////////////////////////////////////////////////////////

    public boolean hasRole(SecuredRole role) {
        return roles.contains(role);
    }

    public void addRole(SecuredRole role) {
        roles.add(role);

    }

    public void removeRole(SecuredRole role) {
        roles.remove(role);
    }

    ////////////////////////////////////////////////////////////////
    //  Privileges Methods
    ////////////////////////////////////////////////////////////////

    public boolean hasPrivilege(SecuredPrivilege privilege) {
        return privileges.contains(privilege);
    }

    public void addPrivilege(SecuredPrivilege privilege) {
        privileges.add(privilege);

    }

    public void removePrivilege(SecuredPrivilege privilege) {
        privileges.remove(privilege);
    }


    ////////////////////////////////////////////////////////////////
    //  Role Methods
    ////////////////////////////////////////////////////////////////


    public SecuredActionsSet getAllowedActions(SecuredObjectType objectType) {
        if (admin) {
            return new SecuredActionsSet(~0);
        } else {
            SecuredActionsSet actionsSet = actionsMap.get(objectType);
            if (actionsSet == null) {
                return new SecuredActionsSet();
            } else {
                return actionsSet;
            }
        }
    }

    public void setAllowedActions(SecuredObjectType objectType, SecuredActionsSet actions) {
        actionsMap.put(objectType, actions);
    }

    public void mergePermissions(SecuredObjectType objectType, SecuredActionsSet actions) {
        SecuredActionsSet existing = actionsMap.get(objectType);
        if (existing != null) {
            existing.merge(actions);

        } else {
            actionsMap.put(objectType, actions);
        }
    }

}
