package kz.bars.family.budget.api.service.impl;

import kz.bars.family.budget.api.dto.IncomeDto;
import kz.bars.family.budget.api.dto.IncomeSumDto;
import kz.bars.family.budget.api.mapper.IncomeMapper;
import kz.bars.family.budget.api.model.Check;
import kz.bars.family.budget.api.model.Expense;
import kz.bars.family.budget.api.model.Income;
import kz.bars.family.budget.api.repository.IncomeRepo;
import kz.bars.family.budget.api.service.CheckService;
import kz.bars.family.budget.api.service.IncomeService;
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
public class IncomeServiceImpl implements IncomeService {

    final IncomeRepo incomeRepo;
    final IncomeMapper incomeMapper;
    final CheckService checkService;

    @Override
    public IncomeDto getIncomeDto(Long id) {
        Income income = incomeRepo.findById(id).orElse(null);
        log.debug("!Getting a Income, id={}", id);

        return incomeMapper.toDto(income);
    }

    @Override
    public List<IncomeDto> getAllIncomeDto() {

        List<Income> incomeList;
        incomeList = incomeRepo.findAll();

        List<IncomeDto> incomeDtoList = new ArrayList<>();
        IncomeDto incomeDto;
        for (Income income : incomeList) {
            incomeDto = new IncomeDto();
            incomeDto.setId(income.getId());
            incomeDto.setName(income.getName());
            incomeDto.setDescription(income.getDescription());
            //Add to incomeDtoList
            incomeDtoList.add(incomeDto);
        }
        log.debug("!Getting a list of All Incomes");

        return incomeDtoList;
    }

    @Override
    public IncomeDto addIncomeDto(IncomeDto incomeDto) {

        try {
            Income income = new Income();
            income.setName(incomeDto.getName());
            income.setDescription(incomeDto.getDescription());

            incomeRepo.save(income);
            log.debug("!Income added, name={}, description={}",
                    incomeDto.getName(), incomeDto.getDescription());

            return incomeDto;

        } catch (Exception ex) {

            log.error("!Income not added, name={}, description={}",
                    incomeDto.getName(), incomeDto.getDescription());

            return null;
        }
   }

    @Override
    public IncomeDto updateIncomeDto(IncomeDto incomeDto) {

        try {
            Income income = incomeRepo.findById(incomeDto.getId()).orElseThrow();
            income.setName(incomeDto.getName());
            income.setDescription(incomeDto.getDescription());

            incomeRepo.save(income);
            log.debug("!Income updated successfully, id={}, name={}, description={}",
                    incomeDto.getId(), incomeDto.getName(), incomeDto.getDescription());

            return incomeDto;

        } catch (Exception ex) {

            log.error("!Income not updated, id={}, name={}, description={}",
                    incomeDto.getId(), incomeDto.getName(), incomeDto.getDescription());

            return null;
        }
    }

    @Override
    public Long deleteIncomeDto(Long id) {

        try {
            Income income = incomeRepo.findById(id).orElse(null);
            Set<Check> checks = income.getChecks();

            incomeRepo.deleteById(id);

            // Checking for related entries in checks
            if (!checks.isEmpty()) {
                // Update the budget for all checks
                checkService.updateBudget();
            }

            log.debug("!Income removed, id={}", id);
            return id;

        } catch (Exception ex){

            log.error("!Income not removed, id={}", id);
            return null;
        }
    }

    @Override
    public List<IncomeSumDto> getAllIncomeSumDto() {

        List<Income> incomeList;
        incomeList = incomeRepo.findAll();

        List<IncomeSumDto> incomeSumDtoList = new ArrayList<>();
        IncomeSumDto incomeSumDto;
        for (Income income : incomeList) {
            incomeSumDto = new IncomeSumDto();
            incomeSumDto.setId(income.getId());
            incomeSumDto.setName(income.getName());
            incomeSumDto.setDescription(income.getDescription());
            //Count Value
            var sum = 0.0;
            for (Check check : income.getChecks()) {
                if (check.getExpense() == null) {
                    sum += check.getVal();
                }
            }
            incomeSumDto.setSumVal(Math.round(sum * 100.0) / 100.0);
            //Add to incomeDtoList
            incomeSumDtoList.add(incomeSumDto);
        }
        log.debug("!Getting a list of All Incomes with sum");

        return incomeSumDtoList;
    }

    @Override
    public List<IncomeSumDto> getAllIncomeSumDtoBetweenDate(LocalDate dateFrom, LocalDate dateTo) {

        List<Income> incomeList;
        incomeList = incomeRepo.findAllByChecksBetweenDateOrderByDate(dateFrom, dateTo);

        List<IncomeSumDto> incomeSumDtoList = new ArrayList<>();
        IncomeSumDto incomeSumDto;
        for (Income income : incomeList) {
            incomeSumDto = new IncomeSumDto();
            incomeSumDto.setId(income.getId());
            incomeSumDto.setName(income.getName());
            incomeSumDto.setDescription(income.getDescription());
            //Count Value
            var sum = 0.0;
            for (Check check : income.getChecks()) {
                if (check.getDate().compareTo(dateFrom) >= 0 && check.getDate().compareTo(dateTo) <= 0) {
                    if (check.getExpense() == null) {
                        sum += check.getVal();
                    }
                }
            }
            incomeSumDto.setSumVal(Math.round(sum * 100.0) / 100.0);
            //Add to incomeDtoList
            incomeSumDtoList.add(incomeSumDto);
        }
        log.debug("!Getting a list of Incomes with sum for the period from {} to {}", dateFrom, dateTo);

        return incomeSumDtoList;
    }

}
