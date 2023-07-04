package kz.bars.family.budget.api.service;

import kz.bars.family.budget.api.dto.IncomeDto;
import kz.bars.family.budget.api.dto.IncomeSumDto;

import java.time.LocalDate;
import java.util.List;

public interface IncomeService {
    IncomeDto getIncomeDto(Long id);
    List<IncomeDto> getAllIncomeDto();
    IncomeDto addIncomeDto(IncomeDto incomeDto);
    IncomeDto updateIncomeDto(IncomeDto incomeDto);
    Long deleteIncomeDto(Long id);
    List<IncomeSumDto> getAllIncomeSumDto();
    List<IncomeSumDto> getAllIncomeSumDtoBetweenDate(LocalDate dateFrom, LocalDate dateTo);

}
