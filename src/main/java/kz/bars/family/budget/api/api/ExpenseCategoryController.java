package kz.bars.family.budget.api.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import kz.bars.family.budget.api.dto.ExpenseCategoryDto;
import kz.bars.family.budget.api.payload.response.MessageResponse;
import kz.bars.family.budget.api.service.ExpenseCategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@Tag(name = "Category", description = "All methods for getting a list of Expense Categories")
public class ExpenseCategoryController {

    private final ExpenseCategoryService expenseCategoryService;

    @GetMapping(value = "{id}")
    @PreAuthorize("isAuthenticated()")
    @Operation(description = "Getting a Expense Category..")
    public ResponseEntity<Object> getExpenseCategory(@Parameter(description = "'expense category' id")
                                                     @PathVariable(name = "id") Long id) {

        log.debug("!Call method getting a Expense Category");

        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {

            ExpenseCategoryDto expenseCategoryDto = expenseCategoryService.getExpenseCategoryDto(id);
            if (expenseCategoryDto != null) {
                return ResponseEntity.ok(expenseCategoryDto);
            }
        }
        return new ResponseEntity<>(new MessageResponse("Expense Category not found"), HttpStatus.BAD_REQUEST);
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    @Operation(description = "Getting a list of All Expense Categories")
    public ResponseEntity<Object> getAllExpenseCategory() {

        log.debug("!Call method getting a list of All Expense Categories");

        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {

            List<ExpenseCategoryDto> expenseCategoryDto = expenseCategoryService.getAllExpenseCategoryDto();
            return ResponseEntity.ok(expenseCategoryDto);
        }
        return new ResponseEntity<>(new MessageResponse("Expense Category list not found"), HttpStatus.BAD_REQUEST);
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(description = "Expense Category added")
    public ResponseEntity<Object> addExpenseCategory(@RequestBody ExpenseCategoryDto expenseCategoryDto) {

        log.debug("!Call method Expense Category added");

        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .anyMatch(role -> role.equals("ROLE_ADMIN"))) { // contains("ROLE_ADMIN")

                if (expenseCategoryService.addExpenseCategoryDto(expenseCategoryDto) != null) {
                    return new ResponseEntity<>(new MessageResponse("Expense Category added successfully!"), HttpStatus.CREATED);
                }

            } else {
                return new ResponseEntity<>(new MessageResponse("Access denied"), HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>(new MessageResponse("Expense Category not added"), HttpStatus.BAD_REQUEST);
    }

    @PutMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(description = "Expense Category updated")
    public ResponseEntity<Object> updateExpenseCategory(@RequestBody ExpenseCategoryDto expenseCategoryDto) {

        log.debug("!Call method Expense Category updated");

        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .anyMatch(role -> role.equals("ROLE_ADMIN"))) { // contains("ROLE_ADMIN")

                if (expenseCategoryService.updateExpenseCategoryDto(expenseCategoryDto) != null) {
                    return ResponseEntity.ok(new MessageResponse("Expense Category updated successfully!"));
                }

            } else {
                return new ResponseEntity<>(new MessageResponse("Access denied"), HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>(new MessageResponse("Expense Category not updated"), HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping(value = "{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(description = "Expense Category.. removed")
    public ResponseEntity<Object> deleteExpenseCategory(@Parameter(description = "'expense category' id")
                                                        @PathVariable(name = "id") Long id) {

        log.debug("!Call method Expense Category removed");

        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .anyMatch(role -> role.equals("ROLE_ADMIN"))) { // contains("ROLE_ADMIN")

                if (expenseCategoryService.deleteExpenseCategoryDto(id) != null) {
                    return ResponseEntity.ok(new MessageResponse("Expense Category removed successfully!"));
                }

            } else {
                return new ResponseEntity<>(new MessageResponse("Access denied"), HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>(new MessageResponse("Expense Category not removed"), HttpStatus.BAD_REQUEST);
    }

}
