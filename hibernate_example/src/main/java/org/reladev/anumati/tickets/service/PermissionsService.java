package org.reladev.anumati.tickets.service;

import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

import org.reladev.anumati.tickets.dto.PermissionsDto;
import org.reladev.anumati.tickets.dto.RoleDto;
import org.reladev.anumati.tickets.dto.UserDto;
import org.reladev.anumati.tickets.entity.Permissions;
import org.reladev.anumati.tickets.entity.Role;
import org.reladev.anumati.tickets.entity.User;
import org.reladev.anumati.tickets.repository.PermissionsRepository;
import org.reladev.anumati.tickets.repository.RoleRepository;
import org.reladev.anumati.tickets.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class PermissionsService {
    @Inject
    PermissionsRepository permissionsRepository;
    @Inject
    UserRepository userRepository;
    @Inject
    RoleRepository roleRepository;


    public UserDto convert(User user) {
        UserDto dto = new UserDto();
        dto.copyTo(user);

        return dto;
    }

    public Permissions updateCreate(PermissionsDto permissionsDto) {

        Permissions permissions = permissionsRepository.find(permissionsDto.getUserId(), permissionsDto.getReferenceId(), permissionsDto.getReferenceType());
        if (permissions == null) {
            User permissionsUser = userRepository.get(permissionsDto.getUserId());
            permissions = new Permissions(permissionsUser, permissionsDto.getReferenceId(), permissionsDto.getReferenceType());
        }

        Set<Role> roles = new HashSet<>();
        for (RoleDto roleDto : permissionsDto.getRoles()) {
            Role role = roleRepository.get(roleDto.getId());
            roles.add(role);
        }
        permissions.setRoles(roles);

        permissionsRepository.save(permissions);

        return permissions;
    }
}
