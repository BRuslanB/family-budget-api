package kz.bars.family.budget.api.mapper;

import kz.bars.family.budget.api.dto.IncomeDto;
import kz.bars.family.budget.api.model.Income;

public interface IncomeMapper {

    IncomeDto toDto(Income income);
    Income toEntity(IncomeDto incomeDto);

}
