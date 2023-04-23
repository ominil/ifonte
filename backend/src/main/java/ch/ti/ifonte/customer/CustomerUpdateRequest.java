package ch.ti.ifonte.customer;

public record CustomerUpdateRequest(
        String name,
        String email
) {
}
