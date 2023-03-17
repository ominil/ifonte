package ch.ti.ifonte.employer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface EmployerRepository extends JpaRepository<Employer, Integer> {

}
