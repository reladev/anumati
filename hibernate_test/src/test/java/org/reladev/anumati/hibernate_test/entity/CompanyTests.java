package org.reladev.anumati.hibernate_test.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.reladev.anumati.hibernate_test.JpaBaseRolledBackTestCase;
import org.reladev.anumati.hibernate_test.TestSecurityContext;
import org.reladev.anumati.hibernate_test.repository.CompanyRepository;

public class CompanyTests extends JpaBaseRolledBackTestCase {
    CompanyRepository repository;

    @Before
    public void init() {
        TestSecurityContext.init();
        repository = new CompanyRepository(em);
    }

    @Test
    public void testFactoryCreate() {
        TestSecurityContext.setSuperAdmin(true);

        Company company = new CompanyFactory().getOrCreatePersist();

        assertNotNull(company.getId());

        Company company1 = repository.get(company.getId());
        assertEquals(company, company1);

        List<Company> all = repository.findAll();
        assertThat(all).hasSize(1).contains(company);
    }

}
