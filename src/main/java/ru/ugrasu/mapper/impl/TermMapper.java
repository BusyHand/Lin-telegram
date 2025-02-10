package ru.ugrasu.mapper.impl;

import org.mapstruct.*;
import ru.ugrasu.dto.TermDto;
import ru.ugrasu.entity.Term;
import ru.ugrasu.mapper.Mappable;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface TermMapper extends Mappable<Term, TermDto> {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Term partialUpdate(TermDto termDto, @MappingTarget Term term);

    Term updateWithNull(TermDto termDto, @MappingTarget Term term);
}