package org.reladev.anumati.tickets;

import org.reladev.anumati.SecuredRole;

public enum TicketsRole implements SecuredRole {
    ADMIN,
    DEVELOPER,
    EMPLOYEE
}
