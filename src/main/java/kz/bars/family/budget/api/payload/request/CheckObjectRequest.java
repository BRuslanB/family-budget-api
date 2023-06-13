package kz.bars.family.budget.api.payload.request;

import lombok.Data;

@Data
public class CheckObjectRequest {

    private Long id;
    private String object;

}
