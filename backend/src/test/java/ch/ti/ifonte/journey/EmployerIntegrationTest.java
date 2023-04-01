package ch.ti.ifonte.journey;

import ch.ti.ifonte.employer.Employer;
import ch.ti.ifonte.employer.EmployerRegistrationRequest;
import ch.ti.ifonte.employer.EmployerUpdateRequest;
import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = RANDOM_PORT)
public class EmployerIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;
    private static final String EMPLOYERS_URI = "/api/v1/employers";


    @Test
    void canRegisterAEmployer() {
        // Create registration request
        Faker faker = new Faker();
        Name fakerName = faker.name();
        String name = fakerName.firstName();
        String lastname = fakerName.lastName();
        String email = name + "." + lastname + "-" + UUID.randomUUID() +  "@testexample.com";
        EmployerRegistrationRequest employerRegistrationRequest = new EmployerRegistrationRequest(
                name,
                email
        );
        // send post request

        webTestClient.post()
                .uri(EMPLOYERS_URI)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(employerRegistrationRequest), EmployerRegistrationRequest.class)
                .exchange()
                .expectStatus()
                .isOk();
        // get all employer
        List<Employer> allEmployers = webTestClient.get()
                .uri(EMPLOYERS_URI)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(new ParameterizedTypeReference<Employer>() {})
                .returnResult()
                .getResponseBody();

        // make sure employer is present
        Employer expectedEmployer = Employer.builder()
                .name(name)
                .email(email)
                .build();

        assertThat(allEmployers)
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
                .contains(expectedEmployer);

        var id = allEmployers.stream()
                .filter(employer -> employer.getEmail().equals(email))
                .map(Employer::getId)
                .findFirst()
                .orElseThrow();

        expectedEmployer.setId(id);
        // get employer by id
        webTestClient.get()
                .uri(EMPLOYERS_URI + "/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(new ParameterizedTypeReference<Employer>() {})
                .isEqualTo(expectedEmployer);
    }

    @Test
    void canDeleteEmployer() {
        // Create registration request
        Faker faker = new Faker();
        Name fakerName = faker.name();
        String name = fakerName.firstName();
        String lastname = fakerName.lastName();
        String email = name + "." + lastname + "-" + UUID.randomUUID() +  "@testexample.com";
        EmployerRegistrationRequest employerRegistrationRequest = new EmployerRegistrationRequest(
                name,
                email
        );
        // send post request

        webTestClient.post()
                .uri(EMPLOYERS_URI)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(employerRegistrationRequest), EmployerRegistrationRequest.class)
                .exchange()
                .expectStatus()
                .isOk();
        // get all employer
        List<Employer> allEmployers = webTestClient.get()
                .uri(EMPLOYERS_URI)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(new ParameterizedTypeReference<Employer>() {})
                .returnResult()
                .getResponseBody();

        var id = allEmployers.stream()
                .filter(employer -> employer.getEmail().equals(email))
                .map(Employer::getId)
                .findFirst()
                .orElseThrow();

        // delete employer
        webTestClient.delete()
                .uri(EMPLOYERS_URI + "/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk();

        // get employer by id
        webTestClient.get()
                .uri(EMPLOYERS_URI + "/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isNotFound();
    }

    @Test
    void canUpdateCustomer() {
        // Create registration request
        Faker faker = new Faker();
        Name fakerName = faker.name();
        String name = fakerName.firstName();
        String lastname = fakerName.lastName();
        String email = name + "." + lastname + "-" + UUID.randomUUID() +  "@testexample.com";
        EmployerRegistrationRequest employerRegistrationRequest = new EmployerRegistrationRequest(
                name,
                email
        );
        // send post request

        webTestClient.post()
                .uri(EMPLOYERS_URI)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(employerRegistrationRequest), EmployerRegistrationRequest.class)
                .exchange()
                .expectStatus()
                .isOk();
        // get all employer
        List<Employer> allEmployers = webTestClient.get()
                .uri(EMPLOYERS_URI)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(new ParameterizedTypeReference<Employer>() {})
                .returnResult()
                .getResponseBody();

        var id = allEmployers.stream()
                .filter(employer -> employer.getEmail().equals(email))
                .map(Employer::getId)
                .findFirst()
                .orElseThrow();

        // update employer
        String newName = "bobbie";
        EmployerUpdateRequest updateRequest = new EmployerUpdateRequest(
                newName, null
        );

        webTestClient.put()
                .uri(EMPLOYERS_URI + "/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(updateRequest), EmployerUpdateRequest.class)
                .exchange()
                .expectStatus()
                .isOk();

        // get employer by id
        Employer updatedEmployer = webTestClient.get()
                .uri(EMPLOYERS_URI + "/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(Employer.class)
                .returnResult()
                .getResponseBody();

        Employer expected = Employer.builder()
                .id(id)
                .name(newName)
                .email(email)
                .build();

        assertThat(updatedEmployer).isEqualTo(expected);
    }
}
