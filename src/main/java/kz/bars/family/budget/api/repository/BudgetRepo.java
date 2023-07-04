package kz.bars.family.budget.api.repository;

import kz.bars.family.budget.api.model.Actor;
import kz.bars.family.budget.api.model.Budget;
import kz.bars.family.budget.api.model.Check;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
@Transactional
public interface BudgetRepo extends JpaRepository<Budget, Long> {

    Budget findByDate (LocalDate date);

    @Query("SELECT b FROM Budget b WHERE b.date >= :date1 AND b.date <= :date2 ORDER BY b.date")
    List<Budget> findAllBudgetBetweenDateOrderByDate(LocalDate date1, LocalDate date2);

}
