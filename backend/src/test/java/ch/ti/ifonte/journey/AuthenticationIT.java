package ch.ti.ifonte.journey;

import ch.ti.ifonte.auth.AuthenticationRequest;
import ch.ti.ifonte.auth.AuthenticationResponse;
import ch.ti.ifonte.employer.EmployerRegistrationRequest;
import ch.ti.ifonte.jwt.JWTUtil;
import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@SpringBootTest(webEnvironment = RANDOM_PORT)
public class AuthenticationIT {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private JWTUtil jwtUtil;
    private static final String AUTHENTICATION_PATH = "/api/v1/auth";
    private static final String EMPLOYER_PATH = "/api/v1/employers";

    @Test
    void canLogin() {
        // Create registration request
        Faker faker = new Faker();
        Name fakerName = faker.name();
        String name = fakerName.firstName();
        String lastname = fakerName.lastName();
        String email = name + "." + lastname + "-" + UUID.randomUUID() +  "@testexample.com";
        String password = "password";

        EmployerRegistrationRequest employerRegistrationRequest = new EmployerRegistrationRequest(
                name,
                email,
                password);

        AuthenticationRequest authenticationRequest = new AuthenticationRequest(email, password);

        webTestClient.post()
                .uri(AUTHENTICATION_PATH + "/login")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(authenticationRequest), AuthenticationRequest.class)
                .exchange()
                .expectStatus()
                .isUnauthorized();

        webTestClient.post()
                .uri(EMPLOYER_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(employerRegistrationRequest), EmployerRegistrationRequest.class)
                .exchange()
                .expectStatus()
                .isOk();

        EntityExchangeResult<AuthenticationResponse> result = webTestClient.post()
                .uri(AUTHENTICATION_PATH + "/login")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(authenticationRequest), AuthenticationRequest.class)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(new ParameterizedTypeReference<AuthenticationResponse>() {
                })
                .returnResult();

        String jwtToken = result.getResponseHeaders().get(AUTHORIZATION).get(0);

        assertThat(jwtUtil.isTokenValid(jwtToken,email)).isTrue();

    }
}
