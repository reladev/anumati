package org.reladev.anumati.hibernate_test.security;

import org.reladev.anumati.hibernate.IdDto;

public interface ProjectOwnedDto extends IdDto {
    Long getOwnerId();
	void setOwnerId(Long owner);
}
