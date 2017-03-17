package org.reladev.anumati.hibernate_test.repository;

import javax.persistence.EntityManager;

import org.reladev.anumati.hibernate_test.entity.Submission;
import org.reladev.anumati.hibernate_test.security.SecurityObjectType;

public class SubmissionRepository extends EntityRepository<Submission> {
	public SubmissionRepository(EntityManager entityManager) {
		super(Submission.class, SecurityObjectType.PROJECT, entityManager);
	}
}
