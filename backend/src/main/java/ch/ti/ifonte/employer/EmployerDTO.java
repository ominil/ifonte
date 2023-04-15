package ch.ti.ifonte.employer;

import java.util.List;

public record EmployerDTO(
        Integer id,
        String name,
        String email,
        List<String> roles,
        String username
) {
}
