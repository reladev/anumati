package org.reladev.anumati.tickets.controller;

import java.util.Collections;
import java.util.List;

import org.reladev.anumati.tickets.dto.UserDto;
import org.reladev.anumati.tickets.entity.User;
import org.reladev.anumati.tickets.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@RequestMapping("/v1/user")
public class UserController {

    @Autowired
    UserService userService;

    public UserController() {
    }

    @RequestMapping(path = "/{id}", method = GET)
    public String get(@PathVariable String id) {
        return "Welcome to company";
    }

    @RequestMapping(method = GET)
    public List<User> get() {
        return Collections.emptyList();
    }

    @RequestMapping(method = POST)
    public UserDto create(@RequestBody UserDto userDto) {
        User user = userService.updateCreate(userDto);
        return userService.convert(user);
    }

    @RequestMapping(path = "/{id}/roles", method = GET)
    public List<String> getRoles(@PathVariable String id) {
        return null;
    }

    @RequestMapping(path = "/{id}/roles/{role}", method = POST)
    public List<String> addRole(@PathVariable String id, @PathVariable String role) {
        return null;
    }

    @RequestMapping(path = "/{id}/roles/{role}", method = DELETE)
    public List<String> removeRole(@PathVariable String id, @PathVariable String role) {
        return null;
    }

    @RequestMapping(path = "/{id}/privileges", method = GET)
    public List<String> getPrivileges(@PathVariable String id) {
        return null;
    }

    @RequestMapping(path = "/{id}/privileges/{privilege}", method = POST)
    public List<String> addPrivilege(@PathVariable String id, @PathVariable String privilege) {
        return null;
    }

    @RequestMapping(path = "/{id}/privileges/{privilege}", method = DELETE)
    public List<String> removePrivilege(@PathVariable String id, @PathVariable String privilege) {
        return null;
    }

}
