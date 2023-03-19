package ch.ti.ifonte;

import ch.ti.ifonte.employer.Employer;
import ch.ti.ifonte.employer.EmployerRepository;
import com.github.javafaker.Faker;
import com.github.javafaker.Name;
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
            Faker faker = new Faker();
            Name name = faker.name();
            String firstname = name.firstName();
            String lastname = name.lastName();
            Employer employer = new Employer(
                    firstname + " " + lastname,
                    firstname.toLowerCase() + "." + lastname.toLowerCase() + "@example.com"
            );

            List<Employer> employers = List.of(employer);
            employerRepository.saveAll(employers);
        };
    }
}
