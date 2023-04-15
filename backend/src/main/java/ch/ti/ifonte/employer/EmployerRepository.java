package ch.ti.ifonte.employer;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployerRepository extends JpaRepository<Employer, Integer> {

    boolean existsEmployerByEmail(String email);
    Optional<Employer> findEmployerByEmail(String email);

}
