package br.com.emerion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.emerion.model.ExceptionModel;

@SpringBootApplication
@EnableAutoConfiguration
@EntityScan(basePackageClasses = ExceptionModel.class)
@RestController
//public class Application extends SpringBootServletInitializer {
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

//	@Override
//	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//		return application.sources(Application.class);
//	}

	@GetMapping(path = "/ping")
	public String ping() {
		return "ok.";
	}
}