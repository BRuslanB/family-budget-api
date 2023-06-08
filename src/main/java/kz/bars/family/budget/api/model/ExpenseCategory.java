package kz.bars.family.budget.api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
public class ExpenseCategory extends BaseEntity {

    @Column(nullable = false)
    private String name;

    private String description;

    @OneToMany(mappedBy = "category")
    private Set<Expense> expenses;

}
