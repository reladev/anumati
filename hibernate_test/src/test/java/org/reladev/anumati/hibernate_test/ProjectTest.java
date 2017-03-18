package org.reladev.anumati.hibernate_test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.reladev.anumati.hibernate_test.dto.ProjectDto;
import org.reladev.anumati.hibernate_test.entity.Company;
import org.reladev.anumati.hibernate_test.entity.Department;
import org.reladev.anumati.hibernate_test.entity.Project;
import org.reladev.anumati.hibernate_test.factory.CompanyFactory;
import org.reladev.anumati.hibernate_test.factory.DepartmentFactory;
import org.reladev.anumati.hibernate_test.factory.ProjectFactory;
import org.reladev.anumati.hibernate_test.repository.ProjectRepository;
import org.reladev.anumati.hibernate_test.security.UserContext;
import org.reladev.anumati.hibernate_test.service.ProjectService;

public class ProjectTest extends JpaBaseRolledBackTestCase {
	ProjectService service;

	@Before
	public void init() {
		TestSecurityContext.init();
		service = new ProjectService(new ProjectRepository(em));
	}

	@Test
	public void testCreate() {
		ProjectDto projectDto = new ProjectDto();
		projectDto.setName("Test1");

		Assertions.assertThatExceptionThrownBy(() -> service.save(projectDto));

		TestSecurityContext.setCompanyPermissions("DEPARTMENT_V", "PROJECT_C");

		service.save(projectDto);
	}

	@Test
	public void testEdit() {
		Project project = new ProjectFactory().getOrCreatePersist();

		ProjectDto projectDto = new ProjectDto();
		projectDto.setId(project.getId());
		projectDto.setName("Test2");

		Assertions.assertThatExceptionThrownBy(() -> service.save(projectDto));

		TestSecurityContext.setCompanyPermissions("DEPARTMENT_V", "PROJECT_C");

		Project actual = service.save(projectDto);
	}

	@Test
	public void testFilter() {
		Project projectA = new ProjectFactory().getOrCreatePersist();
		Department departmentB = new DepartmentFactory().createPersist();
		Project projectB = new ProjectFactory().createPersist();

		Company companyZ = new CompanyFactory().createPersist();
		Department departmentZ = new DepartmentFactory().create();
		Project projectZ = new ProjectFactory().getOrCreatePersist();

		List<Project> all = service.getAll();

		assertThat(all).hasSize(0);

		TestSecurityContext.setCompanyPermissions("PROJECT_V");
		all = service.getAll();

		assertThat(all)
			  .hasSize(2)
			  .contains(projectA, projectB);

		TestSecurityContext.setCompanyPermissions();
		TestSecurityContext.setPermissions(UserContext.getUser().getDefaultDepartment(), "PROJECT_V");

		all = service.getAll();
		assertThat(all)
			  .hasSize(1)
			  .contains(projectA);

	}
}
