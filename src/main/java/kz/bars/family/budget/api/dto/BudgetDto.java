package kz.bars.family.budget.api.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class BudgetDto {

    private Long id;
    private Double sum;
    private LocalDate date;
    private ActorDto actor;

}
