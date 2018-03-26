package org.reladev.anumati.tickets.service;

import javax.inject.Inject;

import org.reladev.anumati.SecurityContext;
import org.reladev.anumati.tickets.TicketsAction;
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
        SecurityContext.assertPermission(role, TicketsAction.READ);

        User authUser = (User) SecurityContext.getUser();
        return role;
    }

    public Role updateCreate(RoleDto roleDto) {
        User authUser = (User) SecurityContext.getUser();

        Role role;
        if (roleDto.getId() != null) {
            SecurityContext.assertPermission(TicketsPrivilege.RoleUpdate);
            role = roleRepository.get(roleDto.getId());
        } else {
            SecurityContext.assertPermission(TicketsPrivilege.RoleCreate);
            role = new Role();
            role.setCompany(authUser.getCompany());
        }
        roleDto.copyTo(role);

        roleRepository.save(role);

        return role;
    }
}
