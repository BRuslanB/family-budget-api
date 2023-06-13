package kz.bars.family.budget.api.service;

import kz.bars.family.budget.api.dto.CheckDto;
import kz.bars.family.budget.api.payload.request.CheckObjectRequest;
import kz.bars.family.budget.api.payload.request.CheckRequest;

import java.time.LocalDate;
import java.util.List;

public interface CheckService {
    CheckDto getCheckDto(Long id);
    CheckDto addCheckDto(CheckDto checkDto);
    CheckRequest updateCheckRequest(CheckRequest checkRequest);
    CheckObjectRequest updateCheckObjectRequest(CheckObjectRequest checkObjectRequest);
    Long deleteCheckDto(Long id);
    void updateBudget();
    List<CheckDto> getAllCheckDto();
    List<CheckDto> getAllCheckDtoBetweenDate(LocalDate dateFrom, LocalDate dateTo);
    List<CheckDto> getAllCheckDtoByIncomeId(Long id);
    List<CheckDto> getAllCheckDtoByIncomeBetweenDate(Long id, LocalDate dateFrom, LocalDate dateTo);
    List<CheckDto> getAllCheckDtoByExpenseId(Long id);
    List<CheckDto> getAllCheckDtoByExpenseBetweenDate(Long id, LocalDate dateFrom, LocalDate dateTo);
    List<CheckDto> getAllCheckDtoByActorId(Long id);
    List<CheckDto> getAllCheckDtoByActorBetweenDate(Long id, LocalDate dateFrom, LocalDate dateTo);

}
