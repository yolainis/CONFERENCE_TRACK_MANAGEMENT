package com.fisa.ctm;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.fisa.ctm.dto.Conference;
import com.fisa.ctm.dto.Response;
import com.fisa.ctm.enumerated.EnumResponse;
import com.fisa.ctm.service.ConferenceService;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan({ "com.fisa.ctm" })
public class CtmApplication {
	private static final Logger LOG = LoggerFactory.getLogger(CtmApplication.class);

	@Autowired
	ConferenceService conferenceService;

	public static void main(String[] args) {
		SpringApplication.run(CtmApplication.class, args);
	}

	@PostConstruct
	public void scheduler() {
		LOG.info("Launch the App");
		Scanner scanner = new Scanner(System.in);

		LOG.info(">>>>>CONFERENCE TRACK MANAGEMENT<<<<<");
		LOG.info("Enter the path of the text file that contains the events to schedule: ");
		String input = scanner.nextLine();
		if (input.isEmpty()) {
			LOG.error("Input file with events must be supplied to this program.");
			System.exit(1);
		}

		try {
			BufferedReader reader = new BufferedReader(new FileReader(input));
			Response<Conference> conference = conferenceService.scheduler(reader);
			if (EnumResponse.ERROR.equals(conference.getResultado())) {
				LOG.error("Errors found:");
				LOG.error("{}", conference.getMensajes());
			} else {
				LOG.info("{}", conference.getRespuesta());
			}
		} catch (IOException e) {
			scanner.close();
			LOG.error("Cannot read from input file: {}", input);
		} catch (NullPointerException e) {
			LOG.error("Non-instantiated objects error.");
		} finally {
			LOG.info("End of Application");
			// System.exit(1);
		}
	}

}
