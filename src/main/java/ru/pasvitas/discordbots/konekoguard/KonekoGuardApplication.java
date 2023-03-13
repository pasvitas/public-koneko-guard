package ru.pasvitas.discordbots.konekoguard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class KonekoGuardApplication {

	public static void main(String[] args) {
		SpringApplication.run(KonekoGuardApplication.class, args);
	}

}
