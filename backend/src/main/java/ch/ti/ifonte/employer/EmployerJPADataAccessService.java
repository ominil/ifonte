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
    public Optional<Employer> selectEmployerById(Integer employerId) {
        return employerRepository.findById(employerId);
    }

    @Override
    public void insertEmployer(Employer employer) {
        employerRepository.save(employer);
    }

    @Override
    public void deleteEmployerById(Integer id) {
        employerRepository.deleteById(id);
    }

    @Override
    public boolean existPersonWithEmail(String email) {
        return employerRepository.existsEmployerByEmail(email);
    }

    @Override
    public boolean existEmployerById(Integer id) {
        return employerRepository.existsById(id);
    }

    @Override
    public void updateEmployer(Employer employer) {
        employerRepository.save(employer);
    }
}
