package kz.bars.family.budget.api.service;

import kz.bars.family.budget.api.dto.ExpenseDto;
import kz.bars.family.budget.api.dto.ExpenseSumDto;

import java.time.LocalDate;
import java.util.List;

public interface ExpenseService {
    ExpenseDto getExpenseDto(Long id);
    List<ExpenseDto> getAllExpenseDto();
    ExpenseDto addExpenseDto(ExpenseDto expenseDto);
    ExpenseDto updateExpenseDto(ExpenseDto expenseDto);
    Long deleteExpenseDto(Long id);
    List<ExpenseSumDto> getAllExpenseSumDto();
    List<ExpenseSumDto> getAllExpenseSumDtoBetweenDate(LocalDate dateFrom, LocalDate dateTo);

}
