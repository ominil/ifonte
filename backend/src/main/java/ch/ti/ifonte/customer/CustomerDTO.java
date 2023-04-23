package ch.ti.ifonte.customer;

import java.util.List;

public record CustomerDTO(
        Integer id,
        String name,
        String email,
        List<String> roles,
        String username
) {
}
