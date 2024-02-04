package it.restapp.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class SpringBootRestAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootRestAppApplication.class, args);
	}

}
