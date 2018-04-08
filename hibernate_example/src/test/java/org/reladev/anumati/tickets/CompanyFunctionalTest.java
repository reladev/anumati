package org.reladev.anumati.tickets;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.reladev.anumati.tickets.entity.Company;
import org.reladev.anumati.tickets.service.CompanyService;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@TestPropertySource(locations = "classpath:test-application.properties")
@AutoConfigureTestEntityManager
@Transactional
public class CompanyFunctionalTest {

    @Inject
    private TestEntityManager entityManager;
    @Inject
    AcmeDataCreator acmeDataCreator;

    @Inject
    private CompanyService companyService;

    // write test cases here
    @Test
    public void testCrud() {
        acmeDataCreator.create();
        entityManager.flush();
        entityManager.clear();

        Company company = entityManager.find(Company.class, acmeDataCreator.Acme.getId());
        assertNotNull(company);

    }

}