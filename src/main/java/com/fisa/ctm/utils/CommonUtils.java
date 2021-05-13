package com.fisa.ctm.utils;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import com.fisa.ctm.dto.Response;
import com.fisa.ctm.enumerated.EnumResponse;

public class CommonUtils {

	private CommonUtils() {
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Response initResultadoError(String message) {
		return initResultadoErrors(Arrays.asList(message));
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Response initResultadoErrors(List<String> message) {
		Response resultDto = new Response<>();
		resultDto.setResultado(EnumResponse.ERROR);
		resultDto.setMensajes(message);
		return resultDto;
	}

	public static Pattern getPattern(String regex) {
		return Pattern.compile(regex);
	}

	public static String minutesToDisplayTime(int minutes) {
		int maxSupportedTimeInMinutes = (12 * 60 + 12 * 60) - 1;
		if (minutes > maxSupportedTimeInMinutes) {
			throw new IllegalArgumentException(
					"Time in minutes cannot be greater than" + maxSupportedTimeInMinutes + " minutes.");
		}

		int hours = minutes / 60;
		String hoursToDisplay = Integer.toString(hours);
		if (hours > 12) {
			hoursToDisplay = Integer.toString(hours - 12);
		}
		if (hoursToDisplay.length() == 1) {
			hoursToDisplay = "0" + hoursToDisplay;
		}

		minutes = minutes - (hours * 60);
		String minutesToDisplay = null;
		if (minutes == 0) {
			minutesToDisplay = "00";
		} else if (minutes < 10) {
			minutesToDisplay = "0" + minutes;
		} else {
			minutesToDisplay = "" + minutes;
		}

		String displayValue;
		if (hours < 12) {
			displayValue = " AM";
			if (hoursToDisplay.equals("00")) {
				hoursToDisplay = "12";
			}
		} else {
			displayValue = " PM";
		}
		displayValue = hoursToDisplay + ":" + minutesToDisplay + displayValue;

		return displayValue;
	}
}
