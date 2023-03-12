package ch.ti.ifonte.employer;


import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class EmployerController {

    private final EmployerService employerService;

    @GetMapping("api/v1/employee")
    public List<Employer> getEmployee() {
        return employerService.getAllEmployee();
    }

    @GetMapping("api/v1/employee/{id}")
    public Employer getEmployerById(@PathVariable("id") Integer id) {
        return employerService.getEmployer(id);
    }
}
