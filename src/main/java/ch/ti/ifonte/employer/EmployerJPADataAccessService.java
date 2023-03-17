package ch.ti.ifonte.employer;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("employer_jpa")
@AllArgsConstructor
public class EmployerJPADataAccessService implements EmployerDao {

    private final EmployerRepository employerRepository;
    @Override
    public List<Employer> selectAllEmployers() {
        return employerRepository.findAll();
    }

    @Override
    public Optional<Employer> getEmployerById(Integer employerId) {
        return Optional.empty();
    }
}
