package com.fisa.ctm.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fisa.ctm.dto.Event;
import com.fisa.ctm.dto.Response;
import com.fisa.ctm.dto.Slot;
import com.fisa.ctm.dto.Track;
import com.fisa.ctm.enumerated.DurationUnit;
import com.fisa.ctm.utils.Constant;

@Service
public class TrackService {

	public Response<List<Track>> createConfereceTracks(List<Event> events) {
		List<Track> tracks = new ArrayList<>();
		int trackNumber = 1;
		while (!events.isEmpty()) {
			Slot morningSlot = new Slot(Constant.MORNING_SLOT_DURATION, Constant.MORNING_SLOT_START_TIME);
			fillSlotWithEvents(morningSlot, events);

			Slot lunchSlot = new Slot(Constant.LUNCH_SLOT_DURATION, Constant.LUNCH_SLOT_START_TIME);
			lunchSlot.addEvent(new Event(Constant.LUNCH_SLOT_NAME, Constant.LUNCH_SLOT_DURATION, DurationUnit.MINUTES));

			Slot afternoonSlot = new Slot(Constant.AFTERNOON_SLOT_DURATION, Constant.AFTERNOON_SLOT_START_TIME);
			fillSlotWithEvents(afternoonSlot, events);

			Event networkingEvent = new Event(Constant.NETWORKING_EVENT_NAME, Constant.NETWORKING_EVENT_DURATION,
					DurationUnit.MINUTES);
			Slot networkingSlot = new Slot(networkingEvent.getDurationInMinutes(),
					Constant.NETWORKING_EVENT_MIN_START_TIME);
			networkingSlot.addEvent(networkingEvent);
			afternoonSlot.addSupplementSlot(networkingSlot);

			Track track = new Track("Track " + trackNumber + ":");
			track.addSlot(morningSlot);
			track.addSlot(lunchSlot);
			track.addSlot(afternoonSlot);
			tracks.add(track);
			trackNumber++;
		}
		return new Response<>(tracks);
	}

	private static void fillSlotWithEvents(Slot slot, List<Event> events) {
		for (Iterator<Event> iter = events.iterator(); iter.hasNext();) {
			Event event = iter.next();
			if (slot.hasRoomFor(event)) {
				slot.addEvent(event);
				iter.remove();
			}
		}
	}
}
