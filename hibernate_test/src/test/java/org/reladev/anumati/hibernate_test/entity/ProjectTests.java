package org.reladev.anumati.hibernate_test.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.reladev.anumati.hibernate_test.JpaBaseRolledBackTestCase;
import org.reladev.anumati.hibernate_test.TestSecurityContext;
import org.reladev.anumati.hibernate_test.repository.ProjectRepository;

public class ProjectTests extends JpaBaseRolledBackTestCase {
    private ProjectRepository repository;

    @Before
    public void init() {
        TestSecurityContext.init();
        repository = new ProjectRepository(em);
    }

    @Test
    public void testFactoryCreate() {
        TestSecurityContext.setSuperAdmin(true);

        Project project = new ProjectFactory().getOrCreatePersist();

        assertNotNull(project.getId());

        Project project1 = repository.get(project.getId());
        assertEquals(project, project1);

        List<Project> all = repository.findAll();
        assertThat(all).hasSize(1).contains(project);
    }

}
