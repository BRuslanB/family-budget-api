package kz.bars.family.budget.api.mapper;

import kz.bars.family.budget.api.dto.ExpenseDto;
import kz.bars.family.budget.api.model.Expense;

public interface ExpenseMapper {

    ExpenseDto toDto(Expense expense);

    Expense toEntity(ExpenseDto expenseDto);

}
