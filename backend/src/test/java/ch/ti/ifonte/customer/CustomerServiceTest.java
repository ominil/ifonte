package ch.ti.ifonte.customer;

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
class CustomerServiceTest {

    private CustomerService underTest;

    @Mock
    private CustomerDao customerDao;

    @Mock
    private PasswordEncoder passwordEncoder;

    private final CustomerDTOMapper customerDTOMapper = new CustomerDTOMapper();

    @BeforeEach
    void setUp() {
        underTest = new CustomerService(customerDao, passwordEncoder, customerDTOMapper);
    }

    @Test
    void getAllEmployee() {
        // When
        underTest.getAllCustomers();

        // Then
        verify(customerDao).selectAllCustomers();
    }

    @Test
    void getCustomer() {
        // Given
        Integer id = 10;
        Customer customer = Customer.builder()
                .id(id)
                .name("bob")
                .email("example@domain.com")
                .build();

        CustomerDTO expected = customerDTOMapper.apply(customer);

        when(customerDao.selectCustomerById(id)).thenReturn(
            Optional.of(customer)
        );

        // When
        CustomerDTO actual = underTest.getCustomerById(id);

        // Then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void willThrowWhenGetCustomerReturnsEmptyOptional() {
        // Given
        Integer id = 10;
        when(customerDao.selectCustomerById(id)).thenReturn(
                Optional.empty()
        );

        // When
        assertThatThrownBy(() -> underTest.getCustomerById(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage(
                        "Customer with id [%s] not found".formatted(id)
                );

        // Then
    }

    @Test
    void addCustomer() {
        // Given
        String email = "foo@example.com";

        when(customerDao.existCustomerWithEmail(email)).thenReturn(false);

        CustomerRegistrationRequest request = new CustomerRegistrationRequest(
                "foo", email,
                "password");

        String passwordHash = "YFOEHPOAJCPO#§";
        when(passwordEncoder.encode(request.password())).thenReturn(passwordHash);

        // When
        underTest.addCustomer(request);

        // Then
        ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(Customer.class);
        verify(customerDao).insertCustomer(customerArgumentCaptor.capture());

        Customer captoredCustomer = customerArgumentCaptor.getValue();

        assertThat(captoredCustomer.getId()).isNull();
        assertThat(captoredCustomer.getName()).isEqualTo(request.name());
        assertThat(captoredCustomer.getEmail()).isEqualTo(request.email());
        assertThat(captoredCustomer.getPassword()).isEqualTo(passwordHash);
    }

    @Test
    void willThrowWhenEmailExistsWhenAddingCustomer() {
        // Given
        String email = "foo@example.com";

        when(customerDao.existCustomerWithEmail(email)).thenReturn(true);

        CustomerRegistrationRequest request = new CustomerRegistrationRequest(
                "foo", email,
                "password");

        // When
        assertThatThrownBy(() -> underTest.addCustomer(request))
                .isInstanceOf(DuplicateResourceException.class)
                .hasMessage(
                        "Email already taken"
                );

        // Then
        verify(customerDao, never()).insertCustomer(any());
    }

    @Test
    void deleteCustomerById() {
        // Given
        Integer id = 10;
        when(customerDao.existCustomerById(id)).thenReturn(true);


        // When
        underTest.deleteCustomerById(id);

        // Then
        verify(customerDao).deleteCustomerById(id);
    }

    @Test
    void willThrowWhenCustomerNotFoundOnDelete() {
        // Given
        Integer id = 10;
        when(customerDao.existCustomerById(id)).thenReturn(false);

        // When
        assertThatThrownBy(() -> underTest.deleteCustomerById(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage(
                        "Customer with id [%s] not found".formatted(id)
                );

        // Then
        verify(customerDao, never()).deleteCustomerById(any());
    }

    @Test
    void canUpdateAllCustomerProperties() {
        // Given
        Integer id = 10;
        String email = "mario@example.com";
        CustomerUpdateRequest request = new CustomerUpdateRequest(
                "mario",
                email
        );

        Customer customer = Customer.builder()
                .id(id)
                .name("luigi")
                .email("luigi@example.com")
                .build();

        when(customerDao.selectCustomerById(id)).thenReturn(
                Optional.of(customer)
        );
        when(customerDao.existCustomerWithEmail(email)).thenReturn(false);


        // When
        underTest.updateCustomer(id, request);


        // Then
        ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(Customer.class);
        verify(customerDao).updateCustomer(customerArgumentCaptor.capture());

        Customer captoredCustomer = customerArgumentCaptor.getValue();

        assertThat(captoredCustomer.getId()).isEqualTo(id);
        assertThat(captoredCustomer.getName()).isEqualTo(request.name());
        assertThat(captoredCustomer.getEmail()).isEqualTo(request.email());
    }

    @Test
    void canUpdateOnlyCustomerName() {
        // Given
        Integer id = 10;
        String email = "luigi@example.com";

        CustomerUpdateRequest request = new CustomerUpdateRequest(
                "mario",
                null
        );

        Customer customer = Customer.builder()
                .id(id)
                .name("luigi")
                .email(email)
                .build();

        when(customerDao.selectCustomerById(id)).thenReturn(
                Optional.of(customer)
        );


        // When
        underTest.updateCustomer(id, request);


        // Then
        ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(Customer.class);
        verify(customerDao).updateCustomer(customerArgumentCaptor.capture());

        Customer captoredCustomer = customerArgumentCaptor.getValue();

        assertThat(captoredCustomer.getId()).isEqualTo(id);
        assertThat(captoredCustomer.getName()).isEqualTo(request.name());
        assertThat(captoredCustomer.getEmail()).isEqualTo(email);
    }

    @Test
    void canUpdateOnlyCustomerEmail() {
        // Given
        Integer id = 10;
        String name = "luigi";
        String email = "luigi@example.com";
        CustomerUpdateRequest request = new CustomerUpdateRequest(
                null,
                email
        );

        Customer customer = Customer.builder()
                .id(id)
                .name(name)
                .email("mario@example.com")
                .build();

        when(customerDao.selectCustomerById(id)).thenReturn(
                Optional.of(customer)
        );
        when(customerDao.existCustomerWithEmail(email)).thenReturn(false);


        // When
        underTest.updateCustomer(id, request);


        // Then
        ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(Customer.class);
        verify(customerDao).updateCustomer(customerArgumentCaptor.capture());

        Customer captoredCustomer = customerArgumentCaptor.getValue();

        assertThat(captoredCustomer.getId()).isEqualTo(id);
        assertThat(captoredCustomer.getName()).isEqualTo(name);
        assertThat(captoredCustomer.getEmail()).isEqualTo(request.email());
    }

    @Test
    void willThrowWhenTryingToUpdateCustomerEmailWhenAlreadyExisting() {
        // Given
        Integer id = 10;
        String oldName = "mario";
        String updateName = "luigi";
        String oldEmail = "mario@example.com";
        String updateEmail = "luigi@example.com";

        CustomerUpdateRequest request = new CustomerUpdateRequest(
                updateName,
                updateEmail
        );

        Customer customer = Customer.builder()
                .id(id)
                .name(oldName)
                .email(oldEmail)
                .build();

        when(customerDao.selectCustomerById(id)).thenReturn(
                Optional.of(customer)
        );
        when(customerDao.existCustomerWithEmail(updateEmail)).thenReturn(true);

        // When
        assertThatThrownBy(() -> underTest.updateCustomer(id, request))
                .isInstanceOf(DuplicateResourceException.class)
                .hasMessage(
                        "Email already taken"
                );

        // Then
        verify(customerDao, never()).updateCustomer(any());
    }

    @Test
    void willThrowWhenTryingToUpdateCustomerWithNoChanges() {
        // Given
        Integer id = 10;
        String oldName = "mario";
        String oldEmail = "marioi@example.com";

        CustomerUpdateRequest request = new CustomerUpdateRequest(
                oldName,
                oldEmail
        );

        Customer customer = Customer.builder()
                .id(id)
                .name(oldName)
                .email(oldEmail)
                .build();

        when(customerDao.selectCustomerById(id)).thenReturn(
                Optional.of(customer)
        );

        // When
        assertThatThrownBy(() -> underTest.updateCustomer(id, request))
                .isInstanceOf(RequestValidationException.class)
                .hasMessage(
                        "No changes detected"
                );

        // Then
        verify(customerDao, never()).updateCustomer(any());
    }
}