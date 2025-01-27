package ru.ugrasu.mapper;

import org.mapstruct.*;
import ru.ugrasu.dto.ToolDto;
import ru.ugrasu.entity.Tool;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ToolMapper extends Mappable<Tool, ToolDto> {
    Tool toEntity(ToolDto toolDto);

    ToolDto toDto(Tool tool);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Tool partialUpdate(ToolDto toolDto, @MappingTarget Tool tool);

    Tool updateWithNull(ToolDto toolDto, @MappingTarget Tool tool);
}