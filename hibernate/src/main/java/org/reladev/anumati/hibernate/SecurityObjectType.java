package org.reladev.anumati.hibernate;

import org.reladev.anumati.SecuredObjectType;

public enum SecurityObjectType implements SecuredObjectType {
	COMPANY,
	DEPARTMENT,
	PROJECT,
	USER;

	public static final int COMPANY_ORDINAL = 0;
	public static final int DEPARTMENT_ORDINAL = 1;
	public static final int PROJECT_ORDINAL = 2;
	public static final int USER_ORDINAL = 3;
}
