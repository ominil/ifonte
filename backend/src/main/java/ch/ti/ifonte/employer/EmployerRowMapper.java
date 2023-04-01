package ch.ti.ifonte.employer;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class EmployerRowMapper implements RowMapper<Employer> {

    @Override
    public Employer mapRow(ResultSet rs, int rowNum) throws SQLException {

        return Employer.builder()
                .id(rs.getInt("id"))
                .name(rs.getString("name"))
                .email(rs.getString("email"))
                .build();
    }
}
