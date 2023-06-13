package kz.bars.family.budget.api.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import kz.bars.family.budget.api.dto.CheckDto;
import kz.bars.family.budget.api.payload.request.CheckObjectRequest;
import kz.bars.family.budget.api.payload.request.CheckRequest;
import kz.bars.family.budget.api.payload.response.MessageResponse;
import kz.bars.family.budget.api.service.CheckService;
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
    public ResponseEntity<Object> getCheck(@Parameter(description = "'check' id")
                                           @PathVariable(name = "id") Long id) {
        log.debug("!Call method getting a Check");

        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {

            CheckDto checkDto = checkService.getCheckDto(id);
            if (checkDto != null) {
                return ResponseEntity.ok(checkDto);
            }
        }
        return new ResponseEntity<>(new MessageResponse("Check not found"), HttpStatus.BAD_REQUEST);
    }

    @PostMapping
    @Operation(description = "Check added")
    public ResponseEntity<Object> addCheck(@RequestBody CheckDto checkDto) {
        log.debug("!Call method Check added");

        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {

            if (checkService.addCheckDto(checkDto) != null) {
                return new ResponseEntity<>(new MessageResponse("Check added successfully!"), HttpStatus.CREATED);
            }

        } else {
            return new ResponseEntity<>(new MessageResponse("Access denied"), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new MessageResponse("Check not added"), HttpStatus.BAD_REQUEST);
    }

    @PutMapping
    @Operation(description = "Check updated without field Object")
    public ResponseEntity<Object> updateCheck(@RequestBody CheckRequest checkRequest) {
        log.debug("!Call method Check updated without field Object");

        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {

            if (checkService.updateCheckRequest(checkRequest) != null) {
                return ResponseEntity.ok(new MessageResponse("Check updated without field Object successfully!"));
            }

        } else {
            return new ResponseEntity<>(new MessageResponse("Access denied"), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new MessageResponse("Check not updated"), HttpStatus.BAD_REQUEST);
    }

    @PutMapping(value = "object")
    @Operation(description = "Check Object updated")
    public ResponseEntity<Object> updateCheckObject(@RequestBody CheckObjectRequest checkObjectRequest) {
        log.debug("!Call method Check Object updated");

        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {

            if (checkService.updateCheckObjectRequest(checkObjectRequest) != null) {
                return ResponseEntity.ok(new MessageResponse("Check Object updated successfully!"));
            }

        } else {
            return new ResponseEntity<>(new MessageResponse("Access denied"), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new MessageResponse("Check Object not updated"), HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping(value = "{id}")
    @Operation(description = "Check.. removed")
    public ResponseEntity<Object> deleteCheck(@Parameter(description = "'check' id")
                                              @PathVariable(name = "id") Long id) {
        log.debug("!Call method Check removed");

        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {

            if (checkService.deleteCheckDto(id) != null) {
                return ResponseEntity.ok(new MessageResponse("Check removed successfully!"));
            }

        } else {
            return new ResponseEntity<>(new MessageResponse("Access denied"), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new MessageResponse("Check not removed"), HttpStatus.BAD_REQUEST);
    }

    @GetMapping()
    @Operation(description = "Getting a list of Checks")
    public ResponseEntity<Object> getAllCheck() {
        log.debug("!Call method getting a list of All Checks");

        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {

            List<CheckDto> checkDtoList = checkService.getAllCheckDto();
            return ResponseEntity.ok(checkDtoList);
        }
        return new ResponseEntity<>(new MessageResponse("Check list not found"), HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "dates/{date1}/{date2}")
    @Operation(description = "Getting a list of Checks for the period from.. to..")
    public ResponseEntity<Object> getAllCheckBetweenDate(@Parameter(description = "date 'from'")
                                                         @PathVariable(name = "date1") LocalDate dateFrom,
                                                         @Parameter(description = "date 'to'")
                                                         @PathVariable(name = "date2") LocalDate dateTo) {
        log.debug("!Call method getting a list of Checks for the period");

        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {

            List<CheckDto> checkDtoList = checkService.getAllCheckDtoBetweenDate(dateFrom, dateTo);
            return ResponseEntity.ok(checkDtoList);
        }
        return new ResponseEntity<>(new MessageResponse("Check list for the period not found"), HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "incomes/{id}")
    @Operation(description = "Getting a list of Checks for a given Income..")
    public ResponseEntity<Object> getAllCheckByIncomeId(@Parameter(description = "'income' id")
                                                        @PathVariable(name = "id") Long id) {
        log.debug("!Call method getting a list of Checks for a given Income");

        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {

            List<CheckDto> checkDtoList = checkService.getAllCheckDtoByIncomeId(id);
            return ResponseEntity.ok(checkDtoList);
        }
        return new ResponseEntity<>(new MessageResponse("Check list for a given Income not found"), HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "incomes/{id}/dates/{date1}/{date2}")
    @Operation(description = "Getting a list of Checks for a given Income.. for the period from.. to..")
    public ResponseEntity<Object> getAllCheckByIncomeBetweenDate(@Parameter(description = "'income' id")
                                                                 @PathVariable(name = "id") Long id,
                                                                 @Parameter(description = "date 'from'")
                                                                 @PathVariable(name = "date1") LocalDate dateFrom,
                                                                 @Parameter(description = "date 'to'")
                                                                 @PathVariable(name = "date2") LocalDate dateTo) {
        log.debug("!Call method getting a list of Checks for a given Income for the period");

        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {

            List<CheckDto> checkDtoList = checkService.getAllCheckDtoByIncomeBetweenDate(id, dateFrom, dateTo);
            return ResponseEntity.ok(checkDtoList);
        }
        return new ResponseEntity<>(new MessageResponse("Check list for a given Income for the period not found"), HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "expenses/{id}")
    @Operation(description = "Getting a list of Checks for a given Expense..")
    public ResponseEntity<Object> getAllCheckByExpenseId(@Parameter(description = "'expense' id")
                                                         @PathVariable(name = "id") Long id) {
        log.debug("!Call method getting a list of Checks for a given Expense");

        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {

            List<CheckDto> checkDtoList = checkService.getAllCheckDtoByExpenseId(id);
            return ResponseEntity.ok(checkDtoList);
        }
        return new ResponseEntity<>(new MessageResponse("Check list for a given Expense not found"), HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "expenses/{id}/dates/{date1}/{date2}")
    @Operation(description = "Getting a list of Checks for a given Expense.. for the period from.. to..")
    public ResponseEntity<Object> getAllCheckByExpenseBetweenDate(@Parameter(description = "'expense' id")
                                                                  @PathVariable(name = "id") Long id,
                                                                  @Parameter(description = "date 'from'")
                                                                  @PathVariable(name = "date1") LocalDate dateFrom,
                                                                  @Parameter(description = "date 'to'")
                                                                  @PathVariable(name = "date2") LocalDate dateTo) {
        log.debug("!Call method getting a list of Checks for a given Expense for the period");

        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {

            List<CheckDto> checkDtoList = checkService.getAllCheckDtoByExpenseBetweenDate(id, dateFrom, dateTo);
            return ResponseEntity.ok(checkDtoList);
        }
        return new ResponseEntity<>(new MessageResponse("Check list for a given Expense for the period not found"), HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "actors/{id}")
    @Operation(description = "Getting a list of Checks for a given Actor..")
    public ResponseEntity<Object> getAllCheckByActorId(@Parameter(description = "'actor' id")
                                                       @PathVariable(name = "id") Long id) {
        log.debug("!Call method getting a list of Checks for a given Actor");

        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {

            List<CheckDto> checkDtoList = checkService.getAllCheckDtoByActorId(id);
            return ResponseEntity.ok(checkDtoList);
        }
        return new ResponseEntity<>(new MessageResponse("Check list for a given Actor not found"), HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "actors/{id}/dates/{date1}/{date2}")
    @Operation(description = "Getting a list of Checks for a given Actor.. for the period from.. to..")
    public ResponseEntity<Object> getAllCheckByActorBetweenDate(@Parameter(description = "'expense' id")
                                                                @PathVariable(name = "id") Long id,
                                                                @Parameter(description = "date 'from'")
                                                                @PathVariable(name = "date1") LocalDate dateFrom,
                                                                @Parameter(description = "date 'to'")
                                                                @PathVariable(name = "date2") LocalDate dateTo) {
        log.debug("!Call method getting a list of Checks for a given Actor for the period");

        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {

            List<CheckDto> checkDtoList = checkService.getAllCheckDtoByActorBetweenDate(id, dateFrom, dateTo);
            return ResponseEntity.ok(checkDtoList);
        }
        return new ResponseEntity<>(new MessageResponse("Check list for a given Actor for the period not found"), HttpStatus.BAD_REQUEST);
    }

}
