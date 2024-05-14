package dev.josedegracia.jamboardReplacement;

import dev.josedegracia.jamboardReplacement.board.AnonymounsUser;
import dev.josedegracia.jamboardReplacement.board.Board;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

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
