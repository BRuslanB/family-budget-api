package kz.bars.family.budget.api.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import kz.bars.family.budget.api.dto.ActorDto;
import kz.bars.family.budget.api.service.ActorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
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
    public ActorDto getActor(@Parameter(description = "'actor' id")
                             @PathVariable(name = "id") Long id) {
        log.debug("!Call method getting an Actor");
//        log.debug(SecurityContextHolder.getContext().getAuthentication().getName());
        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            return actorService.getActorDto(id);
        }
        return null;
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    @Operation(description = "Getting a list of All Actors")
    public List<ActorDto> getAllActor() {
        log.debug("!Call method getting a list of All Actors");
//        log.debug(SecurityContextHolder.getContext().getAuthentication().getName());
        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            return actorService.getAllActorDto();
        }
        return null;
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(description = "Actor added")
    public ActorDto addActor(@RequestBody ActorDto actorDto) {
        log.debug("!Call method Actor added");
//        log.debug(SecurityContextHolder.getContext().getAuthentication().getName());
        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .anyMatch(role -> role.equals("ROLE_ADMIN"))) { // contains("ROLE_ADMIN")

                return actorService.addActorDto(actorDto);
            }
        }
        return null;
    }

    @PutMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(description = "Actor updated")
    public ActorDto updateActor(@RequestBody ActorDto actorDto) {
        log.debug("!Call method Actor updated");
//        log.debug(SecurityContextHolder.getContext().getAuthentication().getName());
        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .anyMatch(role -> role.equals("ROLE_ADMIN"))) { // contains("ROLE_ADMIN")

                return actorService.updateActorDto(actorDto);
            }
        }
        return null;
    }

    @DeleteMapping(value = "{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(description = "Actor.. removed")
    public Long deleteActor(@Parameter(description = "'actor' id")
                            @PathVariable(name = "id") Long id) {
        log.debug("!Call method Actor removed");
//        log.debug(SecurityContextHolder.getContext().getAuthentication().getName());
        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .anyMatch(role -> role.equals("ROLE_ADMIN"))) { // contains("ROLE_ADMIN")

                return actorService.deleteActorDto(id);
            }
        }
        return null;
    }

}
