package kz.bars.family.budget.api.repository;

import kz.bars.family.budget.api.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
@Transactional
public interface ExpenseRepo extends JpaRepository<Expense, Long> {

    @Query("SELECT b FROM Expense b JOIN b.checks c WHERE c.date >= :date1 AND c.date <= :date2 ORDER BY c.date")
    List<Expense> findAllByChecksBetweenDateOrderByDate(LocalDate date1, LocalDate date2);

}
