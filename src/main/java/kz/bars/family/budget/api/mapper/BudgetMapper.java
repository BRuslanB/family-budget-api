package kz.bars.family.budget.api.mapper;

import kz.bars.family.budget.api.dto.BudgetDto;
import kz.bars.family.budget.api.model.Budget;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BudgetMapper {

    BudgetDto toDto(Budget budget);

    Budget toEntity(BudgetDto budgetDto);

}
