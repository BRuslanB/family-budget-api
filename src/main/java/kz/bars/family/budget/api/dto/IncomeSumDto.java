package kz.bars.family.budget.api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IncomeSumDto {

    private Long id;
    private String name;
    private String description;
    //The sum of all income on checks
    private Double sumVal;

}
