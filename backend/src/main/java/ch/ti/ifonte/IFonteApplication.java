package ch.ti.ifonte;

import ch.ti.ifonte.customer.Customer;
import ch.ti.ifonte.customer.CustomerRepository;
import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.UUID;

@SpringBootApplication
public class IFonteApplication {

    public static void main(String[] args) {
        SpringApplication.run(IFonteApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(CustomerRepository customerRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            Faker faker = new Faker();
            Name name = faker.name();
            String firstname = name.firstName();
            String lastname = name.lastName();
            Customer customer = new Customer(
                    firstname + " " + lastname,
                    firstname.toLowerCase() + "." + lastname.toLowerCase() + "@example.com",
                    passwordEncoder.encode(UUID.randomUUID().toString()));
            List<Customer> customers = List.of(customer);
            customerRepository.saveAll(customers);
        };
    }
}
