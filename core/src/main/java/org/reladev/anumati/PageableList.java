package org.reladev.anumati;

import java.util.List;

public class PageableList<T> {
	private Integer nextPagePointer;
	private List<T> list;

	public Integer getNextPagePointer() {
		return nextPagePointer;
	}

	public void setNextPagePointer(Integer nextPagePointer) {
		this.nextPagePointer = nextPagePointer;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}
}
