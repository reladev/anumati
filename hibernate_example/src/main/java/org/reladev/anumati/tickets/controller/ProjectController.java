package org.reladev.anumati.tickets.controller;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import org.reladev.anumati.tickets.dto.RoleDto;
import org.reladev.anumati.tickets.entity.Role;
import org.reladev.anumati.tickets.service.RoleService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/v1/project")
public class ProjectController {

    @Inject
    RoleService roleService;

    public ProjectController() {
    }

    @RequestMapping(method = GET)
    public List<RoleDto> get() {
        return Collections.emptyList();
    }

    @RequestMapping(path = "/{id}", method = GET)
    public RoleDto get(@PathVariable String id) {
        Long longId = Long.parseLong(id);
        Role role = roleService.get(longId);
        return roleService.convert(role);
    }

    @RequestMapping(method = POST)
    public RoleDto create(@RequestBody RoleDto roleDto) {
        Role role = roleService.updateCreate(roleDto);
        return roleService.convert(role);
    }
}
