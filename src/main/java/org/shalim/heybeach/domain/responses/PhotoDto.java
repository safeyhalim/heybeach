package org.shalim.heybeach.domain.responses;

import org.shalim.heybeach.model.Photo;

public class PhotoDto {
	private Long id;
	private String name;
	private String type;

	public PhotoDto(Photo photo) {
		id = photo.getId();
		name = photo.getName();
		type = photo.getType();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
