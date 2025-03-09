package com.codeup.crudspring.controller;

import com.codeup.crudspring.dto.CourseDTO;
import com.codeup.crudspring.dto.CoursePageDTO;
import com.codeup.crudspring.dto.LessonDTO;
import com.codeup.crudspring.enums.CategoryEnum;
import com.codeup.crudspring.exception.RecordNotFoundException;
import com.codeup.crudspring.service.CourseService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CourseController.class)
class CourseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CourseService courseService;

    private final String COURSE_JSON = """
                {
                    "id": 1,
                    "name": "Java",
                    "category": "Front-end",
                    "lessons": [
                        {
                            "id": 1,
                            "name": "Test JUnit",
                            "youtubeUrl": "1234567890"
                        }
                    ]
                }
            """;

    @Test
    void shouldReturnCoursePageDTOWhenListingCourses() throws Exception {
        // Mockando a resposta esperada
        CoursePageDTO mockResponse = new CoursePageDTO(List.of(new CourseDTO(1L, "Java", CategoryEnum.BACK_END.getValue(), List.of())), 10L,
                1);
        Mockito.when(courseService.list(anyInt(), anyInt())).thenReturn(mockResponse);

        mockMvc.perform(get("/api/courses")
                        .param("page", "0")
                        .param("pageSize", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.courses[0].name").value("Java"))
                .andExpect(jsonPath("$.totalPages").value(1));
    }

    @Test
    void shouldReturnCourseDTOWhenFindById() throws Exception {
        // Mockando a resposta esperada
        CourseDTO mockResponse = new CourseDTO(1L, "Java", CategoryEnum.BACK_END.getValue(), List.of());
        Mockito.when(courseService.findById(1L)).thenReturn(mockResponse);

        mockMvc.perform(get("/api/courses/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Java"));
    }

    @Test
    void shouldNotReturnCourseDTOWhenFindById() throws Exception {
        // Mockando a resposta esperada
        Mockito.when(courseService.findById(1L)).thenThrow(new RecordNotFoundException(1L));

        mockMvc.perform(get("/api/courses/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldCreateCourseSuccessfully() throws Exception {
        CourseDTO mockResponse = new CourseDTO(1L, "Java", CategoryEnum.BACK_END.getValue(),
                List.of());
        Mockito.when(courseService.create(any(CourseDTO.class))).thenReturn(mockResponse);

        mockMvc.perform(post("/api/courses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(COURSE_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$._id").value(1))
                .andExpect(jsonPath("$.name").value("Java"));
    }

    @Test
    void shouldReturnBadRequestWhenCreateWithInvalidData() throws Exception {
        String invalidCourseJson = """
                {
                    "id": 1,
                    "name": "",
                    "category": CategoryEnum.BACK_END.getValue(),
                    "lessons": []
                }
                """;

        mockMvc.perform(post("/api/courses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidCourseJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldUpdateCourseSuccessfully() throws Exception {
        CourseDTO mockResponse = new CourseDTO(1L, "Updated Course", CategoryEnum.BACK_END.getValue(),
                List.of(new LessonDTO(1L, "Test JUnit", "1234567890")));
        Mockito.when(courseService.update(anyLong(), any(CourseDTO.class))).thenReturn(mockResponse);

        mockMvc.perform(put("/api/courses/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(COURSE_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Course"));
    }

    @Test
    void shouldDeleteCourseSuccessfully() throws Exception {
        Mockito.doNothing().when(courseService).delete(anyLong());

        mockMvc.perform(delete("/api/courses/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

}