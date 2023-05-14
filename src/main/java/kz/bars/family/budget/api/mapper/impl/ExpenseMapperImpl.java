package kz.bars.family.budget.api.mapper.impl;

import kz.bars.family.budget.api.dto.ExpenseDto;
import kz.bars.family.budget.api.dto.IncomeDto;
import kz.bars.family.budget.api.mapper.ExpenseCategoryMapper;
import kz.bars.family.budget.api.mapper.ExpenseMapper;
import kz.bars.family.budget.api.mapper.IncomeMapper;
import kz.bars.family.budget.api.model.Expense;
import kz.bars.family.budget.api.model.Income;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExpenseMapperImpl implements ExpenseMapper {

    final ExpenseCategoryMapper expenseCategoryMapper;

    @Override
    public ExpenseDto toDto(Expense expense) {

        if (expense == null) {
            return null;
        }
        ExpenseDto expenseDto = new ExpenseDto();
        expenseDto.setId(expense.getId());
        expenseDto.setName(expense.getName());
        expenseDto.setDescription(expense.getDescription());
        expenseDto.setCategory(expenseCategoryMapper.toDto(expense.getCategory()));

        return expenseDto;
    }

    @Override
    public Expense toEntity(ExpenseDto expenseDto) {

        if (expenseDto == null) {
            return null;
        }
        Expense expense = new Expense();
        expense.setId(expenseDto.getId());
        expense.setName(expenseDto.getName());
        expense.setDescription(expenseDto.getDescription());
        expense.setCategory(expenseCategoryMapper.toEntity(expenseDto.getCategory()));

        return expense;
    }

}
