package kz.bars.family.budget.api.mapper;

import kz.bars.family.budget.api.dto.ActorDto;
import kz.bars.family.budget.api.model.Actor;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ActorMapper {

    ActorDto toDto(Actor actor);

    Actor toEntity(ActorDto actorDto);

}
