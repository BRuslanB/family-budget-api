package kz.bars.family.budget.api.service;

import kz.bars.family.budget.api.dto.CheckDto;

import java.time.LocalDate;
import java.util.List;

public interface CheckService {
    CheckDto getCheckDto(Long id);
    CheckDto addCheckDto(CheckDto checkDto);
    CheckDto updateCheckDto(CheckDto checkDto);
    Long deleteCheckDto(Long id);
    List<CheckDto> getAllCheckDto();
    List<CheckDto> getAllCheckDtoBetweenDate(LocalDate dateFrom, LocalDate dateTo);
    List<CheckDto> getAllCheckDtoByIncomeId(Long id);
    List<CheckDto> getAllCheckDtoByIncomeBetweenDate(Long id, LocalDate dateFrom, LocalDate dateTo);
    List<CheckDto> getAllCheckDtoByExpenseId(Long id);
    List<CheckDto> getAllCheckDtoByExpenseBetweenDate(Long id, LocalDate dateFrom, LocalDate dateTo);
    List<CheckDto> getAllCheckDtoByActorId(Long id);
    List<CheckDto> getAllCheckDtoByActorBetweenDate(Long id, LocalDate dateFrom, LocalDate dateTo);

}
