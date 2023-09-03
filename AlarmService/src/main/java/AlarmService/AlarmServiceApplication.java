package AlarmService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class AlarmServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AlarmServiceApplication.class, args);
	}

}
