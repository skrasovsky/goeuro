package com.goeuro;

import com.goeuro.config.ApplicationConfig;
import com.goeuro.core.CommandExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.Scanner;

import static com.goeuro.service.logger.CustomConsoleLogger.error;
import static com.goeuro.service.logger.CustomConsoleLogger.info;

@Configuration
@Import(value = {ApplicationConfig.class})
public class GoEuroApplication {

	private static final Logger LOGGER = LoggerFactory.getLogger(GoEuroApplication.class);

	private static final String LINE_BREAK = "";

	@Autowired
	private CommandExecutor commandExecutor;

	public static void main(String[] args) {

		ConfigurableApplicationContext context = new SpringApplicationBuilder()
			.sources(GoEuroApplication.class)
			.bannerMode(Banner.Mode.OFF)
			.run(args);

		GoEuroApplication app = context.getBean(GoEuroApplication.class);
		app.start();
	}

	public void start() {

		info("Application has initialized and ready to work.");
		info("Execute '-help'.");
		while (true) {
			try {
				info(LINE_BREAK);
				Scanner inputReader = new Scanner(System.in);
				if (inputReader.hasNext()) {
					String input = inputReader.nextLine();
					commandExecutor.execute(input.split(" "));
				}
			} catch(Throwable e) {
				error("For more information see the application log.");
				LOGGER.error("Obtain error while executing command.", e);
			}
		}
	}

}
