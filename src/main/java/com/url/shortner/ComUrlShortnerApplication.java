package com.url.shortner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class ComUrlShortnerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ComUrlShortnerApplication.class, args);
	}

}
