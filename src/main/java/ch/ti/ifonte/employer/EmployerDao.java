package ch.ti.ifonte.employer;

import java.util.List;
import java.util.Optional;

public interface EmployerDao {

    List<Employer> selectAllEmployers();
    Optional<Employer> getEmployerById(Integer employerId);
}
