package com.fisa.ctm.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fisa.ctm.converter.EventConverter;
import com.fisa.ctm.dto.Event;
import com.fisa.ctm.dto.Response;
import com.fisa.ctm.enumerated.EnumResponse;

@Service
public class EventService {
	@Autowired
	private ValidateService validateService;

	@Autowired
	private EventConverter eventConverter;

	public Response<List<Event>> readFileInput(BufferedReader reader) {
		try {
			List<String> inputString = readInput(reader);
			Response<Boolean> validatedEvents = validateService.validatedEvents(inputString);
			if (!EnumResponse.OK.equals(validatedEvents.getResultado())) {
				return new Response<>(validatedEvents.getResultado(), validatedEvents.getMensajes());
			}
			return new Response<>(eventConverter.mapListEntityToListDto(inputString));
		} catch (Exception e) {
			return new Response<>(EnumResponse.ERROR, Arrays.asList(e.getMessage()));
		}
	}

	private List<String> readInput(BufferedReader input) throws IOException {
		List<String> events = new ArrayList<>();
		String strLine = input.readLine();
		while (strLine != null) {
			events.add(strLine.trim());
			strLine = input.readLine();
		}
		return events;

	}

}
