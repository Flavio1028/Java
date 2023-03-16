package com.codeup.crudspring;

import com.codeup.crudspring.enums.CategoryEnum;
import com.codeup.crudspring.model.Course;
import com.codeup.crudspring.repository.CourseRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CrudSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrudSpringApplication.class, args);
	}

	@Bean
	CommandLineRunner initDataBase(CourseRepository repository) {
		return args -> {
			repository.deleteAll();

			Course c = new Course();
			c.setName("Angular com Spring");
			c.setCategory(CategoryEnum.FRONT_END);

			repository.save(c);
		};
	}

}
