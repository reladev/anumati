package org.reladev.anumati.hibernate_test;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.reladev.anumati.hibernate_test.dto.ProjectDto;
import org.reladev.anumati.hibernate_test.repository.ProjectRepository;
import org.reladev.anumati.hibernate_test.service.ProjectService;

public class ProjectTest extends JpaBaseRolledBackTestCase {
	@Test
	public void testCreate() {
		TestSecurityContext.init();

		ProjectService service = new ProjectService(new ProjectRepository(em));

		ProjectDto projectDto = new ProjectDto();
		projectDto.setName("Test1");


		Assertions.assertThatExceptionThrownBy(() -> service.save(projectDto));

		TestSecurityContext.setCompanyPermissions("DEPARTMENT_V", "PROJECT_C");

		service.save(projectDto);
	}
}
