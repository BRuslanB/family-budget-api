package kz.bars.family.budget.api.repository;

import kz.bars.family.budget.api.model.ExpenseCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface ExpenseCategoryRepo extends JpaRepository<ExpenseCategory, Long> {

}
