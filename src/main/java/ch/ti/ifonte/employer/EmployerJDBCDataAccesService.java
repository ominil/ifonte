package ch.ti.ifonte.employer;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("jdbc")
@AllArgsConstructor
public class EmployerJDBCDataAccesService implements EmployerDao {

    private final JdbcTemplate jdbcTemplate;
    private final EmployerRowMapper employerRowMapper;

    @Override
    public List<Employer> selectAllEmployers() {
        var sql = """
                    SELECT id, name, email
                    FROM employer
                """;


       return jdbcTemplate.query(sql, employerRowMapper);
    }

    @Override
    public Optional<Employer> getEmployerById(Integer employerId) {

        var sql = """
                   SELECT id, name, email
                   FROM employer
                   WHERE id = ?
                """;

        return jdbcTemplate.query(sql, employerRowMapper, employerId).stream()
                .findFirst();
    }

    @Override
    public void insertEmployer(Employer employer) {
        var sql = """
                    INSERT INTO employer(name, email)
                    VALUES (?, ?)
                """;

        jdbcTemplate.update(sql, employer.getName(), employer.getEmail());
    }

    @Override
    public void deleteEmployerById(Integer id) {
        var sql = """
                DELETE
                FROM employer
                WHERE id = ?
                """;

        jdbcTemplate.update(sql, id);
    }

    @Override
    public boolean existPersonWithEmail(String email) {

        var sql = """
                SELECT count(id)
                FROM employer
                WHERE email = ?
                """;

        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, email);

        return count != null && count > 0;
    }

    @Override
    public boolean existEmployerById(Integer id) {
        var sql = """
                SELECT count(id)
                FROM employer
                WHERE id = ?
                """;

        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);

        return count != null && count > 0;
    }

    @Override
    public void updateEmployer(Employer employer) {
        if (employer.getName() != null) {
            String  sql = "UPDATE employer SET name = ? WHERE id = ?";

            jdbcTemplate.update(sql, employer.getName(), employer.getId());
        }

        if (employer.getEmail() != null) {
            String  sql = "UPDATE employer SET email = ? WHERE id = ?";

            jdbcTemplate.update(sql, employer.getEmail(), employer.getId());
        }
    }
}
