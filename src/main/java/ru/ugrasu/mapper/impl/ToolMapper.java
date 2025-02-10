package ru.ugrasu.mapper.impl;

import org.mapstruct.*;
import ru.ugrasu.dto.ToolDto;
import ru.ugrasu.entity.Tool;
import ru.ugrasu.mapper.Mappable;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ToolMapper extends Mappable<Tool, ToolDto> {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Tool partialUpdate(ToolDto toolDto, @MappingTarget Tool tool);

    Tool updateWithNull(ToolDto toolDto, @MappingTarget Tool tool);
}