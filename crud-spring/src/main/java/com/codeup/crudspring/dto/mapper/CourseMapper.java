package com.codeup.crudspring.dto.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.codeup.crudspring.dto.CourseDTO;
import com.codeup.crudspring.dto.LessonDTO;
import com.codeup.crudspring.enums.CategoryEnum;
import com.codeup.crudspring.model.Course;
import com.codeup.crudspring.model.Lesson;

@Component
public class CourseMapper {

    public CourseDTO toDTO(Course course) {
        if (course == null) {
            return null;
        }
        List<LessonDTO> lessons = course.getLessons()
                .stream()
                .map(lessson -> new LessonDTO(lessson.getId(), lessson.getName(), lessson.getYoutubeUrl()))
                .collect(Collectors.toList());

        return new CourseDTO(course.getId(), course.getName(), course.getCategory().getValue(),
                lessons);
    }

    public Course toEntity(CourseDTO courseDTO) {
        Course course = new Course();
        if (courseDTO.Id() != null) {
            course.setId(courseDTO.Id());
        }
        course.setName(courseDTO.name());
        course.setCategory(this.converterCategoryEnumValue(courseDTO.category()));
            
        
        List<Lesson> lessons = courseDTO.lessons().stream().map(lessonDTO -> {
           var lesson = new Lesson();
           lesson.setId(lessonDTO.id());
           lesson.setName(lessonDTO.name());
           lesson.setYoutubeUrl(lessonDTO.youtubeUrl());
           lesson.setCourse(course);
           return lesson;
        }).collect(Collectors.toList());
        course.setLessons(lessons);
        
        return course;
    }

    public CategoryEnum converterCategoryEnumValue(String value) {
        if (value == null) {
            return null;
        }
        return switch (value) {
            case "Front-end" -> CategoryEnum.FRONT_END;
            case "Back-end" -> CategoryEnum.BACK_END;
            default -> throw new IllegalArgumentException("Categoria inválida: " + value);
        };
    }
    
}