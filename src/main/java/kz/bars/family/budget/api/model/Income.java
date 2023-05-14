package kz.bars.family.budget.api.model;

import jakarta.persistence.*;
import kz.bars.family.budget.api.dto.IncomeDto;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
public class Income extends BaseEntity {

    @Column(nullable = false)
    private String name;

    private String description;

    @OneToMany(mappedBy = "income", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Check> checks;

}
