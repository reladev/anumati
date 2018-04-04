package org.reladev.anumati.article;

import org.reladev.anumati.AuthAction;
import org.reladev.anumati.SecuredByRef;

public class SecurityContext {
    public static void assertRole(String... roles) {

    }

    public static void assertRole(SecuredByRef entity, String... roles) {

    }

    public static void assertAcl(SecuredByRef entity, String action) {

    }

    public static void assertPermissions(SecuredByRef entity, AuthAction action) {
    }

    public static boolean checkPermissions(SecuredByRef entity, AuthAction action) {
        //...
        return true;
    }
}
