package com.fisa.ctm.service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import com.fisa.ctm.dto.Response;
import com.fisa.ctm.exception.CTMException;
import com.fisa.ctm.utils.CommonUtils;
import com.fisa.ctm.utils.Constant;

@Service
public class ValidateService {

	@SuppressWarnings("unchecked")
	public Response<Boolean> validatedEvents(List<String> inputString) {
		if (inputString.isEmpty()) {
			return CommonUtils.initResultadoError(Constant.EVENTS_EMPTY);
		}
		List<String> messages = new ArrayList<>();
		int line = 1;
		for (String event : inputString) {
			try {
				validateInputString(event.trim());
			} catch (CTMException e) {
				StringBuilder errorBuilder = new StringBuilder();
				errorBuilder.append(Constant.LINE_NUM).append(line).append(Constant.DOT).append(event)
						.append("->" + e.getMessage()).append(Constant.LINE_BREAK);
				messages.add(errorBuilder.toString());
			}
			line++;
		}
		if (!messages.isEmpty()) {
			return CommonUtils.initResultadoErrors(messages);
		}
		return new Response<>(true);
	}

	private void validateInputString(String event) {

		if (event == null || event.isEmpty()) {
			throw new CTMException(Constant.EVENT_EMPTY);

		}
		Pattern pattern = CommonUtils.getPattern(Constant.INPUT_LINE_PATTERN);
		Matcher match = pattern.matcher(event);
		boolean find = match.find();
		if (Boolean.FALSE.equals(find)) {
			throw new CTMException(Constant.EVENT_EMPTY);
		}
		String durationInString = match.group(Constant.EVENT_DURATION_INDEX);
		String durationUnit = match.group(Constant.EVENT_DURATION_UNIT_INDEX);
		if (durationInString == null && Constant.MINITES.equalsIgnoreCase(durationUnit)) {
			throw new CTMException(Constant.ERROR_INVAILD_DURATIION);
		}

		if (durationInString != null && !Constant.LIGHTNING.equalsIgnoreCase(durationInString)) {
			int duration = Integer.parseInt(durationInString);
			if (duration > Constant.MAX_EVENT_DURATION) {
				throw new CTMException(Constant.ERROR_DURATION_MORE_THEN_MAXTIME);

			}
		}

	}

}
