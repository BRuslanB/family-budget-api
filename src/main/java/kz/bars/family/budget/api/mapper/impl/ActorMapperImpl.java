package kz.bars.family.budget.api.mapper.impl;

import kz.bars.family.budget.api.dto.ActorDto;
import kz.bars.family.budget.api.mapper.ActorMapper;
import kz.bars.family.budget.api.model.Actor;
import org.springframework.stereotype.Component;

@Component
public class ActorMapperImpl implements ActorMapper {

    @Override
    public ActorDto toDto(Actor actor) {
        if (actor == null) {
            return null;
        }
        ActorDto actorDto = new ActorDto();
        actorDto.setId(actor.getId());
        actorDto.setName(actor.getName());
        actorDto.setDescription(actor.getDescription());

        return actorDto;
    }

    @Override
    public Actor toEntity(ActorDto actorDto) {
        if (actorDto == null) {
            return null;
        }
        Actor actor = new Actor();
        actor.setId(actorDto.getId());
        actor.setName(actorDto.getName());
        actor.setDescription(actorDto.getDescription());

        return actor;
    }

}
