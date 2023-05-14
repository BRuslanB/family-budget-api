package kz.bars.family.budget.api.service;

import kz.bars.family.budget.api.dto.ExpenseCategoryDto;

import java.util.List;

public interface ExpenseCategoryService {
    List<ExpenseCategoryDto> getAllExpenseCategoryDto();
    ExpenseCategoryDto getExpenseCategoryDto(Long id);
    ExpenseCategoryDto addExpenseCategoryDto(ExpenseCategoryDto expenseCategoryDto);
    ExpenseCategoryDto updateExpenseCategoryDto(ExpenseCategoryDto expenseCategoryDto);
    Long deleteExpenseCategoryDto(Long id);

}
