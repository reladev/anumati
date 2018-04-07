package org.reladev.anumati.tickets;

import java.util.Arrays;

import javax.inject.Inject;

import org.reladev.anumati.SecurityContext;
import org.reladev.anumati.tickets.dto.PermissionsDto;
import org.reladev.anumati.tickets.entity.Company;
import org.reladev.anumati.tickets.entity.Project;
import org.reladev.anumati.tickets.entity.Role;
import org.reladev.anumati.tickets.entity.User;
import org.reladev.anumati.tickets.repository.CompanyRepository;
import org.reladev.anumati.tickets.repository.ProjectRepository;
import org.reladev.anumati.tickets.repository.RoleRepository;
import org.reladev.anumati.tickets.repository.UserRepository;
import org.reladev.anumati.tickets.service.PermissionsService;
import org.reladev.anumati.tickets.service.UserService;
import org.springframework.stereotype.Component;

@Component
public class AcmeDataCreator {
    public Company Acme;
    public Company ECorp;

    public Role ADMIN;
    public Role MANAGER;
    public Role DEVELOPER;
    public Role EMPLOYEE;

    public User SallyAdmin;
    public User MikeManager;
    public User JoeDev;
    public User JaneEmployee;

    public Project WebUi;

    @Inject
    CompanyRepository companyRepository;
    @Inject
    RoleRepository roleRepository;
    @Inject
    UserRepository userRepository;
    @Inject
    UserService userService;
    @Inject
    PermissionsService permissionService;
    @Inject
    ProjectRepository projectRepository;

    public void create() {
        createCompany();
        createRoles();
        createUsers();
    }

    public void createCompany() {
        Acme = new Company("Acme");
        companyRepository.save(Acme);
    }

    public void createRoles() {
        ADMIN = new Role(Acme, TicketsRole.ADMIN, Arrays.asList("TICKET_CRUD", "PROJECT_CRUD"));
        roleRepository.save(ADMIN);
        MANAGER = new Role(Acme, TicketsRole.MANAGER, Arrays.asList("TICKET_CRUD", "PROJECT_CRUD"));
        roleRepository.save(MANAGER);
        DEVELOPER = new Role(Acme, TicketsRole.DEVELOPER, Arrays.asList("TICKET_CRUD", "PROJECT_CRUD"));
        roleRepository.save(DEVELOPER);
        EMPLOYEE = new Role(Acme, TicketsRole.EMPLOYEE, Arrays.asList("TICKET_CRUD", "PROJECT_CRUD"));
        roleRepository.save(EMPLOYEE);
    }

    public void createUsers() {
        SallyAdmin = new User(Acme, "sally", "password", TicketsRole.ADMIN);
        userRepository.save(SallyAdmin);
        MikeManager = new User(Acme, "mike", "password", TicketsRole.MANAGER);
        userRepository.save(MikeManager);
        JoeDev = new User(Acme, "joe", "password", TicketsRole.DEVELOPER);
        userRepository.save(JoeDev);
        JaneEmployee = new User(Acme, "jane", "password", TicketsRole.EMPLOYEE);
        userRepository.save(JoeDev);
    }

    public void createProject() {
        WebUi = new Project(Acme, "WebUI");
        projectRepository.save(WebUi);

        SecurityContext.setSecuredUserContext(() -> SallyAdmin);

        PermissionsDto permissions = new PermissionsDto();
        permissions.setUserId(MikeManager.getId());
        permissions.setReferenceId(WebUi.getId());
        permissions.setReferenceType(WebUi.getSecuredReferenceType());

        permissionService.updateCreate(permissions);

    }
}
