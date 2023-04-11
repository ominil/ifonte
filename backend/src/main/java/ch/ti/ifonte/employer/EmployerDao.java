package ch.ti.ifonte.employer;

import java.util.List;
import java.util.Optional;

public interface EmployerDao {

    List<Employer> selectAllEmployers();
    Optional<Employer> selectEmployerById(Integer employerId);
    void insertEmployer(Employer employer);

    void deleteEmployerById(Integer id);

    boolean existPersonWithEmail(String email);

    boolean existEmployerById(Integer id);

    void updateEmployer(Employer employer);
    Optional<Employer> selectUserByEmail(String email);
}
