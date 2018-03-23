package org.reladev.anumati.tickets;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.reladev.anumati.SecurityContext;
import org.reladev.anumati.UserPermissions;
import org.reladev.anumati.tickets.dto.CompanyDto;
import org.reladev.anumati.tickets.dto.UserDto;
import org.reladev.anumati.tickets.entity.Company;
import org.reladev.anumati.tickets.entity.User;
import org.reladev.anumati.tickets.repository.UserRepository;
import org.reladev.anumati.tickets.service.CompanyService;
import org.reladev.anumati.tickets.service.RoleService;
import org.reladev.anumati.tickets.service.UserService;
import org.springframework.stereotype.Component;

@SuppressWarnings("UnusedAssignment")
@Component
public class BasicSecuritySetup {
    @Inject
    UserRepository userRepository;
    @Inject
    RoleService roleService;
    @Inject
    UserService userService;
    @Inject
    CompanyService companyService;

    @Transactional
    public void setup() {
        System.out.println("Setting up SuperAdmin");
        User superAdmin = new User();
        superAdmin.setUsername("superAdmin");
        superAdmin.setPassword("password");
        UserPermissions permissions = new UserPermissions();
        permissions.setSuperAdmin(true);
        superAdmin.setUserPermissions(permissions);
        userRepository.save(superAdmin);

        SecurityContext.setSecuredUserContext(() -> superAdmin);

        System.out.println("Setting up base Company");
        CompanyDto companyDto = new CompanyDto();
        companyDto.setName("Test");
        Company company = companyService.updateCreate(companyDto);

        UserDto userDto = new UserDto();
        userDto.setUsername("admin");
        userDto.setPassword("password");
        userDto.setCompanyId(company.getId());
        User user = userService.updateCreate(userDto);

        userDto = new UserDto();
        userDto.setUsername("developer");
        userDto.setPassword("password");
        userDto.setCompanyId(company.getId());
        user = userService.updateCreate(userDto);

        userDto = new UserDto();
        userDto.setUsername("employee");
        userDto.setPassword("password");
        userDto.setCompanyId(company.getId());
        user = userService.updateCreate(userDto);


    }
}
