package application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MovieLibraryApplication {

	public static void main(String[] args) {
		SpringApplication.run(MovieLibraryApplication.class, args);
		System.out.println("Movie Library Application");
	}

}
