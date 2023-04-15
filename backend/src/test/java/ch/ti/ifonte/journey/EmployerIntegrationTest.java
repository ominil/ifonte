package ch.ti.ifonte.journey;

import ch.ti.ifonte.employer.EmployerDTO;
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
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@SpringBootTest(webEnvironment = RANDOM_PORT)
public class EmployerIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;
    private static final String EMPLOYER_PATH = "/api/v1/employers";


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
                email,
                "password");
        // send post request

        String jwtToken = webTestClient.post()
                .uri(EMPLOYER_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(employerRegistrationRequest), EmployerRegistrationRequest.class)
                .exchange()
                .expectStatus()
                .isOk()
                .returnResult(Void.class)
                .getResponseHeaders()
                .get(AUTHORIZATION)
                .get(0);

        // get all employer
        List<EmployerDTO> allEmployers = webTestClient.get()
                .uri(EMPLOYER_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .header(AUTHORIZATION, String.format("Bearer %s", jwtToken))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(new ParameterizedTypeReference<EmployerDTO>() {})
                .returnResult()
                .getResponseBody();


        var id = allEmployers.stream()
                .filter(employer -> employer.email().equals(email))
                .map(EmployerDTO::id)
                .findFirst()
                .orElseThrow();

        // make sure employer is present
        EmployerDTO expectedEmployer = new EmployerDTO(
                id,
                name,
                email,
                List.of("ROLE_USER"),
                email
        );



        assertThat(allEmployers).contains(expectedEmployer);

        // get employer by id
        webTestClient.get()
                .uri(EMPLOYER_PATH + "/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .header(AUTHORIZATION, String.format("Bearer %s", jwtToken))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(new ParameterizedTypeReference<EmployerDTO>() {})
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
                email,
                "password");

        EmployerRegistrationRequest employerRegistrationRequest2 = new EmployerRegistrationRequest(
                name,
                email + ".ch",
                "password");

        // create a employer to delete
        webTestClient.post()
                .uri(EMPLOYER_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(employerRegistrationRequest), EmployerRegistrationRequest.class)
                .exchange()
                .expectStatus()
                .isOk();

        // send post request to create employer that will delete the previous one
        String jwtToken = webTestClient.post()
                .uri(EMPLOYER_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(employerRegistrationRequest2), EmployerRegistrationRequest.class)
                .exchange()
                .expectStatus()
                .isOk()
                .returnResult(Void.class)
                .getResponseHeaders()
                .get(AUTHORIZATION)
                .get(0);

        // get all employer
        List<EmployerDTO> allEmployers = webTestClient.get()
                .uri(EMPLOYER_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .header(AUTHORIZATION, String.format("Bearer %s", jwtToken))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(new ParameterizedTypeReference<EmployerDTO>() {})
                .returnResult()
                .getResponseBody();

        var id = allEmployers.stream()
                .filter(employer -> employer.email().equals(email))
                .map(EmployerDTO::id)
                .findFirst()
                .orElseThrow();

        // employer 2 deletes employer 1
        webTestClient.delete()
                .uri(EMPLOYER_PATH + "/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .header(AUTHORIZATION, String.format("Bearer %s", jwtToken))
                .exchange()
                .expectStatus()
                .isOk();

        // employer 2 gets employer by id
        webTestClient.get()
                .uri(EMPLOYER_PATH + "/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .header(AUTHORIZATION, String.format("Bearer %s", jwtToken))
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
                email,
                "password");
        // send post request

        String jwtToken = webTestClient.post()
                .uri(EMPLOYER_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(employerRegistrationRequest), EmployerRegistrationRequest.class)
                .exchange()
                .expectStatus()
                .isOk()
                .returnResult(Void.class)
                .getResponseHeaders()
                .get(AUTHORIZATION)
                .get(0);
        // get all employer
        List<EmployerDTO> allEmployers = webTestClient.get()
                .uri(EMPLOYER_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .header(AUTHORIZATION, String.format("Bearer %s", jwtToken))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(new ParameterizedTypeReference<EmployerDTO>() {})
                .returnResult()
                .getResponseBody();

        var id = allEmployers.stream()
                .filter(employer -> employer.email().equals(email))
                .map(EmployerDTO::id)
                .findFirst()
                .orElseThrow();

        // update employer
        String newName = "bobbie";
        EmployerUpdateRequest updateRequest = new EmployerUpdateRequest(
                newName, null
        );

        webTestClient.put()
                .uri(EMPLOYER_PATH + "/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(updateRequest), EmployerUpdateRequest.class)
                .header(AUTHORIZATION, String.format("Bearer %s", jwtToken))
                .exchange()
                .expectStatus()
                .isOk();

        // get employer by id
        EmployerDTO updatedEmployer = webTestClient.get()
                .uri(EMPLOYER_PATH + "/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .header(AUTHORIZATION, String.format("Bearer %s", jwtToken))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(EmployerDTO.class)
                .returnResult()
                .getResponseBody();

        EmployerDTO expected = new EmployerDTO(
                id, newName, email, List.of("ROLE_USER"), email
        );

        assertThat(updatedEmployer).isEqualTo(expected);
    }
}
