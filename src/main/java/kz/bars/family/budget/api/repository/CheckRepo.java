package kz.bars.family.budget.api.repository;

import kz.bars.family.budget.api.model.Check;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
@Transactional
public interface CheckRepo extends JpaRepository<Check, Long> {

    List<Check> findAllByDate(LocalDate date);
    List<Check> findAllByDateBetweenOrderByDate(LocalDate date1, LocalDate date2);
    List<Check> findAllByIncomeIdOrderByDate(Long id);
    List<Check> findAllByIncomeIdAndDateBetweenOrderByDate(Long id, LocalDate date1, LocalDate date2);
    List<Check> findAllByExpenseIdOrderByDate(Long id);
    List<Check> findAllByExpenseIdAndDateBetweenOrderByDate(Long id, LocalDate date1, LocalDate date2);
    List<Check> findAllByActorIdOrderByDate(Long id);
    List<Check> findAllByActorIdAndDateBetweenOrderByDate(Long id, LocalDate date1, LocalDate date2);
    @Query("SELECT c FROM Check c WHERE (c.date) IN (SELECT c2.date FROM Check c2 GROUP BY c2.date)")
    List<Check> findDistinctByDate();

}
