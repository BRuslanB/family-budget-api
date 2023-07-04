package kz.bars.family.budget.api.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import kz.bars.family.budget.api.dto.BudgetDto;
import kz.bars.family.budget.api.payload.response.MessageResponse;
import kz.bars.family.budget.api.service.BudgetService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/budgets")
@CrossOrigin
@Log4j2
@SecurityRequirement(name = "family-budget-api")
@Tag(name = "Budget", description = "All methods for getting a list of Budgets")
public class BudgetController {

    private final BudgetService budgetService;

    @GetMapping(value = "dates/{date1}/{date2}")
    @Operation(description = "Getting a list of Budgets for the period from.. to..")
    public ResponseEntity<Object> getAllBudgetBetweenDate(@Parameter(description = "date 'from'")
                                                          @PathVariable(name = "date1") LocalDate dateFrom,
                                                          @Parameter(description = "date 'to'")
                                                          @PathVariable(name = "date2") LocalDate dateTo) {
        log.debug("!Call method getting a list of Budgets for the period");

        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {

            List<BudgetDto> budgetDtoList = budgetService.getAllBudgetDtoBetweenDate(dateFrom, dateTo);
            return ResponseEntity.ok(budgetDtoList);
        }
        return new ResponseEntity<>(new MessageResponse("Budget list for the period not found"), HttpStatus.BAD_REQUEST);
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    @Operation(description = "Getting a list of All Budgets")
    public ResponseEntity<Object> getAllBudget() {

        log.debug("!Call method getting a list of All Budgets");

        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {

            List<BudgetDto> budgetDtoList = budgetService.getAllBudgetDto();
            return ResponseEntity.ok(budgetDtoList);
        }
        return new ResponseEntity<>(new MessageResponse("Budget list not found"), HttpStatus.BAD_REQUEST);
    }

}
