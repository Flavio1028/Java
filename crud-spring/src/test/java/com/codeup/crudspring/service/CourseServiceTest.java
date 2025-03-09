package com.codeup.crudspring.service;

import com.codeup.crudspring.dto.CourseDTO;
import com.codeup.crudspring.dto.CoursePageDTO;
import com.codeup.crudspring.dto.LessonDTO;
import com.codeup.crudspring.enums.CategoryEnum;
import com.codeup.crudspring.exception.RecordNotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CourseServiceTest {

    @Autowired
    private CourseService service;

    @PersistenceContext
    private EntityManager entityManager;

    private CourseDTO createCourse() {
        return new CourseDTO(
                null, "JUnit 5", CategoryEnum.BACK_END.getValue(),
                List.of(new LessonDTO(null, "Test Course", "1234567890"))
        );
    }

    @Test
    void shouldCreateWithSuccess() {
        // Create a record
        CourseDTO dto = this.createCourse();
        CourseDTO response = service.create(dto);
        assertAll(
                () -> assertNotNull(response.Id()),
                () -> assertEquals("JUnit 5", response.name()),
                () -> assertEquals(CategoryEnum.BACK_END.getValue(), response.category()),
                () -> assertFalse(response.lessons().isEmpty())
        );
    }

    @Test
    @Transactional
    void shouldFindAllWithSuccess() {
        // Create a record
        CourseDTO dto = this.createCourse();
        service.create(dto);

        CoursePageDTO response = service.list(0, 10);
        assertFalse(response.courses().isEmpty());
    }

    @Test
    void shouldRecordNotFindById() {
        assertThrows(RecordNotFoundException.class, () -> service.findById(9999L));
    }

    @Test
    @Transactional
    void shouldUpdateWithSuccess() {
        // Create a record
        CourseDTO dto = this.createCourse();
        CourseDTO request = service.create(dto);

        entityManager.flush(); // Forçar o flush para garantir que as alterações sejam persistidas imediatamente
        entityManager.clear(); // Evitar que dados fiquem apenas no contexto de persistência

        CourseDTO response = service.update(request.Id(), new CourseDTO(
                null, "Alterado", CategoryEnum.BACK_END.getValue(),
                List.of(new LessonDTO(null, "Test Course", "1234567890"))
        ));

        assertAll(
                () -> assertEquals(request.Id(), response.Id()),
                () -> assertEquals("Alterado", response.name())
        );
    }

    @Test
    void shouldUpdateWithRecordNotFound() {
        assertThrows(RecordNotFoundException.class, () -> {
            service.update(9999L, this.createCourse());
        });
    }


    @Test
    void shouldDeleteWithSuccess() {
        // Create a record
        CourseDTO dto = this.createCourse();
        CourseDTO response = service.create(dto);

        assertDoesNotThrow(() -> service.delete(response.Id()));
    }

    @Test
    void shouldRecordNotFoundExceptionTest() {
        RecordNotFoundException exception = assertThrows(RecordNotFoundException.class, () -> service.delete(500L));
        assertEquals("Registro não encontrado com o id: 500", exception.getMessage());
    }

}