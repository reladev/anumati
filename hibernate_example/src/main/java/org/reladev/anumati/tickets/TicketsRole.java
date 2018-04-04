package org.reladev.anumati.tickets;

import org.reladev.anumati.AuthRole;

public enum TicketsRole implements AuthRole {
    ADMIN,
    MANAGER,
    DEVELOPER,
    EMPLOYEE
}
