package org.reladev.anumati.tickets;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.reladev.anumati.tickets.service.CompanyService;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
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


    }

}