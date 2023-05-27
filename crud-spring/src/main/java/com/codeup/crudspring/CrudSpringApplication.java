package com.codeup.crudspring;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.codeup.crudspring.enums.CategoryEnum;
import com.codeup.crudspring.model.Course;
import com.codeup.crudspring.model.Lesson;
import com.codeup.crudspring.repository.CourseRepository;

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
			
			Lesson l = new Lesson();
			l.setName("Introdução");
			l.setYoutubeUrl("aaaa");
			l.setCourse(c);
			c.getLessons().add(l);
			
			Lesson l2 = new Lesson();
			l2.setName("Aula 1");
			l2.setYoutubeUrl("watch=123");
			l2.setCourse(c);
            c.getLessons().add(l2);
			
			repository.save(c);
		};
	}

}
