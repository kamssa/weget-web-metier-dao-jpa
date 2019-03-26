package ci.weget.web;

import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;



@SpringBootApplication
@EnableScheduling
@EnableAsync
@EntityScan(basePackageClasses = { 
		WegetWebMetierDaoJpaApplication.class,
		Jsr310JpaConverters.class 
})
public class WegetWebMetierDaoJpaApplication implements CommandLineRunner {
	
	@PostConstruct
	void init() {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
	}
	
	public static void main(String[] args) {
		SpringApplication.run(WegetWebMetierDaoJpaApplication.class, args);
		
	}

	@Override
	public void run(String... args) throws Exception {
		
		
	}
}
