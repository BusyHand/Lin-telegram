package ru.ugrasu.dto;

import lombok.Value;

/**
 * DTO for {@link ru.ugrasu.entity.Tool}
 */
@Value
public class ToolDto {
    Long id;
    String name;
    String description;
}