package dev.josedegracia.jamboardReplacement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class WebApp {

	private static final Logger log =  LoggerFactory.getLogger(WebApp.class);

	public static void main(String[] args) {
		SpringApplication.run(WebApp.class, args);
	}

	@Bean
	CommandLineRunner runner() {
		return args -> {
			log.info("Application started");
		};
	}

}
