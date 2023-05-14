package kz.bars.family.budget.api.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CheckDto {

    private Long id;
    private Double val;
    private LocalDate date;
    private String note;
    private IncomeDto income;
    private ExpenseDto expense;
    private ActorDto actor;
    private String object;

}
