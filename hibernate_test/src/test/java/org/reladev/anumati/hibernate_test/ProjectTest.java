package org.reladev.anumati.hibernate_test;

import org.junit.Test;
import org.reladev.anumati.hibernate_test.entity.Project;

public class ProjectTest extends JpaBaseRolledBackTestCase {
	@Test
	public void test() {
		Project project = new Project();
		em.persist(project);
	}
}
