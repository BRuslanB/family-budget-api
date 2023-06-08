package kz.bars.family.budget.api.service.impl;

import kz.bars.family.budget.api.dto.ActorDto;
import kz.bars.family.budget.api.model.Actor;
import kz.bars.family.budget.api.model.Check;
import kz.bars.family.budget.api.model.Income;
import kz.bars.family.budget.api.repository.ActorRepo;
import kz.bars.family.budget.api.service.ActorService;
import kz.bars.family.budget.api.service.CheckService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Log4j2
public class ActorServiceImpl implements ActorService {

    final ActorRepo actorRepo;
    final CheckService checkService;

    @Override
    public ActorDto getActorDto(Long id) {

        Actor actor = actorRepo.findById(id).orElse(null);
        ActorDto actorDto = new ActorDto();

        if (actor != null) {
            actorDto.setId(actor.getId());
            actorDto.setName(actor.getName());
            actorDto.setDescription(actor.getDescription());
        }
        log.debug("!Getting an Actor, id={}", id);

        return actorDto;
    }

    @Override
    public ActorDto addActorDto(ActorDto actorDto) {

        try {
            Actor actor = new Actor();
            actor.setName(actorDto.getName());
            actor.setDescription(actorDto.getDescription());

            actorRepo.save(actor);
            log.debug("!Actor added successfully, name={}, description={}",
                    actorDto.getName(), actorDto.getDescription());
            return actorDto;

        } catch (Exception ex) {

            log.error("!Actor not added, name={}, description={}",
                    actorDto.getName(), actorDto.getDescription());
            return null;
        }
    }

    @Override
    public ActorDto updateActorDto(ActorDto actorDto) {

        try {
            Actor actor = actorRepo.findById(actorDto.getId()).orElseThrow();

            actor.setName(actorDto.getName());
            actor.setDescription(actorDto.getDescription());

            actorRepo.save(actor);
            log.debug("!Actor updated successfully, id={}, name={}, description={}",
                    actorDto.getId(), actorDto.getName(), actorDto.getDescription());
            return actorDto;

        } catch (Exception ex) {

            log.error("!Actor not updated, id={}, name={}, description={}",
                    actorDto.getId(), actorDto.getName(), actorDto.getDescription());
            return null;
        }
    }

    @Override
    public Long deleteActorDto(Long id) {

        try {
            Actor actor = actorRepo.findById(id).orElse(null);
            Set<Check> checks = actor.getChecks();

            actorRepo.deleteById(id);

            // Checking for related entries in checks
            if (!checks.isEmpty()) {
                // Update the budget for all checks
                checkService.updateBudget();
            }

            log.debug("!Actor removed, id={}", id);
            return id;

        } catch (Exception ex){

            log.error("!Actor not removed, id={}", id);
            return null;
        }
    }

    @Override
    public List<ActorDto> getAllActorDto() {

        List<Actor> actorList;
        actorList = actorRepo.findAll();

        List<ActorDto> actorDtoList = new ArrayList<>();

        for (Actor actor : actorList) {
            ActorDto actorDto = new ActorDto();
            actorDto.setId(actor.getId());
            actorDto.setName(actor.getName());
            actorDto.setDescription(actor.getDescription());
            //Add to actorDtoList
            actorDtoList.add(actorDto);
        }
        log.debug("!Getting a list of All Actors");

        return actorDtoList;
    }

}
