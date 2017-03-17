package org.reladev.anumati.hibernate_test.factory;


import org.reladev.anumati.hibernate_test.entity.Asset;

public class AssetFactory extends BaseFactory<AssetFactory, Asset> {

	public AssetFactory() {
		super(new Asset());
	}

	protected void ensureRequired() {
		if (entity.getProject() == null) {
			entity.setProject(new ProjectFactory().getOrCreatePersist());
		}
	}
}
