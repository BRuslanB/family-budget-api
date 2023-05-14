package kz.bars.family.budget.api.mapper.impl;

import kz.bars.family.budget.api.dto.IncomeDto;
import kz.bars.family.budget.api.mapper.IncomeMapper;
import kz.bars.family.budget.api.model.Income;
import org.springframework.stereotype.Component;

@Component
public class IncomeMapperImpl implements IncomeMapper {

    @Override
    public IncomeDto toDto(Income income) {
        if (income == null) {
            return null;
        }
        IncomeDto incomeDto = new IncomeDto();
        incomeDto.setId(income.getId());
        incomeDto.setName(income.getName());
        incomeDto.setDescription(income.getDescription());

        return incomeDto;
    }

    @Override
    public Income toEntity(IncomeDto incomeDto) {
        if (incomeDto == null) {
            return null;
        }
        Income income = new Income();
        income.setId(incomeDto.getId());
        income.setName(incomeDto.getName());
        income.setDescription(incomeDto.getDescription());

        return income;
    }

}
