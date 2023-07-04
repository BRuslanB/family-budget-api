package kz.bars.family.budget.api.service;

import kz.bars.family.budget.api.dto.BudgetDto;

import java.time.LocalDate;
import java.util.List;

public interface BudgetService {
    List<BudgetDto> getAllBudgetDto();

    List<BudgetDto> getAllBudgetDtoBetweenDate(LocalDate dateFrom, LocalDate dateTo);

}
