package com.fisa.ctm.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fisa.ctm.utils.Constant;

import lombok.Data;

@Data
public class Track implements Serializable {

	private static final long serialVersionUID = -3277749999313324466L;
	private String name;
	private List<Slot> slots;

	public Track(String name) {
		this.name = name;
		slots = new ArrayList<>();
	}

	public void addSlot(Slot slot) {
		slots.add(slot);
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append(name);
		str.append(Constant.LINE_BREAK);
		for (Slot slot : slots) {
			str.append(slot);
		}
		return str.toString();
	}
}
