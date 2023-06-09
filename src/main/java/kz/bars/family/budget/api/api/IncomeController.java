package kz.bars.family.budget.api.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import kz.bars.family.budget.api.dto.IncomeDto;
import kz.bars.family.budget.api.dto.IncomeSumDto;
import kz.bars.family.budget.api.payload.response.MessageResponse;
import kz.bars.family.budget.api.service.IncomeService;
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
    public ResponseEntity<Object> getIncome(@Parameter(description = "'income' id")
                                            @PathVariable(name = "id") Long id) {

        log.debug("!Call method getting a Income");

        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {

            IncomeDto incomeDto = incomeService.getIncomeDto(id);
            if (incomeDto != null) {
                return ResponseEntity.ok(incomeDto);
            }
        }
        return new ResponseEntity<>(new MessageResponse("Income not found"), HttpStatus.BAD_REQUEST);
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    @Operation(description = "Getting a list of All Incomes")
    public ResponseEntity<Object> getAllIncome() {

        log.debug("!Getting a list of All Incomes");

        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {

            List<IncomeDto> incomeDto = incomeService.getAllIncomeDto();
            return ResponseEntity.ok(incomeDto);
        }
        return new ResponseEntity<>(new MessageResponse("Income list not found"), HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "sum")
    @PreAuthorize("isAuthenticated()")
    @Operation(description = "Getting a list of All Incomes with sum")
    public ResponseEntity<Object> getAllIncomeSum() {

        log.debug("!Getting a list of All Incomes with sum");

        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {

            List<IncomeSumDto> incomeSumDto = incomeService.getAllIncomeSumDto();
            return ResponseEntity.ok(incomeSumDto);
        }
        return new ResponseEntity<>(new MessageResponse("Income with sum list not found"), HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "dates/{date1}/{date2}")
    @PreAuthorize("isAuthenticated()")
    @Operation(description = "Getting a list of Incomes with sum for the period from.. to..")
    public ResponseEntity<Object> getAllIncomeSumBetweenDate(@Parameter(description = "date 'from'")
                                                          @PathVariable(name = "date1") LocalDate dateFrom,
                                                          @Parameter(description = "date 'to'")
                                                          @PathVariable(name = "date2") LocalDate dateTo) {

        log.debug("!Getting a list of Incomes with sum for the period");

        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {

            List<IncomeSumDto> incomeSumDto = incomeService.getAllIncomeSumDtoBetweenDate(dateFrom, dateTo);
            return ResponseEntity.ok(incomeSumDto);
        }
        return new ResponseEntity<>(new MessageResponse("Income with sum list for the period not found"), HttpStatus.BAD_REQUEST);
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(description = "Income added")
    public ResponseEntity<Object> addIncome(@RequestBody IncomeDto incomeDto) {

        log.debug("!Call method Income added");

        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .anyMatch(role -> role.equals("ROLE_ADMIN"))) { // contains("ROLE_ADMIN")

                if (incomeService.addIncomeDto(incomeDto) != null) {
                    return new ResponseEntity<>(new MessageResponse("Income added successfully!"), HttpStatus.CREATED);
                }

            } else {
                return new ResponseEntity<>(new MessageResponse("Access denied"), HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>(new MessageResponse("Income not added"), HttpStatus.BAD_REQUEST);
    }

    @PutMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(description = "Income updated")
    public ResponseEntity<Object> updateIncome(@RequestBody IncomeDto incomeDto) {

        log.debug("!Call method Income updated");

        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .anyMatch(role -> role.equals("ROLE_ADMIN"))) { // contains("ROLE_ADMIN")

                if (incomeService.updateIncomeDto(incomeDto) != null) {
                    return ResponseEntity.ok(new MessageResponse("Income updated successfully!"));
                }

            } else {
                return new ResponseEntity<>(new MessageResponse("Access denied"), HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>(new MessageResponse("Income not updated"), HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping(value = "{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(description = "Income.. removed")
    public ResponseEntity<Object> deleteIncome(@Parameter(description = "'income' id")
                                               @PathVariable(name = "id") Long id) {

        log.debug("!Call method Income removed");

        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .anyMatch(role -> role.equals("ROLE_ADMIN"))) { // contains("ROLE_ADMIN")

                if (incomeService.deleteIncomeDto(id) != null) {
                    return ResponseEntity.ok(new MessageResponse("Income removed successfully!"));
                }

            } else {
                return new ResponseEntity<>(new MessageResponse("Access denied"), HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>(new MessageResponse("Income not removed"), HttpStatus.BAD_REQUEST);
    }

}
