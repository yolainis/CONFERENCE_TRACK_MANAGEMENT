package com.fisa.ctm.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fisa.ctm.utils.CommonUtils;
import com.fisa.ctm.utils.Constant;

public class Slot implements Serializable {

	private static final long serialVersionUID = 422753098025969421L;
	private List<Event> events;
	private int remainingDuration;
	private int startTime;
	private Slot supplement;

	public Slot(int duration, int startTime) {
		this.remainingDuration = duration;
		this.startTime = startTime;
		events = new ArrayList<>();
	}

	public void addEvent(Event event) {
		if (remainingDuration < event.getDurationInMinutes()) {
			throw new IllegalStateException("Not enough room in this slot to fit the event: '" + event.getName() + "'");
		}
		events.add(event);
		remainingDuration -= event.getDurationInMinutes();
	}

	public boolean hasRoomFor(Event event) {
		return remainingDuration >= event.getDurationInMinutes();
	}

	public void addSupplementSlot(Slot slot) {
		this.supplement = slot;
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		int nextEventStartTime = addEventsSchedule(events, startTime, str);
		if (supplement != null) {
			int supplementStartTime = supplement.startTime;
			if (nextEventStartTime > supplement.startTime) {
				supplementStartTime = nextEventStartTime;
			}
			addEventsSchedule(supplement.events, supplementStartTime, str);
		}
		return str.toString();
	}

	private int addEventsSchedule(List<Event> events, int startTime, StringBuilder str) {
		int nextEventStartTime = startTime;
		for (Event event : events) {
			str.append(CommonUtils.minutesToDisplayTime(nextEventStartTime) + " " + event + Constant.LINE_BREAK);
			nextEventStartTime += event.getDurationInMinutes();
		}

		return nextEventStartTime;
	}
}
