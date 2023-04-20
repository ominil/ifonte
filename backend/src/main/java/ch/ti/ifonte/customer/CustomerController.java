package ch.ti.ifonte.customer;


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
@RequestMapping("api/v1/customers")
@AllArgsConstructor
public class CustomerController {

    private final CustomerService customerService;
    private final JWTUtil jwtUtil;

    @Operation(summary = "Get a list of all registered customers")
    @GetMapping
    public List<CustomerDTO> getCustomers() {
        return customerService.getAllCustomers();
    }

    @Operation(summary = "Retrieve customer by id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "successful operation", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Customer not found", content = @Content),
            }
    )
    @GetMapping("{id}")
    public CustomerDTO getCustomerById(@PathVariable("id") Integer id) {
        return customerService.getCustomerById(id);
    }

    @Operation(summary = "Create new customer")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "successful operation", content = @Content),
                    @ApiResponse(responseCode = "409", description = "Conflict", content = @Content),
            }
    )
    @PostMapping
    public ResponseEntity<?> registerCustomer(
            @RequestBody CustomerRegistrationRequest customerRegistrationRequest
    ) {

        customerService.addCustomer(customerRegistrationRequest);
        String jwtToken = jwtUtil.issueToken(customerRegistrationRequest.email(), "ROLE_USER");
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, jwtToken)
                .build();
    }

    @Operation(summary = "Update customer info")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "successful operation", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Bad request", content = @Content),
            }
    )
    @PutMapping("{customerId}")
    public void updateCustomer(
        @PathVariable("customerId") Integer customerId,
        @RequestBody CustomerUpdateRequest customerUpdateRequest
    ) {
        customerService.updateCustomer(customerId, customerUpdateRequest);
    }

    @Operation(summary = "Delete customer by id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "successful operation", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Customer not found", content = @Content),
            }
    )
    @DeleteMapping("{customerId}")
    public void deleteCustomerById(@PathVariable("customerId") Integer customerId) {
        customerService.deleteCustomerById(customerId);
    }
}
