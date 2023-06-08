package kz.bars.family.budget.api.service.impl;

import kz.bars.family.budget.api.dto.ExpenseDto;
import kz.bars.family.budget.api.dto.ExpenseSumDto;
import kz.bars.family.budget.api.mapper.ExpenseCategoryMapper;
import kz.bars.family.budget.api.mapper.ExpenseMapper;
import kz.bars.family.budget.api.model.Check;
import kz.bars.family.budget.api.model.Expense;
import kz.bars.family.budget.api.repository.ExpenseRepo;
import kz.bars.family.budget.api.service.CheckService;
import kz.bars.family.budget.api.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Log4j2
public class ExpenseServiceImpl implements ExpenseService {

    final ExpenseRepo expenseRepo;
    final ExpenseMapper expenseMapper;
    final ExpenseCategoryMapper expenseCategoryMapper;
    final CheckService checkService;

    @Override
    public ExpenseDto getExpenseDto(Long id) {
        Expense expense = expenseRepo.findById(id).orElse(null);
        log.debug("!Getting a Expense, id={}", id);

        return expenseMapper.toDto(expense);
    }

    @Override
    public ExpenseDto addExpenseDto(ExpenseDto expenseDto) {

        try {
            Expense expense = new Expense();
            expense.setName(expenseDto.getName());
            expense.setDescription(expenseDto.getDescription());
            expense.setCategory(expenseCategoryMapper.toEntity(expenseDto.getCategory()));

            expenseRepo.save(expense);
            log.debug("!Expense added, name={}, description={}, category={}",
                    expenseDto.getName(), expenseDto.getDescription(),
                    expenseDto.getCategory() !=null ? expenseDto.getCategory().getId() : null);

            return expenseDto;

        } catch (Exception ex) {

            log.error("!Expense not added, name={}, description={}, category={}",
                    expenseDto.getName(), expenseDto.getDescription(),
                    expenseDto.getCategory() !=null ? expenseDto.getCategory().getId() : null);

            return null;
        }
    }

    @Override
    public ExpenseDto updateExpenseDto(ExpenseDto expenseDto) {

        try {
            Expense expense = expenseRepo.findById(expenseDto.getId()).orElseThrow();
            expense.setName(expenseDto.getName());
            expense.setDescription(expenseDto.getDescription());
            expense.setCategory(expenseCategoryMapper.toEntity(expenseDto.getCategory()));

            expenseRepo.save(expense);

            log.debug("!Expense updated successfully id={}, name={}, description={}, category={}",
                    expenseDto.getId(), expenseDto.getName(), expenseDto.getDescription(),
                    expenseDto.getCategory() !=null ? expenseDto.getCategory().getId() : null);

            return expenseDto;

        } catch (Exception ex) {

            log.error("!Expense not updated, id={}, name={}, description={}, category={}",
                    expenseDto.getId(), expenseDto.getName(), expenseDto.getDescription(),
                    expenseDto.getCategory() !=null ? expenseDto.getCategory().getId() : null);

            return null;
        }
    }

    @Override
    public Long deleteExpenseDto(Long id) {

        try {
            Expense expense = expenseRepo.findById(id).orElse(null);
            Set<Check> checks = expense.getChecks();

            expenseRepo.deleteById(id);

            // Checking for related entries in checks
            if (!checks.isEmpty()) {
                // Update the budget for all checks
                checkService.updateBudget();
            }

            log.debug("!Expense removed, id={}", id);
            return id;

        } catch (Exception ex){

            log.error("!Expense not removed, id={}", id);
            return null;
        }
    }

    @Override
    public List<ExpenseSumDto> getAllExpenseDto() {
        List<Expense> expenseList;
        expenseList = expenseRepo.findAll();

        List<ExpenseSumDto> expenseSumDtoList = new ArrayList<>();
        ExpenseSumDto expenseSumDto;
        for (Expense expense : expenseList) {
            expenseSumDto = new ExpenseSumDto();
            expenseSumDto.setId(expense.getId());
            expenseSumDto.setName(expense.getName());
            expenseSumDto.setDescription(expense.getDescription());
            expenseSumDto.setCategory(expenseCategoryMapper.toDto(expense.getCategory()));
            // Count Value
            var sum = 0.0;
            for (Check check : expense.getChecks()) {
                sum += check.getVal();
            }
            expenseSumDto.setSumVal(Math.round(sum * 100.0) / 100.0);
            //Add to expenseDtoList
            expenseSumDtoList.add(expenseSumDto);
        }
        log.debug("!Getting a list of All Expenses");

        return expenseSumDtoList;
    }

    @Override
    public List<ExpenseSumDto> getAllExpenseDtoBetweenDate(LocalDate dateFrom, LocalDate dateTo) {
        List<Expense> expenseList;
        expenseList = expenseRepo.findAllByChecksBetweenDateOrderByDate(dateFrom, dateTo);

        List<ExpenseSumDto> expenseSumDtoList = new ArrayList<>();
        ExpenseSumDto expenseSumDto;
        for (Expense expense : expenseList) {
            expenseSumDto = new ExpenseSumDto();
            expenseSumDto.setId(expense.getId());
            expenseSumDto.setName(expense.getName());
            expenseSumDto.setDescription(expense.getDescription());
            expenseSumDto.setCategory(expenseCategoryMapper.toDto(expense.getCategory()));
            // Count Value
            var sum = 0.0;
            for (Check check : expense.getChecks()) {
                if (check.getDate().compareTo(dateFrom) >= 0 && check.getDate().compareTo(dateTo) <= 0) {
                    sum += check.getVal();
                }
            }
            expenseSumDto.setSumVal(Math.round(sum * 100.0) / 100.0);
            //Add to expenseDtoList
            expenseSumDtoList.add(expenseSumDto);
        }
        log.debug("!Getting a list of Expenses for the period from {} to {}", dateFrom, dateTo);

        return expenseSumDtoList;
    }

}
