package kz.bars.family.budget.api.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import kz.bars.family.budget.api.dto.IncomeDto;
import kz.bars.family.budget.api.dto.IncomeSumDto;
import kz.bars.family.budget.api.service.IncomeService;
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
@RequestMapping(value = "/api/incomes")
@CrossOrigin
@Log4j2
@SecurityRequirement(name = "family-budget-api")
@Tag(name = "Income", description = "All methods for getting a list of Incomes")
public class IncomeController {

    private final IncomeService incomeService;

    @GetMapping(value = "{id}")
    @PreAuthorize("isAuthenticated()")
    @Operation(description = "Getting a Income..")
    public IncomeDto getIncome(@Parameter(description = "'income' id")
                               @PathVariable(name = "id") Long id) {
        log.debug("!Call method getting a Income");
//        log.debug(SecurityContextHolder.getContext().getAuthentication().getName());
        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            return incomeService.getIncomeDto(id);
        }
        return null;
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    @Operation(description = "Getting a list of All Incomes")
    public List<IncomeSumDto> getAllIncome() {
        log.debug("!Getting a list of All Incomes");
//        log.debug(SecurityContextHolder.getContext().getAuthentication().getName());
        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            return incomeService.getAllIncomeDto();
        }
        return null;
    }

    @GetMapping(value = "dates/{date1}/{date2}")
    @PreAuthorize("isAuthenticated()")
    @Operation(description = "Getting a list of Incomes for the period from.. to..")
    public List<IncomeSumDto> getAllIncomeBetweenDate(@Parameter(description = "date 'from'")
                                                   @PathVariable(name = "date1") LocalDate dateFrom,
                                                   @Parameter(description = "date 'to'")
                                                   @PathVariable(name = "date2") LocalDate dateTo) {
        log.debug("!Getting a list of Incomes for the period");
//        log.debug(SecurityContextHolder.getContext().getAuthentication().getName());
        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            return incomeService.getAllIncomeDtoBetweenDate(dateFrom, dateTo);
        }
        return null;
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(description = "Income added")
    public IncomeDto addIncome(@RequestBody IncomeDto incomeDto) {
        log.debug("!Call method Income added");
//        log.debug(SecurityContextHolder.getContext().getAuthentication().getName());
        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .anyMatch(role -> role.equals("ROLE_ADMIN"))) { // contains("ROLE_ADMIN")

                return incomeService.addIncomeDto(incomeDto);
            }
        }
        return null;
    }

    @PutMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(description = "Income updated")
    public IncomeDto updateIncome(@RequestBody IncomeDto incomeDto) {
        log.debug("!Call method Income updated");
//        log.debug(SecurityContextHolder.getContext().getAuthentication().getName());
        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .anyMatch(role -> role.equals("ROLE_ADMIN"))) { // contains("ROLE_ADMIN")

                return incomeService.updateIncomeDto(incomeDto);
            }
        }
        return null;
    }

    @DeleteMapping(value = "{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(description = "Income.. removed")
    public Long deleteIncome(@Parameter(description = "'income' id")
                             @PathVariable(name = "id") Long id) {
        log.debug("!Call method Income removed");
//        log.debug(SecurityContextHolder.getContext().getAuthentication().getName());
        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .anyMatch(role -> role.equals("ROLE_ADMIN"))) { // contains("ROLE_ADMIN")

                return incomeService.deleteIncomeDto(id);
            }
        }
        return null;
    }

}
