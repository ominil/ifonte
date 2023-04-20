package ch.ti.ifonte.customer;

public record CustomerRegistrationRequest(
        String name,
        String email,
        String password
) {}
