package ch.ti.ifonte.employer;

import ch.ti.ifonte.exception.DuplicateResourceException;
import ch.ti.ifonte.exception.RequestValidationException;
import ch.ti.ifonte.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployerService {

    private final EmployerDao employerDao;

    public EmployerService(@Qualifier("jdbc") EmployerDao employerDao) {
        this.employerDao = employerDao;
    }
    public List<Employer> getAllEmployee() {
        return employerDao.selectAllEmployers();
    }

    public Employer getEmployer(Integer employerId) {
        return employerDao.getEmployerById(employerId).orElseThrow(
                () -> new ResourceNotFoundException(
                        "Employer with id [%s] not found".formatted(employerId)
                )
        );
    }

    public void addEmployer(EmployerRegistrationRequest employerRegistrationRequest) {
        // check if email exists
        if (employerDao.existPersonWithEmail(employerRegistrationRequest.email())) {
            throw new DuplicateResourceException(
                    "Email already taken"
            );
        }

        // add employer
        Employer employer = Employer.builder()
                .email(employerRegistrationRequest.email())
                .name(employerRegistrationRequest.name())
                .build();

        employerDao.insertEmployer(employer);
    }


    public void deleteEmployerById(Integer id) {

        if (!employerDao.existEmployerById(id)) {
            throw new ResourceNotFoundException(
                    "Employer with id [%s] not found".formatted(id)
            );
        }

        employerDao.deleteEmployerById(id);
    }

    public void updateEmployer(Integer employerId, EmployerUpdateRequest employerUpdateRequest) {

        Employer employer = getEmployer(employerId);

        boolean changes = false;

        if (employerUpdateRequest.name() != null && !employerUpdateRequest.name().equals(employer.getName())) {
            employer.setName(employerUpdateRequest.name());
            changes = true;
        }

        if (employerUpdateRequest.email() != null && !employerUpdateRequest.email().equals(employer.getEmail())) {
            if (employerDao.existPersonWithEmail(employerUpdateRequest.email())) {
                throw new DuplicateResourceException(
                        "Email already taken"
                );
            }

            employer.setEmail(employerUpdateRequest.name());
            changes = true;
        }

        if (!changes) {
            throw new RequestValidationException("No changes detected");
        }

        employerDao.updateEmployer(employer);
    }
}
