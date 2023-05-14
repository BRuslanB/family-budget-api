package kz.bars.family.budget.api.service;

import kz.bars.family.budget.api.dto.ExpenseDto;
import kz.bars.family.budget.api.dto.ExpenseSumDto;

import java.time.LocalDate;
import java.util.List;

public interface ExpenseService {
    ExpenseDto getExpenseDto(Long id);

    ExpenseDto addExpenseDto(ExpenseDto expenseDto);

    ExpenseDto updateExpenseDto(ExpenseDto expenseDto);

    Long deleteExpenseDto(Long id);

    List<ExpenseSumDto> getAllExpenseDto();

    List<ExpenseSumDto> getAllExpenseDtoBetweenDate(LocalDate dateFrom, LocalDate dateTo);

}
