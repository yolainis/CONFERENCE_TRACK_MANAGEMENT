package com.fisa.ctm;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fisa.ctm.dto.Conference;
import com.fisa.ctm.dto.Response;
import com.fisa.ctm.service.ConferenceService;

@SpringBootTest
class CtmApplicationTests {

	@Autowired
	ConferenceService conferenceService;

	@Test
	public void testConferenceTrackManagementMultipleFullDayEvents() throws IOException {
		testConferenceTrackManagement("/file_events");
	}

	@Test
	public void testConferenceTrackManagementMultipleDayLessEvents() throws IOException {
		testConferenceTrackManagement("/file_few_events");
	}

	@Test
	public void testConferenceTrackManagementSingleDayEvents() throws IOException {
		testConferenceTrackManagement("/file_events_one_day");
	}

	private void testConferenceTrackManagement(String inputFile) throws IOException {
		Response<Conference> conference = conferenceService
				.scheduler(getBufferedReaderForResourceFile(inputFile, this));

		assertTrue(contentEquals(getExpectedOutputFile(inputFile), conference.getRespuesta().toString(), this));
	}

	private String getExpectedOutputFile(String inputFile) {
		return inputFile + "_expected";
	}

	public static BufferedReader getBufferedReaderForResourceFile(String resourceFile, Object context) {
		InputStream inputStream = context.getClass().getResourceAsStream(resourceFile);
		return new BufferedReader(new InputStreamReader(inputStream));
	}

	public static boolean contentEquals(String resourceFile, String string, Object context) throws IOException {
		BufferedReader fileReader = getBufferedReaderForResourceFile(resourceFile, context);
		BufferedReader stringReader = new BufferedReader(new StringReader(string));
		while (true) {
			String fileLine = getNextLine(fileReader);
			String stringLine = getNextLine(stringReader);
			if (fileLine == null && stringLine == null) {
				break;
			}

			if (fileLine == null || stringLine == null) {
				return false;
			}

			if (fileLine.equals(stringLine) == false) {
				return false;
			}
		}
		return true;
	}

	private static String getNextLine(BufferedReader reader) throws IOException {
		while (true) {
			String line = reader.readLine();
			if (line == null) {
				return null;
			}

			if (line.trim().length() != 0) {
				return line;
			}
		}
	}

}
