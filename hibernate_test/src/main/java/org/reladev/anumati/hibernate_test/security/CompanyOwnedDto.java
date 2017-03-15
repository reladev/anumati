package org.reladev.anumati.hibernate_test.security;

import org.reladev.anumati.hibernate.IdDto;

public interface CompanyOwnedDto extends IdDto {
	Long getOwnerId();
	void setOwnerId(Long owner);
}
