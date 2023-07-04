package kz.bars.family.budget.api.service.impl;

import kz.bars.family.budget.api.dto.ActorDto;
import kz.bars.family.budget.api.dto.ActorSumDto;
import kz.bars.family.budget.api.dto.IncomeSumDto;
import kz.bars.family.budget.api.mapper.ActorMapper;
import kz.bars.family.budget.api.model.Actor;
import kz.bars.family.budget.api.model.Check;
import kz.bars.family.budget.api.model.Income;
import kz.bars.family.budget.api.repository.ActorRepo;
import kz.bars.family.budget.api.service.ActorService;
import kz.bars.family.budget.api.service.CheckService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Log4j2
public class ActorServiceImpl implements ActorService {

    final ActorRepo actorRepo;
    final ActorMapper actorMapper;
    final CheckService checkService;

    @Override
    public ActorDto getActorDto(Long id) {

        Actor actor = actorRepo.findById(id).orElse(null);

        if (actor != null) {
            log.debug("!Getting an Actor, id={}", id);
        } else {
            log.error("!Actor not found: id={}", id);
        }

        return actorMapper.toDto(actor);
    }

    @Override
    public List<ActorDto> getAllActorDto() {

        List<Actor> actorList;
        actorList = actorRepo.findAll();
        List<ActorDto> actorDtoList = new ArrayList<>();

        for (Actor actor : actorList) {
            //Add to actorDtoList
            actorDtoList.add(actorMapper.toDto(actor));
        }
        log.debug("!Getting a list of All Actors");

        return actorDtoList;
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

        } catch (Exception ex) {

            log.error("!Actor not removed, id={}", id);
            return null;
        }
    }

    @Override
    public List<ActorSumDto> getAllActorSumDto() {

        List<Actor> actorList;
        actorList = actorRepo.findAll();

        List<ActorSumDto> actorSumDtoList = new ArrayList<>();
        ActorSumDto actorSumDto;

        for (Actor actor : actorList) {
            actorSumDto = new ActorSumDto();
            actorSumDto.setId(actor.getId());
            actorSumDto.setName(actor.getName());
            actorSumDto.setDescription(actor.getDescription());
            //Count Value
            var sum = 0.0;
            for (Check check : actor.getChecks()) {
                if (check.getIncome() != null && check.getExpense() == null ||
                    check.getIncome() == null && check.getExpense() != null)
                    sum += check.getVal();
            }
            actorSumDto.setSumVal(Math.round(sum * 100.0) / 100.0);
            //Add to actorDtoList
            actorSumDtoList.add(actorSumDto);
        }
        log.debug("!Getting a list of All Actor with sum");

        return actorSumDtoList;
    }

    @Override
    public List<ActorSumDto> getAllActorSumDtoBetweenDate(LocalDate dateFrom, LocalDate dateTo) {

        List<Actor> actorList;
        actorList = actorRepo.findAllByChecksBetweenDateOrderByDate(dateFrom, dateTo);

        List<ActorSumDto> actorSumDtoList = new ArrayList<>();
        ActorSumDto actorSumDto;
        for (Actor actor : actorList) {
            actorSumDto = new ActorSumDto();
            actorSumDto.setId(actor.getId());
            actorSumDto.setName(actor.getName());
            actorSumDto.setDescription(actor.getDescription());
            //Count Value
            var sum = 0.0;
            for (Check check : actor.getChecks()) {
                if (check.getDate().compareTo(dateFrom) >= 0 && check.getDate().compareTo(dateTo) <= 0) {
                    if (check.getIncome() != null && check.getExpense() == null ||
                        check.getIncome() == null && check.getExpense() != null)
                        sum += check.getVal();
                }
            }
            actorSumDto.setSumVal(Math.round(sum * 100.0) / 100.0);
            //Add to actorDtoList
            actorSumDtoList.add(actorSumDto);
        }
        log.debug("!Getting a list of Actors with sum for the period from {} to {}", dateFrom, dateTo);

        return actorSumDtoList;
    }

}
