package ch.ti.ifonte.customer;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

class CustomerJPADataAccessServiceTest {

    private CustomerJPADataAccessService underTest;
    @Mock
    private CustomerRepository customerRepository;
    private AutoCloseable autoCloseable;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new CustomerJPADataAccessService(customerRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void selectAllCustomers() {
        // When
        underTest.selectAllCustomers();

        // Then
        verify(customerRepository)
                .findAll();
    }

    @Test
    void selectCustomerById() {
        // Given
        Integer id = 1;
        // When
        underTest.selectCustomerById(id);

        // Then
        verify(customerRepository)
                .findById(id);
    }

    @Test
    void insertCustomer() {
        // Given
        Customer customer = new Customer();

        // When
        underTest.insertCustomer(customer);

        // Then
        verify(customerRepository)
                .save(customer);
    }

    @Test
    void deleteCustomerById() {
        // Given
        Integer id = 1;

        // When
        underTest.deleteCustomerById(id);

        // Then
        verify(customerRepository)
                .deleteById(id);
    }

    @Test
    void existPersonWithEmail() {
        // Given
        String email = "example@domain.com";

        // When
        underTest.existCustomerWithEmail(email);

        // Then
        verify(customerRepository)
                .existsCustomerByEmail(email);
    }

    @Test
    void existCustomerById() {
        // Given
        Integer id = 1;

        // When
        underTest.existCustomerById(id);

        // Then
        verify(customerRepository)
                .existsById(id);
    }

    @Test
    void updateCustomer() {
        // Given
        Customer customer = new Customer();

        // When
        underTest.updateCustomer(customer);

        // Then
        verify(customerRepository)
                .save(customer);
    }
}