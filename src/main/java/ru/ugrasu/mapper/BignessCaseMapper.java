package ru.ugrasu.mapper;

import org.mapstruct.*;
import ru.ugrasu.dto.BignessCaseDto;
import ru.ugrasu.entity.BignessCase;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface BignessCaseMapper extends Mappable<BignessCase, BignessCaseDto> {
    BignessCase toEntity(BignessCaseDto bignessCaseDto);

    BignessCaseDto toDto(BignessCase bignessCase);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    BignessCase partialUpdate(BignessCaseDto bignessCaseDto, @MappingTarget BignessCase bignessCase);

    BignessCase updateWithNull(BignessCaseDto bignessCaseDto, @MappingTarget BignessCase bignessCase);
}