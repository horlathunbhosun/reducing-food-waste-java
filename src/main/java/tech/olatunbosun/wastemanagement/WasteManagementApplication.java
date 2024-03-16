package tech.olatunbosun.wastemanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@EnableRedisRepositories
public class WasteManagementApplication {
	public static void main(String[] args) {
		SpringApplication.run(WasteManagementApplication.class, args);
	}

}
