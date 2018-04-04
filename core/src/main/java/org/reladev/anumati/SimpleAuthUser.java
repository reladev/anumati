package org.reladev.anumati;

public class SimpleAuthUser implements AuthUser {
    UserPermissions userPermissions = new UserPermissions();

    @Override
    public UserPermissions getUserPermissions() {
        return userPermissions;
    }


    public static class Builder {
        SimpleAuthUser user = new SimpleAuthUser();

        public Builder() {
        }

        public Builder withSuperAdmin() {
            user.userPermissions.setSuperAdmin(true);
            return this;
        }

        public Builder withRoles(AuthRole... roles) {
            user.userPermissions.addRoles(roles);
            return this;
        }

        public Builder withPrivileges(AuthPrivilege... privileges) {
            user.userPermissions.addPrivileges(privileges);
            return this;
        }

        public SimpleAuthUser getUser() {
            return user;
        }
    }


}
