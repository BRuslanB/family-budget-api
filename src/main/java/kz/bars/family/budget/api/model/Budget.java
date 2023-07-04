package kz.bars.family.budget.api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Budget extends BaseEntity {

    @Column(nullable = false)
    private Double sum;

    @Column(nullable = false)
    private LocalDate date;

}
