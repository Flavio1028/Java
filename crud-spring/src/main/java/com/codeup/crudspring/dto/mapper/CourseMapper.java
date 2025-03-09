package com.codeup.crudspring.dto.mapper;

import com.codeup.crudspring.dto.CourseDTO;
import com.codeup.crudspring.dto.LessonDTO;
import com.codeup.crudspring.enums.CategoryEnum;
import com.codeup.crudspring.model.Course;
import com.codeup.crudspring.model.Lesson;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CourseMapper {

    public CourseDTO toDTO(Course course) {
        if (course == null) {
            return null;
        }
        List<LessonDTO> lessons = course.getLessons()
                .stream()
                .map(lesson -> new LessonDTO(lesson.getId(), lesson.getName(),
                        lesson.getYoutubeUrl()))
                .toList();
        return new CourseDTO(course.getId(), course.getName(), course.getCategory().getValue(),
                lessons);
    }

    public Course toEntity(CourseDTO courseDTO) {
        if (courseDTO == null) {
            return null;
        }

        Course course = new Course();
        if (courseDTO.Id() != null) {
            course.setId(courseDTO.Id());
        }
        course.setName(courseDTO.name());
        course.setCategory(convertCategoryValue(courseDTO.category()));

        List<Lesson> lessons = courseDTO.lessons().stream().map(lessonDTO -> {
            var lesson = new Lesson();
            lesson.setId(lessonDTO.id());
            lesson.setName(lessonDTO.name());
            lesson.setYoutubeUrl(lessonDTO.youtubeUrl());
            lesson.setCourse(course);
            return lesson;
        }).toList();
        course.setLessons(lessons);

        return course;
    }

    public CategoryEnum convertCategoryValue(String value) {

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