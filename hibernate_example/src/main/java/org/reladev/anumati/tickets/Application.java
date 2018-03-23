package org.reladev.anumati.tickets;

import java.util.Collections;

import javax.inject.Inject;

import org.reladev.anumati.SecurityContext;
import org.reladev.anumati.tickets.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Component
    public class ApplicationSetup implements CommandLineRunner {
        @Inject
        BasicSecuritySetup basicSecuritySetup;

        @Override
        public void run(String... args) throws Exception {
            SecurityContext.setThrowPermissionFailed(message -> {
                throw new AccessDeniedException(message);
            });

            basicSecuritySetup.setup();

        }
    }


    @Configuration
    @ComponentScan("org.reladev.anumati.tickets")
    class WebSecurityConfiguration extends GlobalAuthenticationConfigurerAdapter {
        @Inject
        UserRepository userRepository;

        @Override
        public void init(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(userDetailsService());
        }

        @Bean
        UserDetailsService userDetailsService() {
            return username -> {
                try {
                    org.reladev.anumati.tickets.entity.User securedUser = userRepository.findByUsername(username);
                    if (securedUser == null) {
                        throw new UsernameNotFoundException(username);
                    }
                    SecurityContext.setSecuredUserContext(() -> securedUser);
                    User user = new User(username, securedUser.getPassword(), true, true, true, true, Collections.emptyList());
                    return user;
                } catch (Exception e) {
                    e.printStackTrace();
                    throw e;
                }
            };
        }

        @SuppressWarnings("deprecation")
        @Bean
        public NoOpPasswordEncoder passwordEncoder() {
            return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
        }
    }

    @EnableWebSecurity
    @Configuration
    @EnableGlobalMethodSecurity(prePostEnabled = true)
    class WebSecurityConfig extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests().anyRequest().fullyAuthenticated().and().
                  httpBasic().and().
                  csrf().disable();
        }

    }

}


