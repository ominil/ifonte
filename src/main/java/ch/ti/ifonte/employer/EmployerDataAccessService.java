package ch.ti.ifonte.employer;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class EmployerDataAccessService implements EmployerDao {
    @Override
    public List<Employer> selectAllEmployee() {
        return List.of(
                new Employer(1, "Luca","luca@gmail.com"),
                new Employer(2, "Andrea","andrea@gmail.com")
        );
    }

    @Override
    public Optional<Employer> getEmployerById(Integer employerId) {

        List<Employer> employee = List.of(
                new Employer(1, "Luca","luca@gmail.com"),
                new Employer(2, "Andrea","andrea@gmail.com")
        );

        Optional<Employer> employer = employee.stream()
                .filter(emp -> emp.getId().equals(employerId))
                .findFirst();

        return employer;
    }
}
