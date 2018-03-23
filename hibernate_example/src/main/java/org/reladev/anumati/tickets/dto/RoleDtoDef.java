package org.reladev.anumati.tickets.dto;

import java.util.List;

import org.reladev.anumati.tickets.TicketsRole;
import org.reladev.anumati.tickets.entity.Role;
import org.reladev.quickdto.shared.CopyFromOnly;
import org.reladev.quickdto.shared.QuickDto;

@QuickDto(source = Role.class)
public class RoleDtoDef {
    @CopyFromOnly
    Long id;
    Long companyId;

    TicketsRole role;
    List<String> privileges;
}
