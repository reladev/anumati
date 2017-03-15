package org.reladev.anumati.hibernate_test.security;

import org.reladev.anumati.SecuredByRef;
import org.reladev.anumati.SecuredObjectType;
import org.reladev.anumati.hibernate_test.entity.Asset;
import org.reladev.anumati.hibernate_test.entity.Project;

public enum SecurityObjectType implements SecuredObjectType {
	COMPANY(null),
	DEPARTMENT(null),
	USER(null),
	PROJECT(Project.class),
	ASSET(Asset.class);

	public static final int COMPANY_ORDINAL = 0;
	public static final int DEPARTMENT_ORDINAL = 1;
	public static final int USER_ORDINAL = 2;
	public static final int PROJECT_ORDINAL = 3;
	public static final int ASSET_ORDINAL = 4;

	private Class<? extends SecuredByRef> typeClass;

	SecurityObjectType(Class<? extends SecuredByRef> typeClass) {
		this.typeClass = typeClass;
	}

	public Class<? extends SecuredByRef> getTypeClass() {
		return typeClass;
	}
}
