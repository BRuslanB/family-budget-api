package kz.bars.family.budget.api.repository;

import kz.bars.family.budget.api.model.Check;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
@Transactional
public interface CheckRepo extends JpaRepository<Check, Long> {

    List<Check> findAllByDateBetweenOrderByDate(LocalDate date1, LocalDate date2);
    List<Check> findAllByIncomeIdOrderByDate(Long id);
    List<Check> findAllByIncomeIdAndDateBetweenOrderByDate(Long id, LocalDate date1, LocalDate date2);
    List<Check> findAllByExpenseIdOrderByDate(Long id);
    List<Check> findAllByExpenseIdAndDateBetweenOrderByDate(Long id, LocalDate date1, LocalDate date2);
    List<Check> findAllByActorIdOrderByDate(Long id);
    List<Check> findAllByActorIdAndDateBetweenOrderByDate(Long id, LocalDate date1, LocalDate date2);
    List<Check> findAllByActorIdAndDate(Long id, LocalDate date);

}
