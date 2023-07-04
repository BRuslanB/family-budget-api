package kz.bars.family.budget.api.service;

import kz.bars.family.budget.api.dto.ActorDto;
import kz.bars.family.budget.api.dto.ActorSumDto;

import java.time.LocalDate;
import java.util.List;

public interface ActorService {
    ActorDto getActorDto(Long id);

    List<ActorDto> getAllActorDto();

    ActorDto addActorDto(ActorDto actorDto);

    ActorDto updateActorDto(ActorDto actorDto);

    Long deleteActorDto(Long id);

    List<ActorSumDto> getAllActorSumDto();

    List<ActorSumDto> getAllActorSumDtoBetweenDate(LocalDate dateFrom, LocalDate dateTo);

}
