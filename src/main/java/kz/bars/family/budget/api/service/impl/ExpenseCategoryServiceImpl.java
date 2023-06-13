package kz.bars.family.budget.api.service.impl;

import kz.bars.family.budget.api.dto.ExpenseCategoryDto;
import kz.bars.family.budget.api.mapper.ExpenseCategoryMapper;
import kz.bars.family.budget.api.model.ExpenseCategory;
import kz.bars.family.budget.api.repository.ExpenseCategoryRepo;
import kz.bars.family.budget.api.service.ExpenseCategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class ExpenseCategoryServiceImpl implements ExpenseCategoryService {

    final ExpenseCategoryRepo expenseCategoryRepo;
    final ExpenseCategoryMapper expenseCategoryMapper;

    @Override
    public ExpenseCategoryDto getExpenseCategoryDto(Long id) {

        ExpenseCategory expenseCategory = expenseCategoryRepo.findById(id).orElse(null);
        log.debug("!Getting a Expense Category, id={}", id);

        return expenseCategoryMapper.toDto(expenseCategory);
    }

    @Override
    public ExpenseCategoryDto addExpenseCategoryDto(ExpenseCategoryDto expenseCategoryDto) {

        try {
            ExpenseCategory expenseCategory = new ExpenseCategory();
            expenseCategory.setName(expenseCategoryDto.getName());
            expenseCategory.setDescription(expenseCategoryDto.getDescription());

            expenseCategoryRepo.save(expenseCategory);
            log.debug("!Expense Category added, name={}, description={}",
                    expenseCategoryDto.getName(), expenseCategoryDto.getDescription());
            return expenseCategoryDto;

        } catch (Exception ex) {

            log.error("!Expense Category not added, name={}, description={}",
                    expenseCategoryDto.getName(), expenseCategoryDto.getDescription());
            return null;
        }
    }

    @Override
    public ExpenseCategoryDto updateExpenseCategoryDto(ExpenseCategoryDto expenseCategoryDto) {

        try {
            ExpenseCategory expenseCategory = expenseCategoryRepo.findById(expenseCategoryDto.getId()).orElseThrow();
            expenseCategory.setName(expenseCategoryDto.getName());
            expenseCategory.setDescription(expenseCategoryDto.getDescription());

            expenseCategoryRepo.save(expenseCategory);
            log.debug("!Expense Category updated successfully id={}, name={}, description={}",
                    expenseCategoryDto.getId(), expenseCategoryDto.getName(), expenseCategoryDto.getDescription());
            return expenseCategoryDto;

        } catch (Exception ex) {

            log.error("!Expense Category not updated, id={}, name={}, description={}",
                    expenseCategoryDto.getId(), expenseCategoryDto.getName(), expenseCategoryDto.getDescription());
            return null;
        }
    }

    @Override
    public Long deleteExpenseCategoryDto(Long id) {

        try {
            expenseCategoryRepo.deleteById(id);
            log.debug("!Expense Category removed, id={}", id);
            return id;

        } catch (Exception ex) {

            log.error("!Expense Category not removed, id={}", id);
            return null;
        }
    }

    @Override
    public List<ExpenseCategoryDto> getAllExpenseCategoryDto() {

        List<ExpenseCategory> expenseCategoryList;
        expenseCategoryList = expenseCategoryRepo.findAll();

        List<ExpenseCategoryDto> expenseCategoryDtoList = new ArrayList<>();
        ExpenseCategoryDto expenseCategoryDto;

        for (ExpenseCategory expenseCategory : expenseCategoryList) {
            expenseCategoryDto = new ExpenseCategoryDto();
            expenseCategoryDto.setId(expenseCategory.getId());
            expenseCategoryDto.setName(expenseCategory.getName());
            expenseCategoryDto.setDescription(expenseCategory.getDescription());
            //Add to expenseDtoList
            expenseCategoryDtoList.add(expenseCategoryDto);
        }
        log.debug("!Getting a list of All Expense Categories");

        return expenseCategoryDtoList;
    }

}
