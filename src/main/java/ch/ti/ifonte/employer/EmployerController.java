package ch.ti.ifonte.employer;


import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/employers")
@AllArgsConstructor
public class EmployerController {

    private final EmployerService employerService;

    @GetMapping
    public List<Employer> getEmployers() {
        return employerService.getAllEmployee();
    }

    @GetMapping("{id}")
    public Employer getEmployerById(@PathVariable("id") Integer id) {
        return employerService.getEmployer(id);
    }

    @PostMapping
    public void registerEmployer(
            @RequestBody EmployerRegistrationRequest employerRegistrationRequest
    ) {
        employerService.addEmployer(employerRegistrationRequest);
    }


    @PutMapping("{employerId}")
    public void updateEmployer(
        @PathVariable("employerId") Integer employerId,
        @RequestBody EmployerUpdateRequest employerUpdateRequest
    ) {
        employerService.updateEmployer(employerId, employerUpdateRequest);
    }

    @DeleteMapping("{id}")
    public void deleteEmployerById(@PathVariable("id") Integer id) {
        employerService.deleteEmployerById(id);
    }
}
