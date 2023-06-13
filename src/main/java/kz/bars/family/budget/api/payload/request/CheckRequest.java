package kz.bars.family.budget.api.payload.request;

import kz.bars.family.budget.api.dto.ActorDto;
import kz.bars.family.budget.api.dto.ExpenseDto;
import kz.bars.family.budget.api.dto.IncomeDto;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CheckRequest {

    private Long id;
    private Double val;
    private LocalDate date;
    private String note;
    private IncomeDto income;
    private ExpenseDto expense;
    private ActorDto actor;

}
