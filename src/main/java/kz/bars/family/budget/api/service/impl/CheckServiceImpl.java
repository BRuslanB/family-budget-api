package kz.bars.family.budget.api.service.impl;

import kz.bars.family.budget.api.dto.CheckDto;
import kz.bars.family.budget.api.mapper.ActorMapper;
import kz.bars.family.budget.api.mapper.ExpenseMapper;
import kz.bars.family.budget.api.mapper.IncomeMapper;
import kz.bars.family.budget.api.model.*;
import kz.bars.family.budget.api.payload.request.CheckObjectRequest;
import kz.bars.family.budget.api.payload.request.CheckRequest;
import kz.bars.family.budget.api.repository.*;
import kz.bars.family.budget.api.service.CheckService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class CheckServiceImpl implements CheckService {

    final CheckRepo checkRepo;
    final IncomeRepo incomeRepo;
    final IncomeMapper incomeMapper;
    final ExpenseRepo expenseRepo;
    final ExpenseMapper expenseMapper;
    final ActorRepo actorRepo;
    final ActorMapper actorMapper;
    final BudgetRepo budgetRepo;

    @Override
    public CheckDto getCheckDto(Long id) {

        Check check = checkRepo.findById(id).orElse(null);
        CheckDto checkDto = new CheckDto();

        if (check != null) {
            checkDto.setId(check.getId());
            checkDto.setVal(check.getVal());
            checkDto.setDate(check.getDate());
            checkDto.setNote(check.getNote());
            checkDto.setIncome(incomeMapper.toDto(check.getIncome()));
            checkDto.setExpense(expenseMapper.toDto(check.getExpense()));
            checkDto.setActor(actorMapper.toDto(check.getActor()));
            checkDto.setObject(check.getObjectId());

            log.debug("!Getting a Check, id={}", id);

        } else {

            log.error("!Check not found: id={}", id);
        }

        return checkDto;
    }

    @Override
    public CheckDto addCheckDto(CheckDto checkDto) {

        Check check = new Check();
        check.setVal(checkDto.getVal());
        check.setDate(checkDto.getDate());
        check.setNote(checkDto.getNote());
        check.setObjectId(checkDto.getObject());

        try {
            if (checkDto.getIncome() != null) {
                incomeRepo.findById(checkDto.getIncome().getId()).ifPresent(check::setIncome);
            }
            if (checkDto.getExpense() != null) {
                expenseRepo.findById(checkDto.getExpense().getId()).ifPresent(check::setExpense);
            }
            actorRepo.findById(checkDto.getActor().getId()).ifPresent(check::setActor);

            checkRepo.save(check);
            updateBudget(checkDto.getDate());

            log.debug("!Check added, val={}, date={}, note={}, actor={}, income={}, expense={}, object={}",
                    checkDto.getVal(), checkDto.getDate(), checkDto.getNote(), checkDto.getActor().getId(),
                    checkDto.getIncome() != null ? checkDto.getIncome().getId() : null,
                    checkDto.getExpense() != null ? checkDto.getExpense().getId() : null,
                    checkDto.getObject());

            return checkDto;

        } catch (Exception ex) {

            log.error("!Check not added, val={}, date={}, note={}, actor={}, income={}, expense={}, object={}",
                    checkDto.getVal(), checkDto.getDate(), checkDto.getNote(), checkDto.getActor().getId(),
                    checkDto.getIncome() != null ? checkDto.getIncome().getId() : null,
                    checkDto.getExpense() != null ? checkDto.getExpense().getId() : null,
                    checkDto.getObject());

            return null;
        }
    }

    @Override
    public CheckRequest updateCheckRequest(CheckRequest checkRequest) {

        try {
            Check check = checkRepo.findById(checkRequest.getId()).orElseThrow();
            check.setVal(checkRequest.getVal());
            check.setDate(checkRequest.getDate());
            check.setNote(checkRequest.getNote());

            try {
                Income income = incomeRepo.findById(checkRequest.getIncome().getId()).orElseThrow();
                check.setIncome(income);
            } catch (Exception ex) {
                check.setIncome(null);
            }

            try {
                Expense expense = expenseRepo.findById(checkRequest.getExpense().getId()).orElseThrow();
                check.setExpense(expense);
            } catch (Exception ex) {
                check.setExpense(null);
            }

            actorRepo.findById(checkRequest.getActor().getId()).ifPresent(check::setActor);

            checkRepo.save(check);
            updateBudget();

            log.debug("!Check updated successfully, val={}, date={}, note={}, actor={}, income={}, expense={}",
                    checkRequest.getVal(), checkRequest.getDate(), checkRequest.getNote(), checkRequest.getActor().getId(),
                    checkRequest.getIncome() != null ? checkRequest.getIncome().getId() : null,
                    checkRequest.getExpense() != null ? checkRequest.getExpense().getId() : null);

            return checkRequest;

        } catch (Exception ex) {

            log.error("!Check not updated, val={}, date={}, note={}, actor={}, income={}, expense={}",
                    checkRequest.getVal(), checkRequest.getDate(), checkRequest.getNote(), checkRequest.getActor().getId(),
                    checkRequest.getIncome() != null ? checkRequest.getIncome().getId() : null,
                    checkRequest.getExpense() != null ? checkRequest.getExpense().getId() : null);

            return null;
        }
    }

    @Override
    public CheckObjectRequest updateCheckObjectRequest(CheckObjectRequest checkObjectRequest) {

        try {
            Check check = checkRepo.findById(checkObjectRequest.getId()).orElseThrow();
            check.setObjectId(checkObjectRequest.getObject());

            checkRepo.save(check);

            log.debug("!Check Object updated successfully id={}, object={}",
                    checkObjectRequest.getId(), checkObjectRequest.getObject());

            return checkObjectRequest;

        } catch (Exception ex) {

            log.error("!Check Object not updated id={}, object={}",
                    checkObjectRequest.getId(), checkObjectRequest.getObject());

            return null;
        }
    }

    @Override
    public Long deleteCheckDto(Long id) {

        try {
            Check check = checkRepo.findById(id).orElseThrow();

            checkRepo.deleteById(id);
            updateBudget(check.getDate());

            log.debug("!Check removed, id={}", id);
            return id;

        } catch (Exception ex) {

            log.error("!Check not removed, id={}", id);
            return null;
        }
    }

    private void updateBudget(LocalDate checkDate) {

        List<Check> checkList = checkRepo.findAllByDate(checkDate);
        Budget budget = budgetRepo.findByDate(checkDate);

        var sum = 0.0;
        for (Check check : checkList) {
            //Count Value
            if (check.getIncome() != null && check.getExpense() == null ||
                check.getIncome() == null && check.getExpense() != null)
                sum += check.getVal();
        }

        if (budget == null) {
            budget = new Budget();
            budget.setSum(sum);
            budget.setDate(checkDate);
        } else {
            budget.setSum(sum);
        }

        try {
            budgetRepo.save(budget);
            log.debug("!Budget updated successfully, id={}, sum={}, date={}",
                    budget.getId(), budget.getSum(), budget.getDate());

        } catch (Exception ex) {

            log.error("!Budget updated error");
        }
    }

    public void updateBudget() {

        //Find all unique entries by date
        List<Check> checkResultList = checkRepo.findDistinctByDate();
        budgetRepo.deleteAll();

        for (Check checkRes : checkResultList) {

            List<Check> checkList = checkRepo.findAllByDate(checkRes.getDate());
            var sum = 0.0;

            for (Check check : checkList) {
                //Count Value
                if (check.getIncome() != null && check.getExpense() == null ||
                    check.getIncome() == null && check.getExpense() != null)
                    sum += check.getVal();
            }

            Budget budget = new Budget();
            budget.setSum(sum);
            budget.setDate(checkRes.getDate());

            try {
                budgetRepo.save(budget);
                log.debug("!Budget updated successfully, id={}, sum={}, date={}",
                        budget.getId(), budget.getSum(), budget.getDate());

            } catch (Exception ex) {

                log.error("!Budget updated error");
            }
        }
    }

    @Override
    public List<CheckDto> getAllCheckDto() {

        List<Check> checkList;
        checkList = checkRepo.findAll(Sort.by(Sort.Order.by("date")));

        List<CheckDto> checkDtoList = new ArrayList<>();

        for (Check check : checkList) {
            CheckDto checkDto = new CheckDto();
            checkDto.setId(check.getId());
            if (check.getIncome() != null && check.getExpense() == null ||
                check.getIncome() == null && check.getExpense() != null)
                checkDto.setVal(check.getVal());
            else
                checkDto.setVal(null);

            checkDto.setDate(check.getDate());
            checkDto.setNote(check.getNote());
            checkDto.setIncome(incomeMapper.toDto(check.getIncome()));
            checkDto.setExpense(expenseMapper.toDto(check.getExpense()));
            checkDto.setActor(actorMapper.toDto(check.getActor()));
            checkDto.setObject(check.getObjectId());
            //Add to checkDtoList
            checkDtoList.add(checkDto);
        }
        log.debug("!Getting a list of All Checks");

        return checkDtoList;
    }

    @Override
    public List<CheckDto> getAllCheckDtoBetweenDate(LocalDate dateFrom, LocalDate dateTo) {

        List<Check> checkList;
        checkList = checkRepo.findAllByDateBetweenOrderByDate(dateFrom, dateTo);

        List<CheckDto> checkDtoList = new ArrayList<>();
        CheckDto checkDto;
        for (Check check : checkList) {
            checkDto = new CheckDto();
            checkDto.setId(check.getId());
            if (check.getIncome() != null && check.getExpense() == null ||
                check.getIncome() == null && check.getExpense() != null)
                checkDto.setVal(check.getVal());
            else
                checkDto.setVal(null);

            checkDto.setDate(check.getDate());
            checkDto.setNote(check.getNote());
            checkDto.setIncome(incomeMapper.toDto(check.getIncome()));
            checkDto.setExpense(expenseMapper.toDto(check.getExpense()));
            checkDto.setActor(actorMapper.toDto(check.getActor()));
            checkDto.setObject(check.getObjectId());
            //Add to checkDtoList
            checkDtoList.add(checkDto);
        }
        log.debug("!Getting a list of Checks for the period from {} to {}", dateFrom, dateTo);

        return checkDtoList;
    }

    @Override
    public List<CheckDto> getAllCheckDtoByIncomeId(Long id) {

        List<Check> checkList = checkRepo.findAllByIncomeIdOrderByDate(id);
        List<CheckDto> checkDtoList = new ArrayList<>();
        CheckDto checkDto;

        for (Check check : checkList) {
            checkDto = new CheckDto();
            checkDto.setId(check.getId());
            if (check.getIncome() != null && check.getExpense() == null ||
                check.getIncome() == null && check.getExpense() != null)
                checkDto.setVal(check.getVal());
            else
                checkDto.setVal(null);

            checkDto.setDate(check.getDate());
            checkDto.setNote(check.getNote());
            checkDto.setIncome(incomeMapper.toDto(check.getIncome()));
            checkDto.setObject(check.getObjectId());
            //Add to checkDtoList
            checkDtoList.add(checkDto);
        }
        log.debug("!Getting a list of Checks for a given Income, id={}", id);

        return checkDtoList;
    }

    @Override
    public List<CheckDto> getAllCheckDtoByIncomeBetweenDate(Long id, LocalDate dateFrom, LocalDate dateTo) {

        List<Check> checkList = checkRepo.findAllByIncomeIdAndDateBetweenOrderByDate(id, dateFrom, dateTo);
        List<CheckDto> checkDtoList = new ArrayList<>();
        CheckDto checkDto;

        for (Check check : checkList) {
            checkDto = new CheckDto();
            checkDto.setId(check.getId());
            if (check.getIncome() != null && check.getExpense() == null ||
                check.getIncome() == null && check.getExpense() != null)
                checkDto.setVal(check.getVal());
            else
                checkDto.setVal(null);

            checkDto.setDate(check.getDate());
            checkDto.setNote(check.getNote());
            checkDto.setIncome(incomeMapper.toDto(check.getIncome()));
            checkDto.setObject(check.getObjectId());
            //Add to checkDtoList
            checkDtoList.add(checkDto);
        }
        log.debug("!Getting a list of Checks for a given Income, id={} from {} to {}", id, dateFrom, dateTo);

        return checkDtoList;
    }

    @Override
    public List<CheckDto> getAllCheckDtoByExpenseId(Long id) {

        List<Check> checkList = checkRepo.findAllByExpenseIdOrderByDate(id);
        List<CheckDto> checkDtoList = new ArrayList<>();
        CheckDto checkDto;

        for (Check check : checkList) {
            checkDto = new CheckDto();
            checkDto.setId(check.getId());
            if (check.getIncome() != null && check.getExpense() == null ||
                check.getIncome() == null && check.getExpense() != null)
                checkDto.setVal(check.getVal());
            else
                checkDto.setVal(null);

            checkDto.setDate(check.getDate());
            checkDto.setNote(check.getNote());
            checkDto.setExpense(expenseMapper.toDto(check.getExpense()));
            checkDto.setObject(check.getObjectId());
            //Add to checkDtoList
            checkDtoList.add(checkDto);
        }
        log.debug("!Getting a list of Checks for a given Expense, id={}", id);

        return checkDtoList;
    }

    @Override
    public List<CheckDto> getAllCheckDtoByExpenseBetweenDate(Long id, LocalDate dateFrom, LocalDate dateTo) {

        List<Check> checkList = checkRepo.findAllByExpenseIdAndDateBetweenOrderByDate(id, dateFrom, dateTo);
        List<CheckDto> checkDtoList = new ArrayList<>();
        CheckDto checkDto;

        for (Check check : checkList) {
            checkDto = new CheckDto();
            checkDto.setId(check.getId());
            if (check.getIncome() != null && check.getExpense() == null ||
                check.getIncome() == null && check.getExpense() != null)
                checkDto.setVal(check.getVal());
            else
                checkDto.setVal(null);

            checkDto.setDate(check.getDate());
            checkDto.setNote(check.getNote());
            checkDto.setExpense(expenseMapper.toDto(check.getExpense()));
            checkDto.setObject(check.getObjectId());
            //Add to checkDtoList
            checkDtoList.add(checkDto);
        }
        log.debug("!Getting a list of Checks for a given Expense, id={} from {} to {}", id, dateFrom, dateTo);

        return checkDtoList;
    }

    @Override
    public List<CheckDto> getAllCheckDtoByActorId(Long id) {

        List<Check> checkList = checkRepo.findAllByActorIdOrderByDate(id);
        List<CheckDto> checkDtoList = new ArrayList<>();
        CheckDto checkDto;

        for (Check check : checkList) {
            checkDto = new CheckDto();
            checkDto.setId(check.getId());
            if (check.getIncome() != null && check.getExpense() == null ||
                check.getIncome() == null && check.getExpense() != null)
                checkDto.setVal(check.getVal());
            else
                checkDto.setVal(null);

            checkDto.setDate(check.getDate());
            checkDto.setNote(check.getNote());
            checkDto.setIncome(incomeMapper.toDto(check.getIncome()));
            checkDto.setExpense(expenseMapper.toDto(check.getExpense()));
            checkDto.setActor(actorMapper.toDto(check.getActor()));
            checkDto.setObject(check.getObjectId());
            //Add to checkDtoList
            checkDtoList.add(checkDto);
        }
        log.debug("!Getting a list of Checks for a given Actor, id={}", id);

        return checkDtoList;
    }

    @Override
    public List<CheckDto> getAllCheckDtoByActorBetweenDate(Long id, LocalDate dateFrom, LocalDate dateTo) {

        List<Check> checkList = checkRepo.findAllByActorIdAndDateBetweenOrderByDate(id, dateFrom, dateTo);
        List<CheckDto> checkDtoList = new ArrayList<>();
        CheckDto checkDto;

        for (Check check : checkList) {
            checkDto = new CheckDto();
            checkDto.setId(check.getId());
            if (check.getIncome() != null && check.getExpense() == null ||
                check.getIncome() == null && check.getExpense() != null)
                checkDto.setVal(check.getVal());
            else
                checkDto.setVal(null);

            checkDto.setDate(check.getDate());
            checkDto.setNote(check.getNote());
            checkDto.setIncome(incomeMapper.toDto(check.getIncome()));
            checkDto.setExpense(expenseMapper.toDto(check.getExpense()));
            checkDto.setActor(actorMapper.toDto(check.getActor()));
            checkDto.setObject(check.getObjectId());
            //Add to checkDtoList
            checkDtoList.add(checkDto);
        }
        log.debug("!Getting a list of Checks for a given Actor, id={} from {} to {}", id, dateFrom, dateTo);

        return checkDtoList;
    }

    @Override
    public List<CheckDto> getAllCheckDtoByDate(LocalDate checkDate) {

        List<Check> checkList = checkRepo.findAllByDate(checkDate);
        List<CheckDto> checkDtoList = new ArrayList<>();
        CheckDto checkDto;

        for (Check check : checkList) {
            checkDto = new CheckDto();
            checkDto.setId(check.getId());
            if (check.getIncome() != null && check.getExpense() == null ||
                check.getIncome() == null && check.getExpense() != null)
                checkDto.setVal(check.getVal());
            else
                checkDto.setVal(null);

            checkDto.setDate(check.getDate());
            checkDto.setNote(check.getNote());
            checkDto.setIncome(incomeMapper.toDto(check.getIncome()));
            checkDto.setExpense(expenseMapper.toDto(check.getExpense()));
            checkDto.setActor(actorMapper.toDto(check.getActor()));
            checkDto.setObject(check.getObjectId());
            //Add to checkDtoList
            checkDtoList.add(checkDto);
        }
        log.debug("!Getting a list of Checks for a given Date, date={}", checkDate);

        return checkDtoList;
    }

}
