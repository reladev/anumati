package org.reladev.anumati.tickets.auth;

import org.reladev.anumati.SecuredByRef;
import org.reladev.anumati.SecuredObjectType;
import org.reladev.anumati.tickets.entity.Company;
import org.reladev.anumati.tickets.entity.Project;
import org.reladev.anumati.tickets.entity.Role;
import org.reladev.anumati.tickets.entity.Ticket;
import org.reladev.anumati.tickets.entity.User;

public enum SecurityObjectType implements SecuredObjectType {
    COMPANY(Company.class),
    PROJECT(Project.class),
    USER(User.class),
    TICKET(Ticket.class),
    ROLE(Role.class);

    public static final int COMPANY_ORDINAL = 0;
    public static final int PROJECT_ORDINAL = 1;
    public static final int USER_ORDINAL = 2;
    public static final int TICKET_ORDINAL = 3;
    public static final int ROLE_ORDINAL = 4;

    private Class<? extends SecuredByRef> typeClass;

    SecurityObjectType(Class<? extends SecuredByRef> typeClass) {
        this.typeClass = typeClass;
    }

    public Class<? extends SecuredByRef> getTypeClass() {
        return typeClass;
    }
}
