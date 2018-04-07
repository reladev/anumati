package org.reladev.anumati.tickets.dto;

import java.util.Set;

import org.reladev.anumati.AuthReferenceType;
import org.reladev.quickdto.shared.QuickDto;

@QuickDto
public class PermissionsDtoDef {
    Long userId;
    Long referenceId;
    AuthReferenceType referenceType;

    Set<RoleDto> roles;
    Set<String> privileges;
}
