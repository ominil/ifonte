package ch.ti.ifonte.customer;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("jdbc")
@AllArgsConstructor
public class CustomerJDBCDataAccessService implements CustomerDao {

    private final JdbcTemplate jdbcTemplate;
    private final CustomerRowMapper customerRowMapper;

    @Override
    public List<Customer> selectAllCustomers() {
        var sql = """
                    SELECT id, name, email, password
                    FROM customer
                """;


       return jdbcTemplate.query(sql, customerRowMapper);
    }

    @Override
    public Optional<Customer> selectCustomerById(Integer customerId) {

        var sql = """
                   SELECT id, name, email, password
                   FROM customer
                   WHERE id = ?
                """;

        return jdbcTemplate.query(sql, customerRowMapper, customerId).stream()
                .findFirst();
    }

    @Override
    public Optional<Customer> selectCustomerByEmail(String email) {

        var sql = """
                   SELECT id, name, email, password
                   FROM customer
                   WHERE email = ?
                """;

        return jdbcTemplate.query(sql, customerRowMapper, email).stream()
                .findFirst();
    }

    @Override
    public void insertCustomer(Customer customer) {
        var sql = """
                    INSERT INTO customer(name, email, password)
                    VALUES (?, ?, ?)
                """;

        jdbcTemplate.update(sql, customer.getName(), customer.getEmail(), customer.getPassword());
    }

    @Override
    public void deleteCustomerById(Integer id) {
        var sql = """
                DELETE
                FROM customer
                WHERE id = ?
                """;

        jdbcTemplate.update(sql, id);
    }

    @Override
    public boolean existCustomerWithEmail(String email) {

        var sql = """
                SELECT count(id)
                FROM customer
                WHERE email = ?
                """;

        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, email);

        return count != null && count > 0;
    }

    @Override
    public boolean existCustomerById(Integer id) {
        var sql = """
                SELECT count(id)
                FROM customer
                WHERE id = ?
                """;

        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);

        return count != null && count > 0;
    }

    @Override
    public void updateCustomer(Customer customer) {
        if (customer.getName() != null) {
            String  sql = "UPDATE customer SET name = ? WHERE id = ?";

            jdbcTemplate.update(sql, customer.getName(), customer.getId());
        }

        if (customer.getEmail() != null) {
            String  sql = "UPDATE customer SET email = ? WHERE id = ?";

            jdbcTemplate.update(sql, customer.getEmail(), customer.getId());
        }
    }
}
