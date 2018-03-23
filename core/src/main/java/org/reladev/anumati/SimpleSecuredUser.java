package org.reladev.anumati;

public class SimpleSecuredUser implements SecuredUser {
    UserPermissions userPermissions = new UserPermissions();

    @Override
    public UserPermissions getUserPermissions() {
        return userPermissions;
    }


    public static class Builder {
        SimpleSecuredUser user = new SimpleSecuredUser();

        public Builder() {
        }

        public Builder withSuperAdmin() {
            user.userPermissions.setSuperAdmin(true);
            return this;
        }

        public Builder withRoles(SecuredRole... roles) {
            user.userPermissions.addRoles(roles);
            return this;
        }

        public Builder withPrivileges(SecuredPrivilege... privileges) {
            user.userPermissions.addPrivileges(privileges);
            return this;
        }

        public SimpleSecuredUser getUser() {
            return user;
        }
    }


}
