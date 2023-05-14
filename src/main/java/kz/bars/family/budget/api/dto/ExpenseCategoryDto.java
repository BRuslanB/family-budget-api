package kz.bars.family.budget.api.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseCategoryDto {

    private Long id;
    private String name;
    private String description;

}
