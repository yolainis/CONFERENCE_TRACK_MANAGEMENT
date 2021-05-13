package com.fisa.ctm.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fisa.ctm.utils.Constant;

import lombok.Data;

@Data
public class Conference implements Serializable {

	private static final long serialVersionUID = -3277749999313324466L;
	private List<Track> tracks;

	public Conference() {
		tracks = new ArrayList<>();
	}

	public void addTrack(Track track) {
		tracks.add(track);
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append("Schedule:" + Constant.LINE_BREAK);
		for (int i = 0; i < tracks.size(); i++) {
			// str.append("Track " + (i + 1) + ":" + Constant.LINE_BREAK);
			str.append(tracks.get(i));
			str.append(Constant.LINE_BREAK);
		}
		return str.toString();
	}
}
