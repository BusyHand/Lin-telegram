package ru.ugrasu.dto;

import lombok.Value;

/**
 * DTO for {@link ru.ugrasu.entity.BignessCase}
 */
@Value
public class BignessCaseDto {
    Long id;
    String name;
    String description;
}