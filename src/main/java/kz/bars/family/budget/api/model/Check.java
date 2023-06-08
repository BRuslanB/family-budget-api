package kz.bars.family.budget.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "checks")
public class Check extends BaseEntity {

    @Column(nullable = false)
    private Double val;

    @Column(nullable = false)
    private LocalDate date;

    private String note;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "actor_id", foreignKey = @ForeignKey(name = "checks_actor_id_fk"))
    @JsonIgnore
    private Actor actor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="income_id", foreignKey = @ForeignKey(name = "checks_income_id_fk"))
    @JsonIgnore
    private Income income;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="expense_id", foreignKey = @ForeignKey(name = "checks_expense_id_fk"))
    @JsonIgnore
    private Expense expense;

    @Column(name = "object_id")
    private String objectId;

}
