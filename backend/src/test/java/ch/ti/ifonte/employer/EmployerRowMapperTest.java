package ch.ti.ifonte.employer;

import org.junit.jupiter.api.Test;


import java.sql.ResultSet;
import java.sql.SQLException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import static org.assertj.core.api.Assertions.*;

class EmployerRowMapperTest {


    @Test
    void mapRow() throws SQLException {
        // Given
        EmployerRowMapper employerRowMapper = new EmployerRowMapper();
        ResultSet resultSet = mock(ResultSet.class);

        int id = 10;
        when(resultSet.getInt("id")).thenReturn(id);
        String name = "mario";
        when(resultSet.getString("name")).thenReturn(name);
        String email = "mario@example.com";
        when(resultSet.getString("email")).thenReturn(email);

        // When
        Employer actual = employerRowMapper.mapRow(resultSet, 1);

        // Then
        Employer expected = Employer.builder()
                .id(id)
                .name(name)
                .email(email)
                .build();

        assertThat(actual).isEqualTo(expected);
    }
}