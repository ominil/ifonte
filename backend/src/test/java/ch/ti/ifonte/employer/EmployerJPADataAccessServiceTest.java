package ch.ti.ifonte.employer;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

class EmployerJPADataAccessServiceTest {

    private EmployerJPADataAccessService underTest;
    @Mock
    private EmployerRepository employerRepository;
    private AutoCloseable autoCloseable;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new EmployerJPADataAccessService(employerRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void selectAllEmployers() {
        // When
        underTest.selectAllEmployers();

        // Then
        verify(employerRepository)
                .findAll();
    }

    @Test
    void selectEmployerById() {
        // Given
        Integer id = 1;
        // When
        underTest.selectEmployerById(id);

        // Then
        verify(employerRepository)
                .findById(id);
    }

    @Test
    void insertEmployer() {
        // Given
        Employer employer = new Employer();

        // When
        underTest.insertEmployer(employer);

        // Then
        verify(employerRepository)
                .save(employer);
    }

    @Test
    void deleteEmployerById() {
        // Given
        Integer id = 1;

        // When
        underTest.deleteEmployerById(id);

        // Then
        verify(employerRepository)
                .deleteById(id);
    }

    @Test
    void existPersonWithEmail() {
        // Given
        String email = "example@domain.com";

        // When
        underTest.existPersonWithEmail(email);

        // Then
        verify(employerRepository)
                .existsEmployerByEmail(email);
    }

    @Test
    void existEmployerById() {
        // Given
        Integer id = 1;

        // When
        underTest.existEmployerById(id);

        // Then
        verify(employerRepository)
                .existsById(id);
    }

    @Test
    void updateEmployer() {
        // Given
        Employer employer = new Employer();

        // When
        underTest.updateEmployer(employer);

        // Then
        verify(employerRepository)
                .save(employer);
    }
}