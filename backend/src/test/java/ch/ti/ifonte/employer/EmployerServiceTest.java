package ch.ti.ifonte.employer;

import ch.ti.ifonte.exception.DuplicateResourceException;
import ch.ti.ifonte.exception.RequestValidationException;
import ch.ti.ifonte.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployerServiceTest {

    private EmployerService underTest;

    @Mock
    private EmployerDao employerDao;

    @Mock
    private PasswordEncoder passwordEncoder;

    private final EmployerDTOMapper employerDTOMapper = new EmployerDTOMapper();

    @BeforeEach
    void setUp() {
        underTest = new EmployerService(employerDao, passwordEncoder, employerDTOMapper);
    }

    @Test
    void getAllEmployee() {
        // When
        underTest.getAllEmployers();

        // Then
        verify(employerDao).selectAllEmployers();
    }

    @Test
    void getEmployer() {
        // Given
        Integer id = 10;
        Employer employer = Employer.builder()
                .id(id)
                .name("bob")
                .email("example@domain.com")
                .build();

        EmployerDTO expected = employerDTOMapper.apply(employer);

        when(employerDao.selectEmployerById(id)).thenReturn(
            Optional.of(employer)
        );

        // When
        EmployerDTO actual = underTest.getEmployer(id);

        // Then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void willThrowWhenGetEmployerReturnsEmptyOptional() {
        // Given
        Integer id = 10;
        when(employerDao.selectEmployerById(id)).thenReturn(
                Optional.empty()
        );

        // When
        assertThatThrownBy(() -> underTest.getEmployer(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage(
                        "Employer with id [%s] not found".formatted(id)
                );

        // Then
    }

    @Test
    void addEmployer() {
        // Given
        String email = "foo@example.com";

        when(employerDao.existPersonWithEmail(email)).thenReturn(false);

        EmployerRegistrationRequest request = new EmployerRegistrationRequest(
                "foo", email,
                "password");

        String passwordHash = "YFOEHPOAJCPO#§";
        when(passwordEncoder.encode(request.password())).thenReturn(passwordHash);

        // When
        underTest.addEmployer(request);

        // Then
        ArgumentCaptor<Employer> employerArgumentCaptor = ArgumentCaptor.forClass(Employer.class);
        verify(employerDao).insertEmployer(employerArgumentCaptor.capture());

        Employer captoredEmployer = employerArgumentCaptor.getValue();

        assertThat(captoredEmployer.getId()).isNull();
        assertThat(captoredEmployer.getName()).isEqualTo(request.name());
        assertThat(captoredEmployer.getEmail()).isEqualTo(request.email());
        assertThat(captoredEmployer.getPassword()).isEqualTo(passwordHash);
    }

    @Test
    void willThrowWhenEmailExistsWhenAddingEmployer() {
        // Given
        String email = "foo@example.com";

        when(employerDao.existPersonWithEmail(email)).thenReturn(true);

        EmployerRegistrationRequest request = new EmployerRegistrationRequest(
                "foo", email,
                "password");

        // When
        assertThatThrownBy(() -> underTest.addEmployer(request))
                .isInstanceOf(DuplicateResourceException.class)
                .hasMessage(
                        "Email already taken"
                );

        // Then
        verify(employerDao, never()).insertEmployer(any());
    }

    @Test
    void deleteEmployerById() {
        // Given
        Integer id = 10;
        when(employerDao.existEmployerById(id)).thenReturn(true);


        // When
        underTest.deleteEmployerById(id);

        // Then
        verify(employerDao).deleteEmployerById(id);
    }

    @Test
    void willThrowWhenEmployerNotFoundOnDelete() {
        // Given
        Integer id = 10;
        when(employerDao.existEmployerById(id)).thenReturn(false);

        // When
        assertThatThrownBy(() -> underTest.deleteEmployerById(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage(
                        "Employer with id [%s] not found".formatted(id)
                );

        // Then
        verify(employerDao, never()).deleteEmployerById(any());
    }

    @Test
    void canUpdateAllEmployerProperties() {
        // Given
        Integer id = 10;
        String email = "mario@example.com";
        EmployerUpdateRequest request = new EmployerUpdateRequest(
                "mario",
                email
        );

        Employer employer = Employer.builder()
                .id(id)
                .name("luigi")
                .email("luigi@example.com")
                .build();

        when(employerDao.selectEmployerById(id)).thenReturn(
                Optional.of(employer)
        );
        when(employerDao.existPersonWithEmail(email)).thenReturn(false);


        // When
        underTest.updateEmployer(id, request);


        // Then
        ArgumentCaptor<Employer> employerArgumentCaptor = ArgumentCaptor.forClass(Employer.class);
        verify(employerDao).updateEmployer(employerArgumentCaptor.capture());

        Employer captoredEmployer = employerArgumentCaptor.getValue();

        assertThat(captoredEmployer.getId()).isEqualTo(id);
        assertThat(captoredEmployer.getName()).isEqualTo(request.name());
        assertThat(captoredEmployer.getEmail()).isEqualTo(request.email());
    }

    @Test
    void canUpdateOnlyEmployerName() {
        // Given
        Integer id = 10;
        String email = "luigi@example.com";

        EmployerUpdateRequest request = new EmployerUpdateRequest(
                "mario",
                null
        );

        Employer employer = Employer.builder()
                .id(id)
                .name("luigi")
                .email(email)
                .build();

        when(employerDao.selectEmployerById(id)).thenReturn(
                Optional.of(employer)
        );


        // When
        underTest.updateEmployer(id, request);


        // Then
        ArgumentCaptor<Employer> employerArgumentCaptor = ArgumentCaptor.forClass(Employer.class);
        verify(employerDao).updateEmployer(employerArgumentCaptor.capture());

        Employer captoredEmployer = employerArgumentCaptor.getValue();

        assertThat(captoredEmployer.getId()).isEqualTo(id);
        assertThat(captoredEmployer.getName()).isEqualTo(request.name());
        assertThat(captoredEmployer.getEmail()).isEqualTo(email);
    }

    @Test
    void canUpdateOnlyEmployerEmail() {
        // Given
        Integer id = 10;
        String name = "luigi";
        String email = "luigi@example.com";
        EmployerUpdateRequest request = new EmployerUpdateRequest(
                null,
                email
        );

        Employer employer = Employer.builder()
                .id(id)
                .name(name)
                .email("mario@example.com")
                .build();

        when(employerDao.selectEmployerById(id)).thenReturn(
                Optional.of(employer)
        );
        when(employerDao.existPersonWithEmail(email)).thenReturn(false);


        // When
        underTest.updateEmployer(id, request);


        // Then
        ArgumentCaptor<Employer> employerArgumentCaptor = ArgumentCaptor.forClass(Employer.class);
        verify(employerDao).updateEmployer(employerArgumentCaptor.capture());

        Employer captoredEmployer = employerArgumentCaptor.getValue();

        assertThat(captoredEmployer.getId()).isEqualTo(id);
        assertThat(captoredEmployer.getName()).isEqualTo(name);
        assertThat(captoredEmployer.getEmail()).isEqualTo(request.email());
    }

    @Test
    void willThrowWhenTryingToUpdateEmployerEmailWhenAlreadyExisting() {
        // Given
        Integer id = 10;
        String oldName = "mario";
        String updateName = "luigi";
        String oldEmail = "mario@example.com";
        String updateEmail = "luigi@example.com";

        EmployerUpdateRequest request = new EmployerUpdateRequest(
                updateName,
                updateEmail
        );

        Employer employer = Employer.builder()
                .id(id)
                .name(oldName)
                .email(oldEmail)
                .build();

        when(employerDao.selectEmployerById(id)).thenReturn(
                Optional.of(employer)
        );
        when(employerDao.existPersonWithEmail(updateEmail)).thenReturn(true);

        // When
        assertThatThrownBy(() -> underTest.updateEmployer(id, request))
                .isInstanceOf(DuplicateResourceException.class)
                .hasMessage(
                        "Email already taken"
                );

        // Then
        verify(employerDao, never()).updateEmployer(any());
    }

    @Test
    void willThrowWhenTryingToUpdateEmployerWithNoChanges() {
        // Given
        Integer id = 10;
        String oldName = "mario";
        String oldEmail = "marioi@example.com";

        EmployerUpdateRequest request = new EmployerUpdateRequest(
                oldName,
                oldEmail
        );

        Employer employer = Employer.builder()
                .id(id)
                .name(oldName)
                .email(oldEmail)
                .build();

        when(employerDao.selectEmployerById(id)).thenReturn(
                Optional.of(employer)
        );

        // When
        assertThatThrownBy(() -> underTest.updateEmployer(id, request))
                .isInstanceOf(RequestValidationException.class)
                .hasMessage(
                        "No changes detected"
                );

        // Then
        verify(employerDao, never()).updateEmployer(any());
    }
}