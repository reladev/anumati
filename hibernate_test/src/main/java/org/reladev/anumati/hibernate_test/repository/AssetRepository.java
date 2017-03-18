package org.reladev.anumati.hibernate_test.repository;

import java.util.List;

import javax.persistence.EntityManager;

import org.reladev.anumati.hibernate_test.entity.Asset;
import org.reladev.anumati.hibernate_test.security.SecurityObjectType;

public class AssetRepository extends EntityRepository<Asset> {
	public AssetRepository(EntityManager entityManager) {
		super(Asset.class, SecurityObjectType.ASSET, entityManager);
	}

	@Override
	public List<Asset> findAll() {
		return buildQuery().execute();
	}
}
