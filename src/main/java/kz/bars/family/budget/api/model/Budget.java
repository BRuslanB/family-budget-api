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

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "actor_id", foreignKey = @ForeignKey(name = "budget_actor_id_fk"))
    private Actor actor;

}
