package ch.ti.ifonte.employer;


import ch.ti.ifonte.jwt.JWTUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/employers")
@AllArgsConstructor
public class EmployerController {

    private final EmployerService employerService;
    private final JWTUtil jwtUtil;

    @Operation(summary = "Get a list of all registered employers")
    @GetMapping
    public List<EmployerDTO> getEmployers() {
        return employerService.getAllEmployers();
    }

    @Operation(summary = "Retrieve employer by id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "successful operation", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Employer not found", content = @Content),
            }
    )
    @GetMapping("{id}")
    public EmployerDTO getEmployerById(@PathVariable("id") Integer id) {
        return employerService.getEmployer(id);
    }

    @Operation(summary = "Create new employer")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "successful operation", content = @Content),
                    @ApiResponse(responseCode = "409", description = "Conflict", content = @Content),
            }
    )
    @PostMapping
    public ResponseEntity<?> registerEmployer(
            @RequestBody EmployerRegistrationRequest employerRegistrationRequest
    ) {

        employerService.addEmployer(employerRegistrationRequest);
        String jwtToken = jwtUtil.issueToken(employerRegistrationRequest.email(), "ROLE_USER");
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, jwtToken)
                .build();
    }

    @Operation(summary = "Update employer info")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "successful operation", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Bad request", content = @Content),
            }
    )
    @PutMapping("{employerId}")
    public void updateEmployer(
        @PathVariable("employerId") Integer employerId,
        @RequestBody EmployerUpdateRequest employerUpdateRequest
    ) {
        employerService.updateEmployer(employerId, employerUpdateRequest);
    }

    @Operation(summary = "Delete employer by id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "successful operation", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Employer not found", content = @Content),
            }
    )
    @DeleteMapping("{id}")
    public void deleteEmployerById(@PathVariable("id") Integer id) {
        employerService.deleteEmployerById(id);
    }
}
