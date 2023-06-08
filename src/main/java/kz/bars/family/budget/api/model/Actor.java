package kz.bars.family.budget.api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
public class Actor extends BaseEntity {

    @Column(nullable = false)
    private String name;

    private String description;

    @OneToMany(mappedBy = "actor", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<Check> checks;

    @OneToMany(mappedBy = "actor", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<Budget> budget;

}
