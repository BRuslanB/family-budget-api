package kz.bars.family.budget.api.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import kz.bars.family.budget.api.dto.ExpenseDto;
import kz.bars.family.budget.api.dto.ExpenseSumDto;
import kz.bars.family.budget.api.response.MessageResponse;
import kz.bars.family.budget.api.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Object> getExpense(@Parameter(description = "'expense' id")
                                             @PathVariable(name = "id") Long id) {
        log.debug("!Call method getting a Expense");

        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {

            ExpenseDto expenseDto = expenseService.getExpenseDto(id);
            if (expenseDto != null) {
                return ResponseEntity.ok(expenseDto);
            }
        }
        return new ResponseEntity<>(new MessageResponse("Expense not found"), HttpStatus.BAD_REQUEST);
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    @Operation(description = "Getting a list of All Expenses")
    public ResponseEntity<Object> getAllExpense() {
        log.debug("!Call method getting a list of All Expenses");

        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {

            List<ExpenseSumDto> expenseSumDto = expenseService.getAllExpenseDto();
            return ResponseEntity.ok(expenseSumDto);
        }
        return new ResponseEntity<>(new MessageResponse("Expense list empty"), HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "dates/{date1}/{date2}")
    @PreAuthorize("isAuthenticated()")
    @Operation(description = "Getting a list of Expenses for the period from.. to..")
    public ResponseEntity<Object> getAllExpenseBetweenDate(@Parameter(description = "date 'from'")
                                                           @PathVariable(name = "date1") LocalDate dateFrom,
                                                           @Parameter(description = "date 'to'")
                                                           @PathVariable(name = "date2") LocalDate dateTo) {
        log.debug("!Call method getting a list of Expenses for the period");

        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {

            List<ExpenseSumDto> expenseSumDto = expenseService.getAllExpenseDtoBetweenDate(dateFrom, dateTo);
            return ResponseEntity.ok(expenseSumDto);
        }
        return new ResponseEntity<>(new MessageResponse("Expense list for the period empty"), HttpStatus.BAD_REQUEST);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')") //не работает почему то
    @Operation(description = "Expense added")
    public ResponseEntity<Object> addExpense(@RequestBody ExpenseDto expenseDto) {
        log.debug("!Call method Expense added");

        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .anyMatch(role -> role.equals("ROLE_ADMIN"))) { // contains("ROLE_ADMIN")

                if (expenseService.addExpenseDto(expenseDto) != null) {
                    return new ResponseEntity<>(new MessageResponse("Expense added successfully!"), HttpStatus.CREATED);
                }

            } else {
                return new ResponseEntity<>(new MessageResponse("Access denied"), HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>(new MessageResponse("Expense not added"), HttpStatus.BAD_REQUEST);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')") //не работает почему то
    @Operation(description = "Expense updated")
    public ResponseEntity<Object> updateExpense(@RequestBody ExpenseDto expenseDto) {
        log.debug("!Call method Expense updated");

        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .anyMatch(role -> role.equals("ROLE_ADMIN"))) { // contains("ROLE_ADMIN")

                if (expenseService.updateExpenseDto(expenseDto) != null) {
                    return ResponseEntity.ok(new MessageResponse("Expense updated successfully!"));
                }

            } else {
                return new ResponseEntity<>(new MessageResponse("Access denied"), HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>(new MessageResponse("Expense not updated"), HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping(value = "{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')") //не работает почему то
    @Operation(description = "Expense.. removed")
    public ResponseEntity<Object> deleteExpense(@Parameter(description = "'expense' id")
                                                @PathVariable(name = "id") Long id) {
        log.debug("!Call method Expense removed");

        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .anyMatch(role -> role.equals("ROLE_ADMIN"))) { // contains("ROLE_ADMIN")

                if (expenseService.deleteExpenseDto(id) != null) {
                    return ResponseEntity.ok(new MessageResponse("Expense removed successfully!"));
                }

            } else {
                return new ResponseEntity<>(new MessageResponse("Access denied"), HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>(new MessageResponse("Expense not removed"), HttpStatus.BAD_REQUEST);
    }

}
