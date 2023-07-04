package kz.bars.family.budget.api.service.impl;

import kz.bars.family.budget.api.dto.BudgetDto;
import kz.bars.family.budget.api.mapper.BudgetMapper;
import kz.bars.family.budget.api.model.Budget;
import kz.bars.family.budget.api.repository.BudgetRepo;
import kz.bars.family.budget.api.service.BudgetService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class BudgetServiceImpl implements BudgetService {

    final BudgetRepo budgetRepo;
    final BudgetMapper budgetMapper;

    @Override
    public List<BudgetDto> getAllBudgetDto() {

        List<Budget> budgetList = budgetRepo.findAll();
        List<BudgetDto> budgetDtoList = new ArrayList<>();

        for (Budget budget : budgetList) {
            //Add to budgetDtoList
            budgetDtoList.add(budgetMapper.toDto(budget));
        }
        log.debug("!Getting a list of All Budgets");

        return budgetDtoList;
    }

    @Override
    public List<BudgetDto> getAllBudgetDtoBetweenDate(LocalDate dateFrom, LocalDate dateTo) {

        List<Budget> budgetList = budgetRepo.findAllBudgetBetweenDateOrderByDate(dateFrom, dateTo);
        List<BudgetDto> budgetDtoList = new ArrayList<>();

        for (Budget budget : budgetList) {
            //Add to budgetDtoList
            budgetDtoList.add(budgetMapper.toDto(budget));
        }
        log.debug("!Getting a list of Budgets from {} to {}", dateFrom, dateTo);

        return budgetDtoList;
    }

}
