package kz.bars.family.budget.api.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import kz.bars.family.budget.api.dto.ExpenseCategoryDto;
import kz.bars.family.budget.api.service.ExpenseCategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/categories")
@CrossOrigin
@Log4j2
@SecurityRequirement(name = "family-budget-api")
@Tag(name = "Expense", description = "All methods for getting a list of Expense Categories")
public class ExpenseCategoryController {

    private final ExpenseCategoryService expenseCategoryService;

    @GetMapping(value = "{id}")
    @PreAuthorize("isAuthenticated()")
    @Operation(description = "Getting a Expense Category..")
    public ExpenseCategoryDto getExpenseCategory(@Parameter(description = "'expense category' id")
                                                 @PathVariable(name = "id") Long id) {
        log.debug("!Call method getting a Expense Category");
//        log.debug(SecurityContextHolder.getContext().getAuthentication().getName());
        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            return expenseCategoryService.getExpenseCategoryDto(id);
        }
        return null;
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    @Operation(description = "Getting a list of All Expense Categories")
    public List<ExpenseCategoryDto> getAllExpenseCategory() {
        log.debug("!Call method getting a list of All Expense Categories");
//        log.debug(SecurityContextHolder.getContext().getAuthentication().getName());
        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            return expenseCategoryService.getAllExpenseCategoryDto();
        }
        return null;
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(description = "Expense Category added")
    public ExpenseCategoryDto addExpenseCategory(@RequestBody ExpenseCategoryDto expenseCategoryDto) {
        log.debug("!Call method Expense Category added");
//        log.debug(SecurityContextHolder.getContext().getAuthentication().getName());
        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .anyMatch(role -> role.equals("ROLE_ADMIN"))) { // contains("ROLE_ADMIN")

                return expenseCategoryService.addExpenseCategoryDto(expenseCategoryDto);
            }
        }
        return null;
    }

    @PutMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(description = "Expense Category updated")
    public ExpenseCategoryDto updateExpenseCategory(@RequestBody ExpenseCategoryDto expenseCategoryDto) {
        log.debug("!Call method Expense Category updated");
//        log.debug(SecurityContextHolder.getContext().getAuthentication().getName());
        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .anyMatch(role -> role.equals("ROLE_ADMIN"))) { // contains("ROLE_ADMIN")

                return expenseCategoryService.updateExpenseCategoryDto(expenseCategoryDto);
            }
        }
        return null;
    }

    @DeleteMapping(value = "{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(description = "Expense Category.. removed")
    public Long deleteExpenseCategory(@Parameter(description = "'expense category' id")
                                      @PathVariable(name = "id") Long id) {
        log.debug("!Call method Expense Category removed");
//        log.debug(SecurityContextHolder.getContext().getAuthentication().getName());
        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .anyMatch(role -> role.equals("ROLE_ADMIN"))) { // contains("ROLE_ADMIN")

                return expenseCategoryService.deleteExpenseCategoryDto(id);
            }
        }
        return null;
    }

}
