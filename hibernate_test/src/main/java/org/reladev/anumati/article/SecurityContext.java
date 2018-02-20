package org.reladev.anumati.article;

import org.reladev.anumati.SecuredAction;
import org.reladev.anumati.SecuredByRef;

public class SecurityContext {
    public static void assertRole(String... roles) {

    }

    public static void assertRole(SecuredByRef entity, String... roles) {

    }

    public static void assertAcl(SecuredByRef entity, String action) {

    }

    public static void assertPermissions(SecuredByRef entity, SecuredAction action) {
    }

    public static boolean checkPermissions(SecuredByRef entity, SecuredAction action) {
        //...
        return true;
    }
}
