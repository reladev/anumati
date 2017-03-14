package org.reladev.anumati.hibernate.security;

import org.reladev.anumati.hibernate.IdDto;

public interface DepartmentOwnedDto extends IdDto {
	Long getOwnerId();
	void setOwnerId(Long owner);
}
