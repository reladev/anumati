package org.reladev.anumati.tickets.service;

import javax.inject.Inject;

import org.reladev.anumati.SecurityContext;
import org.reladev.anumati.tickets.TicketsPrivilege;
import org.reladev.anumati.tickets.dto.UserDto;
import org.reladev.anumati.tickets.entity.User;
import org.reladev.anumati.tickets.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Inject
    UserRepository userRepository;


    public UserDto convert(User user) {
        UserDto dto = new UserDto();
        dto.copyTo(user);

        return dto;
    }

    public User updateCreate(UserDto userDto) {
        User authUser = (User) SecurityContext.getUser();

        User user;
        if (userDto.getId() != null) {
            SecurityContext.assertPermission(TicketsPrivilege.UserUpdate);
            user = userRepository.get(userDto.getId());
        } else {
            SecurityContext.assertPermission(TicketsPrivilege.UserCreate);
            user = new User();
            user.setCompany(authUser.getCompany());
        }
        userDto.copyTo(user);

        userRepository.save(user);

        return user;
    }
}
