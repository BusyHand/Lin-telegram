package ru.ugrasu.mapper;

import java.util.List;

public interface Mappable<Entity, Dto> {

    Entity toEntity(Dto dto);

    Dto toDto(Entity entity);

    List<Dto> toDtoList(List<Entity> entities);

    List<Entity> toEntityList(List<Dto> dtos);
}
