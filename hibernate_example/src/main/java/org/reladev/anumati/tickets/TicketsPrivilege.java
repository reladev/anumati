package org.reladev.anumati.tickets;

import org.reladev.anumati.AuthPrivilege;

public enum TicketsPrivilege implements AuthPrivilege {
    CompanyCreate,
    CompanyRead,
    CompanyUpdate,
    CompanyDelete,

    ProjectCreate,
    ProjectRead,
    ProjectUpdate,
    ProjectDelete,

    RoleCreate,
    RoleRead,
    RoleUpdate,
    RoleDelete,

    TicketCreate,
    TicketRead,
    TicketUpdate,
    TicketDelete,

    UserCreate,
    UserRead,
    UserUpdate,
    UserDelete,

}
