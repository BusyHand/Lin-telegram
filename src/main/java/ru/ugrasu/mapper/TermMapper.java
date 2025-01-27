package ru.ugrasu.mapper;

import org.mapstruct.*;
import ru.ugrasu.dto.TermDto;
import ru.ugrasu.entity.Term;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface TermMapper extends Mappable<Term, TermDto> {
    Term toEntity(TermDto termDto);

    TermDto toDto(Term term);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Term partialUpdate(TermDto termDto, @MappingTarget Term term);

    Term updateWithNull(TermDto termDto, @MappingTarget Term term);
}