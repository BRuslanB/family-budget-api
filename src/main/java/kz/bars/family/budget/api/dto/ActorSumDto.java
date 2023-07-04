package kz.bars.family.budget.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ActorSumDto {

    private Long id;
    private String name;
    private String description;
    //The sum of all actors on checks
    private Double sumVal;

}
