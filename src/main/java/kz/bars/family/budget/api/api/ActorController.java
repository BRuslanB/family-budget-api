package kz.bars.family.budget.api.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import kz.bars.family.budget.api.dto.ActorDto;
import kz.bars.family.budget.api.payload.response.MessageResponse;
import kz.bars.family.budget.api.service.ActorService;
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
@RequestMapping(value = "/api/actors")
@CrossOrigin
@Log4j2
@SecurityRequirement(name = "family-budget-api")
@Tag(name = "Expense", description = "All methods for getting a list of Actors")
public class ActorController {

    private final ActorService actorService;

    @GetMapping(value = "{id}")
    @PreAuthorize("isAuthenticated()")
    @Operation(description = "Getting an Actor..")
    public ResponseEntity<Object> getActor(@Parameter(description = "'actor' id")
                                           @PathVariable(name = "id") Long id) {
        log.debug("!Call method getting an Actor");

        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {

            ActorDto actorDto = actorService.getActorDto(id);
            if (actorDto != null) {
                return ResponseEntity.ok(actorDto);
            }
        }
        return new ResponseEntity<>(new MessageResponse("Actor not found"), HttpStatus.BAD_REQUEST);
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    @Operation(description = "Getting a list of All Actors")
    public ResponseEntity<Object> getAllActor() {
        log.debug("!Call method getting a list of All Actors");

        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {

            List<ActorDto> actorDtoList = actorService.getAllActorDto();
            return ResponseEntity.ok(actorDtoList);
        }
        return new ResponseEntity<>(new MessageResponse("Actor list not found"), HttpStatus.BAD_REQUEST);
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(description = "Actor added")
    public ResponseEntity<Object> addActor(@RequestBody ActorDto actorDto) {
        log.debug("!Call method Actor added");

        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .anyMatch(role -> role.equals("ROLE_ADMIN"))) { // contains("ROLE_ADMIN")

                if (actorService.addActorDto(actorDto) != null) {
                    return new ResponseEntity<>(new MessageResponse("Actor added successfully!"), HttpStatus.CREATED);
                }

            } else {
                return new ResponseEntity<>(new MessageResponse("Access denied"), HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>(new MessageResponse("Actor not added"), HttpStatus.BAD_REQUEST);
    }

    @PutMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(description = "Actor updated")
    public ResponseEntity<Object> updateActor(@RequestBody ActorDto actorDto) {
        log.debug("!Call method Actor updated");

        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .anyMatch(role -> role.equals("ROLE_ADMIN"))) { // contains("ROLE_ADMIN")

                if (actorService.updateActorDto(actorDto) != null) {
                    return ResponseEntity.ok(new MessageResponse("Actor updated successfully!"));
                }

            } else {
                return new ResponseEntity<>(new MessageResponse("Access denied"), HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>(new MessageResponse("Actor not updated"), HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping(value = "{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(description = "Actor.. removed")
    public ResponseEntity<Object> deleteActor(@Parameter(description = "'actor' id")
                                              @PathVariable(name = "id") Long id) {
        log.debug("!Call method Actor removed");

        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .anyMatch(role -> role.equals("ROLE_ADMIN"))) { // contains("ROLE_ADMIN")

                if (actorService.deleteActorDto(id) != null) {
                    return ResponseEntity.ok(new MessageResponse("Actor removed successfully!"));
                }

            } else {
                return new ResponseEntity<>(new MessageResponse("Access denied"), HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>(new MessageResponse("Actor not removed"), HttpStatus.BAD_REQUEST);
    }
}
