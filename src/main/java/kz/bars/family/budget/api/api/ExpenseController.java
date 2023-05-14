package kz.bars.family.budget.api.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import kz.bars.family.budget.api.dto.ExpenseDto;
import kz.bars.family.budget.api.dto.ExpenseSumDto;
import kz.bars.family.budget.api.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/expenses")
@CrossOrigin
@Log4j2
@SecurityRequirement(name = "family-budget-api")
@Tag(name = "Expense", description = "All methods for getting a list of Expenses")
public class ExpenseController {

    final ExpenseService expenseService;

    @GetMapping(value = "{id}")
    @PreAuthorize("isAuthenticated()")
    @Operation(description = "Getting a Expense..")
    public ExpenseDto getExpense(@Parameter(description = "'expense' id")
                                 @PathVariable(name = "id") Long id) {
        log.debug("!Call method getting a Expense");
//        log.debug(SecurityContextHolder.getContext().getAuthentication().getName());
        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            return expenseService.getExpenseDto(id);
        }
        return null;
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    @Operation(description = "Getting a list of All Expenses")
    public List<ExpenseSumDto> getAllExpense() {
        log.debug("!Call method getting a list of All Expenses");
//        log.debug(SecurityContextHolder.getContext().getAuthentication().getName());
        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            return expenseService.getAllExpenseDto();
        }
        return null;
    }

    @GetMapping(value = "dates/{date1}/{date2}")
    @PreAuthorize("isAuthenticated()")
    @Operation(description = "Getting a list of Expenses for the period from.. to..")
    public List<ExpenseSumDto> getAllExpenseBetweenDate(@Parameter(description = "date 'from'")
                                                     @PathVariable(name = "date1") LocalDate dateFrom,
                                                     @Parameter(description = "date 'to'")
                                                     @PathVariable(name = "date2") LocalDate dateTo) {
        log.debug("!Call method getting a list of Expenses for the period");
//        log.debug(SecurityContextHolder.getContext().getAuthentication().getName());
        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            return expenseService.getAllExpenseDtoBetweenDate(dateFrom, dateTo);
        }
        return null;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')") //не работает почему то
    @Operation(description = "Expense added")
    public ExpenseDto addExpense(@RequestBody ExpenseDto expenseDto) {
        log.debug("!Call method Expense added");
//        log.debug(SecurityContextHolder.getContext().getAuthentication().getName());
        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .anyMatch(role -> role.equals("ROLE_ADMIN"))) { // contains("ROLE_ADMIN")

                return expenseService.addExpenseDto(expenseDto);
            }
        }
        return null;
    }

    @PutMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')") //не работает почему то
    @Operation(description = "Expense updated")
    public ExpenseDto updateExpense(@RequestBody ExpenseDto expenseDto) {
        log.debug("!Call method Expense updated");
//        log.debug(SecurityContextHolder.getContext().getAuthentication().getName());
        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .anyMatch(role -> role.equals("ROLE_ADMIN"))) { // contains("ROLE_ADMIN")

                return expenseService.updateExpenseDto(expenseDto);
            }
        }
        return null;
    }

    @DeleteMapping(value = "{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')") //не работает почему то
    @Operation(description = "Expense.. removed")
    public Long deleteExpense(@Parameter(description = "'expense' id")
                              @PathVariable(name = "id") Long id) {
        log.debug("!Call method Expense removed");
//        log.debug(SecurityContextHolder.getContext().getAuthentication().getName());
        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .anyMatch(role -> role.equals("ROLE_ADMIN"))) { // contains("ROLE_ADMIN")

                return expenseService.deleteExpenseDto(id);
            }
        }
        return null;
    }

}
