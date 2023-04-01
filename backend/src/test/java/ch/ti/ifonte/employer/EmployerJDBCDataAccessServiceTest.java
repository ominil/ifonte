package ch.ti.ifonte.employer;

import ch.ti.ifonte.AbstractTestcontainers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;




class EmployerJDBCDataAccessServiceTest extends AbstractTestcontainers {

    private EmployerJDBCDataAccessService underTest;
    private final EmployerRowMapper employerRowMapper = new EmployerRowMapper();


    @BeforeEach
    void setUp() {
        underTest = new EmployerJDBCDataAccessService(
                getJdbcTemplate(),
                employerRowMapper
        );
    }

    @Test
    void selectAllEmployers() {
        // GIVEN
        Employer employer = Employer.builder()
                .name(FAKER.name().fullName())
                .email(FAKER.internet().safeEmailAddress() + "." +  UUID.randomUUID())
                .build();
        underTest.insertEmployer(employer);

        // WHEN
        List<Employer> actual = underTest.selectAllEmployers();

        // THEN
        assertThat(actual).isNotEmpty();
    }

    @Test
    void selectEmployerById() {
        // GIVEN
        String email = FAKER.internet().safeEmailAddress() + "." + UUID.randomUUID();
        Employer employer = Employer.builder()
                .name(FAKER.name().fullName())
                .email(email)
                .build();
        underTest.insertEmployer(employer);
        
        Integer id = underTest.selectAllEmployers()
                .stream()
                .filter(e -> e.getEmail().equals(email))
                .map(Employer::getId)
                .findFirst()
                .orElseThrow();

        // WHEN
        Optional<Employer> actual = underTest.selectEmployerById(id);

        // THEN
        assertThat(actual).isPresent().hasValueSatisfying(e -> {
            assertThat(e.getId()).isEqualTo(id);
            assertThat(e.getName()).isEqualTo(employer.getName());
            assertThat(e.getEmail()).isEqualTo(employer.getEmail());

        });
    }

    @Test
    void willReturnEmptyWhenGetEmployerById() {
        // GIVEN
        Integer id = -1;

        // WHEN
        Optional<Employer> actual = underTest.selectEmployerById(id);

        // THEN
        assertThat(actual).isEmpty();

    }


    @Test
    void existPersonWithEmail() {
        // GIVEN
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        String name = FAKER.name().fullName();
        Employer Employer = new Employer(
                name,
                email
        );

        underTest.insertEmployer(Employer);

        // When
        boolean actual = underTest.existPersonWithEmail(email);

        // Then
        assertThat(actual).isTrue();
    }

    @Test
    void existsPersonWithEmailReturnsFalseWhenDoesNotExists() {
        // Given
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();

        // When
        boolean actual = underTest.existPersonWithEmail(email);

        // Then
        assertThat(actual).isFalse();
    }

    @Test
    void existEmployerById() {
        // Given
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        Employer employer = Employer.builder().name(
                FAKER.name().fullName())
                .email(email)
                .build();

        underTest.insertEmployer(employer);

        int id = underTest.selectAllEmployers()
                .stream()
                .filter(e -> e.getEmail().equals(email))
                .map(Employer::getId)
                .findFirst()
                .orElseThrow();

        // When
        var actual = underTest.existEmployerById(id);

        // Then
        assertThat(actual).isTrue();
    }

    @Test
    void existsPersonWithIdWillReturnFalseWhenIdNotPresent() {
        // Given
        int id = -1;

        // When
        var actual = underTest.existEmployerById(id);

        // Then
        assertThat(actual).isFalse();
    }

    @Test
    void deleteEmployerById() {
        // Given
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        Employer employer = new Employer(
                FAKER.name().fullName(),
                email
        );

        underTest.insertEmployer(employer);

        int id = underTest.selectAllEmployers()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Employer::getId)
                .findFirst()
                .orElseThrow();

        // When
        underTest.deleteEmployerById(id);

        // Then
        Optional<Employer> actual = underTest.selectEmployerById(id);
        assertThat(actual).isNotPresent();
    }


    @Test
    void updateEmployerEmail() {
        // Given
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        Employer employer = new Employer(
                FAKER.name().fullName(),
                email
        );

        underTest.insertEmployer(employer);

        int id = underTest.selectAllEmployers()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Employer::getId)
                .findFirst()
                .orElseThrow();

        var newEmail = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();

        // When email is changed
        Employer update = new Employer();
        update.setId(id);
        update.setEmail(newEmail);

        underTest.updateEmployer(update);

        // Then
        Optional<Employer> actual = underTest.selectEmployerById(id);

        assertThat(actual).isPresent().hasValueSatisfying(c -> {
            assertThat(c.getId()).isEqualTo(id);
            assertThat(c.getEmail()).isEqualTo(newEmail); // change
            assertThat(c.getName()).isEqualTo(employer.getName());
        });
    }


    @Test
    void willUpdateAllPropertiesEmployer() {
        // Given
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        Employer employer = new Employer(
                FAKER.name().fullName(),
                email
        );

        underTest.insertEmployer(employer);

        int id = underTest.selectAllEmployers()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Employer::getId)
                .findFirst()
                .orElseThrow();

        // When update with new name, age and email
        Employer update = new Employer();
        update.setId(id);
        update.setName("foo");
        String newEmail = UUID.randomUUID().toString();
        update.setEmail(newEmail);

        underTest.updateEmployer(update);

        // Then
        Optional<Employer> actual = underTest.selectEmployerById(id);

        assertThat(actual).isPresent().hasValueSatisfying(updated -> {
            assertThat(updated.getId()).isEqualTo(id);
            assertThat(updated.getName()).isEqualTo("foo");
            assertThat(updated.getEmail()).isEqualTo(newEmail);
        });
    }

    @Test
    void willNotUpdateWhenNothingToUpdate() {
        // Given
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        Employer employer = new Employer(
                FAKER.name().fullName(),
                email
        );

        underTest.insertEmployer(employer);

        int id = underTest.selectAllEmployers()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Employer::getId)
                .findFirst()
                .orElseThrow();

        // When update without no changes
        Employer update = new Employer();
        update.setId(id);

        underTest.updateEmployer(update);

        // Then
        Optional<Employer> actual = underTest.selectEmployerById(id);

        assertThat(actual).isPresent().hasValueSatisfying(c -> {
            assertThat(c.getId()).isEqualTo(id);
            assertThat(c.getName()).isEqualTo(employer.getName());
            assertThat(c.getEmail()).isEqualTo(employer.getEmail());
        });
    }
}