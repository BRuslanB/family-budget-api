package kz.bars.family.budget.api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExpenseDto {

    private Long id;
    private String name;
    private String description;
    private ExpenseCategoryDto category;

}
