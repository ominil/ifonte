package ch.ti.ifonte.employer;

import ch.ti.ifonte.exception.ResourceNotFound;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EmployerService {

    private final EmployerDao employerDao;
    public List<Employer> getAllEmployee() {
        return employerDao.selectAllEmployee();
    }

    public Employer getEmployer(Integer employerId) {
        return employerDao.getEmployerById(employerId).orElseThrow(
                () -> new ResourceNotFound(
                        "Employer with id [%s] not found".formatted(employerId)
                )
        );
    }

}
