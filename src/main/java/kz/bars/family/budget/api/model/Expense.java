package kz.bars.family.budget.api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
public class Expense extends BaseEntity {

    @Column(nullable = false)
    private String name;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", foreignKey = @ForeignKey(name = "expense_expense_category_id_fk"))
    private ExpenseCategory category;

    @OneToMany(mappedBy = "expense", cascade = CascadeType.REMOVE)
    private Set<Check> checks;

}
