package ru.ugrasu.dto;

import lombok.Value;
import org.jetbrains.annotations.NotNull;

/**
 * DTO for {@link ru.ugrasu.entity.Term}
 */
@Value
public class TermDto {
    Long id;
    String name;
    String description;
}