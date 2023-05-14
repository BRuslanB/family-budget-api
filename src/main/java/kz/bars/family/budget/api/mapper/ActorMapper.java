package kz.bars.family.budget.api.mapper;

import kz.bars.family.budget.api.dto.ActorDto;
import kz.bars.family.budget.api.model.Actor;

public interface ActorMapper {

    ActorDto toDto(Actor actor);

    Actor toEntity(ActorDto actorDto);

}
