package org.reladev.anumati.tickets;

import java.util.Arrays;

import javax.inject.Inject;

import org.reladev.anumati.tickets.entity.Company;
import org.reladev.anumati.tickets.entity.Role;
import org.reladev.anumati.tickets.entity.User;
import org.reladev.anumati.tickets.repository.CompanyRepository;
import org.reladev.anumati.tickets.repository.RoleRepository;
import org.reladev.anumati.tickets.repository.UserRepository;
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

    @Inject
    CompanyRepository companyRepository;
    @Inject
    RoleRepository roleRepository;
    @Inject
    UserRepository userRepository;

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
}
