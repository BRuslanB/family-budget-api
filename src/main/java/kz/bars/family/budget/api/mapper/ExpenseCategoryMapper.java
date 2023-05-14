package kz.bars.family.budget.api.mapper;

import kz.bars.family.budget.api.dto.ExpenseCategoryDto;
import kz.bars.family.budget.api.model.ExpenseCategory;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ExpenseCategoryMapper {

    ExpenseCategoryDto toDto(ExpenseCategory expenseCategory);
    ExpenseCategory toEntity(ExpenseCategoryDto expenseCategoryDto);

}
