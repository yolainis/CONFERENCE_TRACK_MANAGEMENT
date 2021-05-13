package com.fisa.ctm.service;

import java.io.BufferedReader;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;

import com.fisa.ctm.dto.Conference;
import com.fisa.ctm.dto.Event;
import com.fisa.ctm.dto.Response;
import com.fisa.ctm.dto.Track;
import com.fisa.ctm.enumerated.EnumResponse;
import com.fisa.ctm.utils.CommonUtils;

@Component
@Configurable
public class ConferenceService {

	@Autowired
	private EventService eventService;

	@Autowired
	private TrackService trackService;

	@SuppressWarnings("unchecked")
	public Response<Conference> scheduler(BufferedReader reader) {
		Response<List<Event>> events = eventService.readFileInput(reader);
		if (!EnumResponse.OK.equals(events.getResultado())) {
			return CommonUtils.initResultadoErrors(events.getMensajes());
		}
		return createConference(events.getRespuesta());
	}

	@SuppressWarnings("unchecked")
	private Response<Conference> createConference(List<Event> events) {
		Conference conference = new Conference();
		Response<List<Track>> tracks = trackService.createConfereceTracks(events);
		if (!EnumResponse.OK.equals(tracks.getResultado())) {
			return CommonUtils.initResultadoErrors(tracks.getMensajes());
		}
		tracks.getRespuesta().forEach(track -> conference.addTrack(track));
		return new Response<>(conference);
	}
}
