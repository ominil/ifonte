package ch.ti.ifonte;

import ch.ti.ifonte.employer.Employer;
import ch.ti.ifonte.employer.EmployerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class IFonteApplication {

    public static void main(String[] args) {
        SpringApplication.run(IFonteApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(EmployerRepository employerRepository) {
        return args -> {
                    Employer luca = new Employer("Luca","luca@gmail.com");
                    Employer andrea = new Employer("Andrea","andrea@gmail.com");

            List<Employer> employers = List.of(luca, andrea);
            employerRepository.saveAll(employers);
        };
    }
}
