package com.example.trabajofinal.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CourseDto {

    private String id;

    @NotNull
    private String name;

    @NotNull
    private String acronym;

    @NotNull
    private Boolean state;
}
