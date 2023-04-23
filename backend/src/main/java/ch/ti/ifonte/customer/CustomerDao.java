package ch.ti.ifonte.customer;

import java.util.List;
import java.util.Optional;

public interface CustomerDao {

    List<Customer> selectAllCustomers();
    Optional<Customer> selectCustomerById(Integer customerId);

    Optional<Customer> selectCustomerByEmail(String email);

    void insertCustomer(Customer customer);

    void deleteCustomerById(Integer id);

    boolean existCustomerWithEmail(String email);

    boolean existCustomerById(Integer id);

    void updateCustomer(Customer customer);
    
}
