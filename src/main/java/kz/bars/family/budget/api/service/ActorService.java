package kz.bars.family.budget.api.service;

import kz.bars.family.budget.api.dto.ActorDto;

import java.util.List;

public interface ActorService {
    ActorDto getActorDto(Long id);
    ActorDto addActorDto(ActorDto actorDto);
    ActorDto updateActorDto(ActorDto actorDto);
    Long deleteActorDto(Long id);
    List<ActorDto> getAllActorDto();

}
