package com.fisa.ctm.converter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fisa.ctm.dto.Event;
import com.fisa.ctm.enumerated.DurationUnit;
import com.fisa.ctm.utils.CommonUtils;
import com.fisa.ctm.utils.Constant;

@Service
public class EventConverter extends AbstractEntidadConverter<String, Event> {
	private static final Logger LOG = LoggerFactory.getLogger(AbstractEntidadConverter.class);

	@Override
	protected boolean isAmbiguousSupported() {
		return true;
	}

	@Override
	public Event mapEntityToDto(String entidad) {
		Pattern pattern = CommonUtils.getPattern(Constant.INPUT_LINE_PATTERN);
		Matcher match = pattern.matcher(entidad);
		boolean find = match.find();
		if (Boolean.FALSE.equals(find)) {
			return null;
		}
		DurationUnit unit = DurationUnit.MINUTES;
		String name = match.group(Constant.EVENT_NAME_INDEX);
		String durationInString = match.group(Constant.EVENT_DURATION_INDEX);
		String durationUnit = match.group(Constant.EVENT_DURATION_UNIT_INDEX);
		if (!durationUnit.equalsIgnoreCase(unit.getStringRepresentation())) {
			unit = DurationUnit.LIGHTENING;
		}
		if (durationInString == null) {
			durationInString = "1";
		}
		int duration = Integer.parseInt(durationInString);

		Event event = new Event(name, duration, unit);
		if (event.getDurationInMinutes() > Constant.MAX_EVENT_DURATION) {
			LOG.warn("Duration of event '" + name + "' is more than the maximum duration"
					+ " allowed for an event. Dropping this event for scheduling.");
			return null;
		}

		return event;
	}

}
