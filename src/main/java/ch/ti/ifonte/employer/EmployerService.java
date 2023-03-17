package ch.ti.ifonte.employer;

import ch.ti.ifonte.exception.ResourceNotFound;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployerService {

    private final EmployerDao employerDao;

    public EmployerService(@Qualifier("employer_jpa") EmployerDao employerDao) {
        this.employerDao = employerDao;
    }
    public List<Employer> getAllEmployee() {
        return employerDao.selectAllEmployers();
    }

    public Employer getEmployer(Integer employerId) {
        return employerDao.getEmployerById(employerId).orElseThrow(
                () -> new ResourceNotFound(
                        "Employer with id [%s] not found".formatted(employerId)
                )
        );
    }

}
