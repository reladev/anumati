package org.reladev.anumati.tickets.service;

import javax.inject.Inject;

import org.reladev.anumati.SecurityContext;
import org.reladev.anumati.tickets.TicketsPrivilege;
import org.reladev.anumati.tickets.dto.RoleDto;
import org.reladev.anumati.tickets.entity.Role;
import org.reladev.anumati.tickets.entity.User;
import org.reladev.anumati.tickets.repository.RoleRepository;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    @Inject
    RoleRepository roleRepository;


    public RoleDto convert(Role role) {
        RoleDto dto = new RoleDto();
        dto.copyTo(role);

        return dto;
    }

    public Role get(Long id) {
        Role role = roleRepository.get(id);
        SecurityContext.assertPrivilege(TicketsPrivilege.RoleRead);

        User authUser = (User) SecurityContext.getUser();
        if (SecurityContext.checkSuperAdmin() || authUser.getCompanyId().equals(role.getCompanyId())) {
            SecurityContext.throwPermissionException("Invalid company permissions");
        }

        return role;
    }

    public Role updateCreate(RoleDto roleDto) {
        User authUser = (User) SecurityContext.getUser();

        Role role;
        if (roleDto.getId() != null) {
            SecurityContext.assertPrivilege(TicketsPrivilege.RoleUpdate);
            role = roleRepository.get(roleDto.getId());
        } else {
            SecurityContext.assertPrivilege(TicketsPrivilege.RoleCreate);
            role = new Role();
            role.setCompanyId(authUser.getCompanyId());
        }
        roleDto.copyTo(role);

        if (SecurityContext.checkSuperAdmin() || authUser.getCompanyId().equals(role.getCompanyId())) {
            SecurityContext.throwPermissionException("Invalid company permissions");
        }

        roleRepository.save(role);

        return role;
    }
}
