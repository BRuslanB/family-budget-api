package kz.bars.family.budget.api.repository;

import kz.bars.family.budget.api.model.Budget;
import kz.bars.family.budget.api.model.Check;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
@Transactional
public interface BudgetRepo extends JpaRepository<Budget, Long> {

    Budget findByActorIdAndDate (Long id, LocalDate date);

}
