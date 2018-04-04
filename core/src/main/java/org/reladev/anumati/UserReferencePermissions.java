package org.reladev.anumati;

import java.util.HashMap;
import java.util.HashSet;

public class UserReferencePermissions {
    private Object referenceId;
    private AuthReferenceType referenceType;

    private boolean admin;
    private HashSet<AuthRole> roles = new HashSet<>();
    private HashSet<AuthPrivilege> privileges = new HashSet<>();
    private HashMap<SecuredObjectType, AuthActionSet> actionsMap = new HashMap<>();

    private UserReferencePermissions() {}

    public UserReferencePermissions(Object referenceId, AuthReferenceType referenceType) {
        this.referenceId = referenceId;
        this.referenceType = referenceType;
    }

    public Object getReferenceId() {
        return referenceId;
    }

    public AuthReferenceType getReferenceType() {
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

    public boolean hasRole(AuthRole role) {
        return roles.contains(role);
    }

    public void addRole(AuthRole role) {
        roles.add(role);

    }

    public void removeRole(AuthRole role) {
        roles.remove(role);
    }

    ////////////////////////////////////////////////////////////////
    //  Privileges Methods
    ////////////////////////////////////////////////////////////////

    public boolean hasPrivilege(AuthPrivilege privilege) {
        return privileges.contains(privilege);
    }

    public void addPrivilege(AuthPrivilege privilege) {
        privileges.add(privilege);

    }

    public void removePrivilege(AuthPrivilege privilege) {
        privileges.remove(privilege);
    }


    ////////////////////////////////////////////////////////////////
    //  Role Methods
    ////////////////////////////////////////////////////////////////


    public AuthActionSet getAllowedActions(SecuredObjectType objectType) {
        if (admin) {
            return new AuthActionSet(~0);
        } else {
            AuthActionSet actionsSet = actionsMap.get(objectType);
            if (actionsSet == null) {
                return new AuthActionSet();
            } else {
                return actionsSet;
            }
        }
    }

    public void setAllowedActions(SecuredObjectType objectType, AuthActionSet actions) {
        actionsMap.put(objectType, actions);
    }

    public void mergePermissions(SecuredObjectType objectType, AuthActionSet actions) {
        AuthActionSet existing = actionsMap.get(objectType);
        if (existing != null) {
            existing.merge(actions);

        } else {
            actionsMap.put(objectType, actions);
        }
    }

}
