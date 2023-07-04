package kz.bars.family.budget.api.repository;

import kz.bars.family.budget.api.model.Actor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
@Transactional
public interface ActorRepo extends JpaRepository<Actor, Long> {

    @Query("SELECT b FROM Actor b JOIN b.checks c WHERE c.date >= :date1 AND c.date <= :date2 ORDER BY c.date")
    List<Actor> findAllByChecksBetweenDateOrderByDate(LocalDate date1, LocalDate date2);

}
