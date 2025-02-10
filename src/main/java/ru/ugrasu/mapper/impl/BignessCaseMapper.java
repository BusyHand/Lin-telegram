package ru.ugrasu.mapper.impl;

import org.mapstruct.*;
import ru.ugrasu.dto.BignessCaseDto;
import ru.ugrasu.entity.BignessCase;
import ru.ugrasu.mapper.Mappable;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface BignessCaseMapper extends Mappable<BignessCase, BignessCaseDto> {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    BignessCase partialUpdate(BignessCaseDto bignessCaseDto, @MappingTarget BignessCase bignessCase);

    BignessCase updateWithNull(BignessCaseDto bignessCaseDto, @MappingTarget BignessCase bignessCase);
}