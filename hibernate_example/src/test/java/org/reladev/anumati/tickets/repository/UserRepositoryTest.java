package org.reladev.anumati.tickets.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.reladev.anumati.tickets.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    // write test cases here
    @Test
    public void testCrud() {
        User user = new User();
        //user.setUserPermissions(new UserPermissions());
        //user.setPrivileges(Arrays.asList("foo", "bar"));
        userRepository.save(user);
        entityManager.flush();
        entityManager.clear();

        assertNotNull(user.getId());

        User gotUser = userRepository.get(user.getId());
        assertNotNull(gotUser);
        assertNotNull(gotUser.getUserPermissions());
        //assertNotNull(gotUser.getPrivileges());
        //assertEquals(2, gotUser.getPrivileges().size());

        userRepository.delete(user.getId());
        entityManager.flush();
        entityManager.clear();

        User deleted = userRepository.get(user.getId());
        assertNull(deleted);
    }

    @Test
    public void testFindByUsername() {
        User user = new User();
        user.setUsername("foo");
        userRepository.save(user);
        entityManager.flush();
        entityManager.clear();

        User gotUser = userRepository.findByUsername("foo");
        assertNotNull(gotUser);
    }

}