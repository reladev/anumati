package org.reladev.anumati.tickets.repository;

import org.reladev.anumati.tickets.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserRepository extends BaseRepository<User> {
    public UserRepository() {
    }

    public User findByUsername(String username) {
        return entityManager.createQuery("SELECT u FROM User u WHERE u.username =:username", User.class).setParameter("username", username).getSingleResult();
    }


}