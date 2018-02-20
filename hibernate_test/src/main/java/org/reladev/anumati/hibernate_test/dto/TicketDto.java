package org.reladev.anumati.hibernate_test.dto;

import org.reladev.anumati.hibernate.IdDto;
import org.reladev.anumati.hibernate_test.security.ProjectOwnedDto;

public class TicketDto implements IdDto, ProjectOwnedDto {
    private Long id;
	private Long ownerId;
	private String name;

	@Override
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public Long getOwnerId() {
		return ownerId;
	}

	@Override
	public void setOwnerId(Long ownerId) {
		this.ownerId = ownerId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
