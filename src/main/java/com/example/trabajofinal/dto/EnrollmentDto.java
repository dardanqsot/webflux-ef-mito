package com.example.trabajofinal.dto;

import com.example.trabajofinal.model.Student;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EnrollmentDto {

    private String id;

    @NotNull
    private LocalDateTime enrollmentDate;

    @NotNull
    private Student student;

    @NotNull
    private List<EnrollmentDetailDto> enrollments;
}

