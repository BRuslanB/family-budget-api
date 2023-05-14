package kz.bars.family.budget.api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExpenseSumDto {

    private Long id;
    private String name;
    private String description;
    private ExpenseCategoryDto category;
    //The sum of all expenses on checks
    private Double sumVal;

}
