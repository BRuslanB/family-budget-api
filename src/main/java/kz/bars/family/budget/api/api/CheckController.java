package kz.bars.family.budget.api.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import kz.bars.family.budget.api.dto.CheckDto;
import kz.bars.family.budget.api.service.CheckService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/checks")
@CrossOrigin
@Log4j2
@PreAuthorize("isAuthenticated()")
@SecurityRequirement(name = "family-budget-api")
@Tag(name = "Check", description = "All methods for getting a list of Checks")
public class CheckController {

    private final CheckService checkService;

    @GetMapping(value = "{id}")
    @Operation(description = "Getting a Check..")
    public CheckDto getCheck(@Parameter(description = "'check' id")
                             @PathVariable(name = "id") Long id) {
        log.debug("!Call method getting a Check");
//        log.debug(SecurityContextHolder.getContext().getAuthentication().getName());
        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            return checkService.getCheckDto(id);
        }
        return null;
    }

    @PostMapping
    @Operation(description = "Check added")
    public CheckDto addCheck(@RequestBody CheckDto checkDto) {
        log.debug("!Call method Check added");
//        log.debug(SecurityContextHolder.getContext().getAuthentication().getName());
        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            return checkService.addCheckDto(checkDto);
        }
        return null;
    }

    @PutMapping
    @Operation(description = "Check updated")
    public CheckDto updateCheck(@RequestBody CheckDto checkDto) {
        log.debug("!Call method Check updated");
//        log.debug(SecurityContextHolder.getContext().getAuthentication().getName());
        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            return checkService.updateCheckDto(checkDto);
        }
        return null;
    }

    @DeleteMapping(value = "{id}")
    @Operation(description = "Check.. removed")
    public Long deleteCheck(@Parameter(description = "'check' id")
                            @PathVariable(name = "id") Long id) {
        log.debug("!Call method Check removed");
//        log.debug(SecurityContextHolder.getContext().getAuthentication().getName());
        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            return checkService.deleteCheckDto(id);
        }
        return null;
    }

    @GetMapping()
    @Operation(description = "Getting a list of Checks")
    public List<CheckDto> getAllCheck() {
        log.debug("!Call method getting a list of All Checks");
//        log.debug(SecurityContextHolder.getContext().getAuthentication().getName());
        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            return checkService.getAllCheckDto();
        }
        return null;
    }

    @GetMapping(value = "dates/{date1}/{date2}")
    @Operation(description = "Getting a list of Checks for the period from.. to..")
    public List<CheckDto> getAllCheckBetweenDate(@Parameter(description = "date 'from'")
                                                 @PathVariable(name = "date1") LocalDate dateFrom,
                                                 @Parameter(description = "date 'to'")
                                                 @PathVariable(name = "date2") LocalDate dateTo) {
        log.debug("!Call method getting a list of Checks for the period");
//        log.debug(SecurityContextHolder.getContext().getAuthentication().getName());
        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            return checkService.getAllCheckDtoBetweenDate(dateFrom, dateTo);
        }
        return null;
    }

    @GetMapping(value = "incomes/{id}")
    @Operation(description = "Getting a list of Checks for a given Income..")
    public List<CheckDto> getAllCheckByIncomeId(@Parameter(description = "'income' id")
                                                @PathVariable(name = "id") Long id) {
        log.debug("!Call method getting a list of Checks for a given Income");
//        log.debug(SecurityContextHolder.getContext().getAuthentication().getName());
        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            return checkService.getAllCheckDtoByIncomeId(id);
        }
        return null;
    }

    @GetMapping(value = "incomes/{id}/dates/{date1}/{date2}")
    @Operation(description = "Getting a list of Checks for a given Income.. for the period from.. to..")
    public List<CheckDto> getAllCheckByIncomeBetweenDate(@Parameter(description = "'income' id")
                                                         @PathVariable(name = "id") Long id,
                                                         @Parameter(description = "date 'from'")
                                                         @PathVariable(name = "date1") LocalDate dateFrom,
                                                         @Parameter(description = "date 'to'")
                                                         @PathVariable(name = "date2") LocalDate dateTo) {
        log.debug("!Call method getting a list of Checks for a given Income for the period");
//        log.debug(SecurityContextHolder.getContext().getAuthentication().getName());
        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            return checkService.getAllCheckDtoByIncomeBetweenDate(id, dateFrom, dateTo);
        }
        return null;
    }

    @GetMapping(value = "expenses/{id}")
    @Operation(description = "Getting a list of Checks for a given Expense..")
    public List<CheckDto> getAllCheckByExpenseId(@Parameter(description = "'expense' id")
                                                 @PathVariable(name = "id") Long id) {
        log.debug("!Call method getting a list of Checks for a given Expense");
//        log.debug(SecurityContextHolder.getContext().getAuthentication().getName());
        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            return checkService.getAllCheckDtoByExpenseId(id);
        }
        return null;
    }

    @GetMapping(value = "expenses/{id}/dates/{date1}/{date2}")
    @Operation(description = "Getting a list of Checks for a given Expense.. for the period from.. to..")
    public List<CheckDto> getAllCheckByExpenseBetweenDate(@Parameter(description = "'expense' id")
                                                          @PathVariable(name = "id") Long id,
                                                          @Parameter(description = "date 'from'")
                                                          @PathVariable(name = "date1") LocalDate dateFrom,
                                                          @Parameter(description = "date 'to'")
                                                          @PathVariable(name = "date2") LocalDate dateTo) {
        log.debug("!Call method getting a list of Checks for a given Expense for the period");
//        log.debug(SecurityContextHolder.getContext().getAuthentication().getName());
        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            return checkService.getAllCheckDtoByExpenseBetweenDate(id, dateFrom, dateTo);
        }
        return null;
    }

    @GetMapping(value = "actors/{id}")
    @Operation(description = "Getting a list of Checks for a given Actor..")
    public List<CheckDto> getAllCheckByActorId(@Parameter(description = "'actor' id")
                                               @PathVariable(name = "id") Long id) {
        log.debug("!Call method getting a list of Checks for a given Actor");
//        log.debug(SecurityContextHolder.getContext().getAuthentication().getName());
        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            return checkService.getAllCheckDtoByActorId(id);
        }
        return null;
    }

    @GetMapping(value = "actors/{id}/dates/{date1}/{date2}")
    @Operation(description = "Getting a list of Checks for a given Actor.. for the period from.. to..")
    public List<CheckDto> getAllCheckByActorBetweenDate(@Parameter(description = "'expense' id")
                                                        @PathVariable(name = "id") Long id,
                                                        @Parameter(description = "date 'from'")
                                                        @PathVariable(name = "date1") LocalDate dateFrom,
                                                        @Parameter(description = "date 'to'")
                                                        @PathVariable(name = "date2") LocalDate dateTo) {
        log.debug("!Call method getting a list of Checks for a given Actor for the period");
//        log.debug(SecurityContextHolder.getContext().getAuthentication().getName());
        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            return checkService.getAllCheckDtoByActorBetweenDate(id, dateFrom, dateTo);
        }
        return null;
    }

}
