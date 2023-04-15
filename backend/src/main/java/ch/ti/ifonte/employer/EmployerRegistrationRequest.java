package ch.ti.ifonte.employer;

public record EmployerRegistrationRequest (
        String name,
        String email,
        String password
) {}
