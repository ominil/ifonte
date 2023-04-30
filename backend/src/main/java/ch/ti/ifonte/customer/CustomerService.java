package ch.ti.ifonte.customer;

import ch.ti.ifonte.exception.DuplicateResourceException;
import ch.ti.ifonte.exception.RequestValidationException;
import ch.ti.ifonte.exception.ResourceNotFoundException;
import ch.ti.ifonte.roles.Role;
import ch.ti.ifonte.roles.RoleService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    private final CustomerDao customerDao;
    private final RoleService roleService;

    private final PasswordEncoder passwordEncoder;
    private final CustomerDTOMapper customerDTOMapper;

    public CustomerService(@Qualifier("customer_jpa") CustomerDao customerDao, RoleService roleService, PasswordEncoder passwordEncoder, CustomerDTOMapper customerDTOMapper) {
        this.customerDao = customerDao;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        this.customerDTOMapper = customerDTOMapper;
    }
    public List<CustomerDTO> getAllCustomers() {
        return customerDao.selectAllCustomers()
                .stream()
                .map(customerDTOMapper)
                .collect(Collectors.toList());
    }

    public CustomerDTO getCustomerById(Integer customerId) {
        return customerDao.selectCustomerById(customerId)
                .map(customerDTOMapper)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Customer with id [%s] not found".formatted(customerId)
                        )
                );
    }

    public void addCustomer(CustomerRegistrationRequest customerRegistrationRequest) {
        // check if email exists
        if (customerDao.existCustomerWithEmail(customerRegistrationRequest.email())) {
            throw new DuplicateResourceException(
                    "Email already taken"
            );
        }

        Role roleUser = roleService.selectRoleByName("ROLE_USER");

        // add customer
        Customer customer = Customer.builder()
                .email(customerRegistrationRequest.email())
                .name(customerRegistrationRequest.name())
                .password(passwordEncoder.encode(customerRegistrationRequest.password()))
                .roles(Set.of(roleUser))
                .build();

        customerDao.insertCustomer(customer);
    }


    public void deleteCustomerById(Integer customerId) {

        if (!customerDao.existCustomerById(customerId)) {
            throw new ResourceNotFoundException(
                    "Customer with id [%s] not found".formatted(customerId)
            );
        }

        customerDao.deleteCustomerById(customerId);
    }

    public void updateCustomer(Integer customerId, CustomerUpdateRequest customerUpdateRequest) {

        Customer customer = customerDao.selectCustomerById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException(
                                "Customer with id [%s] not found".formatted(customerId)
                        )
                );

        boolean changes = false;

        if (customerUpdateRequest.name() != null && !customerUpdateRequest.name().equals(customer.getName())) {
            customer.setName(customerUpdateRequest.name());
            changes = true;
        }

        if (customerUpdateRequest.email() != null && !customerUpdateRequest.email().equals(customer.getEmail())) {
            if (customerDao.existCustomerWithEmail(customerUpdateRequest.email())) {
                throw new DuplicateResourceException(
                        "Email already taken"
                );
            }

            customer.setEmail(customerUpdateRequest.email());
            changes = true;
        }

        if (!changes) {
            throw new RequestValidationException("No changes detected");
        }

        customerDao.updateCustomer(customer);
    }
}
