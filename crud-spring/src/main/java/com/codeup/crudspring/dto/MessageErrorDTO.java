package com.codeup.crudspring.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.List;

public record MessageErrorDTO(
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss") LocalDateTime time,
        List<String> messages) {
}
